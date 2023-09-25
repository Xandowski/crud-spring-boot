package br.com.netservicos.clients.controller;

import br.com.netservicos.clients.model.Client;
import br.com.netservicos.clients.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/clients")
@Slf4j
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Client> findAll(){
        log.info("Início da busca por clientes...");
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody @Valid @NotNull Client client){
        log.info("Início do processo de criação de novo cliente...");
        return service.saveClient(client);
    }

    @GetMapping("/{id}")
    public Client findById(@PathVariable() @NotNull @Positive Long id){
        log.info("Início da busca pelo cliente do id: " + id);
        return service.findById(id);
    }

    @GetMapping("/name")
    public List<Client> findClientsByName(@RequestParam @NotNull String name){
        log.info("Início da busca por clientes contendo nome: " + name);
        return service.findClientsByNameLike(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id){
        log.info("Iniciando processo para remover o cliente com id: " + id);
        service.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Client update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull Client client){
        log.info("Início da atualização do cliente com id: " + id);
        return service.update(id, client);
    }
}
