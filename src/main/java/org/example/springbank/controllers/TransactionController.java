package org.example.springbank.controllers;

import org.example.springbank.constants.Constants;
import org.example.springbank.enums.TransactionType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Transaction;
import org.example.springbank.services.DemoDataService;
import org.example.springbank.services.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final DemoDataService demoDataService;

    public TransactionController(
            TransactionService transactionService, DemoDataService demoDataService) {
        this.transactionService = transactionService;
        this.demoDataService = demoDataService;
    }

    @GetMapping("/")
    public String listAllTransactions(
            Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<Transaction> transactions =
                transactionService.findAll(
                        PageRequest.of(page, Constants.ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("transactions", transactions);
        model.addAttribute("allPages", getPageCount());
        return "/transaction/index";
    }

    @GetMapping("/account/{id}")
    public String listTransactionsByAccount(
            @PathVariable(value = "id") long accountId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model) {
        Account account =
                (accountId != Constants.DEFAULT_ACCOUNT_ID)
                        ? transactionService.findAccount(accountId)
                        : null;
        if (page < 0) page = 0;

        List<Transaction> transactions =
                transactionService.findByAnyAccount(
                        account,
                        PageRequest.of(page, Constants.ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("accounts", transactionService.findAccounts());
        model.addAttribute("transactions", transactions);
        model.addAttribute("byAccountPages", getPageCount(account));
        model.addAttribute("accountId", accountId);

        return "transaction/index";
    }

    @GetMapping("/deposit_page/{id}")
    public String transactionDepositPage(Model model, @PathVariable(value = "id") long accountId) {
        model.addAttribute("accounts", transactionService.findAccounts());
        model.addAttribute("account", transactionService.findAccount(accountId));
        return "transaction/deposit_page";
    }

    @PostMapping(value = "/deposit")
    public String transactionDeposit(
            @RequestParam(value = "fromaccount") long accountId, @RequestParam double amount) {
        Account account =
                (accountId != Constants.DEFAULT_ACCOUNT_ID)
                        ? transactionService.findAccount(accountId)
                        : null;

        Transaction transaction =
                new Transaction(account, account, amount, amount, TransactionType.DEPOSIT);
        transactionService.deposit(transaction);

        return "redirect:/transaction/account/" + accountId;
    }

    @GetMapping("/transfer_page/{id}")
    public String transactionTransferPage(Model model, @PathVariable(value = "id") long accountId) {
        Account sender = transactionService.findAccount(accountId);
        List<Account> accounts = transactionService.findAccounts();

        model.addAttribute("sender", sender);
        model.addAttribute("accounts", accounts);

        Map<String, Double> exchangeRates = new HashMap<>();
        for (Account acc : accounts) {
            String receiverCurrency = acc.getCurrency().toString();
            if (!receiverCurrency.equals(sender.getCurrency())) {
                double rate =
                        transactionService.getTodayRate(
                                sender.getCurrency().toString(), receiverCurrency);
                exchangeRates.put(receiverCurrency, rate);
            }
        }

        model.addAttribute("exchangeRates", exchangeRates);
        return "transaction/transfer_page";
    }

    @PostMapping(value = "/transfer")
    public String transactionTransfer(
            @RequestParam(value = "fromaccount") long fromAccountId,
            @RequestParam(value = "toaccount") long toAccountId,
            @RequestParam double amount,
            @RequestParam(value = "isConversion", required = false) boolean isConversion,
            @RequestParam(value = "convertedAmount", required = false) Double convertedAmount) {
        Account fromAccount =
                (fromAccountId != Constants.DEFAULT_ACCOUNT_ID)
                        ? transactionService.findAccount(fromAccountId)
                        : null;
        Account toAccount =
                (toAccountId != Constants.DEFAULT_ACCOUNT_ID)
                        ? transactionService.findAccount(toAccountId)
                        : null;

        double finalAmount = (isConversion && convertedAmount != null) ? convertedAmount : amount;

        Transaction transaction =
                new Transaction(
                        fromAccount, toAccount, amount, finalAmount, TransactionType.TRANSFER);
        transactionService.transfer(transaction);

        return "redirect:/transaction/account/" + fromAccountId;
    }

    @GetMapping("/reset")
    public String resetDemoData() {
        demoDataService.generateDemoData();
        return "redirect:/transaction/";
    }

    @PostMapping(value = "/search")
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("accounts", transactionService.findAccounts());
        model.addAttribute("transactions", transactionService.findByPattern(pattern, null));

        return "transaction/index";
    }

    private long getPageCount() {
        long totalCount = transactionService.count();
        return (totalCount / Constants.ITEMS_PER_PAGE)
                + ((totalCount % Constants.ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Account account) {
        long totalCount = transactionService.countByReceiverAccount(account);
        return (totalCount / Constants.ITEMS_PER_PAGE)
                + ((totalCount % Constants.ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
