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

    public Command defineController(String command) {
        if (command != null && !command.isEmpty()) {
            try {
                return CommandEnum.valueOf(command.toUpperCase()).getCurrentCommand();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
