package it.academy.DTO.response;

import it.academy.DTO.request.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriesDTO {

    private List<CategoryDTO> categoryDTOList;
    private Long countOfCategories;

}
