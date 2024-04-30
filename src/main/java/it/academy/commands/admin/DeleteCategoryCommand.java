package it.academy.commands.admin;

import it.academy.commands.Command;
import it.academy.services.CategoryService;
import it.academy.services.impl.CategoryServiceImpl;
import it.academy.utilities.Constants;
import it.academy.utilities.Extractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

public class DeleteCategoryCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        Long categoryId = Extractor.extractSingleParamFromRequestBody(req, Constants.CATEGORY_ID_PARAM_KEY, Long.class);
        Boolean root = Extractor.extractSingleParamFromRequestBody(req, Constants.ROOT_PARAM_KEY, Boolean.class);
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.deleteCategory(categoryId, root);
    }
}
