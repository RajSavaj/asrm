package com.asrm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
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

import com.asrm.model.Lead;
import com.asrm.services.LeadStatic;
import com.asrm.services.MainService;

@Controller
public class ManagerController {
	@Autowired
	private MainService service;

	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;
	
	@RequestMapping("manager/home")
	public String mangerHome(Model model) {
		if (checksession()) {
			return "redirect:/login";
		}
		 model.addAttribute("empservices", service.empRepository);
		return "manager/home";
	}
	
	@RequestMapping("/manager/report/{leadId}")
	public void leadeReport(Model model,HttpServletResponse response,@PathVariable("leadId") String id, @RequestParam String daterange) throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.now();
		String[] date=daterange.split("-");
		String sdate=date[0].replace('/','-');
		String edate=date[1].replace('/','-');
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\""+id+"-"+dtf.format(localDate)+".csv\"");
		
		List<Object[]> objects=service.leadRepository.leadecv(sdate, edate, id);
		
		OutputStream outputStream = response.getOutputStream(); 
		String column ="RM Name,Name,Mobile No,Lead Source,Lead Status,Meeting Type,Margin,Expected Revenue,Followup date,Address,Description,Update Time"; 
		outputStream.write(column.getBytes());
		for (Object[] o : objects) {
			String outputResult="\n"+o[15]+","+o[6]+" "+o[10]+","+" "+o[2]+","+LeadStatic.getLeadSource((String) o[8])+","+LeadStatic.getLeadStatus((String)o[9])+","+LeadStatic.getMeetType((String) o[12])+","+o[11]+","+o[5]+","+o[7]+","+((String) o[1]).replace(',', '-')+","+((String)o[3]).replace(',', '-')+","+LeadStatic.getFormatDateTime((Timestamp) o[14]);
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
/*
0	id
1	address
2	cno
3	description
4	emp_id
5	exp_rev
6	fname
7	follow_date
8	lead_source
9	lead_status
10	lname
11	margin
12	meeting_type
13	joint_leader_name
14	update_time
15	rmname
*/
