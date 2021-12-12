package se.casparsylwan.cugexam.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AutenticationRequest {

    private String userEmail;
    private String password;
}
