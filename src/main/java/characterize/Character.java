package characterize;

import java.util.Date;
import java.util.HashMap;

public class Character implements ICharacter {
    String name = null;
    String surname = null;
    String nickname = null;
    MBTITypes mbti = null;
    Alignment alignment = null;
    boolean deceased = false;
    Date born = null;
    Date death = null;
    HashMap<String, String> misc = new HashMap<>();

    public Character() {
    }

    @Override
    public void addNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Add entry
     */
    @Override
    public void add() {

    }

    /**
     * Edit entry
     */
    @Override
    public void edit() {

    }

    /**
     * Delete entry
     */
    @Override
    public void remove() {

    }
}
