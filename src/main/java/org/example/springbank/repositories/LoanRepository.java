package org.example.springbank.repositories;

import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.models.Loan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT loan FROM Loan loan WHERE LOWER(loan.currency) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Loan> findByPattern(@Param("pattern") String pattern, Pageable pageable);

    @Query("SELECT loan FROM Loan loan WHERE loan.client = :client")
    List<Loan> findByClient(@Param("client") Client client, Pageable pageable);

    @Query("SELECT COUNT(loan) FROM Loan loan WHERE loan.client = :client")
    long countByClient(@Param("client") Client client);
}
