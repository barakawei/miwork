package com.barakawei.lightwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("home")
public class HomeController {

	@RequestMapping(value = "/user/myjsp", method = RequestMethod.GET)
	public String myJsp() {

		return "MyJsp";
	}

	@RequestMapping(value = "/user/admin", method = RequestMethod.GET)
	public String admin() {

		return "admin";
	}
}
