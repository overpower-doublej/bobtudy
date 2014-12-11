package doublej.bobtudy.form.user;

import java.util.HashMap;

/**
 * Created by Jun on 2014-12-08.
 */
public class User {
    protected String id;
    protected String name;
    private int[] meetLog;

    private static HashMap userMap = new HashMap();

    public User(String id, String name) {
        this.id = id;
        this.name = name;

        userMap.put(id, this);
    }

    public String getId() {
        return this.id;
    }

    public static User getUser(String id) {
        return (User) userMap.get(id);
    }
}
