package Common;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final Serializable argument;
    private final Serializable objectArgument;

    public Request(String name, Serializable argument, Serializable objectArgument) {
        this.name = name;
        this.argument = argument;
        this.objectArgument = objectArgument;
    }

    public Request(String name, Serializable argument) {
        this.name = name;
        this.argument = argument;
        this.objectArgument = null;
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
}