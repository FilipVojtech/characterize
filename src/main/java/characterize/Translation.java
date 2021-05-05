package characterize;

import java.util.Locale;
import java.util.ResourceBundle;

public class Translation {
    private final ResourceBundle messages;

    public Translation(String[] args) {
        String country;
        String language;
        if (args.length != 2) {
            language = "en";
            country = "US";
        } else {
            language = args[0];
            country = args[1];
        }
        Locale currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle("locales", currentLocale);
    }

    public String getString(String key) {
        return messages.getString(key);
    }
}
