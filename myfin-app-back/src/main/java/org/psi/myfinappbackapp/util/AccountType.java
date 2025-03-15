package org.psi.myfinappbackapp.util;

public enum AccountType {

	
	CTO("CTO"),
	PEA("PEA"),
	PEAPME("PEA-PME"),
	ASSURVIE("ASSRANCE VIE"),
	EPARGNESALARIALE("EPARGNE SALARIALE");
	
	private String accountName;
	
	AccountType(String accountName){
		this.accountName = accountName;
	}
	
	
	
}
