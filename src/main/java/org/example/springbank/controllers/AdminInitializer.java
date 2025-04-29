package org.example.springbank.controllers;

import org.example.springbank.services.DemoDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    private final DemoDataService demoDataService;

    public AdminInitializer(DemoDataService demoDataService) {
        this.demoDataService = demoDataService;
    }

    @Override
    public void run(String... args) {
        demoDataService.createAdminIfNotExists();
    }
}
