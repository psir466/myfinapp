package org.psi.myfinappbackapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.psi.myfinappbackapp.dto.AccountHeaderDTO;
import org.psi.myfinappbackapp.dto.MarketDTO;
import org.psi.myfinappbackapp.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Api", description = "Operations related to markets")
public class MarketController {

    @Autowired
    MarketService marketService;


    @RequestMapping(value = "/markets/{code}/{start}/{end}", method = RequestMethod.GET)
	@Operation(summary = "Get market by code", description = "Retrieves market by its code")
	public ResponseEntity<MarketDTO> getMarketByCode(@PathVariable("code") String code, @PathVariable("start") LocalDate start, @PathVariable("end") LocalDate end) {


        MarketDTO markets = marketService.getMarket(code, start, end);

        if (markets != null) {
			return ResponseEntity.ok(markets);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
    

}
