package it.academy.commands.catalog;

import it.academy.DTO.request.GetReviewsDTO;
import it.academy.DTO.response.ReviewsDTO;
import it.academy.commands.Command;
import it.academy.services.ReviewService;
import it.academy.services.impl.ReviewServiceImpl;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetReviewsPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ReviewService reviewService = new ReviewServiceImpl();
        GetReviewsDTO reviewsDTO = Extractor.extractDTOFromRequest(request, new GetReviewsDTO());
        ReviewsDTO out = reviewService.getAllReviewsPage(reviewsDTO);
        ResponseHelper.sendJsonResponse(response, GSON.toJson(out));
    }
}
