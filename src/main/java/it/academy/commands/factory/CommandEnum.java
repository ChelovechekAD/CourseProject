package it.academy.commands.factory;


import it.academy.commands.Command;
import it.academy.commands.admin.*;
import it.academy.commands.catalog.*;
import it.academy.commands.login.LoginCommand;
import it.academy.commands.login.LogoutCommand;
import it.academy.commands.login.RefreshCommand;
import it.academy.commands.login.RegistrationCommand;
import it.academy.commands.ordercart.AddCartItemCommand;
import it.academy.commands.ordercart.CreateOrderCommand;
import it.academy.commands.ordercart.DeleteCartItemCommand;
import it.academy.commands.ordercart.GetAllCartItemsCommand;
import it.academy.commands.user.*;

public enum CommandEnum {
    POST_REGISTRATION(new RegistrationCommand()),
    POST_LOGIN(new LoginCommand()),
    POST_LOGOUT(new LogoutCommand()),
    GET_REFRESH(new RefreshCommand()),
    GET_ALL_CATEGORIES(new AllCategoriesCommand()),
    GET_PRODUCTS_PAGE(new ProductsPageCommand()),
    GET_CATEGORY_PRODUCTS_PAGE(new FilteredProductsPageCommand()),
    POST_ADD_CART_ITEM(new AddCartItemCommand()),
    POST_DELETE_CART_ITEM(new DeleteCartItemCommand()),
    GET_GET_CART_ITEMS(new GetAllCartItemsCommand()),
    POST_UPDATE_USER(new UpdateUserCommand()),
    POST_CREATE_ORDER(new CreateOrderCommand()),
    GET_ORDERS_PAGE(new GetAllOrdersPageCommand()),
    GET_ORDER_ITEMS(new GetOrderItemsCommand()),
    GET_PRODUCT(new GetProductPageCommand()),
    GET_REVIEW_FOR_PRODUCT(new GetReviewsPageCommand()),
    POST_ADD_REVIEW(new AddReviewCommand()),
    POST_DELETE_REVIEW(new DeleteReviewCommand()),
    GET_USER_REVIEW(new GetReviewCommand()),
    GET_ALL_USER_REVIEWS(new GetAllUserReviewsCommand()),
    GET_USER_ORDERS(new GetUserOrdersCommand()),
    POST_CHANGE_ORDER_STATUS(new ChangeOrderStatusCommand()),
    POST_DELETE_USER(new DeleteUserCommand()),
    GET_ALL_USERS(new GetAllUsersCommand()),
    GET_USER(new GetUserCommand()),
    POST_ADD_CATEGORY(new AddCategoryCommand()),
    POST_DELETE_CATEGORY(new DeleteCategoryCommand()),
    POST_ADD_PRODUCT(new AddProductCommand()),
    POST_DELETE_PRODUCT(new DeleteProductCommand());

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }
}

