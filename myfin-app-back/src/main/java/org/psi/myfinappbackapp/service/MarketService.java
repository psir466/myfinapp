package org.psi.myfinappbackapp.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.dto.MarketDTO;
import org.psi.myfinappbackapp.dto.MarketDTODetail;
import org.psi.myfinappbackapp.market.ChartResponse;
import org.psi.myfinappbackapp.market.WebClientMarket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketService {

    @Autowired
	private WebClientMarket webClientMarket;


    public MarketDTO getMarket(String typeMarket, LocalDate start, LocalDate end) {
	

        ChartResponse chartResponse = webClientMarket.fetchDataMarket(typeMarket).block();

        List<Long> timestamps = chartResponse.getChart().getResult().get(0).getTimestamp();

        List<Double> indicators = chartResponse.getChart().getResult().get(0).getIndicators().getQuote().get(0).getClose();


        List<MarketDTODetail> marketDTODetails = new ArrayList<>();

        MarketDTO marketDTO = new MarketDTO();

        marketDTO.setMarkets(marketDTODetails);

        // timestamps.size() - 1 car je ne pas pouquoi l'api renvoit la même valeur 2 fois à la fin
        for(int i=0 ; i< timestamps.size() - 1 ; i++){

           

            LocalDate date = getLocalDateFromTimestamp(timestamps.get(i));

            if((date.isAfter(start) || date.equals(start)) && (date.isBefore(end) || date.equals(end))){

                MarketDTODetail marketDTODetail = new MarketDTODetail();
            
                marketDTODetail.setYear(date.getYear());

                marketDTODetail.setMonth(date.getMonthValue());

                marketDTODetail.setIndicePoint(indicators.get(i));

                marketDTO.getMarkets().add(marketDTODetail);   
            
            }

        }

		return marketDTO;
	}


    private LocalDate getLocalDateFromTimestamp(Long timestamp){

    
        return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();

    }

}
