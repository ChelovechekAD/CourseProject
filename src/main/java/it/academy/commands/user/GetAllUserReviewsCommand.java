package it.academy.commands.user;

import it.academy.DTO.request.GetUserReviewsDTO;
import it.academy.DTO.response.UserReviewsDTO;
import it.academy.commands.Command;
import it.academy.services.ReviewService;
import it.academy.services.impl.ReviewServiceImpl;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetAllUserReviewsCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetUserReviewsDTO dtoIn = Extractor.extractDTOFromRequest(request, new GetUserReviewsDTO());
        ReviewService reviewService = new ReviewServiceImpl();
        UserReviewsDTO dtoOut = reviewService.getAllUserReviews(dtoIn);
        ResponseHelper.sendJsonResponse(response, GSON.toJson(dtoOut));
    }
}
