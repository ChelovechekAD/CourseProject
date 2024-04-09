package it.academy.DTO.response;

import it.academy.DTO.request.CreateProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsDTO implements Serializable {

    private List<ProductDTO> productDTOList;
    private Long countOfProducts;

}
