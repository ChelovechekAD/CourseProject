package it.academy.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemProductDTO {
    private Long productId;
    private Long count;
    private Double price;
    private String name;
    private String image_link;
}

