package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "users")
public class User {

    @Id
    private String id;

    @NotEmpty(message = "first name is mandatory")
    private String firstName;

    @NotEmpty(message = "last name is mandatory")
    private String lastname;

    @Email(message = "please provide a valid email address")
    private String emailAddress;


    @NotNull(message = "please provide accopunt credentials")
    @Valid
    private Account account;

}
