package com.barakawei.lightwork.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "common/home";
	}

	@RequestMapping(value = "/user/admin", method = RequestMethod.GET)
	public String login() {
		return "common/login";
	 }
}
