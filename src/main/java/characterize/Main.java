package characterize;

import cz.polygonunicorn.javahandysnippets.HSConsoleInput;

public class Main {
    public static Translation translation;

    public static void main(String[] args) {
        translation = new Translation(args);

        System.out.println(Translation.getString("welcome"));
        while (true) {
            String input = HSConsoleInput.getString("> ", false);
            char firstChar = input.toCharArray()[0];
            switch (input) {
                case "help", "h" -> ShellCommands.help();
                case "about" -> ShellCommands.about();
                case "exit" -> System.exit(0);
                default -> ShellCommands.unknown();
            }
            System.out.println();
        }
    }
}