package org.psi.myfinappbackapp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.psi.myfinappbackapp.entities.AccountHeader;
import org.psi.myfinappbackapp.entities.AccountLine;
import org.psi.myfinappbackapp.util.AccountType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class AccountHeaderDTO {


	private Long id;
	
	private String name;
	private AccountType accountType;
	
	private List<AccountLineDTO> accountLines = new ArrayList<>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	

	
    
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<AccountLineDTO> getAccountLines() {
		return accountLines;
	}
	public void setAccountLines(List<AccountLineDTO> accountLines) {
		this.accountLines = accountLines;
	}
	
	
	public AccountHeaderDTO() {
		super();
	}
	
	
	public AccountHeaderDTO(String name, AccountType accountType) {
		super();
		this.name = name;
		this.accountType = accountType;
	}
	
	
	
	
	public AccountHeaderDTO(String name, AccountType accountType, List<AccountLineDTO> accountLines) {
		super();
		this.name = name;
		this.accountType = accountType;
		this.accountLines = accountLines;
	}
	@Override
	public int hashCode() {
		return Objects.hash(accountLines, accountType, id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountHeaderDTO other = (AccountHeaderDTO) obj;
		return Objects.equals(accountLines, other.accountLines) && accountType == other.accountType
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
	
	
	
}
