package mongodb;

import characterize.Character;
import characterize.Note;
import characterize.Quest;
import characterize.Translation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Mongo {
    private static final String dbUsr = System.getenv("DATABASE_USER");
    private static final String dbPwd = System.getenv("DATABASE_PWD");
//    private static final MongoClient mongoClient = MongoClients.create(
//            "mongodb+srv://" + dbUsr + ":" + dbPwd + "@cluster0.wzgos.mongodb.net/?retryWrites=true&w=majority"
//    );
    private static final MongoClient mongoClient = MongoClients.create();

    public static MongoCollection<Document> getCollection(String collectionName) {
        return mongoClient.getDatabase("characterize").getCollection(collectionName);
    }

    static {
//        Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
    }

    // todo Check if it exists based on *some* field

    /**
     * Add character to a database
     *
     * @param character Character class to save
     */
    public static void add(Character character) {
        Document characterDocument = new Document();

        if (!character.isValid()) {
            Translation.print("character.invalid");
            for (String field : character.invalidFields())
                System.out.println(field + " ");
            Translation.print("character.invalid_end");
            return;
        }

        if (character.getNickname() == null) characterDocument.put("nickname", character.getNickname());
        characterDocument.put("name", character.getName());
        if (character.getMiddleName() != null) characterDocument.put("middleName", character.getMiddleName());
        if (character.getSurname() != null) characterDocument.put("surname", character.getSurname());
        characterDocument.put("mbti", character.getMbti().toString());
        characterDocument.put("alignment", character.getAlignment().toString());
        characterDocument.put("deceased", character.isDeceased());
        if (character.getBorn() != null) characterDocument.put("born", character.getBorn());
        if (character.getDeath() != null) characterDocument.put("death", character.getDeath());
        if (!character.getMisc().isEmpty()) characterDocument.put("misc", character.getMisc());

        getCollection("characters").insertOne(characterDocument);
    }

    /**
     * Add quest to a database
     *
     * @param quest Quest class to save
     */
    public static void add(Quest quest) {

    }

    /**
     * Add note to a database
     *
     * @param note Note class to save
     */
    public static void add(Note note) {

    }

    /**
     * Edit entry
     */
    public void edit() {

    }

    /**
     * Delete entry
     */
    public void remove() {

    }

    /**
     * List every document
     */
    public void list() {

    }
}
