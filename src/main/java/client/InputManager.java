package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Управляет вводом в программе.
 * Поддерживает ввод из консоли и из скрипта.
 */
public class InputManager {

    private final Stack<Scanner> scanners = new Stack<>();
    private final Stack<String> scriptStack = new Stack<>();
    private final Scanner systemScanner;
    private boolean scriptMode = false;
    private String bufferedLine;

    public InputManager() {
        systemScanner = new Scanner(System.in);
        scanners.push(systemScanner);
    }

    public void pushFile(String filename) throws FileNotFoundException {

        if (scriptStack.contains(filename)) {
            System.out.println("Ошибка: обнаружен цикл скриптов: " + filename);
            return;
        }

        Scanner fileScanner = new Scanner(new File(filename));

        if (!fileScanner.hasNextLine()) {
            System.out.println("Ошибка: пустой скрипт");
            return;
        }

        scriptStack.push(filename);
        scanners.push(fileScanner);
        scriptMode = true;
    }

    public String nextLine() {

        if (bufferedLine != null) {
            String temp = bufferedLine;
            bufferedLine = null;
            return temp;
        }

        while (!scanners.isEmpty()) {

            Scanner current = scanners.peek();

            if (current.hasNextLine()) {
                String line = current.nextLine();

                if (line == null) {
                    continue;
                }

                return line;
            } else {
                scanners.pop();

                if (!scriptStack.isEmpty()) {
                    scriptStack.pop();
                }

                if (scanners.size() == 1) {
                    scriptMode = false;
                }
            }
        }

        System.out.println("EOF завершение программы.");
        System.exit(0);
        return null;
    }

    public boolean isScriptMode() {
        return scriptMode;
    }

    public void setBufferedLine(String line) {
        this.bufferedLine = line;
    }
}