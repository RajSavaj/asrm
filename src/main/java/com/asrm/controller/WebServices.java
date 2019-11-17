package com.asrm.controller;

import com.asrm.model.JsonObject;
import com.asrm.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebServices {
	@Autowired
	private MainService service;
	
	@PostMapping("/api/getEmpCode")
    public ResponseEntity<?> getEmpCode(@RequestParam String role) {
		List<String> users=null;
		JsonObject jsonObject=new JsonObject();
		jsonObject.setEmps(service.ListHighEmp(role));
		jsonObject.setNewempid(service.findByNextEmpid(role));
	    return ResponseEntity.ok(jsonObject);
	}

    @PostMapping("/api/checkUser")
    public ResponseEntity<?> checkUser(@RequestParam String cno) {
        return ResponseEntity.ok(service.leadRepository.checkUser(cno));
    }

    @PostMapping("/api/checkEmp")
    public ResponseEntity<?> checkEmp(@RequestParam String cno) {
        return ResponseEntity.ok(service.empRepository.checkEmp(cno));
    }

}
