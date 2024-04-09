package it.academy.commands.factory;


import it.academy.commands.Command;

public class CommandFactory {

    private static CommandFactory factory;

    private CommandFactory() {

    }

    public static CommandFactory getFactory() {
        if (factory == null) {
            factory = new CommandFactory();
        }
        return factory;
    }

    public Command defineController(CommandEnum command) {
        return command.getCurrentCommand();
    }
}
