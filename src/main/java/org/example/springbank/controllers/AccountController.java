package org.example.springbank.controllers;

import org.example.springbank.constants.Constants;
import org.example.springbank.enums.CurrencyType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.services.AccountService;
import org.example.springbank.services.DemoDataService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final DemoDataService demoDataService;

    public AccountController(AccountService accountService, DemoDataService demoDataService) {
        this.accountService = accountService;
        this.demoDataService = demoDataService;
    }

    @GetMapping("/")
    public String listAllAccounts(
            Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<Account> accounts =
                accountService.findAll(
                        PageRequest.of(page, Constants.ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("clients", accountService.findClients());
        model.addAttribute("accounts", accounts);
        model.addAttribute("allPages", getPageCount());

        return "/account/index";
    }

    @GetMapping("/client/{id}")
    public String listAccountsByClient(
            @PathVariable(value = "id") long clientId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model) {
        Client client =
                (clientId != Constants.DEFAULT_CLIENT_ID)
                        ? accountService.findClient(clientId)
                        : null;
        if (page < 0) page = 0;

        List<Account> accounts =
                accountService.findByClient(
                        client,
                        PageRequest.of(page, Constants.ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("clients", accountService.findClients());
        model.addAttribute("accounts", accounts);
        model.addAttribute("byClientPages", getPageCount(client));
        model.addAttribute("clientId", clientId);

        return "account/index";
    }

    @GetMapping("/account_add_page/{id}")
    public String accountAddPage(Model model, @PathVariable(value = "id") long clientId) {
        model.addAttribute("clients", accountService.findClients());
        model.addAttribute("client", accountService.findClient(clientId));
        model.addAttribute("currencies", CurrencyType.values());
        return "account/account_add_page";
    }

    @PostMapping(value = "/add")
    public String accountAdd(
            @RequestParam(value = "clientId") long clientId,
            @RequestParam double balance,
            @RequestParam CurrencyType currency) {
        Client client =
                (clientId != Constants.DEFAULT_CLIENT_ID)
                        ? accountService.findClient(clientId)
                        : null;

        Account account = new Account(client, balance, currency);
        accountService.addAccount(account);

        return "redirect:/account/client/" + clientId;
    }

    @GetMapping("/reset")
    public String resetDemoData() {
        demoDataService.generateDemoData();
        return "redirect:/account/";
    }

    @PostMapping(value = "/search")
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("clients", accountService.findClients());
        model.addAttribute("accounts", accountService.findByPattern(pattern, null));

        return "account/index";
    }

    private long getPageCount() {
        long totalCount = accountService.count();
        return (totalCount / Constants.ITEMS_PER_PAGE)
                + ((totalCount % Constants.ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Client client) {
        long totalCount = accountService.countByClient(client);
        return (totalCount / Constants.ITEMS_PER_PAGE)
                + ((totalCount % Constants.ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
