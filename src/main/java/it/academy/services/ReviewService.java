package it.academy.services;

import it.academy.DTO.request.*;
import it.academy.DTO.response.ReviewDTO;
import it.academy.DTO.response.ReviewsDTO;
import it.academy.DTO.response.UserReviewsDTO;
import lombok.NonNull;

public interface ReviewService {
    void createReview(CreateReviewDTO createReviewDTO);

    ReviewDTO getSingleReviewOnProductByUserId(@NonNull GetReviewDTO getReviewDTO);

    void deleteReviewOnProductByUserId(@NonNull DeleteReviewDTO deleteReviewDTO);

    ReviewsDTO getAllReviewsPage(@NonNull GetReviewsDTO getReviewsDTO);

    UserReviewsDTO getAllUserReviews(@NonNull GetUserReviewsDTO getUserReviewsDTO);
}
