package characterize;

public class ShellCommands {

    public static void help() {
        StringBuilder helpCommand = new StringBuilder();

        final String helpGenericStart = Translation.getString("availableCommands") + "\n\n";
        final String helpGenericEnd = """
                  about   About this program
                \sh help   Display this page
                   exit   Exit the app""";
        final String helpLoggedIn = """
                \slogout   Logs you out""";
        final String helpLoggedOut = """
                    login   Logs you in
                \sregister   Register to use the app""";

        helpCommand.append(helpGenericStart);

        if (Main.user.isLoggedIn()) helpCommand.append(helpLoggedIn);
        else helpCommand.append(helpLoggedOut);

        helpCommand.append(helpGenericEnd);
        System.out.println(helpCommand);
    }

    public static void about() {
        System.out.println(Translation.getString("about"));
    }

    public static void unknown() {
        System.out.println(Translation.getString("unknown"));
    }
}
