package common;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final Serializable argument;
    private final Serializable objectArgument;

    private final String login;
    private final String password;

    public Request(String name, Serializable argument, Serializable objectArgument, String login, String password) {
        this.name = name;
        this.argument = argument;
        this.objectArgument = objectArgument;
        this.login = login;
        this.password = password;
    }

    public Request(String name, Serializable argument, String login, String password) {
        this.name = name;
        this.argument = argument;
        this.objectArgument = null;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public Serializable getArgument() {
        return argument;
    }

    public Serializable getObjectArgument() {
        return objectArgument;
    }

    public String getCommandName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}