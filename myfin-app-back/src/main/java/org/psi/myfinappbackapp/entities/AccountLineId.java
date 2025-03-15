package org.psi.myfinappbackapp.entities;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Embeddable
public class AccountLineId {


    
	private LocalDate date;

    @ManyToOne
    private AccountHeader header;

    public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

    public AccountHeader getHeader() {
		return header;
	}

	public void setHeader(AccountHeader header) {
		this.header = header;
	}

    public AccountLineId() {
		super();
	}


    @Override
	public int hashCode() {
		return Objects.hash(date, header);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountLineId other = (AccountLineId) obj;
		return Objects.equals(date, other.date) && Objects.equals(header, other.header);
	}

	@Override
	public String toString() {
		return "AccountLine [date=" + date + ", header=" + header + "]";
	}



}
