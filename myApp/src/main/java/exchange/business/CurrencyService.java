package exchange.business;

import exchange.dao.entity.ExchangeRate;
import exchange.dao.repository.ExchangeRateRepository;
import exchange.dto.CurrencyExchangeRateRequest;
import exchange.dto.CurrencyExchangeRequest;
import exchange.dto.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Completable;
import rx.Single;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service

public class CurrencyService {

    private static final int SCALE_DECIMALS = 2;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    //rx
    public Single<CurrencyExchangeResponse> convert(CurrencyExchangeRequest request) {

        return Single.create(singleSubscriber -> {

            //if(request.getAmount()<0) singleSubscriber.onError();

            Optional<ExchangeRate> optionalRate = getCurrencyExchangeRate(request.getCurrencyFrom(), request.getCurrencyTo());

            if (!optionalRate.isPresent()){

                singleSubscriber.onError(new EntityNotFoundException());
            }
            else {

                CurrencyExchangeResponse response = toResponse(optionalRate.get(), request);

                singleSubscriber.onSuccess(response);
            }
        });
    }

    public Completable saveCurrencyRate(CurrencyExchangeRateRequest request) {

        return Completable.create(completableSubscriber -> {

            Optional<ExchangeRate> optionalRate = getCurrencyExchangeRate(request.getCurrencyFrom(), request.getCurrencyTo());

            ExchangeRate exchangeRate;

            if (!optionalRate.isPresent()){

                exchangeRate = new ExchangeRate();
            }
            else {

                exchangeRate = optionalRate.get();
            }

            exchangeRate.setCurrencyFrom(request.getCurrencyFrom());
            exchangeRate.setCurrencyTo(request.getCurrencyTo());

            if(!exchangeRate.getCurrencyFrom().equalsIgnoreCase(exchangeRate.getCurrencyTo())){
                exchangeRate.setRate(request.getRate());
            }

            exchangeRateRepository.save(exchangeRate);

            completableSubscriber.onCompleted();
        });

    }

    private Optional<ExchangeRate> getCurrencyExchangeRate(String currencyFrom, String currencyTo){

        if(currencyFrom.equalsIgnoreCase(currencyTo)){

            ExchangeRate exchangeRate =  new ExchangeRate();
            exchangeRate.setRate(BigDecimal.ONE.doubleValue());
            exchangeRate.setCurrencyFrom(currencyFrom);
            exchangeRate.setCurrencyTo(currencyFrom);

            return Optional.of(exchangeRate);
        }

        else return exchangeRateRepository.findCurrencyExchangeRate(currencyFrom, currencyTo);

    }

    private CurrencyExchangeResponse toResponse(ExchangeRate exchangeRate, CurrencyExchangeRequest request){

        Double rate = exchangeRate.getRate();

        if(!exchangeRate.getCurrencyFrom().equalsIgnoreCase(request.getCurrencyFrom())){
            rate = 1/exchangeRate.getRate();
        }

        BigDecimal rateFixed = new BigDecimal(rate);
        BigDecimal amountRatedFixed = new BigDecimal(rate * request.getAmount());

        CurrencyExchangeResponse response = new CurrencyExchangeResponse();

        response.setCurrencyFrom(request.getCurrencyFrom());
        response.setCurrencyTo(request.getCurrencyTo());
        response.setAmount (request.getAmount());

        response.setRate(rateFixed.setScale(SCALE_DECIMALS, RoundingMode.HALF_EVEN).doubleValue());
        response.setAmountRated(amountRatedFixed.setScale(SCALE_DECIMALS, RoundingMode.HALF_EVEN).doubleValue());
        return response;
    }

}
