package org.psi.myfinappbackapp.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradingPeriod {

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("start")
    private Long start;

    @JsonProperty("end")
    private Long end;

    @JsonProperty("gmtoffset")
    private Integer gmtoffset;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Integer getGmtoffset() {
        return gmtoffset;
    }

    public void setGmtoffset(Integer gmtoffset) {
        this.gmtoffset = gmtoffset;
    }

    @Override
    public String toString() {
        return "TradingPeriod{" +
               "timezone='" + timezone + '\'' +
               ", start=" + start +
               ", end=" + end +
               ", gmtoffset=" + gmtoffset +
               '}';
    }
}
