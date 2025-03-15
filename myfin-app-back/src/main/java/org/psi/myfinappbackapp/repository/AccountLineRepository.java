package org.psi.myfinappbackapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.entities.AccountLine;
import org.psi.myfinappbackapp.entities.AccountLineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLineRepository extends JpaRepository<AccountLine, AccountLineId>{
	
	
	// Je ne sais pas FUNCTION('YEAR', al.date), FUNCTION('MONTH', al.date) ne ramène pas un integer
	 
	 String jql = "SELECT new org.psi.myfinappbackapp.dto.AccountDateSumDTO("
		 		+ "YEAR(al.accountLineId.date), MONTH(al.accountLineId.date), SUM(al.solde.amount), al.solde.currency" +
				 ") " +
	            "FROM AccountLine al " +
	            "WHERE al.accountLineId.date >= :startDate AND al.accountLineId.date <= :endDate " +
	            "GROUP BY YEAR(al.accountLineId.date), MONTH(al.accountLineId.date), al.solde.currency " +
	            "ORDER BY YEAR(al.accountLineId.date), MONTH(al.accountLineId.date), al.solde.currency ";
	
	 @Query(jql)
	 public List<AccountDateSumDTO> getSumByDate(LocalDate startDate, LocalDate endDate);
}
