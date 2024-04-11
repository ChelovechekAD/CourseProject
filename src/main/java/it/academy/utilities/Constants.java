package it.academy.utilities;

import com.google.gson.Gson;

public class Constants {

    public static final Gson GSON = new Gson();

    //JWT AUTH DATA
    public static final String JWT_ACCESS_SECRET = "gpgdKY9zR4hebFb2wT67AlgsKmbTOmKoyqmGym1dKmM=";
    public static final String JWT_REFRESH_SECRET = "7+bbUaAIaRFDce4rxQLu5QFPqooqHLgfN5mnDB7PNT4=";
    public static final Integer JWT_ACCESS_EXPIRATION = 1;
    public static final Integer JWT_REFRESH_EXPIRATION = 60;
    public static final String TOKEN_PATTERN = "Bearer ";
    //END

    public static final String NULL_EXCEPTION_MESSAGE = "Object is null.";
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String COMMAND_HEADER = "Command";
    public static final String COMMAND_PATTERN = "%s_%s";
    public static final String PARAMETER_KEY = "?";


    public static final String COMMAND_NOT_FOUND = "Command not found.";
    public static final String UNSUPPORTED_COMMAND = "Unsupported command.";
    public static final String INVALID_COMMAND = "Invalid command.";
    public static final String REQUESTED_CATALOG_NOT_EXIST = "Requested catalog not exist.";
    public static final String PRODUCT_NOT_FOUND = "Product not found.";
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String ORDER_NOT_FOUND = "Order not found!";
    public static final String ENTITY_NOT_FOUND = "Entity not found!";
    public static final String ORDER_ITEM_NOT_FOUND = "Order item not found!";
    public static final String ORDER_ITEM_EXIST = "Order item exist!";
    public static final String PASSWORD_MATCH_EXCEPTION = "Password and password confirmation do not match!";
    public static final String SELECT_FROM_USER_WHERE_EMAIL_USER = "select u from User u where email=:userEmail";
    public static final String USER_ALREADY_EXIST = "User already exist!";

    public static final String TOKEN_ERROR = "Token expired! ";
    public static final String UNSUPPORTED_JWT_ERROR = "Unsupported jwt: ";
    public static final String MALFORMED_JWT_ERROR = "Malformed jwt: ";
    public static final String SIGNATURE_ERROR = "Invalid signature: ";
    public static final String INVALID_TOKEN_ERROR = "invalid token: ";
    public static final String WRONG_SECRET_KEY = "Wrong secret key";
    public static final String ROLES_KEY = "roles";


    public static final String SELECT_FROM_REFRESH_TOKEN_WHERE_EMAIL = "select rt from RefreshToken  rt where userEmail=:email";
    public static final String SELECT_COUNT_FROM_USER_WHERE_EMAIL_USER = "select count(u) from User u where email=:userEmail";
    public static final String SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_EMAIL = "select count(rt) from RefreshToken rt where userEmail=:email";
    public static final String SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_REFRESH_TOKEN = "select count(rt) from RefreshToken rt where refreshToken=:token";
    public static final String DELETE_FROM_REFRESH_TOKEN_WHERE_REFRESH_TOKEN = "delete from RefreshToken where refreshToken=:token";
    public static final String TOKEN_NOT_FOUND = "Token not found!";
    public static final String REFRESH_TOKEN_ALREADY_EXIST = "Refresh token already exist!";
    public static final String WRONG_PASSWORD = "Wrong password!";
    public static final String CART_ITEM_NOT_FOUND = "Cart item not found!";
    public static final String SELECT_FROM_CART_ITEM_WHERE_USER_ID = "select ci from CartItem ci where cartItemPK.userId.id=:userId";
    public static final String REVIEW_NOT_FOUND = "Review not found!";
    public static final String CART_ITEM_ALREADY_EXIST = "Cart item already exist!";
    public static final String REVIEW_ALREADY_EXIST = "Review already exist!";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong!";
    public static final String SUCCESSFULLY_CREATED = "User successfully created!";
    public static final String REGISTRATION_REQUEST_OBJECT_NAME = "registrationRequest";
    public static final String LOGOUT_MESSAGE = "Logout success!";
    public static final String REFRESH_TOKEN_ATTR_NAME = "refresh-token";
    public static final String TOKEN_INVALID = "Token invalid!";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCESS_DENIED = "Access denied!";
    public static final String COMMAND_ENUM = "CommandEnum";
    public static final String CATEGORY_ID_PARAM_KEY = "categoryId";
    public static final String PAGE_NUM_PARAM_KEY = "pageNum";
    public static final String COUNT_PER_PAGE_PARAM_KEY = "countPerPage";
    public static final String SELECT_FROM_GENERIC = "select o from %s o";
    public static final String SELECT_COUNT_FROM_GENERIC = "select count(i) from %s i";
    public static final String SELECT_FROM_CATEGORY = "select c from Category c";
    public static final String REQUIRED_REQUEST_PARAMETERS_DOESNT_EXIST_OR_INVALID = "Required request parameters doesn't exist or invalid!";
    public static final String UNSUPPORTED_FIELD_TYPE = "Unsupported field type: ";
    public static final String USER_ID_PARAM_KEY = "userId";
    public static final String SELECT_FROM_PRODUCT = "select p from Product p";
    public static final String CORS_REQUEST_CHECK_HEADER_PARAM = "access-control-request-method";
    public static final int LENGTH_OF_BEARER_PART = 7;
}
