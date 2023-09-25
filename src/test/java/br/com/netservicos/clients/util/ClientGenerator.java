package br.com.netservicos.clients.util;

import br.com.netservicos.clients.model.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClientGenerator {

    public static Client createClientToBeSaved(){
        return Client.builder()
                .name("Alexandre")
                .email("alexandre@test.com")
                .cpfOrCnpj("12345678901")
                .birthdayDate(LocalDate.parse("28-10-1994", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }

    public static Client createValidClient(){
        return Client.builder()
                .id(1L)
                .name("Alexandre")
                .email("alexandre@test.com")
                .cpfOrCnpj("12345678901")
                .birthdayDate(LocalDate.parse("28-10-1994", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }


}
