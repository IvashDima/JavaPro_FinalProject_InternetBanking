package org.example.springbank.services;

import org.example.springbank.dto.TransactionToNotifyDTO;
import org.example.springbank.exceptions.AccountNotFoundException;
import org.example.springbank.exceptions.DataAccessException;
import org.example.springbank.exceptions.InsufficientFundsException;
import org.example.springbank.exceptions.TransactionProcessingException;
import org.example.springbank.models.Account;
import org.example.springbank.models.ExchangeRate;
import org.example.springbank.models.Transaction;
import org.example.springbank.repositories.AccountRepository;
import org.example.springbank.repositories.ExchangeRateRepository;
import org.example.springbank.repositories.TransactionRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            ExchangeRateRepository exchangeRateRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Transactional
    public void addTransaction(Transaction transaction) {
        try {
            transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new TransactionProcessingException("Error while saving transaction", e);
        }
    }

    @Transactional
    public void deposit(Transaction transaction) {
        try {
            transactionRepository.save(transaction);

            transaction.getReceiver().deposit(transaction.getSenderAmount());
            accountRepository.save(transaction.getReceiver());
        } catch (Exception e) {
            throw new TransactionProcessingException("Error while depositing amount", e);
        }
    }

    @Transactional
    public void transfer(Transaction transaction) {
        try {
            transactionRepository.save(transaction);
            if (transaction.getSender().getBalance() < transaction.getSenderAmount()) {
                throw new InsufficientFundsException(
                        "Sender does not have enough funds for this transfer");
            }
            transaction.getSender().withdraw(transaction.getSenderAmount());
            accountRepository.save(transaction.getReceiver());

            transaction.getReceiver().deposit(transaction.getReceiverAmount());
            accountRepository.save(transaction.getReceiver());
        } catch (InsufficientFundsException e) {
            throw e;
        } catch (Exception e) {
            throw new TransactionProcessingException("Error while processing the transfer", e);
        }
    }

    @Transactional(readOnly = true)
    public double getTodayRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return 1.0;
        }

        ExchangeRate rate =
                exchangeRateRepository
                        .findTopByOrderByCreatedAtDesc()
                        .orElseThrow(
                                () ->
                                        new DataAccessException(
                                                "No exchange rate data available", null));
        try {
            double eurToUah = rate.getEurToUah();
            double eurToUsd = rate.getEurToUsd();

            switch (fromCurrency) {
                case "EUR":
                    if ("UAH".equals(toCurrency)) return eurToUah;
                    if ("USD".equals(toCurrency)) return eurToUsd;
                    break;
                case "UAH":
                    if ("EUR".equals(toCurrency)) return 1.0 / eurToUah;
                    if ("USD".equals(toCurrency)) return (1.0 / eurToUah) * eurToUsd;
                    break;
                case "USD":
                    if ("EUR".equals(toCurrency)) return 1.0 / eurToUsd;
                    if ("UAH".equals(toCurrency)) return (1.0 / eurToUsd) * eurToUah;
                    break;
            }
            throw new DataAccessException(
                    "Unsupported currency pair: " + fromCurrency + " → " + toCurrency, null);
        } catch (Exception e) {
            throw new DataAccessException(
                    "Error while calculating exchange rate for "
                            + fromCurrency
                            + " → "
                            + toCurrency,
                    e);
        }
    }

    @Transactional(readOnly = true)
    public List<Account> findAccounts() {
        try {
            return accountRepository.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error while fetching accounts", e);
        }
    }

    @Transactional(readOnly = true)
    public Account findAccount(long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAll(Pageable pageable) {
        try {
            return transactionRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new DataAccessException("Error while fetching transactions", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Transaction> findByPattern(String pattern, Pageable pageable) {
        return transactionRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findByAnyAccount(Account account, Pageable pageable) {
        return transactionRepository.findByAnyAccount(account, pageable);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findBySenderAccount(Account account, Pageable pageable) {
        return transactionRepository.findBySenderAccount(account, pageable);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findByReceiverAccount(Account account, Pageable pageable) {
        return transactionRepository.findByReceiverAccount(account, pageable);
    }

    @Transactional(readOnly = true)
    public long countBySenderAccount(Account account) {
        return transactionRepository.countBySenderAccount(account);
    }

    @Transactional(readOnly = true)
    public long countByReceiverAccount(Account account) {
        return transactionRepository.countByReceiverAccount(account);
    }

    @Transactional(readOnly = true)
    public long count() {
        return transactionRepository.count();
    }

    public void deleteAllTransactions() {
        try {
            transactionRepository.deleteAll();
        } catch (Exception e) {
            throw new DataAccessException("Error while deleting all transactions", e);
        }
    }

    @Transactional(readOnly = true)
    public List<TransactionToNotifyDTO> getTransactionToNotify(Date now) {
        try {
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(now);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date from = calendar.getTime();

            calendar.add(Calendar.MINUTE, 1);
            Date to = calendar.getTime();

            return transactionRepository.findTransactionToNotify(from, to);
        } catch (Exception e) {
            throw new DataAccessException("Error while fetching transactions to notify", e);
        }
    }
}
