package br.com.netservicos.clients.repository;

import br.com.netservicos.clients.model.Client;
import br.com.netservicos.clients.util.ClientGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for JDBC Client Repository")
class JdbcClientRepositoryTest {
    @Mock
    private JdbcClientRepository jdbcClientRepositoryMock;

    @BeforeEach
    void setUp(){
        List<Client> clients = List.of(ClientGenerator.createClientToBeSaved());
        BDDMockito.when(jdbcClientRepositoryMock.findClientsByNameLike(ArgumentMatchers.any()))
                .thenReturn(clients);
    }
    @Test
    @DisplayName("findByNameLike returns list of clients containing that name when successful")
    void findByNameLike(){
        List<Client> clients = this.jdbcClientRepositoryMock.findClientsByNameLike("Alexandre");

        Assertions.assertThat(clients).isNotEmpty();
    }
}