package com.asrm.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import com.asrm.model.Emp;
import com.asrm.model.JsonObject;
import com.asrm.services.MainService;
import com.fasterxml.jackson.annotation.JsonAlias;

@Controller
public class WebServices {
	@Autowired
	private MainService service;
	
	@PostMapping("/api/getEmpCode")
	public ResponseEntity<?> getEmpCode( @RequestParam String role) {
		List<String> users=null;
		JsonObject jsonObject=new JsonObject();
		jsonObject.setEmps(service.ListHighEmp(role));
		jsonObject.setNewempid(service.findByNextEmpid(role));
	    return ResponseEntity.ok(jsonObject);
	}

}
