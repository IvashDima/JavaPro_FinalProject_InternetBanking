package org.example.springbank.config;

import org.example.springbank.dto.CustomUserDTO;
import org.example.springbank.enums.UserRegisterType;
import org.example.springbank.models.CustomUser;
import org.example.springbank.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public AuthHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication)
            throws IOException {
        System.out.println("OAuth2 authentication successful!!!");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.getOrDefault("email", "");

        System.out.println("User email: " + email);

        if (email.isEmpty()) {
            System.out.println("Email is missing from OAuth2 provider.");
            httpServletResponse.sendRedirect("/login?error");
            return;
        }

        CustomUser user = userService.findByEmail(email);

        if (user == null) {
            System.out.println("User not found, creating new Google user");
            CustomUserDTO userDTO =
                    CustomUserDTO.of(
                            email,
                            (String) attributes.get("name"),
                            (String) attributes.get("picture"));

            userService.addGoogleUser(userDTO);
            httpServletResponse.sendRedirect("/");
        } else if (UserRegisterType.GOOGLE.equals(user.getType())) {
            System.out.println("Existing Google user, logging in");
            httpServletResponse.sendRedirect("/");
        } else {
            System.out.println("User exists but not registered via Google");
            httpServletResponse.sendRedirect("/login?errorEmail");
        }
    }
}
