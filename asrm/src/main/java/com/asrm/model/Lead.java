package com.asrm.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Lead")
public class Lead {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String EmpId;
	
	@NotEmpty(message = "Please provide a Firstname")
	private String Fname;
	
	@NotEmpty(message = "Please provide a Lastname")
	private String Lname;
	
	@NotEmpty(message = "Please provide a Contect No")
	private String Cno;
	
	@NotEmpty(message = "Please select Lead source")
	private String Lead_source;
	
	@NotEmpty(message = "Please select Lead Status")
	private String Lead_status;
	
	@NotEmpty(message = "Please provide a Meeting Type")
	private String Meeting_type;
	
	@NotNull(message = "Please provide a Margin")
	private long Margin;
	
	@NotNull(message = "Please provide a Expected Revenue")
	private long Exp_rev;
	
	@NotEmpty(message = "Please provide a Follow Up Date")
	private String Follow_date;
	
	@NotEmpty(message = "Please provide an Address")
	private String Address;
	
	private String Description;
	
	private LocalDateTime update_time;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmpId() {
		return EmpId;
	}
	public void setEmpId(String empId) {
		EmpId = empId;
	}
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		Fname = fname;
	}
	public String getLname() {
		return Lname;
	}
	public void setLname(String lname) {
		Lname = lname;
	}
	public String getCno() {
		return Cno;
	}
	public void setCno(String cno) {
		Cno = cno;
	}
	public String getLead_source() {
		return Lead_source;
	}
	public void setLead_source(String lead_source) {
		Lead_source = lead_source;
	}
	public String getLead_status() {
		return Lead_status;
	}
	public void setLead_status(String lead_status) {
		Lead_status = lead_status;
	}
	public String getMeeting_type() {
		return Meeting_type;
	}
	public void setMeeting_type(String meeting_type) {
		Meeting_type = meeting_type;
	}
	public long getMargin() {
		return Margin;
	}
	public void setMargin(long margin) {
		Margin = margin;
	}
	public long getExp_rev() {
		return Exp_rev;
	}
	public void setExp_rev(long exp_rev) {
		Exp_rev = exp_rev;
	}
	public String getFollow_date() {
		return Follow_date;
	}
	public void setFollow_date(String follow_date) {
		Follow_date = follow_date;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public LocalDateTime getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(LocalDateTime update_time) {
		this.update_time = update_time;
	}
	public String getFormatDateTime()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        String formatDateTime = this.update_time.format(formatter);
        return formatDateTime;
	}
	
	
	public String getLeadSource()
	{
		if(Lead_source.equals("TC")){
			return "Telecalling";
		}
		else if(Lead_source.equals("CC")){
			return "Cold calling";
		}
		else{
			return "References";
		}
	}
	
	public String getLeadStatus()
	{
		if(Lead_status.equals("DN")){
			return "Done";
		}
		else if(Lead_status.equals("PR")){
			return "Prospect";
		}
		else{
			return "Not interested";
		}
	}
	
	public String getMeetType()
	{
		if(Meeting_type.equals("NM")){
			return "New meeting";
		}
		else if(Meeting_type.equals("FP")){
			return "Follow up";
		}
		else if(Meeting_type.equals("JC")){
			return "Joint call";
		}
		else{
			return "Service";
		}
	}
}

