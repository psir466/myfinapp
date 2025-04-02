package org.psi.myfinappbackapp.util;

import java.util.ArrayList;
import java.util.List;

public enum AccountType {

	
	CTO("CTO"),
	PEA("PEA"),
	PEAPME("PEA-PME"),
	ASSURVIE("ASSRANCE VIE"),
	EPARGNESALARIALE("EPARGNE SALARIALE"),
	LIVRETA("LIVRET A"),
	LLD("LLD"),
	CEP("CEP"),
	CPTCOURANT("COMPTE COURANT"),
	PRETIMMO("PRET IMMO")
	;


	private String accountName;
	
	AccountType(String accountName){
		this.accountName = accountName;
	}
	
	public static AccountType getAccountType(String str){

		for (AccountType value : AccountType.values()) {
            if (value.name().equalsIgnoreCase(str)) {
                return value;
            }
        }
        return null; 

	}

	public static List<String> getAccountypes(){

		List<String> listAccountTypessString = new ArrayList<>();

		for (AccountType value : AccountType.values()) {
           listAccountTypessString.add(value.name());
        }

		return listAccountTypessString;

	}
	
}
