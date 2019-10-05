package com.asrm.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.asrm.model.Emp;
import com.asrm.model.Lead;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface EmpRepository  extends JpaRepository<Emp, Long>  {
	 @Query("SELECT t FROM Emp t WHERE t.Emp_Id = ?1 AND t.password = ?2")
	 Emp findByEmpidAndPass(String fooIn, String bar);
	 @Query(value ="SELECT emp_id FROM employee WHERE designation=?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
	 String findByNextEmpid(String id);
	 
	 @Query(value ="SELECT * FROM `employee` WHERE `designation`=?1", nativeQuery = true)
	 List<Emp> ListHighEmp(String desg);
	 
	 @Query(value="SELECT * FROM `employee` WHERE `designation`='LD'",nativeQuery = true)
	 List<Emp> ListLeader();
	 
	 @Query(value="SELECT * FROM `employee` WHERE `manager_id`=?1",nativeQuery = true)
	 List<Emp> ListRm(String lid);
	 
	 
	 @Query(value="select COUNT(*) FROM lead where emp_id in (select emp_id FROM employee WHERE manager_id=?1) AND DATE_FORMAT(follow_date, '%Y-%m-%d')  = CURDATE()",nativeQuery = true)
	 int leadcount(String lid);
	 
	 @Query(value="select COUNT(*) FROM lead where emp_id in (select emp_id FROM employee WHERE manager_id=?1) AND lead_status='DN' AND YEAR(update_time) = YEAR(CURRENT_DATE()) AND  MONTH(update_time) = MONTH(CURRENT_DATE()) ",nativeQuery = true)
	 int MonthLeadDone(String lid);
}	
