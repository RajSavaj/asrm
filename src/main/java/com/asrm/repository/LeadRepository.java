package com.asrm.repository;

import com.asrm.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SuppressWarnings("ALL")
public interface LeadRepository extends JpaRepository<Lead, Long> {
	 @Query(value ="SELECT *  FROM lead  WHERE YEAR(update_time) = YEAR(CURRENT_DATE()) AND  MONTH(update_time) = MONTH(CURRENT_DATE()) AND lead_status='DN' AND emp_id= ?1 ", nativeQuery = true)
	List<Lead> monthclose(String empid);
	 
	 @Query(value="SELECT * FROM lead WHERE  DATE_FORMAT(follow_date, '%Y-%m-%d')  = CURDATE() AND  emp_id= ?1",nativeQuery = true)
	 List<Lead> todaylead(String empid);
	 
	 @Query(value ="SELECT *  FROM lead  WHERE YEAR(update_time) = YEAR(CURRENT_DATE()) AND  MONTH(update_time) = MONTH(CURRENT_DATE()) AND emp_id= ?1 ORDER BY (CASE WHEN `lead_status` = 'DN' THEN 1 ELSE 0 END)", nativeQuery = true)
	 List<Lead> expect_Rev(String empid);
	 
	 @Query(value ="SELECT *  FROM lead  WHERE DATE(update_time) BETWEEN  DATE_FORMAT( STR_TO_DATE(?1, '%d-%m-%Y') , '%Y/%m/%d')  AND  DATE_FORMAT( STR_TO_DATE(?2, '%d-%m-%Y' ) ,'%Y/%m/%d') AND emp_id= ?3", nativeQuery = true)
	 List<Lead> exportCsv(String sdate,String edate,String empid);
	 
	 @Query(value ="SELECT *  FROM lead  WHERE id=?1 ", nativeQuery = true)
	 Lead findByLid(Long id);
	 
	 @Query(value="SELECT * FROM lead WHERE  follow_date BETWEEN DATE_ADD(CURDATE(),INTERVAL 1 DAY) AND DATE_ADD(follow_date, INTERVAL 7 DAY) AND  emp_id=?", nativeQuery = true)
	 List<Lead> featureData(String id);
	
	 @Query(value="SELECT l.*,CONCAT(e.name,' ',e.lname)  FROM lead l,employee e  WHERE e.emp_id=l.emp_id AND DATE(l.update_time) BETWEEN  ?1 AND  ?2 AND e.manager_id=?3",nativeQuery = true)
	 List<Object[]> leadecv(String sdate,String edate,String lid);
	 
	 @Query(value="select COUNT(*) FROM lead where emp_id=?1 AND DATE_FORMAT(follow_date, '%Y-%m-%d')  = CURDATE()",nativeQuery = true)
	 int countRm(String rid);

	 @Query(value="select COUNT(*) FROM lead where emp_id=?1 AND lead_status='DN' AND YEAR(update_time) = YEAR(CURRENT_DATE()) AND  MONTH(update_time) = MONTH(CURRENT_DATE()) ",nativeQuery = true)
	 int countMonthRmLead(String rid);

    @Query(value = "select COUNT(*) FROM lead where emp_id=?1 AND lead_status='PR' AND YEAR(update_time) = YEAR(CURRENT_DATE()) AND  MONTH(update_time) = MONTH(CURRENT_DATE()) ", nativeQuery = true)
    int countPRLead(String rid);

    @Query(value = "select COUNT(*) FROM lead where emp_id=?1 AND lead_status='NI' AND YEAR(update_time) = YEAR(CURRENT_DATE()) AND  MONTH(update_time) = MONTH(CURRENT_DATE()) ", nativeQuery = true)
    int countNILead(String rid);

    @Query(value = "SELECT COUNT(*)  FROM lead  WHERE cno=?1 ", nativeQuery = true)
    int checkUser(String id);
}
