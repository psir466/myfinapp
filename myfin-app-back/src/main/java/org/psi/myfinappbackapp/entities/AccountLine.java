package org.psi.myfinappbackapp.entities;

import java.time.LocalDate;
import java.util.Objects;

import org.psi.myfinappbackapp.util.AmountOfMoney;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AccountLine {
	
	@Id
	private LocalDate date;
	
	@Embedded
	private AmountOfMoney solde;
	
    @ManyToOne
    private AccountHeader header;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public AmountOfMoney getSolde() {
		return solde;
	}

	public void setSolde(AmountOfMoney solde) {
		this.solde = solde;
	}
	
	

	public AccountHeader getHeader() {
		return header;
	}

	public void setHeader(AccountHeader header) {
		this.header = header;
	}

	public AccountLine() {
		super();
	}

	public AccountLine(LocalDate date, AmountOfMoney solde) {
		super();
		this.date = date;
		this.solde = solde;
	}
	
	
	public AccountLine(LocalDate date, AmountOfMoney solde, AccountHeader header) {
		super();
		this.date = date;
		this.solde = solde;
		this.header = header;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, header, solde);
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
		return Objects.equals(date, other.date) && Objects.equals(header, other.header)
				&& Objects.equals(solde, other.solde);
	}

	@Override
	public String toString() {
		return "AccountLine [date=" + date + ", solde=" + solde + ", header=" + header + "]";
	}


	
	
	
	

}
