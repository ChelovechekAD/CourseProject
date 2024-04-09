package it.academy.commands.login;

import it.academy.DTO.response.CategoriesDTO;
import it.academy.commands.Command;
import it.academy.services.CategoryService;
import it.academy.services.impl.CategoryServiceImpl;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static it.academy.utilities.Constants.GSON;

public class AllCategoriesCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        CategoriesDTO categoriesDTO = categoryService.getAllCategories();
        String resp = GSON.toJson(categoriesDTO);
        System.out.println(resp);
        ResponseHelper.sendJsonResponse(response, resp);
    }
}
