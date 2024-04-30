package it.academy.services.impl;

import it.academy.DAO.ProductDAO;
import it.academy.DAO.ReviewDAO;
import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.ProductDAOImpl;
import it.academy.DAO.impl.ReviewDAOImpl;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.request.*;
import it.academy.DTO.response.ReviewDTO;
import it.academy.DTO.response.ReviewsDTO;
import it.academy.DTO.response.UserReviewInfoDTO;
import it.academy.DTO.response.UserReviewsDTO;
import it.academy.exceptions.*;
import it.academy.models.*;
import it.academy.models.embedded.ReviewPK;
import it.academy.models.embedded.ReviewPK_;
import it.academy.services.ReviewService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ReviewServiceImpl implements ReviewService {

    private final TransactionHelper transactionHelper;
    private final ReviewDAO reviewDAO;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;

    public ReviewServiceImpl() {
        transactionHelper = new TransactionHelper();
        reviewDAO = new ReviewDAOImpl(transactionHelper);
        productDAO = new ProductDAOImpl(transactionHelper);
        userDAO = new UserDAOImpl(transactionHelper);
    }

    @Override
    public void createReview(CreateReviewDTO createReviewDTO) {
        Runnable runnable = () -> {
            Product product = productDAO.get(createReviewDTO.getProductId());
            User user = userDAO.get(createReviewDTO.getUserId());
            validateReviewPK(user, product);
            ReviewPK reviewPK = new ReviewPK(user, product);
            Optional.ofNullable(reviewDAO.get(reviewPK)).ifPresent(p->{throw new ReviewExistException();});
            Review review = Review.builder()
                    .rating(createReviewDTO.getRating())
                    .reviewPK(reviewPK)
                    .description(createReviewDTO.getDescription())
                    .build();
            reviewDAO.create(review);
            updateTotalRatingForProduct(product);
        };
        transactionHelper.transaction(runnable);
    }

    @Override
    public ReviewDTO getSingleReviewOnProductByUserId(@NonNull GetReviewDTO dto) {
        Supplier<ReviewDTO> supplier = () -> {
            User user = userDAO.get(dto.getUserId());
            Product product = productDAO.get(dto.getProductId());
            validateReviewPK(user, product);
            ReviewPK reviewPK = new ReviewPK(user, product);
            Review review = reviewDAO.get(reviewPK);
            Optional.ofNullable(review).orElseThrow(ReviewNotFoundException::new);
            return Converter.convertReviewEntityToDTO(review);
        };
        return transactionHelper.transaction(supplier);
    }

    @Override
    public void deleteReviewOnProductByUserId(@NonNull DeleteReviewDTO dto) {
        Runnable runnable = () -> {
            User user = userDAO.get(dto.getUserId());
            Product product = productDAO.get(dto.getProductId());
            validateReviewPK(user, product);
            ReviewPK reviewPK = new ReviewPK(user, product);
            try {
                reviewDAO.delete(reviewPK);
            } catch (NotFoundException e) {
                throw new ReviewNotFoundException();
            }
            updateTotalRatingForProduct(product);
        };
        transactionHelper.transaction(runnable);
    }

    @Override
    public ReviewsDTO getAllReviewsPage(@NonNull GetReviewsDTO getReviewsDTO) {
        Supplier<ReviewsDTO> supplier = () -> {
            List<Review> list = reviewDAO.getReviewsOnProduct(getReviewsDTO.getPageNum(),
                    getReviewsDTO.getCountPerPage(),
                    getReviewsDTO.getProductId());
            Integer count = Math.toIntExact(reviewDAO.getCountOfReviewsOnProduct(getReviewsDTO.getProductId()));
            return Converter.convertListReviewEntityToDTO(list, count);
        };
        return transactionHelper.transaction(supplier);
    }

    @Override
    public UserReviewsDTO getAllUserReviews(@NonNull GetUserReviewsDTO getUserReviewsDTO) {
        Supplier<UserReviewsDTO> supplier = () -> {
            CriteriaBuilder cb = transactionHelper.criteriaBuilder();
            CriteriaQuery<UserReviewInfoDTO> cq = cb.createQuery(UserReviewInfoDTO.class);
            Root<Review> root = cq.from(Review.class);
            Join<Review, ReviewPK> pkJoin = root.join(Review_.REVIEW_PK);
            Join<ReviewPK, Product> productJoin = pkJoin.join(ReviewPK_.PRODUCT_ID);
            cq.multiselect(root.get(Review_.DESCRIPTION), root.get(Review_.RATING),
                            productJoin.get(Product_.ID),
                            productJoin.get(Product_.IMAGE_LINK),
                            productJoin.get(Product_.NAME))
                    .where(root.get(Review_.REVIEW_PK).get(ReviewPK_.USER_ID));
            List<UserReviewInfoDTO> list = transactionHelper.entityManager().createQuery(cq).getResultList();
            Integer count = Math.toIntExact(reviewDAO.getCountOfUserReviews(getUserReviewsDTO.getUserId()));
            return new UserReviewsDTO(list, count);
        };
        return transactionHelper.transaction(supplier);
    }

    private void updateTotalRatingForProduct(Product product) {
        CriteriaBuilder criteriaBuilder = transactionHelper.criteriaBuilder();
        CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
        Root<Review> root = query.from(Review.class);
        query.select(criteriaBuilder.avg(root.get(Review_.RATING)))
                .where(criteriaBuilder.equal(root.get(Review_.REVIEW_PK).get(ReviewPK_.PRODUCT_ID), product));
        Double newRating = transactionHelper.entityManager().createQuery(query).getSingleResult();
        newRating = Optional.ofNullable(newRating).orElse(0d);
        product.setRating(newRating);
        productDAO.update(product);
    }

    private void validateReviewPK(User user, Product product) {
        Optional.ofNullable(product).orElseThrow(ProductNotFoundException::new);
        Optional.ofNullable(user).orElseThrow(UserNotFoundException::new);
    }

}
