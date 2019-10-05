package com.asrm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asrm.model.Emp;
import com.asrm.model.Lead;
import com.asrm.services.LeadStatic;
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
		HttpSession session = httpSessionFactory.getObject();
		Emp e = (Emp) session.getAttribute("emp");
		String lid = e.getEmp_Id();
		model.addAttribute("leadServices", service.leadRepository);
		model.addAttribute("RMs",service.empRepository.ListRm(lid));
		return "leader/home";
	}
	
	@RequestMapping("/leader/report/{RmId}")
	public void leadeReport(Model model,HttpServletResponse response,@PathVariable("RmId") String id, @RequestParam String daterange) throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.now();
		String[] date=daterange.split("-");
		String sdate=date[0].replace('/','-');
		String edate=date[1].replace('/','-');
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\""+id+"-"+dtf.format(localDate)+".csv\"");
		List<Lead> leads = service.leadRepository.exportCsv(sdate, edate, id);
		
		OutputStream outputStream = response.getOutputStream(); 
		String column ="Name,Mobile No,Lead Source,Lead Status,Meeting Type,Margin,Expected Revenue,Followup date,Address,Description,Update Time"; 
		outputStream.write(column.getBytes());
		for(Lead l:leads) {
			 String outputResult = "\n"+l.getFname()+" "+l.getLname()+","+l.getCno()+","+l.getLeadSource()+","+l.getLeadStatus()+","+l.getMeetType()+","+l.getMargin()+","+l.getExp_rev()+","+l.getFollow_date()+","+l.getAddress().replace(',', '-')+","+l.getDescription().replace(',', '-')+","+l.getFormatDateTime();
		     outputStream.write(outputResult.getBytes());
		}
		response.getOutputStream().close();
	}
	
	boolean checksession() {
		HttpSession session = httpSessionFactory.getObject();
		if (session.getAttribute("emp") == null) {
			return true;
		}
		return false;
	}

}
