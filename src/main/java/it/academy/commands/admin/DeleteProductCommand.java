package it.academy.commands.admin;

import it.academy.commands.Command;
import it.academy.services.ProductService;
import it.academy.services.impl.ProductServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.Extractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

public class DeleteProductCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        Long productId = Extractor.extractSingleParamFromRequestBody(req, Constants.PRODUCT_ID_PARAM_KEY, Long.class);
        ProductService productService = new ProductServiceImpl();
        productService.deleteProduct(productId);
    }
}
