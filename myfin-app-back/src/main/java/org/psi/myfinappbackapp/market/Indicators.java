package org.psi.myfinappbackapp.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Indicators {

    @JsonProperty("quote")
    private List<Quote> quote;

    @JsonProperty("adjclose")
    private List<Adjclose> adjclose;

    public List<Quote> getQuote() {
        return quote;
    }

    public void setQuote(List<Quote> quote) {
        this.quote = quote;
    }

    public List<Adjclose> getAdjclose() {
        return adjclose;
    }

    public void setAdjclose(List<Adjclose> adjclose) {
        this.adjclose = adjclose;
    }

    @Override
    public String toString() {
        return "Indicators{" +
               "quote=" + quote +
               ", adjclose=" + adjclose +
               '}';
    }
}
