package org.psi.myfinappbackapp.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    @JsonProperty("volume")
    private List<Long> volume;

    @JsonProperty("high")
    private List<Double> high;

    @JsonProperty("low")
    private List<Double> low;

    @JsonProperty("open")
    private List<Double> open;

    @JsonProperty("close")
    private List<Double> close;

    public List<Long> getVolume() {
        return volume;
    }

    public void setVolume(List<Long> volume) {
        this.volume = volume;
    }

    public List<Double> getHigh() {
        return high;
    }

    public void setHigh(List<Double> high) {
        this.high = high;
    }

    public List<Double> getLow() {
        return low;
    }

    public void setLow(List<Double> low) {
        this.low = low;
    }

    public List<Double> getOpen() {
        return open;
    }

    public void setOpen(List<Double> open) {
        this.open = open;
    }

    public List<Double> getClose() {
        return close;
    }

    public void setClose(List<Double> close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "Quote{" +
               "volume=" + volume +
               ", high=" + high +
               ", low=" + low +
               ", open=" + open +
               ", close=" + close +
               '}';
    }
}