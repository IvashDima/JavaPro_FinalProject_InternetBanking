package org.example.springbank.services;

import org.example.springbank.exceptions.ClientNotFoundException;
import org.example.springbank.exceptions.LoanNotFoundException;
import org.example.springbank.models.Client;
import org.example.springbank.models.Loan;
import org.example.springbank.repositories.ClientRepository;
import org.example.springbank.repositories.LoanRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;

    public LoanService(LoanRepository loanRepository, ClientRepository clientRepository) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void addLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Transactional(readOnly = true)
    public List<Client> findClients() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Loan> findAll(Pageable pageable) {
        return loanRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public Loan findById(long id) {
        return loanRepository
                .findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Loan> findByClient(Client client, Pageable pageable) {
        return loanRepository.findByClient(client, pageable);
    }

    @Transactional(readOnly = true)
    public List<Loan> findByPattern(String pattern, Pageable pageable) {
        return loanRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public long countByClient(Client client) {
        return loanRepository.countByClient(client);
    }

    @Transactional(readOnly = true)
    public long count() {
        return loanRepository.count();
    }

    @Transactional(readOnly = true)
    public Client findClient(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    public void deleteAllLoans() {
        loanRepository.deleteAll();
    }
}
