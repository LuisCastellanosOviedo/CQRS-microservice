package com.springbank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Size(min = 2,message = "user name must have at least 2 characters")
    private String username;

    @Size(min = 7,message = "password  must have at least 7 characters")
    private String password;

    @NotNull(message = "you should specify at least one role")
    private List<Role> roles;

}
