package com.asrm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asrm.model.Emp;
import com.asrm.model.Lead;
import com.asrm.services.MainService;

@Controller
public class MainController {

	@Autowired
	private MainService service;

	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;

	@RequestMapping("home")
	public String Home(Model model,HttpServletResponse response) {
		if (checksession()) {
			return "redirect:/login";
		}
		HttpSession session = httpSessionFactory.getObject();
		Emp e = (Emp) session.getAttribute("emp");
		model.addAttribute("month_closes", service.leadRepository.monthclose(e.getEmp_Id()));
		model.addAttribute("todaymet", service.leadRepository.todaylead(e.getEmp_Id()));
		model.addAttribute("expectedrev", service.leadRepository.expect_Rev(e.getEmp_Id()));
		model.addAttribute("feture",service.leadRepository.featureData(e.getEmp_Id()));
		return "home";
	}
	
	@RequestMapping("login")
	public String Login() {
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String showWelcomePage(ModelMap model, @RequestParam String empid, @RequestParam String pass) {
		Emp e = service.validateUser(empid, pass);
		if (e != null) {
			HttpSession session = httpSessionFactory.getObject();
			session.setAttribute("emp", e);
			if(e.getDesignation().equals("RM"))
			{
				return "redirect:/home";
			}
			else if(e.getDesignation().equals("LD"))
			{
				return "redirect:/leader/home";
			}
			else if(e.getDesignation().equals("MR"))
			{
				return "redirect:/manager/home";
			}
			else
			{
				return "redirect:/admin/home";
			}
		}
		model.addAttribute("errorMessage", "Please Enter Valid Username And Password");
		return "login";
	}

	@RequestMapping("lead")
	public String Lead(Model model,HttpServletResponse response) {
		if (checksession()) {

			return "redirect:/login";
		}
		model.addAttribute("lead", new Lead());
		return "lead";
	}

	@PostMapping("lead")
	public String Savelead(@Valid Lead lead, BindingResult bindingResult, Model model, RedirectAttributes rm) {
		if (checksession()) {
			return "redirect:/login";
		}
		if (bindingResult.hasErrors()) {
			return "lead";
		}
		HttpSession session = httpSessionFactory.getObject();
		Emp e = (Emp) session.getAttribute("emp");
		lead.setEmpId(e.getEmp_Id());
		service.saveLead(lead);
		rm.addFlashAttribute("Success", "Your lead successfully saved");
		return "redirect:/lead";
	}
	

	@GetMapping("lead/edit/{leadId}")
    public String addProductToCart(@PathVariable("leadId") Long id,Model model) {
		if (checksession()) {return "redirect:/login";}
		model.addAttribute("lead", new Lead());
		model.addAttribute("lid", id);
		model.addAttribute("ulead",service.leadRepository.findByLid(id));
        return "editlead";
	}
	
	@PostMapping("leadupdate")
	public String updateLead(@Valid Lead lead, BindingResult bindingResult,Model model, RedirectAttributes rm) {
		if (checksession()) {

			return "redirect:/login";
		}
		HttpSession session = httpSessionFactory.getObject();
		Emp e = (Emp) session.getAttribute("emp");
		lead.setEmpId(e.getEmp_Id());
		service.leadRepository.save(lead);
		return "redirect:/home";
	}
	
	@RequestMapping("changepassword")
	public String ChangePass(Model model,HttpServletResponse response) {
		if (checksession()) {
			return "redirect:/login";
		}
		return "changepass";
	}
	
	@RequestMapping(value = "changepassword", method = RequestMethod.POST)
	public String updatePassword(ModelMap model, @RequestParam String pass,RedirectAttributes rm) {
		if (checksession()) {
			return "redirect:/login";
		}
		HttpSession session = httpSessionFactory.getObject();
		Emp e = (Emp) session.getAttribute("emp");
		e.setPassword(pass);
		service.empRepository.save(e);
		rm.addFlashAttribute("Success", "Password successfully updated !");
		return "redirect:/changepassword";
	}
	
	@RequestMapping(value = "LeadExport", method = RequestMethod.POST)
	public void exportCsv(HttpServletRequest request, HttpServletResponse response, @RequestParam String sdate,
			@RequestParam String edate) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.now();
		response.setContentType("text/csv");
		HttpSession session = httpSessionFactory.getObject();
		Emp e = (Emp) session.getAttribute("emp");
		String Filename = e.getEmp_Id();
		response.setHeader("Content-Disposition", "attachment; filename=\""+dtf.format(localDate)+"-"+Filename+".csv\"");
		try {
			List<Lead> leads = service.leadRepository.exportCsv(sdate, edate, Filename);
			
			OutputStream outputStream = response.getOutputStream(); 
			String column ="Name,Mobile No,Lead Source,Lead Status,Meeting Type,Margin,Expected Revenue,Followup date,Address,Description,Update Time"; 
			outputStream.write(column.getBytes());
			for(Lead l:leads) {
				 String outputResult = "\n"+l.getFname()+" "+l.getLname()+","+l.getCno()+","+l.getLeadSource()+","+l.getLeadStatus()+","+l.getMeetType()+","+l.getMargin()+","+l.getExp_rev()+","+l.getFollow_date()+","+l.getAddress().replace(',', '-')+","+l.getDescription().replace(',', '-')+","+l.getFormatDateTime();
			     outputStream.write(outputResult.getBytes());
			}
			response.getOutputStream().close();
			 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	boolean checksession() {
		HttpSession session = httpSessionFactory.getObject();
		if (session.getAttribute("emp") == null) {
			return true;
		}
		return false;
	}
	
	@GetMapping("logout")
    public String logout(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
		response.addHeader("Pragma", "no-cache"); 
		response.addDateHeader ("Expires", 0);
		HttpSession session = httpSessionFactory.getObject();
		session.invalidate();
		return "redirect:/login";
	}
	
}
