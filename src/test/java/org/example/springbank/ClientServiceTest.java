package org.example.springbank;

import org.example.springbank.models.Client;
import org.example.springbank.repositories.ClientRepository;
import org.example.springbank.services.ClientService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {
    @Test
    public void testGetAllClients() {
        ClientRepository mockRepo = mock(ClientRepository.class);
        when(mockRepo.findAll()).thenReturn(Arrays.asList(
                new Client("Dima", "Berg", "1234567", "dima@test.com", "addressFamily"),
                new Client("Iryna", "Berg", "1234568", "iryna@test.com", "addressFamily")));
        ClientService clientService = new ClientService(mockRepo);

        List<Client> clients = clientService.getAllClients();
        assertEquals(2, clients.size());
    }

    @Test
    public void testGetClientByName() {
        ClientRepository mockRepo = mock(ClientRepository.class);
        when(mockRepo.findByName("Dima")).thenReturn(Optional.of(
                new Client("Dima", "Berg", "1234567", "dima@test.com", "addressFamily")));
        ClientService clientService = new ClientService(mockRepo);

        Client client = clientService.getByName("Dima");
        assertNotNull(client);
        assertEquals("dima@test.com", client.getEmail());
    }
}
