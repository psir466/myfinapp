package org.psi.myfinappbackapp.dto;

import java.util.List;

public class MarketDTO {

    private List<MarketDTODetail> markets;

    public MarketDTO() {
    }

    public List<MarketDTODetail> getMarkets() {
        return markets;
    }

    public void setMarkets(List<MarketDTODetail> markets) {
        this.markets = markets;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((markets == null) ? 0 : markets.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MarketDTO other = (MarketDTO) obj;
        if (markets == null) {
            if (other.markets != null)
                return false;
        } else if (!markets.equals(other.markets))
            return false;
        return true;
    }

    

}
