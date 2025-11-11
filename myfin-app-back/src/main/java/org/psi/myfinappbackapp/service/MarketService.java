package org.psi.myfinappbackapp.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.psi.myfinappbackapp.dto.AccountDateSumDTO;
import org.psi.myfinappbackapp.dto.MarketDTO;
import org.psi.myfinappbackapp.dto.MarketDTODetail;
import org.psi.myfinappbackapp.dto.MarketDTODetailPercentage;
import org.psi.myfinappbackapp.dto.MarketDTOPercentage;
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

           
            LocalDate dateBrut = getLocalDateFromTimestamp(timestamps.get(i));

            // on ajoute un mois à la donnée car le cours correspond au dernier jour du mois
            LocalDate date = dateBrut.plusMonths(1);

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


    public MarketDTOPercentage getMarketPercentage(String typeMarket, LocalDate start, LocalDate end) {
        
         MarketDTO marketDTOs = getMarket(typeMarket, start, end);

       List<MarketDTODetail> sortedMarkets = marketDTOs.getMarkets().stream().sorted((a, b) -> {
            if(a.getYear() == (b.getYear())){
                if(a.getMonth() == (b.getMonth())){
                    return 0;
                } else if (a.getMonth() < (b.getMonth())) {
                        return -1;
                } else if (a.getMonth() > (b.getMonth())){
                        return 1;
                }
               
            }
            else
            {
                
                if (a.getYear() < (b.getYear())) {
                        return -1;
                } else if (a.getYear() > (b.getYear())){
                        return 1;
                }

            }
            return 0;
        }).collect(Collectors.toList());

        List<MarketDTODetailPercentage> marketDTODetailsPercentages = new ArrayList<>();

        MarketDTOPercentage marketDTOPercentage = new MarketDTOPercentage();

        marketDTOPercentage.setMarkets(marketDTODetailsPercentages);

        MarketDTODetail firstMarket = sortedMarkets.get(0);
        double firstIndice = firstMarket.getIndicePoint();

        for(MarketDTODetail marketDTODetail : sortedMarkets){
            MarketDTODetailPercentage marketDTODetailPercentage = new MarketDTODetailPercentage(marketDTODetail.getYear(), marketDTODetail.getMonth(), ((marketDTODetail.getIndicePoint() - firstIndice) / firstIndice) * 100);

            marketDTOPercentage.getMarkets().add(marketDTODetailPercentage);
        }



        return marketDTOPercentage;
    }

    

}
