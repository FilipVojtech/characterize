package characterize;

import cz.polygonunicorn.javahandysnippets.HSConsoleInput;

public class Main {
    public static Translation translation;
    public static User user;

    public static void main(String[] args) {
        translation = new Translation(args);
        user = new User();

        System.out.println(Translation.getString("welcomeTo") + Translation.getString("app.name"));
        while (true) {
            String input = HSConsoleInput.getString("> ", false);

            while (user.isLoggedIn()) {
                char firstChar = input.toCharArray()[0];

                if ("!@#".indexOf(firstChar) == -1) {
                    switch (input) {
                        case "logout" -> user.logout();
                        // General commands
                        case "about" -> ShellCommands.about();
                        case "help", "h" -> ShellCommands.help();
                        case "exit" -> System.exit(0);
                        default -> ShellCommands.unknown();
                    }
                } else {
                    input = input.substring(1);
                    switch (firstChar) {
                        case '!' -> System.out.println("Quest " + input);
                        case '@' -> System.out.println("Character " + input);
                        case '#' -> System.out.println("Note " + input);
                    }
                }
                System.out.println();
            }
            switch (input) {
                case "login", "l" -> user.login();
                case "register", "r" -> user.register();
                // General commands
                case "about" -> ShellCommands.about();
                case "help", "h" -> ShellCommands.help();
                case "exit" -> System.exit(0);
                default -> ShellCommands.unknown();
            }
            System.out.println();
        }
    }
}
