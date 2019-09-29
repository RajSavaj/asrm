package com.asrm.services;

import java.util.List;

import javax.validation.Valid;

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
	
	public String findByNextEmpid(String desg)
	{
		String empid=empRepository.findByNextEmpid(desg);
		if(desg.equals("RM"))
		{
			if(empid==null)
			{
				return "V01";
			}
			empid=empid.replaceAll("[^0-9]", "");
			return "V"+String.format("%02d",Integer.parseInt(empid)+1);
		}
		if(desg.equals("LD"))
		{
			if(empid==null)
			{
				return "L01";
			}
			empid=empid.replaceAll("[^0-9]", "");
			return "L"+String.format("%02d",Integer.parseInt(empid)+1);
		}
		if(desg.equals("MR"))
		{
			if(empid==null)
			{
				return "M01";
			}
			empid=empid.replaceAll("[^0-9]", "");
			return "M"+String.format("%02d",Integer.parseInt(empid)+1);
		}
		return "";
	}
	public List<Emp> ListHighEmp(String desg)
	{
		if(desg.equals("RM"))
		{
			return empRepository.ListHighEmp("LD");
		}
		if(desg.equals("LD"))
		{
			return empRepository.ListHighEmp("MR");
		}
		if(desg.equals("MR"))
		{
			return empRepository.ListHighEmp("ADM");
		}
		return null;
	}

	public void saveemp(Emp emp) {
		empRepository.save(emp);
	}
}
