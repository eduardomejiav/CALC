package exchange.dao.repository;

import exchange.dao.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query(value = "SELECT u FROM ExchangeRate u " +
            " WHERE " +
            " (LOWER(u.currencyFrom) LIKE LOWER(:currencyFrom) " +
            "    AND LOWER(u.currencyTo) LIKE LOWER(:currencyTo) ) OR " +
            " (LOWER(u.currencyFrom) LIKE LOWER(:currencyTo) " +
            "    AND LOWER(u.currencyTo) LIKE LOWER(:currencyFrom) )")
    Optional<ExchangeRate> findCurrencyExchangeRate(
            @Param("currencyFrom") String currencyFrom,
            @Param("currencyTo") String currencyTo);
}
