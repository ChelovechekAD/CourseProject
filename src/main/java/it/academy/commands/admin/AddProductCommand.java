package it.academy.commands.admin;

import it.academy.DTO.request.CreateProductDTO;
import it.academy.commands.Command;
import it.academy.services.ProductService;
import it.academy.services.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

public class AddProductCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        CreateProductDTO createProductDTO = GSON.fromJson(req, CreateProductDTO.class);
        ProductService productService = new ProductServiceImpl();
        productService.addProduct(createProductDTO);
    }
}
