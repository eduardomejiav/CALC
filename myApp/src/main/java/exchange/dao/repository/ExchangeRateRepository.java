package exchange.dao.repository;

import exchange.dao.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    // ExchangeRate findByCurrencyFromAndCurrencyTo(String currencyFrom, String currencyTo);
    ExchangeRate findByCurrencyFromIgnoreCaseAndCurrencyToIgnoreCase (String currencyFrom, String currencyTo);
}
