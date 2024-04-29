package it.academy.commands.user;

import it.academy.DTO.request.DeleteReviewDTO;
import it.academy.commands.Command;
import it.academy.exceptions.ProductNotFoundException;
import it.academy.exceptions.ReviewNotFoundException;
import it.academy.exceptions.UserNotFoundException;
import it.academy.services.ReviewService;
import it.academy.services.impl.ReviewServiceImpl;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class DeleteReviewCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        DeleteReviewDTO reviewDTO = GSON.fromJson(req, DeleteReviewDTO.class);
        ReviewService reviewService = new ReviewServiceImpl();
        try {
            reviewService.deleteReviewOnProductByUserId(reviewDTO);
        }catch (ProductNotFoundException | UserNotFoundException | ReviewNotFoundException e) {
            ResponseHelper.sendResponseWithStatus(response, HttpStatus.SC_NOT_FOUND, e.getMessage());
        }
    }
}
