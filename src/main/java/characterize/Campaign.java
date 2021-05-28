package characterize;

import cz.polygonunicorn.javahandysnippets.HSConsoleInput;
import mongodb.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Campaign managing tools for DMs
 */
public class Campaign {
    //    todo: Set up campaign
//    todo: Add user
//    todo: Remove user
//    todo: Format campaign
//    todo: Edit API Keys
    private static String campaignName = "";

    /**
     * Guides the user through the account creation process
     */
    public static void create() {
        char[][] keys;
        Translation.println("create.all.start");
        showChecklist("account");
        Translation.println("create.all.doing_great");
        System.out.println();
        Translation.println("create.all.api");
        keys = showApiChecklist();
        System.out.println();
        Translation.println("create.all.end");
        setUpCampaign(keys);
    }

    public static void setUpCampaign() {
        //        char[][] keys = askApiKeys();
        char[][] keys = {"f2848028-5b04-423b-90ca-36872ffe4da5".toCharArray(), "icqwynyw".toCharArray()};
        Request request = new Request(keys);
        request.send();

    }

    /**
     * Communicates with MongoDB Atlas and sets everything up
     *
     * @param keys Api keys
     */
    public static void setUpCampaign(char[][] keys) {
//        todo: Implement
    }

    /**
     * Print out checklist from locales
     * Checklist must be specified
     *
     * @param type Choose which checklist to print out
     */
    private static void showChecklist(String type) {
        int i = 1;

        // Print values until exception is thrown
        while (true) {
            System.out.print(i + ". ");
            try {
                Translation.println("create." + type + ".ch" + i);
            } catch (MissingResourceException ignore) {
                break;
            }
            Translation.println("continue_key");
            new Scanner(System.in).nextLine();
            i++;
        }
    }

    private static char[][] askApiKeys() {
        char[][] keys = new char[2][];
        Translation.println("create.api.paste_public");
        keys[0] = HSConsoleInput.getString().toCharArray();
        Translation.println("create.api.paste_private");
        keys[1] = HSConsoleInput.getString().toCharArray();
        return keys;
    }

    private static char[][] showApiChecklist() {
        int i = 1;
        char[][] keys = new char[2][];

        // Print values until exception is thrown
        while (true) {
            System.out.print(i + ". ");
            try {
                Translation.println("create.api.ch" + i);
            } catch (MissingResourceException ignore) {
                return keys;
            }

            if (i == 6) {
                keys = askApiKeys();
            } else {
                Translation.println("continue_key");
                new Scanner(System.in).nextLine();
            }
            i++;
        }
    }

    /**
     * Set a new value for a campaign property
     * If key is not already present, new one is created
     *
     * @param key   Key for the value
     * @param value Value for the specified key, keep empty to delete the key value pairs
     */
    private void campaignProperties(String key, String value) {
        Properties properties = getPropertyFile();
        if (value.equals(""))
            properties.remove(key);
        else {
            properties.setProperty(key, value);
        }
    }

    private Properties getPropertyFile() {
        Properties properties = new Properties();
        File file = new File("campaigns/" + campaignName + ".properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileInputStream fis = new FileInputStream("campaigns/" + campaignName + ".properties")) {
            properties.load(fis);
        } catch (IOException ignore) {
            return properties;
        }
        return properties;
    }
}
