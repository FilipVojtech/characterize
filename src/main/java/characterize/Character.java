package characterize;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import cz.polygonunicorn.javahandysnippets.HSConsoleInput;
import mongodb.Mongo;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Character class for saving character details
 */
public class Character {
    private String nickname = null;
    private String name = null; // Required
    private String middleName = "";
    private String surname = null;
    private String characterClass = null;
    private MbtiTypes mbti = null;
    private Alignment alignment = null; // Required
    private boolean deceased = false;
    private List<String> misc = new ArrayList<>();
    /**
     * MongoDB variable detecting the schema used
     * if it were to change
     */
    private int schemaVersion = 1;
    private final static MongoCollection<Document> characterCollection = Mongo.getCollection("characters");

    public Character() {
    }

    /**
     * Create a character
     *
     * @param name      Required, Characters name
     *                  If name consists of multiple words, the first one gets picked as name, last one as surname and the rest as middle name
     * @param alignment Characters alignment
     * @param other     Add other attributes to a character
     *                  XXXX format will be recognized as a MBTI type
     */
    public Character(String name, Alignment alignment, String... other) {
        this.name = name;
        this.alignment = alignment;

        // todo: Parse others
    }

    public Character(Document document) {
        this.nickname = document.getString("nickname");
        this.name = document.getString("name");
        this.middleName = document.getString("middleName");
        this.surname = document.getString("surname");
        this.characterClass = document.getString("characterClass");
        this.mbti = MbtiTypes.valueOf(document.getString("mbti"));
        this.alignment = Alignment.valueOf(document.getString("alignment"));
        this.deceased = document.getBoolean("deceased");
    }

