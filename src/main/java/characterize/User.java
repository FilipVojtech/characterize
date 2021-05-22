package characterize;

import com.mongodb.client.MongoCollection;
import cz.polygonunicorn.javahandysnippets.HSConsoleInput;
import mongodb.Mongo;
import org.bson.Document;

public class User {
    private String email = null;
    private boolean loggedIn = false;

    public void register() {
        System.out.println("Register thingy");

    }

    /**
     * Ask user their login details, fetch data and log them in
     * Wrong details won't return a document so we will print an error message else log in
     */
    public void login() {
        String email = HSConsoleInput.getString(Translation.get("email"), true);
//            char[] password = System.console().readPassword();
        String password = HSConsoleInput.getString(Translation.get("password"), true);

        Document query = new Document("email", new Document("$eq", email));
//        query.append("password", new Document("$eq", Arrays.toString(password)));
        query.append("password", new Document("$eq", password));
        MongoCollection<Document> users;

        try {
            users = Mongo.getCollection("users");
        } catch (ExceptionInInitializerError e) {
            Translation.print("error_connection");
            return;
        }

        var document = users.find(query).first();

        if (document != null) {
            this.email = document.getString("email");
            this.loggedIn = true;
        } else {
            Translation.print("wrong_login");
        }
    }

    /**
     * Nullify user details resulting in log out
     */
    public void logout() {
        this.email = null;
        this.loggedIn = false;
    }

    /**
     * @return login status
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
}
