package org.psi.myfinappbackapp.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.psi.myfinappbackapp.util.AccountType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class AccountHeader {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID (common strategy)
	private Long id;
	
	private String name;
	private AccountType accountType;
	
	//mappedBy = "header": This is crucial. 
	//It tells JPA that the relationship is owned by the AccountLine entity, 
	//specifically by the header field in the AccountLine class. 
	//This prevents JPA from creating an extra join table. The foreign key is managed in the AccountLine table.
	
	//cascade = CascadeType.ALL: This specifies what happens to the related AccountLine entities 
	//when operations (like persist, remove) are performed on the AccountHeader entity. CascadeType.ALL means that all operations 
	//are cascaded. Other options include CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, etc. Choose the cascade types that are appropriate for your application's needs.
	
	//orphanRemoval (Important!):  Using orphanRemoval = true is highly recommended to prevent orphaned records in your database.
	
	@OneToMany(mappedBy = "header", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AccountLine> accountLines = new ArrayList<>();
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	
	
	// addAccountLine and removeAccountLine : This is much better than directly manipulating the lists and setting the customer field, which can lead to inconsistencies.
	
    public void addAccountLine(AccountLine accountLine) {
        this.accountLines.add(accountLine);
        accountLine.setHeader(this); // Maintain consistency on both sides
    }

    public void removeAccountLine(AccountLine accountLine) {
    	this.accountLines.remove(accountLine);
        accountLine.setHeader(null); // Important: Clear the association
    }

	public void removeAllAccountLine() {
	/* 	
	
		NOT WORKING => CONCURENTE MODIFICATION EXCEPTION  
	for(AccountLine accountLine : this.accountLines){
			this.accountLines.remove(accountLine);
            accountLine.setHeader(null); // Important: Clear the association
		} */ 	

		this.accountLines.forEach(l ->{

			l.setHeader(null);

		});	

		this.accountLines.clear();
    }
	
    
	
	public List<AccountLine> getAccountLines() {
		return accountLines;
	}
	public void setAccountLines(List<AccountLine> accountLines) {
		this.accountLines = accountLines;
	}
	
	
	public AccountHeader() {
		super();
	}
	
	
	public AccountHeader(String name, AccountType accountType) {
		super();
		this.name = name;
		this.accountType = accountType;
	}
	
	
	
	
	public AccountHeader(String name, AccountType accountType, List<AccountLine> accountLines) {
		super();
		this.name = name;
		this.accountType = accountType;
		this.accountLines = accountLines;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(accountType, id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountHeader other = (AccountHeader) obj;
		return accountType == other.accountType && Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	@Override
	public String toString() {
		return "AccountHeader [id=" + id + ", name=" + name + ", accountType=" + accountType 
				+ "]";
	}

	
	
	
	

}
