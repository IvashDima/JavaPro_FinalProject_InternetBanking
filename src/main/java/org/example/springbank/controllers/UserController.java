package org.example.springbank.controllers;

import org.example.springbank.enums.UserRole;
import org.example.springbank.json.Rate;
import org.example.springbank.models.Client;
import org.example.springbank.models.CustomUser;
import org.example.springbank.retrievers.RateRetriever;
import org.example.springbank.services.ClientService;
import org.example.springbank.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ClientService clientService;
    private final RateRetriever rateRetriever;
    private static final Logger logger = LoggerFactory.getLogger(RateRetriever.class);


    public UserController(UserService userService, PasswordEncoder passwordEncoder, ClientService clientService, RateRetriever rateRetriever) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.clientService = clientService;
        this.rateRetriever = rateRetriever;
    }

    @GetMapping("/user/api")
    @ResponseBody
    public List<CustomUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/api/email/{email}")
    @ResponseBody
    public CustomUser getUserByEmail(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/")
    public String index(Model model) {
        resolveUserAndAddAttributes(model, true);
        Rate rateData = null;
        try {
//            rateData = rateRetriever.getRate(); // turn on to receive rate on load page
        } catch (Exception ex) {
            logger.error("Error while retrieving rate", ex);
        }
        model.addAttribute("rateData", rateData);
        return "index";
    }

    @GetMapping("/user_profile")
    public String profile(Model model) {
        resolveUserAndAddAttributes(model, true);
        return "user_profile";
    }

    @PostMapping(value = "/update")
    public String update(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String address) {
        CustomUser customUser = getCurrentCustomUser();
        String email = customUser.getEmail();
        customUser.getClient().setPhone(phone);
        customUser.getClient().setAddress(address);

        userService.updateUser(email, name);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "/newuser")
    public String add(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String address,
                         @ModelAttribute @Valid CustomUser form,
                         BindingResult binding,
                         Model model) {
        String passHash = passwordEncoder.encode(password);

        if (password.length() < 8)
            return "error";

        Client client = new Client();
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);
        client.setPhone(phone);
        client.setAddress(address);

        if ( ! userService.addUser(email, passHash, UserRole.USER, client, name)) {
            model.addAttribute("exists", true);
            model.addAttribute("email", email);
            return "register";
        }

        if (binding.hasErrors()) {
            return "register";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam(name = "toDelete[]", required = false) List<Long> ids,
                         Model model) {
        userService.deleteUsers(ids);
        model.addAttribute("users", userService.getAllUsers());

        return "admin";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // SpEL !!!
    public String admin(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }


    @GetMapping("/error/403")
    public String unauthorized(Model model) {

        model.addAttribute("email", getCurrentUserEmail());
        return "error/403";
    }

    private CustomUser resolveUserAndAddAttributes(Model model, boolean includeDetails) {
        String email = getCurrentUserEmail();
        CustomUser dbUser = userService.findByEmail(email);

        if (dbUser == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        model.addAttribute("email", dbUser.getEmail());

        if (includeDetails) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();

            Collection<? extends GrantedAuthority> roles = (principal instanceof DefaultOidcUser)
                    ? ((DefaultOidcUser) principal).getAuthorities().stream()
                    .filter(a -> a.getAuthority().startsWith("ROLE_"))
                    .collect(Collectors.toList())
                    : ((User) principal).getAuthorities();

            model.addAttribute("roles", roles);
            model.addAttribute("admin", isAdmin(principal));
            model.addAttribute("name", dbUser.getName());
        }

        Client client = dbUser.getClient();
        if (client != null) {
            model.addAttribute("clientid", client.getId());
            if (includeDetails) {
                model.addAttribute("phone", client.getPhone());
                model.addAttribute("address", client.getAddress());
            }
        } else {
            throw new IllegalStateException("Client not associated with user: " + email);
        }

        return dbUser;
    }

    public CustomUser getCurrentCustomUser() {
        String email = getCurrentUserEmail();
        return userService.findByEmail(email);
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else if (principal instanceof DefaultOidcUser) {
            return ((DefaultOidcUser) principal).getAttribute("email");
        } else {
            throw new IllegalStateException("Unknown principal type: " + principal.getClass());
        }
    }

    private boolean isAdmin(Object principal) {
        Collection<? extends GrantedAuthority> authorities;

        if (principal instanceof User) {
            authorities = ((User) principal).getAuthorities();
        } else if (principal instanceof DefaultOidcUser) {
            authorities = ((DefaultOidcUser) principal).getAuthorities();
        } else {
            throw new IllegalStateException("Unknown principal type: " + principal.getClass());
        }

        return authorities.stream()
                .anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));
    }
}
