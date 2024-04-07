package it.academy.DTO.request;

import it.academy.models.Category;
import jakarta.persistence.*;
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
    private String imageLink;

}
