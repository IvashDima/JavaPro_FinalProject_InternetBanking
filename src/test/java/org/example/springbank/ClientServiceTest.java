package org.example.springbank;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientMockRepository;

    @InjectMocks
    private ClientService clientMockService;

    private Client client1, client2;

    @BeforeEach
    void setUp() {
        client1 = new Client("Dima", "Berg", "1234567", "dima@test.com", "addressFamily");
        client2 = new Client("Iryna", "Berg", "1234568", "iryna@test.com", "addressFamily");
    }

    @Test
    public void testGetAllClients() {
        when(clientMockRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> clients = clientMockService.getAllClients();
        assertEquals(2, clients.size());
    }

    @Test
    public void testGetClientByName() {
        when(clientMockRepository.findByName("Dima")).thenReturn(Optional.of(client1));

        Client client = clientMockService.getByName("Dima");
        assertNotNull(client);
        assertEquals("dima@test.com", client.getEmail());
    }
}
