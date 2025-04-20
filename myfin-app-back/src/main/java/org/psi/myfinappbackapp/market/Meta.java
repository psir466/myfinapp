package org.psi.myfinappbackapp.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("exchangeName")
    private String exchangeName;

    @JsonProperty("fullExchangeName")
    private String fullExchangeName;

    @JsonProperty("instrumentType")
    private String instrumentType;

    @JsonProperty("firstTradeDate")
    private Long firstTradeDate;

    @JsonProperty("regularMarketTime")
    private Long regularMarketTime;

    @JsonProperty("hasPrePostMarketData")
    private Boolean hasPrePostMarketData;

    @JsonProperty("gmtoffset")
    private Integer gmtoffset;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("exchangeTimezoneName")
    private String exchangeTimezoneName;

    @JsonProperty("regularMarketPrice")
    private Double regularMarketPrice;

    @JsonProperty("fiftyTwoWeekHigh")
    private Double fiftyTwoWeekHigh;

    @JsonProperty("fiftyTwoWeekLow")
    private Double fiftyTwoWeekLow;

    @JsonProperty("regularMarketDayHigh")
    private Double regularMarketDayHigh;

    @JsonProperty("regularMarketDayLow")
    private Double regularMarketDayLow;

    @JsonProperty("regularMarketVolume")
    private Long regularMarketVolume;

    @JsonProperty("longName")
    private String longName;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("chartPreviousClose")
    private Double chartPreviousClose;

    @JsonProperty("priceHint")
    private Integer priceHint;

    @JsonProperty("currentTradingPeriod")
    private CurrentTradingPeriod currentTradingPeriod;

    @JsonProperty("dataGranularity")
    private String dataGranularity;

    @JsonProperty("range")
    private String range;

    @JsonProperty("validRanges")
    private List<String> validRanges;

    // Getters et setters pour chaque champ

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getFullExchangeName() {
        return fullExchangeName;
    }

    public void setFullExchangeName(String fullExchangeName) {
        this.fullExchangeName = fullExchangeName;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public Long getFirstTradeDate() {
        return firstTradeDate;
    }

    public void setFirstTradeDate(Long firstTradeDate) {
        this.firstTradeDate = firstTradeDate;
    }

    public Long getRegularMarketTime() {
        return regularMarketTime;
    }

    public void setRegularMarketTime(Long regularMarketTime) {
        this.regularMarketTime = regularMarketTime;
    }

    public Boolean getHasPrePostMarketData() {
        return hasPrePostMarketData;
    }

    public void setHasPrePostMarketData(Boolean hasPrePostMarketData) {
        this.hasPrePostMarketData = hasPrePostMarketData;
    }

    public Integer getGmtoffset() {
        return gmtoffset;
    }

    public void setGmtoffset(Integer gmtoffset) {
        this.gmtoffset = gmtoffset;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getExchangeTimezoneName() {
        return exchangeTimezoneName;
    }

    public void setExchangeTimezoneName(String exchangeTimezoneName) {
        this.exchangeTimezoneName = exchangeTimezoneName;
    }

    public Double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setRegularMarketPrice(Double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }

    public Double getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    public void setFiftyTwoWeekHigh(Double fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    public Double getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    public void setFiftyTwoWeekLow(Double fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    public Double getRegularMarketDayHigh() {
        return regularMarketDayHigh;
    }

    public void setRegularMarketDayHigh(Double regularMarketDayHigh) {
        this.regularMarketDayHigh = regularMarketDayHigh;
    }

    public Double getRegularMarketDayLow() {
        return regularMarketDayLow;
    }

    public void setRegularMarketDayLow(Double regularMarketDayLow) {
        this.regularMarketDayLow = regularMarketDayLow;
    }

    public Long getRegularMarketVolume() {
        return regularMarketVolume;
    }

    public void setRegularMarketVolume(Long regularMarketVolume) {
        this.regularMarketVolume = regularMarketVolume;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Double getChartPreviousClose() {
        return chartPreviousClose;
    }

    public void setChartPreviousClose(Double chartPreviousClose) {
        this.chartPreviousClose = chartPreviousClose;
    }

    public Integer getPriceHint() {
        return priceHint;
    }

    public void setPriceHint(Integer priceHint) {
        this.priceHint = priceHint;
    }

    public CurrentTradingPeriod getCurrentTradingPeriod() {
        return currentTradingPeriod;
    }

    public void setCurrentTradingPeriod(CurrentTradingPeriod currentTradingPeriod) {
        this.currentTradingPeriod = currentTradingPeriod;
    }

    public String getDataGranularity() {
        return dataGranularity;
    }

    public void setDataGranularity(String dataGranularity) {
        this.dataGranularity = dataGranularity;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<String> getValidRanges() {
        return validRanges;
    }

    public void setValidRanges(List<String> validRanges) {
        this.validRanges = validRanges;
    }

    @Override
    public String toString() {
        return "Meta{" +
               "currency='" + currency + '\'' +
               ", symbol='" + symbol + '\'' +
               ", exchangeName='" + exchangeName + '\'' +
               ", fullExchangeName='" + fullExchangeName + '\'' +
               ", instrumentType='" + instrumentType + '\'' +
               ", firstTradeDate=" + firstTradeDate +
               ", regularMarketTime=" + regularMarketTime +
               ", hasPrePostMarketData=" + hasPrePostMarketData +
               ", gmtoffset=" + gmtoffset +
               ", timezone='" + timezone + '\'' +
               ", exchangeTimezoneName='" + exchangeTimezoneName + '\'' +
               ", regularMarketPrice=" + regularMarketPrice +
               ", fiftyTwoWeekHigh=" + fiftyTwoWeekHigh +
               ", fiftyTwoWeekLow=" + fiftyTwoWeekLow +
               ", regularMarketDayHigh=" + regularMarketDayHigh +
               ", regularMarketDayLow=" + regularMarketDayLow +
               ", regularMarketVolume=" + regularMarketVolume +
               ", longName='" + longName + '\'' +
               ", shortName='" + shortName + '\'' +
               ", chartPreviousClose=" + chartPreviousClose +
               ", priceHint=" + priceHint +
               ", currentTradingPeriod=" + currentTradingPeriod +
               ", dataGranularity='" + dataGranularity + '\'' +
               ", range='" + range + '\'' +
               ", validRanges=" + validRanges +
               '}';
    }
}
