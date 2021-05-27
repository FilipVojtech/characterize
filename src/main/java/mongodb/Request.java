package mongodb;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Request {
    private char[][] apiKeys;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public Request(char[][] apiKeys) {
        this.apiKeys = apiKeys;
    }

    public void send() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cloud.mongodb.com/api/atlas/v1.0"))
                .GET()
                .setHeader("User-Agent", "Java 15 HttpClient Bot")
                .setHeader("Authorization", "Digest " + passwordDigest())
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String passwordDigest() {
        MessageDigest digest;
        String asdsad = new String(apiKeys[0]) + ":MMS Public API:" + new String(apiKeys[1]);

        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(new String(apiKeys[0]).getBytes());
            digest.update(challengeRequest().getBytes());
            digest.update(new String(apiKeys[1]).getBytes());
            return Arrays.toString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String challengeRequest() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://cloud.mongodb.com/api/atlas/v1.0"))
                .setHeader("User-Agent", "Java 15 HttpClient Bot")
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResponseParser parser = null;
        if (response != null && response.statusCode() == 401) {
            parser = new ResponseParser(response, "www-authenticate");
            return parser.getNonce();
        }
        return null;
    }
}
