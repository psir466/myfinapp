package org.psi.myfinappbackapp.dto;


public class AccountDatePercentageDTO {

    private Integer year;
	
	private Integer month;
	
	private Double percentage;

    public AccountDatePercentageDTO(Integer year, Integer month, Double percentage) {
        super();
        this.year = year;
        this.month = month;
        this.percentage = percentage;
    }

    public AccountDatePercentageDTO() {
        super();
    }


    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        result = prime * result + ((month == null) ? 0 : month.hashCode());
        result = prime * result + ((percentage == null) ? 0 : percentage.hashCode());
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
        AccountDatePercentageDTO other = (AccountDatePercentageDTO) obj;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        if (month == null) {
            if (other.month != null)
                return false;
        } else if (!month.equals(other.month))
            return false;
        if (percentage == null) {
            if (other.percentage != null)
                return false;
        } else if (!percentage.equals(other.percentage))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AccountDatePercentageDTO [year=" + year + ", month=" + month + ", percentage=" + percentage + "]";
    }

    

}


