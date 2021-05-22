package characterize;

public class ShellCommands {

    public static void help() {
        StringBuilder helpCommand = new StringBuilder();

        final String helpGenericStart = Translation.get("available_commands") + "\n\n";
        final String helpGenericEnd = """
                  about   About this program
                \sh help   Display this page
                   exit   Exit the app""";
        final String helpLoggedIn = """
                \slogout   Logs you out
                """;
        final String helpLoggedOut = """
                    login   Logs you in
                \sregister   Register to use the app
                """;

        helpCommand.append(helpGenericStart);

        if (Main.getUser().isLoggedIn()) helpCommand.append(helpLoggedIn);
        else helpCommand.append(helpLoggedOut);
        helpCommand.append('\n');

        helpCommand.append(helpGenericEnd);
        System.out.println(helpCommand);
    }
}
