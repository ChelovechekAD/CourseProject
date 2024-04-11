package it.academy.servlets.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "CorsFilter", urlPatterns = "/api/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String origin = httpRequest.getHeader("Origin");

        // Allow requests from any origin
        httpResponse.setHeader("Access-Control-Allow-Origin", origin);

        // Allow specific HTTP methods (e.g., GET, POST, PUT, DELETE)
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");

        // Allow specific HTTP headers (e.g., Content-Type, Authorization)
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Command");

        // Allow credentials (cookies, authorization headers, etc.) to be included in CORS requests
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Set cache control headers to disable caching for preflight requests
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Expires", "0");

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}