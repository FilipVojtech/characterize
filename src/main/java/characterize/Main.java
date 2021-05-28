package characterize;

import cz.polygonunicorn.javahandysnippets.HSConsoleInput;

public class Main {
    public static Translation translation;
    private static User user;

    public static void main(String[] args) {
        translation = new Translation(args);
        user = new User();

        System.out.println(Translation.get("welcome_to") + Translation.get("app.name"));
        while (true) {
            String input = null;

            while (user.isLoggedIn()) {
                input = HSConsoleInput.getString("> ", false);
                char firstChar = 0;
                if (input.length() > 0) firstChar = input.toCharArray()[0];

                if ("!@#".indexOf(firstChar) == -1) {
                    switch (input) {
                        case "logout" -> user.logout();
                        // General commands
                        case "help", "h" -> ShellCommands.help();
                        case "exit" -> System.exit(0);
                        default -> Translation.print("unknown");
                    }
                } else {
                    input = input.substring(1);
                    switch (firstChar) {
                        case '!' -> System.out.println("Quest " + input);
                        case '@' -> Character.command(input);
                        case '#' -> System.out.println("Note " + input);
                    }
                }
                System.out.println();
            }
            input = HSConsoleInput.getString("> ", false);
            switch (input) {
                case "login", "l" -> user.login();
                case "register", "r" -> user.register();
//                case "create" -> Campaign.create();
//                case "setup" -> Campaign.setUpCampaign();
                // General commands
                case "help", "h" -> ShellCommands.help();
                case "exit" -> System.exit(0);
                default -> Translation.print("unknown");
            }
            System.out.println();
        }
    }

    /**
     * Getter for user variable
     *
     * @return initialized user
     */
    public static User getUser() {
        return user;
    }
}
