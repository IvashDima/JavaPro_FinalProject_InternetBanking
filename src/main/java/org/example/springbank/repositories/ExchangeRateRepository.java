package org.example.springbank.repositories;

import org.example.springbank.models.Account;
import org.example.springbank.models.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findTopByOrderByDateDesc();

    Optional<ExchangeRate> findByDate(LocalDate date);
}
