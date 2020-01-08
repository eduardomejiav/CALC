package hello.business;

import hello.dao.entity.ExchangeRate;
import hello.dao.repository.ExchangeRateRepository;
import hello.dto.CurrencyExchangeRequest;
import hello.dto.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CurrencyService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public CurrencyExchangeResponse convert(CurrencyExchangeRequest request) {

        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrencyFromAndCurrencyTo(request.getCurrencyFrom(), request.getCurrencyTo());
        if(exchangeRate == null){
            return null;
        }

        CurrencyExchangeResponse response = new CurrencyExchangeResponse();
        response.setCurrencyFrom(request.getCurrencyFrom());
        response.setCurrencyTo(request.getCurrencyTo());
        response.setAmount (request.getAmount());
        response.setRate(exchangeRate.getRate());
        response.setAmountRated(exchangeRate.getRate()*request.getAmount());

        return response;
    }
}
