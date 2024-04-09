package it.academy.commands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Command {

    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
