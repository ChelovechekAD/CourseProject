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

public class AddCategoryCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String req = request.getReader().lines().collect(Collectors.joining());
        String categoryName = Extractor
                .extractSingleParamFromRequestBody(req, Constants.CATEGORY_NAME_PARAM_KEY, String.class);
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.addCategory(categoryName);
    }
}
