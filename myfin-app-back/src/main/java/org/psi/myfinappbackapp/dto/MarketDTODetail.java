package org.psi.myfinappbackapp.dto;

import java.time.LocalDate;

public class MarketDTODetail {

    private int year;

    private int month;

    private Double indicePoint;

    public MarketDTODetail() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Double getIndicePoint() {
        return indicePoint;
    }

    public void setIndicePoint(Double indicePoint) {
        this.indicePoint = indicePoint;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        result = prime * result + month;
        result = prime * result + ((indicePoint == null) ? 0 : indicePoint.hashCode());
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
        MarketDTODetail other = (MarketDTODetail) obj;
        if (year != other.year)
            return false;
        if (month != other.month)
            return false;
        if (indicePoint == null) {
            if (other.indicePoint != null)
                return false;
        } else if (!indicePoint.equals(other.indicePoint))
            return false;
        return true;
    }

   

}