    public static void command(String input) {
        String[] args = input.split("\s(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

        for (int i = 0; i < args.length; i++)
            args[i] = args[i].replace("\"", "");

        switch (args[0]) {
            case "add" -> {
                Character character = new Character();
                character.parseName(args[1]);

                for (String arg : args) {
                    // Guess MBTI type
                    for (MbtiTypes type : MbtiTypes.values()) {
                        if (arg.length() != 4) continue;
                        if (type.toString().equals(arg)) {
                            character.setMbti(type);
                            break;
                        } else character.setMbti(MbtiTypes.Unknown);
                    }

                    // Guess Alignment
                    for (Alignment alignment : Alignment.values()) {
                        if (arg.split(" ").length != 2 || arg.equalsIgnoreCase("unaligned")) continue;
                        String alignmentString = alignment.toString().replace("_", " ");
                        if (alignmentString.equalsIgnoreCase(arg)) {
                            character.setAlignment(alignment);
                            break;
                        } else character.setAlignment(Alignment.Unknown);
                    }
                }
                character.print();
                Mongo.add(character);
            }
            case "edit" -> {
                FindIterable<Document> documents;
                try {
                    documents = searchAll(args[1].replace("\"", ""));
                } catch (Exception e) {
                    Translation.println("unknown");
                    break;
                }
                if (documents == null) {
                    Translation.println("nothing_found");
                    break;
                }
                list(documents);

                Translation.println("character.choose.edit");
                ObjectId id = getId(documents);
                if (id == null) break;
                editDocument(id);
            }
            case "delete" -> {
                FindIterable<Document> documents = searchAll(args[1].replace("\"", ""));
                if (documents == null) {
                    Translation.println("nothing_found");
                    break;
                }
                list(documents);

                Translation.println("character.choose.delete");
                ObjectId id = getId(documents);
                if (id == null) break;
                characterCollection.deleteOne(new Document("_id", id));
            }
            default -> Translation.println("unknown");
        }
    }

    private static void editDocument(ObjectId id) {
        FindIterable<Document> documents = characterCollection.find(new Document("_id", id)).limit(1);
        Document document = documents.iterator().next();
        Character character = new Character(document);
        System.out.println(character);

        while (true) {
            switch (HSConsoleInput.getString(Translation.get("choose_field"), true)) {
                case "nickname" -> character.setNickname(HSConsoleInput.getString(Translation.get("new_value"), false));
                case "name" -> character.setName(HSConsoleInput.getString(Translation.get("new_value"), false));
                case "surname" -> character.setSurname(HSConsoleInput.getString(Translation.get("new_value"), false));
                case "middleName" -> character.setMiddleName(HSConsoleInput.getString(Translation.get("new_value"), false));
                case "mbti" -> character.setMbti(Character.parseMbti());
                case "alignment" -> character.setAlignment(Character.parseAlignment());
                case "deceased" -> character.setDeceased(!HSConsoleInput.getBoolean(Translation.get("character.is_alive")));
                case "done" -> {
                    character.print();
                    Document newDocument = Mongo.createCharacterDocument(character);
                    if (newDocument == null) return;
                    characterCollection.findOneAndReplace(new Document("_id", id), newDocument);
                    return;
                }
                default -> Translation.println("unknown_field");
            }
            Translation.println("when_done");
        }
    }

    public static void list(FindIterable<Document> documents) {
        int i = 1;
        for (Document document : documents) {
            System.out.print(i + ". ");
            new Character(document).print();
            System.out.println();
            i++;
        }
    }

    public static ObjectId getId(FindIterable<Document> documents) {
        int choice = HSConsoleInput.getInt("Please choose a number");
        int j = 0;
        for (Document document : documents) {
            if (j == choice - 1)
                return document.getObjectId("_id");
            j++;
        }
        return null;
    }

    private static FindIterable<Document> searchAll(String value) {
        ArrayList<Document> orValue = new ArrayList<>();
        orValue.add(new Document("name", value));
        orValue.add(new Document("middleName", value));
        orValue.add(new Document("surname", value));
        orValue.add(new Document("nickname", value));
        FindIterable<Document> documents = characterCollection.find(new Document("$or", orValue));

        if (!documents.iterator().hasNext()) return null;
        return documents;
    }

    public void print() {
        StringBuilder builder = new StringBuilder();
        if (this.nickname != null) builder.append(this.nickname).append("\n");
        if (this.name != null) builder.append(this.name).append(" ");
        if (this.middleName != null) builder.append(this.middleName).append(" ");
        if (this.surname != null) builder.append(this.surname).append(" ");
        builder.append(this.deceased ? "†" : "").append("\n");
        if (this.mbti != null) builder.append(this.mbti.toString().toUpperCase()).append("\n");
        if (this.alignment != null) builder.append(this.alignment.toString().replace("_", " ")).append("\n");
        System.out.println(builder);
    }

    private void parseName(String name) {
        String[] names = name.split(" ");

        if (name.length() == 1)
            this.name = names[0];
        else if (name.length() == 2) {
            this.name = names[0];
            this.surname = names[1];
        } else {
            this.name = names[0];
            this.surname = names[names.length - 1];
            for (int i = 1; i < names.length - 1; i++)
                //noinspection StringConcatenationInLoop
                this.middleName = this.middleName + names[i] + " ";
            this.middleName = this.middleName.strip();
        }

    }

    public List<String> invalidFields() {
        List<String> invalidFields = new ArrayList<>();

        if (name == null) invalidFields.add("Name");
        if (alignment == null) invalidFields.add("Alignment");

        return invalidFields;
    }

    public boolean isValid() {
        return invalidFields().size() == 0;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public MbtiTypes getMbti() {
        return mbti;
    }

    public void setMbti(MbtiTypes mbti) {
        this.mbti = mbti;
    }

    public static MbtiTypes parseMbti() {
        MbtiTypes type;
        try {
            String value = HSConsoleInput.getString(Translation.get("new_value"), false).replace("\"", "").toUpperCase();
            type = MbtiTypes.valueOf(value);
        } catch (IllegalArgumentException e) {
            Translation.println("wrong_value");
            type = parseMbti();
        }
        return type;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public static Alignment parseAlignment() {
        Alignment alignment;
        try {
            String value = HSConsoleInput.getString(Translation.get("new_value"), false).replace("\"", "").toLowerCase();
            alignment = Alignment.valueOf(value);
        } catch (IllegalArgumentException e) {
            Translation.println("wrong_value");
            alignment = parseAlignment();
        }
        return alignment;
    }

    public boolean isDeceased() {
        return deceased;
    }

    public void setDeceased(boolean deceased) {
        this.deceased = deceased;
    }

    public List<String> getMisc() {
        return misc;
    }

    public void putMisc(String string) {
        this.misc.add(string);
    }

    public int getSchemaVersion() {
        return schemaVersion;
    }

    @Override
    public String toString() {
        return "Character {" +
               "\n\tnickname='" + nickname + '\'' +
               "\n\tname='" + name + '\'' +
               "\n\tmiddleName='" + middleName + '\'' +
               "\n\tsurname='" + surname + '\'' +
               "\n\tmbti=" + mbti +
               "\n\talignment=" + alignment +
               "\n\tdeceased=" + deceased +
               "\n\tmisc=" + misc +
               "\n}";
    }
}
