package com.salvatore.dipalo.movies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentials {

    @NotEmpty
    @Size(min = 3, max = 30)
    private String username;
    @NotEmpty
    @Size(min = 8, max = 40)
    private String password;

}
