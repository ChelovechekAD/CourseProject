package it.academy.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO implements Serializable {

    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private Double price;
    private Double rating;
    private String imageLink;

}
