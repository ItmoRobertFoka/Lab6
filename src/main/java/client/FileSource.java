package client;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSource implements InputSource {
    private final Scanner scanner;
    public FileSource(String path) throws FileNotFoundException {
        scanner = new Scanner(new File(path));
    }
    public String readLine() { return scanner.hasNextLine() ? scanner.nextLine() : null; }
    public boolean hasNext() { return scanner.hasNextLine(); }
}