package exchange.business;

import exchange.dao.entity.ExchangeRate;
import exchange.dao.repository.ExchangeRateRepository;
import exchange.dto.CurrencyExchangeRateRequest;
import exchange.dto.CurrencyExchangeRequest;
import exchange.dto.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service

public class CurrencyService {

    private static final int SCALE_DECIMALS = 2;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public CurrencyExchangeResponse convert(CurrencyExchangeRequest request) {

        ExchangeRate exchangeRate = getExchangeRate(request.getCurrencyFrom(), request.getCurrencyTo());

        if(exchangeRate == null){
            return null;
        }

        CurrencyExchangeResponse response = new CurrencyExchangeResponse();
        response.setCurrencyFrom(request.getCurrencyFrom());
        response.setCurrencyTo(request.getCurrencyTo());
        response.setAmount (request.getAmount());

        BigDecimal rate = new BigDecimal(exchangeRate.getRate());
        BigDecimal amountRated = new BigDecimal(exchangeRate.getRate() * request.getAmount());

        response.setRate(rate.setScale(SCALE_DECIMALS, RoundingMode.HALF_EVEN).doubleValue());
        response.setAmountRated(amountRated.setScale(SCALE_DECIMALS, RoundingMode.HALF_EVEN).doubleValue());

        return response;
    }

    public String saveCurrencyRate(CurrencyExchangeRateRequest request) {

        ExchangeRate exchangeRate = getExchangeRate(request.getCurrencyFrom(), request.getCurrencyTo());

        String response;

        if (exchangeRate == null){

            exchangeRate = new ExchangeRate();
            response = "Added";
        } else {

            response = "Updated";
        }

        exchangeRate.setCurrencyFrom(request.getCurrencyFrom());
        exchangeRate.setCurrencyTo(request.getCurrencyTo());
        exchangeRate.setRate(request.getRate());

        exchangeRateRepository.save(exchangeRate);
        return response;
    }

    private ExchangeRate getExchangeRate(String currencyFrom, String currencyTo){

        boolean divide = false;

        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrencyFromIgnoreCaseAndCurrencyToIgnoreCase(currencyFrom, currencyTo);

        if(exchangeRate == null){
            exchangeRate = exchangeRateRepository.findByCurrencyFromIgnoreCaseAndCurrencyToIgnoreCase(currencyTo, currencyFrom);
            divide = true;
        }

        if(exchangeRate != null){
            if(divide){
                exchangeRate.setRate(1/exchangeRate.getRate());
            }
        }

        return exchangeRate;
    }
}
