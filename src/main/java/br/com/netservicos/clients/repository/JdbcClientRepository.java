package br.com.netservicos.clients.repository;

import br.com.netservicos.clients.model.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

import static br.com.netservicos.clients.constants.ClientRepositoryConstants.NAME_STATEMENT;

@Repository
public class JdbcClientRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> findClientsByNameLike(String name) {

        return jdbcTemplate
                .query(NAME_STATEMENT,
                        new Object[]{"%" + name + "%"},
                        new int[]{Types.VARCHAR},
                        (rs, rowNum) -> {
                    Client client = new Client();
                    client.setId(rs.getLong("ID"));
                    client.setName(rs.getString("NAME"));
                    client.setEmail(rs.getString("EMAIL"));
                    client.setCpfOrCnpj(rs.getString("CPF_CNPJ"));
                    client.setBirthdayDate(rs.getDate(("BIRTHDAY_DATE")).toLocalDate());
                    return client;
                });
    }
}
