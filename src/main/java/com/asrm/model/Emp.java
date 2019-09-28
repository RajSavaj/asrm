package com.asrm.model;
import javax.persistence.*;
import java.util.Collection;
@Entity
@Table(name = "Employee")
public class Emp {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String Name;
	
	private String Lname;
	
	private String Emp_Id;

    private String password;;
    
    private String Email;
    
    private String Mno;
    
    private String Designation;
    
    private String Report_id;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLname() {
		return Lname;
	}

	public void setLname(String lname) {
		Lname = lname;
	}

	public String getEmp_Id() {
		return Emp_Id;
	}

	public void setEmp_Id(String emp_Id) {
		Emp_Id = emp_Id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMno() {
		return Mno;
	}

	public void setMno(String mno) {
		Mno = mno;
	}

	public String getDesignation() {
		return Designation;
	}

	public void setDesignation(String designation) {
		Designation = designation;
	}

	public String getReport_id() {
		return Report_id;
	}

	public void setReport_id(String report_id) {
		Report_id = report_id;
	}
}
