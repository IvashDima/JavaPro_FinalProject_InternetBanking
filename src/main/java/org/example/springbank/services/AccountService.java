package org.example.springbank.services;

import org.example.springbank.exceptions.AccountNotFoundException;
import org.example.springbank.exceptions.AccountProcessingException;
import org.example.springbank.exceptions.ClientNotFoundException;
import org.example.springbank.exceptions.DataAccessException;
import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.repositories.AccountRepository;
import org.example.springbank.repositories.ClientRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void addAccount(Account account) {
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new AccountProcessingException("Error while saving account", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Client> findClients() {
        try {
            return clientRepository.findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error while fetching clients", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Account> findAll(Pageable pageable) {
        try {
            return accountRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new DataAccessException("Error while fetching accounts", e);
        }
    }

    @Transactional(readOnly = true)
    public Account findById(long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Account> findByClient(Client client, Pageable pageable) {
        try {
            return accountRepository.findByClient(client, pageable);
        } catch (Exception e) {
            throw new DataAccessException("Error while fetching accounts for client", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Account> findByPattern(String pattern, Pageable pageable) {
        try {
            return accountRepository.findByPattern(pattern, pageable);
        } catch (Exception e) {
            throw new DataAccessException("Error while fetching accounts by pattern", e);
        }
    }

    @Transactional(readOnly = true)
    public long countByClient(Client client) {
        try {
            return accountRepository.countByClient(client);
        } catch (Exception e) {
            throw new DataAccessException("Error while counting accounts for client", e);
        }
    }

    @Transactional(readOnly = true)
    public long count() {
        try {
            return accountRepository.count();
        } catch (Exception e) {
            throw new DataAccessException("Error while counting all accounts", e);
        }
    }

    @Transactional(readOnly = true)
    public Client findClient(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    public void deleteAllAccounts() {
        try {
            accountRepository.deleteAll();
        } catch (Exception e) {
            throw new DataAccessException("Error while deleting all accounts", e);
        }
    }
}
