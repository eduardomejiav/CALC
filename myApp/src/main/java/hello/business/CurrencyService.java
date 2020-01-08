package hello.business;

import hello.dto.CurrencyExchangeRequest;
import hello.dto.CurrencyExchangeResponse;
import org.springframework.stereotype.Service;

@Service

public class CurrencyService {
    public CurrencyExchangeResponse convert(CurrencyExchangeRequest request) {
        CurrencyExchangeResponse response = new CurrencyExchangeResponse();
        response.setCurrencyFrom(request.getCurrencyFrom());
        response.setCurrencyTo(request.getCurrencyTo());
        response.setAmount (request.getAmount());
        response.setRate(1.0);
        response.setAmountRated(2.0);
        return response;
    }
}
