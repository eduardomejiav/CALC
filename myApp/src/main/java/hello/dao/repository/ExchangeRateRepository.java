package hello.dao.repository;

import hello.dao.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}
