package mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Mongo {
    private static final String dbUsr = System.getenv("DATABASE_USER");
    private static final String dbPwd = System.getenv("DATABASE_PWD");
    private static final MongoClient mongoClient = MongoClients.create(
            "mongodb+srv://" + dbUsr + ":" + dbPwd + "@cluster0.wzgos.mongodb.net/characterize?retryWrites=true&w=majority"
    );

    public static MongoCollection<Document> getCollection(String collectionName) {
        return mongoClient.getDatabase("characterize").getCollection(collectionName);
    }

    static {
//        Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
    }
}
