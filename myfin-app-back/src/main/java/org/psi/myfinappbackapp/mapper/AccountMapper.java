package org.psi.myfinappbackapp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.psi.myfinappbackapp.dto.AccountHeaderDTO;
import org.psi.myfinappbackapp.dto.AccountLineDTO;
import org.psi.myfinappbackapp.entities.AccountHeader;
import org.psi.myfinappbackapp.entities.AccountLine;
import org.psi.myfinappbackapp.entities.AccountLineId;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
@Component
public abstract class AccountMapper {
	
	public abstract AccountHeaderDTO mapHeaderToDTO(AccountHeader accoutHedaer);
	
	
	public AccountHeader mapHeaderFromDTO(AccountHeaderDTO accountHeaderDTO){

		AccountHeader accountHeader = new AccountHeader();

		accountHeader.setName(accountHeaderDTO.getName());
		accountHeader.setAccountType(accountHeaderDTO.getAccountType());
		accountHeader.setAccountLines(mapToAccountLine(accountHeader, accountHeaderDTO.getAccountLines()));

		return accountHeader;

	}
	
	public AccountLineDTO mapLineToDTO(AccountLine accoutLine){

		AccountLineDTO accountLineDTO = new AccountLineDTO();

		accountLineDTO.setDate(accoutLine.getAccountLineId().getDate());
		accountLineDTO.setSolde(accoutLine.getSolde());

		return accountLineDTO;
	};

	public abstract AccountLine mapLine(AccountLineDTO accoutLineDTO);
	
	
	public abstract List<AccountHeaderDTO> mapHeaderListToDTO(List<AccountHeader> accoutHedaers);
	
	public abstract List<AccountHeader> mapHeaderList(List<AccountHeaderDTO> accoutHedaersDTO);


	public List<AccountLine> mapToAccountLine(AccountHeader accountHeader, List<AccountLineDTO> value){


		List<AccountLine> accountLines = new ArrayList<>();


		for(AccountLineDTO accountLineDTO : value){

			AccountLine accountLine = new AccountLine();
			AccountLineId accountLineId = new AccountLineId();

			accountLine = mapLine(accountLineDTO);
			accountLineId.setDate(accountLineDTO.getDate());
			accountLineId.setHeader(accountHeader);
			accountLine.setAccountLineId(accountLineId);

			accountLines.add(accountLine);

		}


		return accountLines;

	}

 	
	
}
