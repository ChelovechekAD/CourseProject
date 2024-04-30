package it.academy.commands.user;

import it.academy.DTO.request.GetReviewDTO;
import it.academy.DTO.response.ReviewDTO;
import it.academy.commands.Command;
import it.academy.services.ReviewService;
import it.academy.services.impl.ReviewServiceImpl;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetReviewCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetReviewDTO dtoIn = Extractor.extractDTOFromRequest(request, new GetReviewDTO());
        ReviewService reviewService = new ReviewServiceImpl();
        ReviewDTO dtoOut = reviewService.getSingleReviewOnProductByUserId(dtoIn);
        ResponseHelper.sendJsonResponse(response, GSON.toJson(dtoOut));
    }
}
