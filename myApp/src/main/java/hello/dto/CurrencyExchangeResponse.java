package hello.dto;

public class CurrencyExchangeResponse extends CurrencyExchangeRequest {
    private Double amountRated;
    private Double rate;

    public Double getAmountRated() {
        return amountRated;
    }

    public void setAmountRated(Double amountRated) {
        this.amountRated = amountRated;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public CurrencyExchangeResponse() {
    }
}
