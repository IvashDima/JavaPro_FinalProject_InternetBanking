package org.example.springbank.config;

import org.example.springbank.services.DemoDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
@PropertySource("classpath:application.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration implements WebMvcConfigurer {

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Bean
    @Scope("prototype")
    public SimpleMailMessage messageTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Transaction notification");
        message.setText("Transaction completed successfully:\n\n [%s] Sender: %s, Receiver: %s, Amount: %s");
        message.setFrom(fromAddress);

        return message;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/");
    }
    @Profile("local")
    @Bean
    public CommandLineRunner demo(final DemoDataService demoDataService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {

                demoDataService.generateDemoData();
            }
        };
    }
}
