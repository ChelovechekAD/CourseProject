package it.academy.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserDTO {

    private Long id;
    private String building;
    private String city;
    private String street;
    private String email;
    private String name;
    private String phoneNumber;
    private String surname;
}
