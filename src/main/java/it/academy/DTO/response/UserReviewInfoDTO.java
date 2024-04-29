package it.academy.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReviewInfoDTO {

    private String description;
    private Double rating;
    private Long productId;
    private String imageLink;
    private String name;

}
