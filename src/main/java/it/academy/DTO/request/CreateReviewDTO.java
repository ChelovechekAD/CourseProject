package it.academy.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReviewDTO implements Serializable {
    private String description;
    private Double rate;
    private Long productId;
    private Long userId;
}
