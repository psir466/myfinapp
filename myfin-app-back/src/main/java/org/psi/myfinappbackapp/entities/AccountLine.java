package org.psi.myfinappbackapp.entities;

import java.time.LocalDate;
import java.util.Objects;

import org.psi.myfinappbackapp.util.AmountOfMoney;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AccountLine {
	
	

	@EmbeddedId
	private AccountLineId accountLineId;

	
	@Embedded
	private AmountOfMoney solde;
	

	public AccountLineId getAccountLineId() {
		return accountLineId;
	}

	public void setAccountLineId(AccountLineId accountLineId) {
		this.accountLineId = accountLineId;
	}
    

	public AmountOfMoney getSolde() {
		return solde;
	}

	public void setSolde(AmountOfMoney solde) {
		this.solde = solde;
	}
	
	

	
	public AccountLine() {
		super();
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(accountLineId, solde);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountLine other = (AccountLine) obj;
		return Objects.equals(accountLineId, other.accountLineId) 
				&& Objects.equals(solde, other.solde);
	}

	@Override
	public String toString() {
		return "AccountLine [date=" + accountLineId.getDate() + ", solde=" + solde + ", header=" + accountLineId.getHeader() + "]";
	}


	
	
	
	

}
