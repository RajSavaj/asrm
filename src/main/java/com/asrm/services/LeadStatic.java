package com.asrm.services;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;    

public class LeadStatic {
	public static String getLeadSource(String Lead_source)
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
	
	public static String getLeadStatus(String Lead_status)
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
	
	public static String getMeetType(String Meeting_type)
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
		else if(Meeting_type.equals("FM"))
		{
			return "Fresh Meeting";
		}
		else{
			return "Service";
		}
	}
	
	public static String getFormatDateTime(Timestamp update_time)
	{
		SimpleDateFormat outSDF = new SimpleDateFormat("dd-MM-yyyy");
        Date sd = new Date(update_time.getTime());
        return outSDF.format(sd);
	}

	public static String ConvertDate(String in_date)
	{
		SimpleDateFormat inSDF = new SimpleDateFormat("dd-mm-yyyy");
		SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			if(!in_date.equals(""))
			{
				date = inSDF.parse(in_date);
			}
			else
			{
				return "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outSDF.format(date);
	}
}
