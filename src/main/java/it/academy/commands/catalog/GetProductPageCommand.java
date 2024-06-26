package it.academy.commands.catalog;

import it.academy.DTO.response.ProductDTO;
import it.academy.commands.Command;
import it.academy.services.ProductService;
import it.academy.services.impl.ProductServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.Extractor;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class GetProductPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = Extractor.extractSingleParamFromRequest(request, Constants.PRODUCT_ID_PARAM_KEY, Long.class);
        ProductService productService = new ProductServiceImpl();
        ProductDTO dto = productService.getProductById(productId);
        ResponseHelper.sendJsonResponse(response, GSON.toJson(dto));
    }
}
