package it.academy.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedItemCartDTO {
    private Long userId;
    private Long productId;
    private Integer quantity;

}
