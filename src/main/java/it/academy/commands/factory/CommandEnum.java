package it.academy.commands.factory;


import it.academy.commands.Command;
import it.academy.commands.login.LoginCommand;
import it.academy.commands.login.RegistrationCommand;

public enum CommandEnum {
    POST_REGISTRATION(new RegistrationCommand()),
    GET_LOGIN(new LoginCommand()),
    POST_LOGIN(new LoginCommand());

    private final Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }
}

