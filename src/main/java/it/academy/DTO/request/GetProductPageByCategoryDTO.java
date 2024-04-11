package it.academy.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductPageByCategoryDTO {

    private Integer pageNum;
    private Integer countPerPage;
    private Long categoryId;

}
