package it.academy.commands.user;

import it.academy.DTO.request.CreateReviewDTO;
import it.academy.commands.Command;
import it.academy.services.ReviewService;
import it.academy.services.impl.ReviewServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class AddReviewCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        CreateReviewDTO dtoIn = GSON.fromJson(req, CreateReviewDTO.class);
        ReviewService reviewService = new ReviewServiceImpl();
        reviewService.createReview(dtoIn);
    }
}
