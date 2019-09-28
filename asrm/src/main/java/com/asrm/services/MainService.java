package com.asrm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asrm.model.Emp;
import com.asrm.model.Lead;
import com.asrm.repository.EmpRepository;
import com.asrm.repository.LeadRepository;

@Service
public class MainService {
	@Autowired
	private EmpRepository empRepository;
	
	@Autowired
	public LeadRepository leadRepository;
	
	public void save(Emp user) {
		
    }
	
	public Emp validateUser(String userid, String password) {
		Emp e=empRepository.findByEmpidAndPass(userid,password);
		return e;
	}
	
	public void saveLead(Lead l)
	{
		leadRepository.save(l);
	}
}
