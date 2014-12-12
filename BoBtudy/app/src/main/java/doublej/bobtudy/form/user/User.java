package doublej.bobtudy.form.user;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import doublej.bobtudy.http.handler.BoolResultHandler;
import doublej.bobtudy.http.user.UserHttp;

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

    public String getName() {
        return this.name;
    }

    public int[] getMeetLog() {
        return this.meetLog;
    }

    public void setMeetLog(int[] meetLog) {
        this.meetLog = meetLog;
    }

    @Override
    public String toString() {
        return this.id + "/" + this.name + "/" + meetLog.toString();
    }

    public static User parseJsonObj(JSONObject jsonObject) {
        String id = jsonObject.optString("_id");
        String name = jsonObject.optString("name");

        JSONArray meetLogJsonArr = jsonObject.optJSONArray("meetLog");
        int meetSuccess = meetLogJsonArr.optInt(0);
        int totalMeet = meetLogJsonArr.optInt(1);

        User user = new User(id, name);
        user.setMeetLog(new int[]{meetSuccess, totalMeet});

        return user;
    }

    public static User findUser(String id) {
        return (User) userMap.get(id);
    }
}
