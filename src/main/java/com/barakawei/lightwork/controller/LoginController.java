package com.barakawei.lightwork.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.barakawei.lightwork.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.barakawei.lightwork.service.UserService;


@Controller
public class LoginController {

	@Autowired
	private UserService userService;

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
