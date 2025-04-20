package org.psi.myfinappbackapp.market;

public class MarketResult {


    private Meta meta;

    public MarketResult() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((meta == null) ? 0 : meta.hashCode());
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
        MarketResult other = (MarketResult) obj;
        if (meta == null) {
            if (other.meta != null)
                return false;
        } else if (!meta.equals(other.meta))
            return false;
        return true;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    
    

}
