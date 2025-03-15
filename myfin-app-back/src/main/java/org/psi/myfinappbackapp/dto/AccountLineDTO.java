package org.psi.myfinappbackapp.dto;

import java.time.LocalDate;
import java.util.Objects;

import org.psi.myfinappbackapp.entities.AccountHeader;
import org.psi.myfinappbackapp.entities.AccountLine;
import org.psi.myfinappbackapp.util.AmountOfMoney;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class AccountLineDTO {

	private LocalDate date;

	private AmountOfMoney solde;

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

	public AccountLineDTO() {
		super();
	}

	public AccountLineDTO(LocalDate date, AmountOfMoney solde) {
		super();
		this.date = date;
		this.solde = solde;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, solde);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountLineDTO other = (AccountLineDTO) obj;
		return Objects.equals(date, other.date) && Objects.equals(solde, other.solde);
	}

}
