package it.academy.commands.factory;


import it.academy.commands.Command;
import it.academy.commands.login.*;

public enum CommandEnum {
    POST_REGISTRATION(new RegistrationCommand()),
    POST_LOGIN(new LoginCommand()),
    POST_LOGOUT(new LogoutCommand()),
    GET_REFRESH(new RefreshCommand()),
    GET_ALL_CATEGORIES(new AllCategoriesCommand()),
    GET_PRODUCTS_PAGE(new ProductsPageCommand()),
    GET_CATEGORY_PRODUCTS_PAGE(new FilteredProductsPageCommand());

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }
}

