package it.academy.services;

import it.academy.DTO.request.CreateProductDTO;
import it.academy.DTO.request.GetProductPageByCategoryDTO;
import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.response.ProductDTO;
import it.academy.DTO.response.ProductsDTO;
import lombok.NonNull;

import java.util.Map;

public interface ProductService {
    void addProduct(@NonNull CreateProductDTO createProductDTO);

    void updateProduct(@NonNull CreateProductDTO createProductDTO);

    void deleteProduct(@NonNull Long id);

    ProductDTO getProductById(@NonNull Long id);

    ProductsDTO getAllExistProducts(@NonNull RequestDataDetailsDTO requestDataDetailsDTO);

    ProductsDTO getAllExistProductByCategoryName(@NonNull GetProductPageByCategoryDTO dto);

    ProductsDTO getAllExistProductByFilterParam(@NonNull Integer pageNum,
                                                @NonNull Integer countPerPage, @NonNull Map<String, Object> paramMap);
}
