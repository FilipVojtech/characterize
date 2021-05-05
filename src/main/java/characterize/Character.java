package characterize;

import java.util.HashMap;

public class Character implements ICharacter {
//    String name = null;
//    String surname = null;
    String nickname = null;
    MBTITypes mbti = null;
    Alignment alignment = null;
    boolean deceased = false;
//    String born = null;
//    String death = null;

    HashMap<String, String> info = new HashMap<>();

    public Character() {
        info.put("name", null);
        info.put("surname", null);
        info.put("born", null);
        info.put("death", null);
    }

    @Override
    public void addNickname(String nickname) {
        this.nickname = nickname;
    }
}
