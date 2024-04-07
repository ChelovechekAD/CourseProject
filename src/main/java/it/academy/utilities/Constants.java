package it.academy.utilities;

public class Constants {

    //JWT AUTH DATA
    public static final String JWT_ACCESS_SECRET = "gpgdKY9zR4hebFb2wT67AlgsKmbTOmKoyqmGym1dKmM=";
    public static final String JWT_REFRESH_SECRET = "7+bbUaAIaRFDce4rxQLu5QFPqooqHLgfN5mnDB7PNT4=";
    public static final Integer JWT_ACCESS_EXPIRATION = 1;
    public static final Integer JWT_REFRESH_EXPIRATION = 60;
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
    public static final String SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_EMAIL = "select count(rt) from RefreshToken rt where userEmail:email";
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
}
