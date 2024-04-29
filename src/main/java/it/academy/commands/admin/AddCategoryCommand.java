package it.academy.commands.admin;

import it.academy.DTO.request.CategoryDTO;
import it.academy.commands.Command;
import it.academy.utilities.Extractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddCategoryCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoryDTO categoryDTO = Extractor.
    }
}
