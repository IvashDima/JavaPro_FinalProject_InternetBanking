package org.example.springbank.controllers;

import org.example.springbank.constants.Constants;
import org.example.springbank.enums.CurrencyType;
import org.example.springbank.models.Account;
import org.example.springbank.models.Client;
import org.example.springbank.models.Loan;
import org.example.springbank.services.AccountService;
import org.example.springbank.services.DemoDataService;
import org.example.springbank.services.LoanService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;
    private final DemoDataService demoDataService;

    public LoanController(LoanService loanService, DemoDataService demoDataService) {
        this.loanService = loanService;
        this.demoDataService = demoDataService;
    }

    @GetMapping("/")
    public String listAllLoans(Model model,
                                  @RequestParam(required = false, defaultValue = "0") Integer page){
        if (page < 0) page = 0;

        List<Loan> loans = loanService
                .findAll(PageRequest.of(page, Constants.ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("clients", loanService.findClients());
        model.addAttribute("loans", loans);
        model.addAttribute("allPages", getPageCount());

        return "/loan/index";
    }

    @GetMapping("/client/{id}")
    public String listLoansByClient(
            @PathVariable(value = "id") long clientId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model)
    {
        Client client = (clientId != Constants.DEFAULT_CLIENT_ID) ? loanService.findClient(clientId) : null;
        if (page < 0) page = 0;

        List<Loan> loans = loanService
                .findByClient(client, PageRequest.of(page, Constants.ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("clients", loanService.findClients());
        model.addAttribute("loans", loans);
        model.addAttribute("byClientPages", getPageCount(client));
        model.addAttribute("clientId", clientId);

        return "loan/index";
    }

    @GetMapping("/loan_add_page/{id}")
    public String loanAddPage(Model model,
                                 @PathVariable(value = "id") long clientId) {
        model.addAttribute("clients", loanService.findClients());
        model.addAttribute("client", loanService.findClient(clientId));
        model.addAttribute("currencies", CurrencyType.values());
        return "loan/loan_add_page";
    }

    @PostMapping(value="/add")
    public String loanAdd(@RequestParam(value = "clientId") long clientId,
                             @RequestParam double balance,
                             @RequestParam CurrencyType currency)
    {
        Client client = (clientId != Constants.DEFAULT_CLIENT_ID) ? loanService.findClient(clientId) : null;

        Loan loan = new Loan(client, balance, currency);
        loanService.addLoan(loan);

        return "redirect:/loan/client/" + clientId;
    }

    @GetMapping("/reset")
    public String resetDemoData() {
        demoDataService.generateDemoData();
        return "redirect:/loan/";
    }

    @PostMapping(value = "/search")
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("clients", loanService.findClients());
        model.addAttribute("accounts", loanService.findByPattern(pattern, null));

        return "loan/index";
    }

    private long getPageCount() {
        long totalCount = loanService.count();
        return (totalCount / Constants.ITEMS_PER_PAGE) + ((totalCount % Constants.ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Client client) {
        long totalCount = loanService.countByClient(client);
        return (totalCount / Constants.ITEMS_PER_PAGE) + ((totalCount % Constants.ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
