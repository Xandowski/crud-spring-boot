package br.com.netservicos.clients.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Column(length = 100)
    private String name;

    @NotNull(message = "CpfOrCnpj cannot be null")
    @NotEmpty(message = "CpfOrCnpj cannot be empty")
    @Column(name = "CPF_CNPJ", length = 14, unique = true)
    private String cpfOrCnpj;

    @Email(message = "Invalid email")
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Size(max = 50, message = "Email length must not exced 50 characters")
    @Column(length = 50)
    private String email;

    @NotNull(message = "birthdayDate cannot be null")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthdayDate;
}
