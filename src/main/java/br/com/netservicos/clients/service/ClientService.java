package br.com.netservicos.clients.service;

import br.com.netservicos.clients.repository.JdbcClientRepository;
import br.com.netservicos.clients.model.Client;
import br.com.netservicos.clients.repository.ClientRepository;
import br.com.netservicos.clients.util.validation.ClientRequestValidationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;

    private final JdbcClientRepository jdbcRepository;

    public ClientService(ClientRepository repository, JdbcClientRepository jdbcRepository) {
        this.repository = repository;
        this.jdbcRepository = jdbcRepository;
    }

    public Client saveClient(Client client) {
        ClientRequestValidationUtils.validateCpfOrCnpj(client.getCpfOrCnpj());
        return repository.save(client);
    }

    public List<Client> findAll(){
        return repository.findAll();
    }

    public Client findById(Long id){
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    public List<Client> findClientsByNameLike(String name) {
        return jdbcRepository.findClientsByNameLike(name);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public Client update(Long id, Client client){
        return repository
                .findById(id)
                .map( clientFounded -> {
                    client.setId(clientFounded.getId());
                    repository.save(client);
                    return clientFounded;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }
}
