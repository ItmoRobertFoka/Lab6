package Commands;

public interface Command {
    String execute();
    String getDescription();
    String getName();
}
