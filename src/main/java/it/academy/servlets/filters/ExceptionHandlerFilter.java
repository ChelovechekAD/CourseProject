package it.academy.servlets.filters;

import it.academy.utilities.Constants;
import it.academy.utilities.ResponseHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "ExceptionHandlerFilter", urlPatterns = "/api/*")
public class ExceptionHandlerFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) res;

        try {
            chain.doFilter(req, res);
        }catch (Exception e){
            e.printStackTrace();
            ResponseHelper.sendResponseWithStatus(httpResponse,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    Constants.SOMETHING_WENT_WRONG);
        }
    }
}
