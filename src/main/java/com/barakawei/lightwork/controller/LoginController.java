package com.barakawei.lightwork.controller;

import java.util.*;

import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.domain.PurchasingDetail;
import com.barakawei.lightwork.service.PurchasingService;
import com.barakawei.lightwork.util.UserContextUtil;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.barakawei.lightwork.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.barakawei.lightwork.service.UserService;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected PurchasingService purchasingService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("common/home");
        List<Purchasing> purc = purchasingService.findAll();
        List<Purchasing> data = new ArrayList<Purchasing>();
        List<Purchasing> others = new ArrayList<Purchasing>();
        User user = UserContextUtil.getCurrentUser();
        Set<String> piSet = new HashSet<String>();
        List<Task> todoList = taskService.createTaskQuery().taskAssignee(user.getId()).active().list();
        for (Task task : todoList) {
            String pi = task.getProcessInstanceId();
            piSet.add(pi);
        }

        for (Purchasing _p : purc) {
            int pending = 0;
            int complete= 0;
            if (_p.getOngoing()) {
                for (PurchasingDetail _pd : _p.getPds()) {
                    if(_pd.getEndTime() != null){
                        complete ++;
                    }
                    if (piSet.contains(_pd.getProcessInstanceId())) {
                        pending++;
                    }
                }
                _p.setPending(pending);
                _p.setComplete(complete);
                if (_p.getPending() > 0) {
                    data.add(_p);
                } else {
                    others.add(_p);
                }

            }
        }
        mav.addObject("purchasings", data);
        mav.addObject("otherPurchasings", others);
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {

        System.out.println("--------login443");

        return "login";
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String denied() {
        System.out.println("--------denied");
        return "denied";
    }

    @RequestMapping(value = "/timedout", method = RequestMethod.GET)
    public String timedout() {
        return "timedout";
    }


}
