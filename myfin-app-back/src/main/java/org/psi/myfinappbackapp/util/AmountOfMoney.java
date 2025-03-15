package org.psi.myfinappbackapp.util;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class AmountOfMoney {

	private BigDecimal amount;
	private Currency currency;
	
	
	
	public AmountOfMoney() {
		super();
	}
	public AmountOfMoney(BigDecimal amount, Currency currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, currency);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmountOfMoney other = (AmountOfMoney) obj;
		return Objects.equals(amount, other.amount) && currency == other.currency;
	}
	
	
	
	
}
