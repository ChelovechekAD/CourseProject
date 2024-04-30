package it.academy.servlets.filters;

import io.jsonwebtoken.Claims;
import it.academy.commands.factory.CommandEnum;
import it.academy.enums.RoleEnum;
import it.academy.exceptions.AccessDeniedException;
import it.academy.exceptions.UnauthorizedException;
import it.academy.models.Role;
import it.academy.utilities.Constants;
import it.academy.utilities.ResponseHelper;
import it.academy.сomponents.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.COMMAND_NOT_FOUND;
import static it.academy.utilities.Constants.GSON;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/api/*")
public class SecurityFilter extends HttpFilter {

    private final List<CommandEnum> userAvailableRoutes = List.of(
            CommandEnum.GET_GET_CART_ITEMS,
            CommandEnum.POST_ADD_CART_ITEM,
            CommandEnum.POST_DELETE_CART_ITEM,
            CommandEnum.GET_GET_CART_ITEMS,
            CommandEnum.POST_UPDATE_USER,
            CommandEnum.POST_CREATE_ORDER,
            CommandEnum.POST_ADD_REVIEW,
            CommandEnum.POST_DELETE_REVIEW,
            CommandEnum.GET_USER_REVIEW,
            CommandEnum.GET_ALL_USER_REVIEWS,
            CommandEnum.GET_USER_ORDERS,
            CommandEnum.POST_DELETE_USER,
            CommandEnum.GET_USER
    );

    private final List<CommandEnum> adminAvailableRoutes = List.of(
            CommandEnum.GET_ORDERS_PAGE,
            CommandEnum.GET_ORDER_ITEMS,
            CommandEnum.POST_CHANGE_ORDER_STATUS,
            CommandEnum.GET_ALL_USERS,
            CommandEnum.POST_ADD_CATEGORY,
            CommandEnum.POST_ADD_PRODUCT,
            CommandEnum.POST_DELETE_PRODUCT,
            CommandEnum.POST_DELETE_CATEGORY
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getHeader(Constants.CORS_REQUEST_CHECK_HEADER_PARAM) != null) {
            super.doFilter(req, res, chain);
            return;
        }

        String command = request.getHeader(Constants.COMMAND_HEADER);
        if (command == null || command.isEmpty()) {
            ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_BAD_REQUEST, Constants.COMMAND_NOT_FOUND);
            return;
        }
        String method = request.getMethod();
        String route = String.format(Constants.COMMAND_PATTERN, method, command);
        CommandEnum commandEnum;
        try {
            commandEnum = CommandEnum.valueOf(route.toUpperCase());
        } catch (IllegalArgumentException e) {
            ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_NOT_FOUND, COMMAND_NOT_FOUND);
            return;
        }
        if (userAvailableRoutes.contains(commandEnum)) {
            List<RoleEnum> userRoles = getUserRoles(request);
            if (!userRoles.contains(RoleEnum.DEFAULT_USER)) {
                throw new AccessDeniedException();
            }
        } else if (adminAvailableRoutes.contains(commandEnum)) {
            List<RoleEnum> userRoles = getUserRoles(request);
            if (!userRoles.contains(RoleEnum.ADMIN)) {
                throw new AccessDeniedException();
            }
        }
        req.setAttribute(Constants.COMMAND_ENUM, commandEnum);
        chain.doFilter(req, res);
    }

    private List<RoleEnum> getUserRoles(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHORIZATION);
        if (token == null || !token.startsWith(Constants.TOKEN_PATTERN)) {
            throw new UnauthorizedException();
        }
        token = token.substring(Constants.LENGTH_OF_BEARER_PART);
        JwtProvider jwtProvider = new JwtProvider();
        if (!jwtProvider.validateAccessToken(token)) {
            throw new UnauthorizedException();
        }
        Claims claims = jwtProvider.getAccessClaims(token);
        @SuppressWarnings("unchecked")
        ArrayList<Object> roles = (ArrayList<Object>) claims.get(Constants.ROLES_KEY);
        return roles.stream()
                .map(r -> GSON.fromJson(r.toString(), Role.class).getRole())
                .collect(Collectors.toList());
    }
}
