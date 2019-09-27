package com.asrm.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.asrm.model.Emp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface EmpRepository  extends JpaRepository<Emp, Long>  {
	 @Query("SELECT t FROM Emp t WHERE t.Emp_Id = ?1 AND t.password = ?2")
	 Emp findByEmpidAndPass(String fooIn, String bar);
}
