// add by liudan 2012-12-25 start
package com.barakawei.lightwork.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.barakawei.lightwork.domain.SearchForm;
import com.barakawei.lightwork.domain.User;

import com.barakawei.lightwork.service.RoleService;
import com.barakawei.lightwork.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liudan
 * @date 2012-11-25
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;


	// private String passwordConfirmation;

	@RequestMapping(value = "/list")
	public ModelAndView list(SearchForm sf) {
		ModelAndView mav = new ModelAndView();
        Page<User> users= userService.findUsers(sf);
        mav.addObject("users", users);
        mav.addObject("searchForm", sf);
		mav.setViewName("user/list");
		return mav;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView();
        mav.addObject("roles",roleService.findAll());
		mav.setViewName("user/add");
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(User user) {
		userService.createUser(user,true);
        return this.ajaxDoneClose("添加成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", userService.findUserById(id));
		mav.setViewName("/user/show");
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView();
		User user = userService.findUserById(id);
		mav.addObject("user", user);
        mav.addObject("roles",roleService.findAll());
		mav.setViewName("/user/edit");
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("type", "edit_self");
		mav.setViewName("user/edit");
		return mav;
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public ModelAndView update(@PathVariable("id") String id, User user) {
		userService.updateUser(user,true);
        return this.ajaxDoneSuccess("修改成功");
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	public ModelAndView delete(@PathVariable("id") String id) {
		userService.deleteUserById(id,true);
        return this.ajaxDoneSuccess("删除成功");
	}

}
// add by liudan 2012-12-25 end