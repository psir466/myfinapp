package org.psi.myfinappbackapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.psi.myfinappbackapp.util.Currency;

public class 
AccountDateSumDTO {
	
	private Integer year;
	
	private Integer month;
	
	private BigDecimal sum;
	
	private Currency cuurency;
	
	




	public AccountDateSumDTO(Integer year, Integer month, BigDecimal sum, Currency cuurency) {
		super();
		this.year = year;
		this.month = month;
		this.sum = sum;
		this.cuurency = cuurency;
	}
	
	



	public AccountDateSumDTO(BigDecimal sum, Currency currency) {
		super();
		this.sum = sum;
		this.cuurency = currency;
	}





	public AccountDateSumDTO(Integer year, Integer month, BigDecimal sum) {
		super();
		this.year = year;
		this.month = month;
		this.sum = sum;
	}





	public AccountDateSumDTO() {
		super();
	}


	
	public Currency getCuurency() {
		return cuurency;
	}



	public void setCuurency(Currency cuurency) {
		this.cuurency = cuurency;
	}


	public Integer getYear() {
		return year;
	}



	public void setYear(Integer year) {
		this.year = year;
	}



	public Integer getMonth() {
		return month;
	}



	public void setMonth(Integer month) {
		this.month = month;
	}



	public BigDecimal getSum() {
		return sum;
	}



	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	
	


	



	@Override
	public int hashCode() {
		return Objects.hash(cuurency, month, sum, year);
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountDateSumDTO other = (AccountDateSumDTO) obj;
		return cuurency == other.cuurency && Objects.equals(month, other.month) && Objects.equals(sum, other.sum)
				&& Objects.equals(year, other.year);
	}





	@Override
	public String toString() {
		return "AccountDateSumDTO [year=" + year + ", month=" + month + ", sum=" + sum + ", cuurency=" + cuurency + "]";
	}











	
	
}
