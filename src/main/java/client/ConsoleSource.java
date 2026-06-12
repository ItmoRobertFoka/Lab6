package client;
import java.util.Scanner;

public class ConsoleSource implements InputSource {
    private final Scanner scanner = new Scanner(System.in);
    public String readLine() { return scanner.hasNextLine() ? scanner.nextLine() : null; }
    public boolean hasNext() { return scanner.hasNextLine(); }
}