package it.academy.servlets.filters;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/api/*")
public class SecurityFilter extends HttpFilter {
}
