package it.academy.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegUserDTO implements Serializable {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private String password;
    private String passwordConfirm;
    private String city;
    private String street;
    private String building;
    private String phoneNumber;

}
