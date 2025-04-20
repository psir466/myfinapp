package org.psi.myfinappbackapp.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class WebClientMarket {


     private WebClient webClient = null;

    @Autowired
    public WebClientMarket(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ChartResponse> fetchDataMarket(String typeMarket) {

        String url = "https://query2.finance.yahoo.com/v8/finance/chart/^GSPC?range=5y&interval=1mo";

        //String url = "https://query2.finance.yahoo.com/v8/finance/chart/" + typeMarket + "?range=5y&interval=1mo";

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ChartResponse.class);
    }
    


}
