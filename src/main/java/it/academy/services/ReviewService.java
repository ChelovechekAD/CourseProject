package it.academy.services;

import it.academy.DTO.request.CreateReviewDTO;
import it.academy.DTO.response.ReviewDTO;
import it.academy.DTO.response.ReviewsDTO;
import lombok.NonNull;

public interface ReviewService {
    void createReview(CreateReviewDTO createReviewDTO);
    ReviewDTO getSingleReviewOnProductByUserId(@NonNull Long userId, @NonNull Long prodId);
    void deleteReviewOnProductByUserId(@NonNull Long userId, @NonNull Long prodId);
    ReviewsDTO getAllReviewsPage(@NonNull Integer countPerPage, @NonNull Integer pageNum);
}
