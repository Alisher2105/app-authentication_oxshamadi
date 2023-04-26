package uz.pdp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;
}
