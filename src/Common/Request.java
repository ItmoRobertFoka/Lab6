package Common;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final Serializable argument;

    public Request(String name, Serializable argument) {
        this.name = name;
        this.argument = argument;
    }

    public String getName() {
        return name;
    }

    public Serializable getArgument() {
        return argument;
    }
}
