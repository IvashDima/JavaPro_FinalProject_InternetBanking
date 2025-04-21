package org.example.springbank;

import org.example.springbank.controllers.ClientController;
import org.example.springbank.controllers.GlobalExceptionHandler;
import org.example.springbank.exceptions.ClientNotFoundException;
import org.example.springbank.models.Client;
import org.example.springbank.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @Mock
    private ClientService clientMockService;

    private MockMvc mockMvc;
    private ClientController clientController;

    private Client client1, client2;

    @BeforeEach
    void setUp() {
        client1 = new Client("Dima", "Berg", "1234567", "dima@test.com", "addressFamily");
        client2 = new Client("Iryna", "Berg", "1234568", "iryna@test.com", "addressFamily");

        clientController = new ClientController(clientMockService, null);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
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
    public void shouldReturnError_whenGetClientByNameNotExistsController() throws Exception {
        when(clientMockService.getByName("Unknown")).thenThrow(new ClientNotFoundException("Unknown"));

        mockMvc.perform(get("/client/api/name/Unknown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Client not found: Unknown"));
    }
}
