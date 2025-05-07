package org.example.springbank.services;

import org.example.springbank.enums.CurrencyType;
import org.example.springbank.enums.TransactionType;
import org.example.springbank.enums.UserRole;
import org.example.springbank.models.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoDataService {
    public static final String ADMIN_LOGIN = "admin";
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final ClientService clientService;
    private final LoanService loanService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final RateService rateService;

    public DemoDataService(UserService userService, PasswordEncoder encoder, ClientService clientService, LoanService loanService, AccountService accountService, TransactionService transactionService, RateService rateService) {
        this.userService = userService;
        this.encoder = encoder;
        this.clientService = clientService;
        this.loanService = loanService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.rateService = rateService;
    }

    @Transactional
    public void generateDemoData() {
        transactionService.deleteAllTransactions();
        accountService.deleteAllAccounts();
//        clientService.deleteAllClients();

        Client client;
        Loan loan;
        Account account;
        Transaction transaction;

        ExchangeRate exchangeRate = new ExchangeRate(47.015393, 1.134572);
        rateService.addDemoRate(exchangeRate);

        for (int i = 1; i < 3; i++) {
            client = new Client("Name" + i, "Surname" + i, "12345678901" + i, "user" + i + "@test.com", "address"+i);
            clientService.addClient(client);

            userService.addUser("user" + i + "@test.com",
                    encoder.encode("password"),
                    UserRole.USER, client,"Name" + i);

            for (CurrencyType currencyType : CurrencyType.values()){
                loan = new Loan(client, 10000, currencyType);
                loanService.addLoan(loan);
            }

            for (CurrencyType currencyType : CurrencyType.values()){
                account = new Account(client, 0, currencyType);
                accountService.addAccount(account);
                transaction = new Transaction(account, account, 1000, 1000, TransactionType.DEPOSIT);
                transactionService.deposit(transaction);
            }
        }
    }

    public void createAdminIfNotExists() {
        if (!userService.adminExists()) {
            Client clientadmin = new Client(ADMIN_LOGIN, ADMIN_LOGIN, "123456789012", ADMIN_LOGIN + "@test.com", "address");
            System.out.println("Create admin in DemoData: "+clientadmin);
            clientService.addClient(clientadmin);
            userService.addUser(ADMIN_LOGIN + "@test.com",
                    encoder.encode("password"),
                    UserRole.ADMIN, clientadmin, ADMIN_LOGIN);
        }
    }
}
