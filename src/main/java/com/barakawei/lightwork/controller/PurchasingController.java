package com.barakawei.lightwork.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;

import com.barakawei.lightwork.domain.DataDict;
import com.barakawei.lightwork.domain.PurchasingDetail;
import com.barakawei.lightwork.domain.SearchForm;
import com.barakawei.lightwork.service.DataDictService;
import com.barakawei.lightwork.service.PurchasingDetailService;
import com.barakawei.lightwork.service.PurchasingService;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.service.PurchasingWorkflowService;

/**
 * 请假控制器，包含保存、启动流程
 *
 * @author barakawei
 */
@Controller
@RequestMapping(value = "/purchasing")
public class PurchasingController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected PurchasingWorkflowService purchasingWorkflowService;

    @Autowired
    protected PurchasingService purchasingService;

    @Autowired
    protected PurchasingDetailService purchasingDetailService;

    @Autowired
    protected DataDictService dataDictService;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {

        return "purchasing/add";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(Purchasing purchasing) {
        purchasingService.createPurchasing(purchasing);
        return this.ajaxDoneClose("添加成功");
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView list(SearchForm sf) {
        ModelAndView mav = new ModelAndView("purchasing/list");
        Page<Purchasing> purchasings = purchasingService.findPurchasings(sf);
        mav.addObject("purchasings", purchasings);
        mav.addObject("searchForm", sf);
        return mav;
    }


    @RequestMapping(value = "/task")
    public ModelAndView task(SearchForm sf) {
        ModelAndView mav = new ModelAndView("purchasing/task");
        String userId = "2";
        Page<Purchasing> purchasings = purchasingService.findPurchasings(sf);
        mav.addObject("purchasings", purchasings);
        mav.addObject("searchForm", sf);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") String id) {
        ModelAndView mav = new ModelAndView("purchasing/edit");
        Purchasing purchasing = purchasingService.findPurchasingById(id);
        mav.addObject("purchasing", purchasing);
        return mav;
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable("id") String id) {
        ModelAndView mav = new ModelAndView("purchasing/show");
        mav.addObject("purchasing", purchasingService.findTaskByCurrentUser(id));
        return mav;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Purchasing purchasing) {
        purchasingService.updatePurchasing(purchasing);
        return this.ajaxDoneSuccess("修改成功");
    }

    @RequestMapping(value = "completePurchasing", method = {RequestMethod.POST})
    public ModelAndView completePurchasing(Purchasing purchasing) {
        purchasingService.complete(purchasing);
        return this.ajaxDoneSuccess("修改成功");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("id") String id) {
        purchasingService.deletePurchasingById(id);
        return this.ajaxDoneSuccess("删除成功");
    }
//	/**
//	 * 读取运行中的流程实例
//	 * @return
//	 */
//  @RequestMapping(value = "list/running")
//  public ModelAndView runningList() {
//    ModelAndView mav = new ModelAndView("/oa/leave/running");
//    List<Leave> results = workflowService.findRunningProcessInstaces();
//    mav.addObject("leaves", results);
//    return mav;
//  }
//
//	/**
//	 * 读取运行中的流程实例
//	 * @return
//	 */
//  @RequestMapping(value = "list/finished")
//  public ModelAndView finishedList() {
//    ModelAndView mav = new ModelAndView("/oa/leave/finished");
//    List<Leave> results = workflowService.findFinishedProcessInstaces();
//    mav.addObject("leaves", results);
//    return mav;
//  }
//
//	/**
//	 * 签收任务
//	 */
//  @RequestMapping(value = "task/claim/{id}")
//  public String claim(@PathVariable("id") String taskId, HttpSession session, RedirectAttributes redirectAttributes) {
//    String userId = UserUtil.getUserFromSession(session).getId();
//    taskService.claim(taskId, userId);
//    redirectAttributes.addFlashAttribute("message", "任务已签收");
//    return "redirect:/oa/leave/list/task";
//  }
//
//	/**
//	 * 读取详细数据
//	 * @param id
//	 * @return
//	 */
//  @RequestMapping(value = "detail/{id}")
//  @ResponseBody
//  public Leave getLeave(@PathVariable("id") Long id) {
//    Leave leave = leaveManager.getLeave(id);
//    return leave;
//  }
//
//	/**
//	 * 读取详细数据
//	 * @param id
//	 * @return
//	 */
//  @RequestMapping(value = "detail-with-vars/{id}/{taskId}")
//  @ResponseBody
//  public Leave getLeaveWithVars(@PathVariable("id") Long id, @PathVariable("taskId") String taskId) {
//    Leave leave = leaveManager.getLeave(id);
//    Map<String, Object> variables = taskService.getVariables(taskId);
//    leave.setVariables(variables);
//    return leave;
//  }
//

}
