package hello.controller;

import hello.business.CurrencyService;
import hello.dto.CurrencyExchangeRequest;
import hello.dto.CurrencyExchangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/conversor")
public class HelloController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(value = "convert"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
        public CurrencyExchangeResponse convert (@RequestBody CurrencyExchangeRequest request) {
            return currencyService.convert(request);
        }
}