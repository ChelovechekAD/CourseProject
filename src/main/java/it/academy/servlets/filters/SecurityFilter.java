package it.academy.servlets.filters;

import io.jsonwebtoken.Claims;
import it.academy.Components.JwtProvider;
import it.academy.commands.factory.CommandEnum;
import it.academy.commands.factory.CommandFactory;
import it.academy.enums.RoleEnum;
import it.academy.models.Role;
import it.academy.utilities.Constants;
import it.academy.utilities.DevUtils;
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static it.academy.utilities.Constants.GSON;

@WebFilter(filterName = "SecurityFilter", urlPatterns = "/api/*")
public class SecurityFilter extends HttpFilter {

    private final List<CommandEnum> userAvailableRoutes = List.of(
            CommandEnum.GET_ALL_CATEGORIES
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (request.getHeader("access-control-request-method") != null){
            super.doFilter(req,res,chain);
            return;
        }

        String command = request.getHeader("command");
        if (command == null || command.isEmpty()){
            ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_BAD_REQUEST, Constants.COMMAND_NOT_FOUND);
            return;
        }
        String method = request.getMethod();
        String route = String.format(Constants.COMMAND_PATTERN, method, command);
        System.out.println("Route: " + route);
        CommandEnum commandEnum = CommandEnum.valueOf(route.toUpperCase());
        if (userAvailableRoutes.contains(commandEnum)){
            String token = request.getHeader(Constants.AUTHORIZATION);
            if (token==null || !token.startsWith(Constants.TOKEN_PATTERN)){
                ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_UNAUTHORIZED, Constants.UNAUTHORIZED);
                return;
            }
            token = token.substring(7);
            JwtProvider jwtProvider = new JwtProvider();
            if (!jwtProvider.validateAccessToken(token)){
                ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_UNAUTHORIZED, Constants.UNAUTHORIZED);
                return;
            }
            Claims claims = jwtProvider.getAccessClaims(token);
            @SuppressWarnings("unchecked")
            ArrayList<Object> roles = (ArrayList<Object>) claims.get(Constants.ROLES_KEY);
            List<RoleEnum> userRoles = roles.stream()
                    .map(r-> GSON.fromJson(roles.get(0).toString(), Role.class).getRole())
                    .collect(Collectors.toList());

            if (!userRoles.contains(RoleEnum.DEFAULT_USER)){
                ResponseHelper.sendResponseWithStatus(response, HttpServletResponse.SC_FORBIDDEN, Constants.ACCESS_DENIED);
                return;
            }
        }
        req.setAttribute(Constants.COMMAND_ENUM, commandEnum);
        chain.doFilter(req, res);
    }
}
