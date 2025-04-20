package org.psi.myfinappbackapp.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartResponse {

    @JsonProperty("chart")
    private Chart chart;

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    @Override
    public String toString() {
        return "ChartResponse{" +
               "chart=" + chart +
               '}';
    }
}