package org.psi.myfinappbackapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.entities.AccountHeader;
import org.psi.myfinappbackapp.util.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHeaderRepository extends JpaRepository<AccountHeader, Long>{


    	 String jql = "SELECT new org.psi.myfinappbackapp.dto.AccountDateSumDTO("
		 		+ "YEAR(al.accountLineId.date), MONTH(al.accountLineId.date), SUM(al.solde.amount), al.solde.currency" +
				 ") " +
	            "FROM AccountHeader ah JOIN ah.accountLines al " +
	            "WHERE ah.accountType = :accountType AND al.accountLineId.date >= :startDate AND al.accountLineId.date <= :endDate " +
	            "GROUP BY YEAR(al.accountLineId.date), MONTH(al.accountLineId.date), al.solde.currency " +
	            "ORDER BY YEAR(al.accountLineId.date), MONTH(al.accountLineId.date), al.solde.currency ";
	
	 @Query(jql)
	 public List<AccountDateSumDTO> getSumByAccountTypeAndByDate(AccountType accountType, LocalDate startDate, LocalDate endDate);

}
