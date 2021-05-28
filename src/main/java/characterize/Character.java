package characterize;

import mongodb.Mongo;

import java.util.*;

/**
 * Character class for saving character details
 */
public class Character {
    private String nickname = null;
    private String name = null; // Required
    private String middleName = "";
    private String surname = null;
    private MbtiTypes mbti = null;
    private Alignment alignment = null; // Required
    private boolean deceased = false;
    private Date born = null;
    private Date death = null;
    private HashMap<String, String> misc = new HashMap<>();
    /**
     * MongoDB variable detecting the schema used
     * if it were to change
     */
    private int schemaVersion = 1;

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

    public static void command(String input) {
        String[] args = input.split("\s(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
        Character character = new Character();

        for (int i = 0; i < args.length; i++)
            args[i] = args[i].replace("\"", "");

        switch (args[0]) {
            case "add":
                character.parseName(args[1]);
                for (String arg : args) {
                    // Guess MBTI type
                    for (MbtiTypes type : MbtiTypes.values()) {
                        System.out.println(type + " : " + arg);
                        if (type.toString().equals(arg)) {
                            character.setMbti(type);
                            break;
                        }
                    }

                    // Guess Alignment
                    for (Alignment alignment : Alignment.values()) {
                        String alignmentString = alignment.toString().toLowerCase().replace("_", " ");
                        System.out.println(alignmentString + " : " + arg);
                        if (alignmentString.equals(arg.toLowerCase())) {
                            character.setAlignment(alignment);
                            break;
                        }
                    }
                }
                Mongo.add(character);
                break;
            default:
                Translation.print("unknown");
        }

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

    public MbtiTypes getMbti() {
        return mbti;
    }

    public void setMbti(MbtiTypes mbti) {
        this.mbti = mbti;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public boolean isDeceased() {
        return deceased;
    }

    public void setDeceased(boolean deceased) {
        this.deceased = deceased;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public Date getDeath() {
        return death;
    }

    public void setDeath(Date death) {
        this.death = death;
    }

    public HashMap<String, String> getMisc() {
        return misc;
    }

    public void setMisc(HashMap<String, String> misc) {
        this.misc = misc;
    }

    public int getSchemaVersion() {
        return schemaVersion;
    }

    @Override
    public String toString() {
        return "Character{" +
               "nickname='" + nickname + '\'' +
               ", name='" + name + '\'' +
               ", middleName='" + middleName + '\'' +
               ", surname='" + surname + '\'' +
               ", mbti=" + mbti +
               ", alignment=" + alignment +
               ", deceased=" + deceased +
               ", born=" + born +
               ", death=" + death +
               ", misc=" + misc +
               '}';
    }
}
