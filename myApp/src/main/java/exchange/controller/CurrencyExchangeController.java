package exchange.controller;

import exchange.business.CurrencyService;
import exchange.dto.CurrencyExchangeRateRequest;
import exchange.dto.CurrencyExchangeRequest;
import exchange.dto.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/conversor")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(value = "convert"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyExchangeResponse convert (@RequestBody CurrencyExchangeRequest request) {

        return currencyService.convert(request);
    }

    @PostMapping(value = "add"
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    public String add (@RequestBody CurrencyExchangeRateRequest request) {

        return currencyService.saveCurrencyRate(request);
    }
}