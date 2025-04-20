package org.psi.myfinappbackapp.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentTradingPeriod {

    @JsonProperty("pre")
    private TradingPeriod pre;

    @JsonProperty("regular")
    private TradingPeriod regular;

    @JsonProperty("post")
    private TradingPeriod post;

    public TradingPeriod getPre() {
        return pre;
    }

    public void setPre(TradingPeriod pre) {
        this.pre = pre;
    }

    public TradingPeriod getRegular() {
        return regular;
    }

    public void setRegular(TradingPeriod regular) {
        this.regular = regular;
    }

    public TradingPeriod getPost() {
        return post;
    }

    public void setPost(TradingPeriod post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "CurrentTradingPeriod{" +
               "pre=" + pre +
               ", regular=" + regular +
               ", post=" + post +
               '}';
    }
}
