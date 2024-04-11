package it.academy.services.impl;

import it.academy.DAO.ProductDAO;
import it.academy.DAO.ReviewDAO;
import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.ProductDAOImpl;
import it.academy.DAO.impl.ReviewDAOImpl;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.request.CreateReviewDTO;
import it.academy.DTO.request.RequestDataDetailsDTO;
import it.academy.DTO.response.ReviewDTO;
import it.academy.DTO.response.ReviewsDTO;
import it.academy.exceptions.*;
import it.academy.models.Product;
import it.academy.models.Review;
import it.academy.models.Review_;
import it.academy.models.User;
import it.academy.models.embedded.ReviewPK;
import it.academy.models.embedded.ReviewPK_;
import it.academy.services.ReviewService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;

import java.util.List;
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
            if (reviewDAO.get(reviewPK) != null) {
                throw new ReviewExistException();
            }
            Review review = Review.builder()
                    .rating(createReviewDTO.getRate())
                    .reviewPK(reviewPK)
                    .description(createReviewDTO.getDescription())
                    .build();
            reviewDAO.create(review);
            updateTotalRatingForProduct(product);
        };
        transactionHelper.transaction(runnable);
    }

    @Override
    public ReviewDTO getSingleReviewOnProductByUserId(@NonNull Long userId, @NonNull Long prodId) {
        Supplier<ReviewDTO> supplier = () -> {
            User user = userDAO.get(userId);
            Product product = productDAO.get(prodId);
            validateReviewPK(user, product);
            ReviewPK reviewPK = new ReviewPK(user, product);
            Review review = reviewDAO.get(reviewPK);
            if (review == null) {
                throw new ReviewNotFoundException();
            }
            return Converter.convertReviewEntityToDTO(review);
        };
        return transactionHelper.transaction(supplier);
    }

    @Override
    public void deleteReviewOnProductByUserId(@NonNull Long userId, @NonNull Long prodId) {
        Runnable runnable = () -> {
            User user = userDAO.get(userId);
            Product product = productDAO.get(prodId);
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
    public ReviewsDTO getAllReviewsPage(@NonNull RequestDataDetailsDTO requestDataDetailsDTO) {
        Supplier<ReviewsDTO> supplier = () -> {
            List<Review> list = transactionHelper.transaction(() -> reviewDAO.getPage(requestDataDetailsDTO.getCountPerPage(),
                    requestDataDetailsDTO.getPageNum()));
            Integer count = Math.toIntExact(transactionHelper.transaction(reviewDAO::getCountOf));
            return Converter.convertListReviewEntityToDTO(list, count);
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
        product.setRating(newRating);
        productDAO.update(product);
        /*Double avg = transactionHelper.entityManager()
                .createQuery("select avg(r.rating) from Review r where reviewPK.productId=:id", Double.class)
                .setParameter("id", product.getId())
                .getSingleResult();*/
    }

    private void validateReviewPK(User user, Product product) {
        if (product == null) {
            throw new ProductNotFoundException();
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

}
