package org.psi.myfinappbackapp.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Adjclose {

    @JsonProperty("adjclose")
    private List<Double> adjclose;

    public List<Double> getAdjclose() {
        return adjclose;
    }

    public void setAdjclose(List<Double> adjclose) {
        this.adjclose = adjclose;
    }

    @Override
    public String toString() {
        return "Adjclose{" +
               "adjclose=" + adjclose +
               '}';
    }
}