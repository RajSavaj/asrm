package com.asrm.controller;

import com.asrm.model.Emp;
import com.asrm.services.MainService;
import com.google.gson.Gson;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "admin/")
public class AdminController {
	@Autowired
	private MainService service;

	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;

    @RequestMapping("home")
	public String adminhome(Model model) {
		if (checksession()) {
			return "redirect:/login";
		}
		model.addAttribute("emp", new Emp());
        return "admin/home";
    }

    @RequestMapping("showuser")
    public String showuser(Model model) {
        if (checksession()) {
            return "redirect:/login";
        }
        List<Emp> emps = service.empRepository.allEmp();
        Gson gson = new Gson();
        model.addAttribute("emps", emps);
        model.addAttribute("json", gson.toJson(emps.toArray()));
        return "admin/showuser";
    }

    @PostMapping("saveemp")
	public String saveemp(@Valid Emp emp, BindingResult bindingResult, Model model, RedirectAttributes rm) {
		if (checksession()) {
			return "redirect:/login";
		}
		service.saveemp(emp);
		rm.addFlashAttribute("Success", "Employee successfully saved");
		return "redirect:/admin/home";
	}

    @PostMapping("updateemp")
    public String updateemp(@RequestParam Map<String, String> param, Model model, RedirectAttributes rm) {
        if (checksession()) {
            return "redirect:/login";
        }
        Emp e = new Emp();
        e.setId(Long.parseLong(param.get("eid")));
        e.setName(param.get("name"));
        e.setLname(param.get("lname"));
        e.setEmp_Id(param.get("e_code"));
        e.setEmail(param.get("email"));
        e.setMno(param.get("e_number"));
        e.setPassword(param.get("e_pass"));
        e.setDesignation(param.get("deg"));
        e.setManager_id(param.get("deg_per"));
        service.empRepository.save(e);
        return "redirect:/admin/showuser";
    }

    @PostMapping("delete_emp")
    public String deleteemp(@RequestParam String empid, RedirectAttributes rm) {
        if (checksession()) {
            return "redirect:/login";
        }
        service.empRepository.deleteById(Long.parseLong(empid));
        rm.addFlashAttribute("Success", "Employee successfully saved");
        return "redirect:/admin/showuser";
    }

	boolean checksession() {
		HttpSession session = httpSessionFactory.getObject();
        return session.getAttribute("emp") == null;
    }
}
