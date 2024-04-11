package it.academy.commands.factory;


import it.academy.commands.Command;
import it.academy.commands.catalog.AllCategoriesCommand;
import it.academy.commands.catalog.FilteredProductsPageCommand;
import it.academy.commands.catalog.ProductsPageCommand;
import it.academy.commands.login.*;
import it.academy.commands.ordercart.AddCartItemCommand;
import it.academy.commands.ordercart.DeleteCartItemCommand;
import it.academy.commands.ordercart.GetAllCartItemsCommand;
import it.academy.commands.user.UpdateUserCommand;

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
    POST_UPDATE_USER(new UpdateUserCommand());

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }
}

