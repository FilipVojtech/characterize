package mongodb;

import java.net.http.HttpResponse;
import java.util.HashMap;

public class ResponseParser {
    private final String string;
    private HashMap<String, String> map;

    public ResponseParser(HttpResponse<String> response, String header) {
        // Get string value from header
        string = response.headers().firstValue("www-authenticate").get();
        System.out.println(string);
    }

//    public String get(String key) {
//        return map.get(key);
//    }

    /**
     * Parse the header string return the nonce
     *
     * @return Parsed nonce
     */
    public String getNonce() {
        String[] words = string.split(", ");
        String[] splitNonce = words[2].split("\"");
        return splitNonce[1];
    }
}
