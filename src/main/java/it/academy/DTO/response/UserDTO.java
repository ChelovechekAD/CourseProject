package it.academy.DTO.response;

import it.academy.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String building;
    private String city;
    private String street;
    private String email;
    private String name;
    private String phoneNumber;
    private String surname;
    private List<String> roles;

}
