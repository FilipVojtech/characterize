package characterize;

import java.util.Date;
import java.util.HashMap;

/**
 * Character class for saving character details
 */
public class Character {
    private String nickname = null;
    private String name = null; // Required
    private String middleName = null;
    private String surname = null;
    private MBTITypes mbti = null;
    private Alignment alignment = null; // Required
    private boolean deceased = false;
    private Date born = null;
    private Date death = null;
    private HashMap<String, String> misc = new HashMap<>();
}
