package org.example.springbank;

import org.example.springbank.controllers.ClientController;
import org.example.springbank.controllers.GlobalExceptionHandler;
import org.example.springbank.controllers.UserController;
import org.example.springbank.enums.UserRegisterType;
import org.example.springbank.enums.UserRole;
import org.example.springbank.models.Client;
import org.example.springbank.models.CustomUser;
import org.example.springbank.services.ClientService;
import org.example.springbank.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private ClientService clientMockService;
    @Mock
    private UserService userMockService;

    private MockMvc mockMvc;
    private ClientController clientController;
    private UserController userController;
    private Client client1, client2;
    private CustomUser customUser1, customUser2;

    @BeforeEach
    void setUp() {
        client1 = new Client("Dima", "Berg", "1234567", "dima@test.com", "addressFamily");
        client2 = new Client("Iryna", "Berg", "1234568", "iryna@test.com", "addressFamily");

        customUser1 = new CustomUser("dima@test.com", "Dima", encoder.encode("password"), UserRole.USER, UserRegisterType.FORM, client1);
        customUser2 = new CustomUser("iryna@test.com", "Iryna", encoder.encode("password"), UserRole.USER, UserRegisterType.FORM, client2);

        userController = new UserController(userMockService, encoder, clientMockService, null);
        clientController = new ClientController(clientMockService, null);
        mockMvc = MockMvcBuilders.standaloneSetup(userController, clientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void shouldReturnListOfClients_whenGetClientsController() throws Exception {
        when(clientMockService.getAllClients()).thenReturn(Arrays.asList(client1, client2));

        mockMvc.perform(get("/client/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dima"))
                .andExpect(jsonPath("$[1].name").value("Iryna"));
    }

    @Test
    public void shouldReturnClient_whenGetClientByNameExistsController() throws Exception {
        when(clientMockService.getByName("Dima")).thenReturn((client1));

        mockMvc.perform(get("/client/api/name/Dima")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dima"));
    }

    @Test
    public void shouldReturnListOfUsers_whenGetUsersController() throws Exception {
        when(userMockService.getAllUsers()).thenReturn(Arrays.asList(customUser1, customUser2));

        mockMvc.perform(get("/user/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dima"))
                .andExpect(jsonPath("$[1].name").value("Iryna"));
    }

    @Test
    public void shouldReturnUser_whenGetUserByEmailExistsController() throws Exception {
        when(userMockService.getByEmail("dima@test.com")).thenReturn((customUser1));

        mockMvc.perform(get("/user/api/email/dima@test.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("dima@test.com"));
    }
}
