package com.barakawei.lightwork.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.domain.PurchasingDetail;
import com.barakawei.lightwork.service.PurchasingService;
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
    protected PurchasingService purchasingService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("common/home");
        List<Purchasing> purc = purchasingService.findAll();
        List<Purchasing> data = new ArrayList<Purchasing>();
        List<Purchasing> others = new ArrayList<Purchasing>();
        for (Purchasing _p : purc) {
            Purchasing p = purchasingService.findTaskByCurrentUser(_p.getId());
            if (p.getOngoing()) {
                if (CollectionUtils.isNotEmpty(p.getToDoList())) {
                    _p.setToDoList(p.getToDoList());
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
