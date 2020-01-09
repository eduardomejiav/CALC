package exchange.controller;

import exchange.business.CurrencyService;
import exchange.dto.CurrencyExchangeRateRequest;
import exchange.dto.CurrencyExchangeRequest;
import exchange.dto.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rx.Single;
import rx.schedulers.Schedulers;

@RestController
@RequestMapping(value = "/conversor")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(value = "convert"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<CurrencyExchangeResponse>> convert (@RequestBody CurrencyExchangeRequest request) {

        return currencyService.convert(request)
                .subscribeOn(Schedulers.io())
                .map(currencyExchangeResponse -> ResponseEntity.ok(currencyExchangeResponse));
    }

    @PostMapping(value = "save"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<String>> save (@RequestBody CurrencyExchangeRateRequest request) {

        return currencyService.saveCurrencyRate(request)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok("Saved"));
    }
}