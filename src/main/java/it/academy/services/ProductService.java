package it.academy.services;

import it.academy.DTO.request.ProductDTO;
import it.academy.DTO.response.ProductsDTO;
import lombok.NonNull;

import java.util.Map;

public interface ProductService {
    void addProduct(@NonNull ProductDTO productDTO);
    void updateProduct(@NonNull ProductDTO productDTO);
    void deleteProduct(@NonNull Long id);
    ProductDTO getProductById(@NonNull Long id);
    ProductsDTO getAllExistProducts(@NonNull Integer pageNum, @NonNull Integer countPerPage);
    ProductsDTO getAllExistProductByCategoryName(@NonNull Long categoryId,
                                                 @NonNull Integer pageNum, @NonNull Integer countPerPage);
    ProductsDTO getAllExistProductByFilterParam(@NonNull Integer pageNum,
                                                @NonNull Integer countPerPage, @NonNull Map<String, Object> paramMap);
}
