package com.asrm.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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
}
