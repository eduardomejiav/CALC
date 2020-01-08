package hello.dao.repository;

import hello.dao.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    ExchangeRate findByCurrencyFromAndCurrencyTo(String currencyFrom, String currencyTo);
}
