package it.academy.commands.user;

import it.academy.DTO.request.AddToCartDTO;
import it.academy.DTO.request.CreateReviewDTO;
import it.academy.commands.Command;
import it.academy.exceptions.ProductNotFoundException;
import it.academy.exceptions.ReviewExistException;
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

public class AddReviewCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        CreateReviewDTO dtoIn = GSON.fromJson(req, CreateReviewDTO.class);
        ReviewService reviewService = new ReviewServiceImpl();
        try {
            reviewService.createReview(dtoIn);
        }catch (ProductNotFoundException | UserNotFoundException e){
            ResponseHelper.sendResponseWithStatus(response, HttpStatus.SC_NOT_FOUND, e.getMessage());
        }catch (ReviewExistException e){
            ResponseHelper.sendResponseWithStatus(response, HttpStatus.SC_CONFLICT, e.getMessage());
        }
    }
}
