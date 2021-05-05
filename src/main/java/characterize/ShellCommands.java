package characterize;

public class ShellCommands {

    public static void help() {
//        System.out.println(Main.translation.getString("help"));
        final String help = """
                Available commands:

                  about   About this program
                 h help   Display this page
                   exit   Exit the program""";
        System.out.println(help);
    }

    public static void about() {
        System.out.println(Main.translation.getString("about"));
    }

    public static void unknown() {
        System.out.println(Main.translation.getString("unknown"));
    }
}
