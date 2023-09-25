package br.com.netservicos.clients.repository;

import br.com.netservicos.clients.model.Client;
import br.com.netservicos.clients.util.ClientGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Tests for Client Repository")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository repository;

    @Test
    @DisplayName("save persist client when successful")
    void save_PersisClient_WhenSuccessful(){
        Client clientToBeSaved = ClientGenerator.createClientToBeSaved();
        Client savedClient = this.repository.save(clientToBeSaved);
        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getName()).isEqualTo(clientToBeSaved.getName());
    }

    @Test
    @DisplayName("save throw ConstraintViolationException when Name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
        Client clientToBeSaved = new Client();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.repository.save(clientToBeSaved))
                .withMessageContaining("Name cannot be empty");
    }

    @Test
    @DisplayName("save throw DataIntegrityViolationException when cpf_cnpj already exists")
    void save_ThrowsDataIntegrityViolationException_WhenCpfOrCnpjAlreadyExists(){
        Client clientToBeSaved = ClientGenerator.createClientToBeSaved();
        Client clientToBeSaved2 = ClientGenerator.createClientToBeSaved();

        Client clientSaved = this.repository.save(clientToBeSaved);

        Assertions.assertThat(clientSaved).isNotNull();

        Assertions.assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.repository.save(clientToBeSaved2))
                .withMessageContaining("could not execute statement");
    }

    @Test
    @DisplayName("save throw ConstraintViolationException when Name is null")
    void save_ThrowsConstraintViolationException_WhenNameIsNull(){
        Client clientToBeSaved = new Client();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.repository.save(clientToBeSaved))
                .withMessageContaining("Name cannot be null");
    }


    @Test
    @DisplayName("save throw ConstraintViolationException when CpfOrCnpj is empty")
    void save_ThrowsConstraintViolationException_WhenCpfOrCnpjIsEmpty(){
        Client clientToBeSaved = new Client();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.repository.save(clientToBeSaved))
                .withMessageContaining("CpfOrCnpj cannot be empty");
    }

    @Test
    @DisplayName("save throw ConstraintViolationException when CpfOrCnpj is null")
    void save_ThrowsConstraintViolationException_WhenCpfOrCnpjIsNull(){
        Client clientToBeSaved = new Client();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.repository.save(clientToBeSaved))
                .withMessageContaining("CpfOrCnpj cannot be null");
    }

    @Test
    @DisplayName("save throw ConstraintViolationException when birthdayDate is null")
    void save_ThrowsConstraintViolationException_WhenBirthdayDateIsNull(){
        Client clientToBeSaved = new Client();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.repository.save(clientToBeSaved))
                .withMessageContaining("birthdayDate cannot be null");
    }

    @Test
    @DisplayName("save throw ConstraintViolationException when Email is null")
    void save_ThrowsConstraintViolationException_WhenEmailIsNull(){
        Client clientToBeSaved = new Client();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.repository.save(clientToBeSaved))
                .withMessageContaining("Email cannot be null");
    }

    @Test
    @DisplayName("save update client when successful")
    void save_UpdateClient_WhenSuccessful(){
        Client clientToBeSaved = ClientGenerator.createClientToBeSaved();
        Client savedClient = this.repository.save(clientToBeSaved);

        savedClient.setName("Maria");
        Client clientUpdated = this.repository.save(savedClient);

        Assertions.assertThat(clientUpdated).isNotNull();
        Assertions.assertThat(clientUpdated.getName()).isEqualTo(savedClient.getName());
    }

    @Test
    @DisplayName("delete remove client when successful")
    void delete_RemoveClient_WhenSuccessful(){
        Client clientToBeSaved = ClientGenerator.createClientToBeSaved();
        Client savedClient = this.repository.save(clientToBeSaved);

        this.repository.delete(savedClient);

        Optional<Client> clientOptional = this.repository.findById(savedClient.getId());

        Assertions.assertThat(clientOptional).isEmpty();
    }

    @Test
    @DisplayName("findById return a client by id when successful")
    void findById_FindClientById_WhenSuccessful(){
        Client clientToBeSaved = ClientGenerator.createClientToBeSaved();
        Client savedClient = this.repository.save(clientToBeSaved);

        Optional<Client> clientToBeFinded = this.repository.findById(savedClient.getId());

        Assertions.assertThat(clientToBeFinded).isPresent();
    }

    @Test
    @DisplayName("findAll return a list of clients when successful")
    void findAll_ReturnsListOfClient_WhenSuccesful(){
        Client clientToBeSaved = ClientGenerator.createClientToBeSaved();
        Client savedClient = this.repository.save(clientToBeSaved);

        List<Client> clients = this.repository.findAll();

        Assertions.assertThat(clients).isNotEmpty().contains(savedClient);
    }

    @Test
    @DisplayName("findAll returns empty list when no client is found")
    void findAll_ReturnsEmptyList_WhenNoClientIsFound(){
        List<Client> clients = this.repository.findAll();

        Assertions.assertThat(clients).isEmpty();
    }

}
