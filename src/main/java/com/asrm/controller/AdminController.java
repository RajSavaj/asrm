package com.asrm.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asrm.model.Emp;
import com.asrm.services.MainService;

@Controller
public class AdminController {
	@Autowired
	private MainService service;

	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;
	
	@RequestMapping("admin/home")
	public String adminhome(Model model) {
		if (checksession()) {
			return "redirect:/login";
		}
		model.addAttribute("emp", new Emp());
		return "admin/home";
	}
	
	@PostMapping("/admin/saveemp")
	public String saveemp(@Valid Emp emp, BindingResult bindingResult, Model model, RedirectAttributes rm) {
		if (checksession()) {
			return "redirect:/login";
		}
		System.out.println(emp.getEmp_Id());
		service.saveemp(emp);
		rm.addFlashAttribute("Success", "Employee successfully saved");
		return "redirect:/admin/home";
	}
	
	boolean checksession() {
		HttpSession session = httpSessionFactory.getObject();
		if (session.getAttribute("emp") == null) {
			return true;
		}
		return false;
	}
}
