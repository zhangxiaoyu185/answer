package com.xiaoyu.lingdian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/")
public class IndexController extends BaseController {

	/**
	 * 跳转到业务登录页
	 **/
	@ResponseBody
	@RequestMapping("")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("login");
		return  model;
	}
}