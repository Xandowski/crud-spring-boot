package br.com.netservicos.clients.service;

import br.com.netservicos.clients.model.Client;
import br.com.netservicos.clients.repository.ClientRepository;
import br.com.netservicos.clients.repository.JdbcClientRepository;
import br.com.netservicos.clients.util.ClientGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Client Service")
public class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private JdbcClientRepository jdbcClientRepositoryMock;

    private ClientService service;

    @BeforeEach
    void setUp(){
        service = new ClientService(repository, jdbcClientRepositoryMock);

        BDDMockito.given(jdbcClientRepositoryMock.findClientsByNameLike("Alexandre"))
                .willReturn(List.of(ClientGenerator.createValidClient()));

        BDDMockito.given(repository.findById(ArgumentMatchers.anyLong()))
                .willReturn(Optional.of(ClientGenerator.createValidClient()));
    }

    @Test
    @DisplayName("findClientsByNameLike returns list of clients containing given name when successful")
    void findByNameLike_ReturnsListOfClientsContainingGivenName_WhenSuccessful(){
        List<Client> client = service.findClientsByNameLike("Alexandre");

        Assertions.assertThat(client).isNotEmpty().isEqualTo(jdbcClientRepositoryMock.findClientsByNameLike("Alexandre"));
    }

    @Test
    @DisplayName("findClientsByNameLike returns empty list when client not found")
    void findByNameLike_ReturnsEmptyList_WhenClientNotFound(){
        List<Client> client = service.findClientsByNameLike("ra");

        Assertions.assertThat(client).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns client when successful")
    void findById_ReturnsClient_WhenSuccesful(){
        Long expectedId = ClientGenerator.createValidClient().getId();
        Client client = service.findById(1L);

        Assertions.assertThat(client).isNotNull();
        Assertions.assertThat(client.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById returns ResponseStatusException when client not found")
    void findById_ReturnsResponseStatusException_WhenSuccesful(){
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());


        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> service.findById(10L))
                .withMessageContaining("Client not found");
    }

    @Test
    @DisplayName("findAll returns list of clients when successful")
    void findAll_ReturnsListOfClients_WhenSuccesful(){
        BDDMockito.given(repository.findAll())
                .willReturn(List.of(ClientGenerator.createValidClient()));

        List<Client> clients = service.findAll();

        Assertions.assertThat(clients).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("findAll returns empty list when no client is recorded")
    void findAll_ReturnsEmptyList_WhenNoClientRecorded(){
        BDDMockito.given(repository.findAll())
                .willReturn(new ArrayList<>());

        List<Client> clients = service.findAll();

        Assertions.assertThat(clients).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns client recorded when successful")
    void save_ReturnsClientRecorded_WhenSuccessful(){
        BDDMockito.given(repository.save(ArgumentMatchers.any(Client.class)))
                .willReturn(ClientGenerator.createValidClient());

        Client client = ClientGenerator.createValidClient();
        Client clientSaved = service.saveClient(client);

        Assertions.assertThat(clientSaved).isNotNull().isEqualTo(client);
    }

    @Test
    @DisplayName("save returns client updated when successful")
    void update_ReturnsClientUpdated_WhenSuccessful(){
        Client client = ClientGenerator.createValidClient();
        client.setName("Alexandre Morais");

        BDDMockito.given(repository.save(ArgumentMatchers.any(Client.class)))
                .willReturn(client);

        BDDMockito.given(repository.findById(ArgumentMatchers.anyLong()))
                .willReturn(Optional.of(client));

        Client clientUpdated = service.update(client.getId(), client);

        Assertions.assertThat(clientUpdated).isNotNull();
        Assertions.assertThat(clientUpdated.getName()).isEqualTo("Alexandre Morais");
    }

    @Test
    @DisplayName("delete removes client when successful")
    void delete_RemovesClient_WhenSuccessful(){
        Assertions.assertThatCode(() -> service.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("save throw DataIntegrityViolationException when cpfjOrCnpj is invalid")
    void save_ThrowsDataIntegrityViolationException_WhenCpfCnpjIsInvalid(){
        Client client = ClientGenerator.createValidClient();
        BDDMockito.given(repository.save(ArgumentMatchers.any(Client.class)))
                .willReturn(client);

        Client client2 = ClientGenerator.createValidClient();
        BDDMockito.given(repository.save(ArgumentMatchers.any(Client.class)))
                .willThrow(DataIntegrityViolationException.class);

        Assertions.assertThatThrownBy(() ->service.saveClient(client2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
