package com.asrm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asrm.services.MainService;

@Controller
public class LeaderController {
	
	@Autowired
	private MainService service;

	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;
	
	@RequestMapping("leader/home")
	public String lhome(Model model) {
		if (checksession()) {
			return "redirect:/login";
		}
		return "leader/home";
	}
	
	boolean checksession() {
		HttpSession session = httpSessionFactory.getObject();
		if (session.getAttribute("emp") == null) {
			return true;
		}
		return false;
	}

}
