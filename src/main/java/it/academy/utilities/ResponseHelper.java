package it.academy.utilities;

import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.PrintWriter;

@UtilityClass
public class ResponseHelper {
    public static void sendResponseWithStatus(HttpServletResponse response, Integer status, String message) throws IOException {
        response.setStatus(status);
        response.getWriter().println(message);
    }

    public static void sendJsonResponse(HttpServletResponse response, String out) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter outPrinter = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        outPrinter.print(out);
        outPrinter.flush();
    }

}