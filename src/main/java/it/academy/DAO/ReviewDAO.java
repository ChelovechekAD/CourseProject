package it.academy.DAO;

import it.academy.models.Review;
import it.academy.models.embedded.ReviewPK;

import java.util.List;

public interface ReviewDAO extends DAO<Review, ReviewPK> {

    List<Review> getReviewsOnProduct(Integer pageNum, Integer countPerPage, Long productId);

    Long getCountOfReviewsOnProduct(Long productId);

    Long getCountOfUserReviews(Long userId);

}
