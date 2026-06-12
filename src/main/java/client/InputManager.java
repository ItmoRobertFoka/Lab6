package client;

import java.io.FileNotFoundException;
import java.util.Stack;

public class InputManager {
    private final Stack<InputSource> sources = new Stack<>();
    private final Stack<String> scriptStack = new Stack<>();
    private String bufferedLine;

    public InputManager(InputSource initialSource) {
        sources.push(initialSource);
    }

    public void pushFile(String filename) throws FileNotFoundException {
        if (scriptStack.contains(filename)) {
            System.out.println("Ошибка: обнаружен цикл скриптов: " + filename);
            return;
        }
        sources.push(new FileSource(filename));
        scriptStack.push(filename);
    }

    public String nextLine() {
        if (bufferedLine != null) {
            String temp = bufferedLine;
            bufferedLine = null;
            return temp;
        }

        while (!sources.isEmpty()) {
            InputSource current = sources.peek();

            if (current.hasNext()) {
                return current.readLine();
            } else {
                sources.pop();
                if (!scriptStack.isEmpty()) scriptStack.pop();
            }
        }

        System.out.println("EOF завершение программы.");
        System.exit(0);
        return null;
    }

    public void setBufferedLine(String line) {
        this.bufferedLine = line;
    }

    public boolean isScriptMode() {
        return sources.size() > 1;
    }
}