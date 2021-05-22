package characterize;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translation {
    private final ResourceBundle messages;

    /**
     * Fetch the localization file
     *
     * @param args Program arguments at startup
     */
    public Translation(String[] args) {
        String language;
        if (args.length != 1) {
            language = "en";
        } else {
            language = args[0];
        }
        Locale currentLocale = new Locale(language);
        messages = ResourceBundle.getBundle("locales", currentLocale);
    }

    /**
     * Print localized string
     *
     * @param key key of the localized string in .properties file
     */
    public static void print(String key) {
        System.out.println(Main.translation.messages.getString(key));
    }

    /**
     * Return localized string
     *
     * @param key key of the localized string in .properties file
     * @return Value of the specified key
     */
    public static String get(String key) {
        return Main.translation.messages.getString(key);
    }
}
