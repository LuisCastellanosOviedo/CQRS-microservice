package com.springbank.user.cmd.api.security;

public interface PasswordEncoder {

    String hashPasseord(String password);
}
