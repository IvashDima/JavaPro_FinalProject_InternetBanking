package org.example.springbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.example.springbank.enums.UserRegisterType;
import org.example.springbank.enums.UserRole;
import org.example.springbank.models.Client;
import org.example.springbank.models.CustomUser;
import org.example.springbank.repositories.ClientRepository;
import org.example.springbank.repositories.UserRepository;
import org.example.springbank.services.ClientService;
import org.example.springbank.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock private PasswordEncoder encoder;
    @Mock private ClientRepository clientMockRepository;
    @Mock private UserRepository userMockRepository;
    @InjectMocks private ClientService clientMockService;
    @InjectMocks private UserService userMockService;
    private Client client1, client2;
    private CustomUser customUser1, customUser2;

    @BeforeEach
    void setUp() {
        client1 = new Client("Dima", "Berg", "1234567", "dima@test.com", "addressFamily");
        client2 = new Client("Iryna", "Berg", "1234568", "iryna@test.com", "addressFamily");

        customUser1 =
                new CustomUser(
                        "dima@test.com",
                        "Dima",
                        encoder.encode("password"),
                        UserRole.USER,
                        UserRegisterType.FORM,
                        client1);
        customUser2 =
                new CustomUser(
                        "iryna@test.com",
                        "Iryna",
                        encoder.encode("password"),
                        UserRole.USER,
                        UserRegisterType.FORM,
                        client2);
    }

    @Test
    public void shouldReturnListOfClients_whenGetClientsService() {
        when(clientMockRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> clients = clientMockService.getAllClients();

        assertEquals(2, clients.size());
    }

    @Test
    public void shouldReturnClient_whenGetClientByNameExistsService() {
        when(clientMockRepository.findByName("Dima")).thenReturn(Optional.of(client1));

        Client foundClient = clientMockService.getByName("Dima");

        assertNotNull(foundClient);
        assertEquals("dima@test.com", foundClient.getEmail());
    }

    @Test
    public void shouldReturnListOfUsers_whenGetUsersService() {
        when(userMockRepository.findAll()).thenReturn(Arrays.asList(customUser1, customUser2));

        List<CustomUser> users = userMockService.getAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    public void shouldReturnUser_whenGetUserByEmailExistsService() {
        when(userMockRepository.findByEmail("dima@test.com")).thenReturn(Optional.of(customUser1));

        CustomUser customUser = userMockService.getByEmail("dima@test.com");

        assertNotNull(customUser);
        assertEquals("Dima", customUser.getName());
    }
}
