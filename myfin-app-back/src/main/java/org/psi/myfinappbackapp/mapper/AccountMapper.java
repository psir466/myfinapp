package org.psi.myfinappbackapp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.psi.myfinappbackapp.dto.AccountHeaderDTO;
import org.psi.myfinappbackapp.dto.AccountLineDTO;
import org.psi.myfinappbackapp.entities.AccountHeader;
import org.psi.myfinappbackapp.entities.AccountLine;

@Mapper
public interface AccountMapper {

	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
	
	AccountHeaderDTO mapHeaderToDTO(AccountHeader accoutHedaer);
	
	AccountHeader mapHeaderFromDTO(AccountHeaderDTO accountHeaderDTO);
	
	AccountLineDTO mapLineToDTO(AccountLine accoutLine);
	
	AccountLine mapLineFromDTO(AccountLineDTO accountHeaderDTO);
	
	List<AccountHeaderDTO> mapHeaderListToDTO(List<AccountHeader> accoutHedaers);
	
	
}
