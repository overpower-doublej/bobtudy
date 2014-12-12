package doublej.bobtudy.form.user;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Properties;

import doublej.bobtudy.http.Http;
import doublej.bobtudy.http.handler.BoolResultHandler;
import doublej.bobtudy.http.handler.UserHandler;
import doublej.bobtudy.http.user.UserHttp;

/**
 * Created by Jun on 2014-12-08.
 */
public class User {
    private static final String tag = "UserPool";

    protected String id;
    protected String name;
    private int[] meetLog;

    private static HashMap userMap = new HashMap();

    protected User(String id, String name) {
        this.id = id;
        this.name = name;

        userMap.put(id, this);

        Log.i(tag, "User Pool size: " + Integer.toString(userMap.keySet().size()));
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

//    public void refresh(UserHandler handler) {
//        UserHttp.getUser(this.id, handler);
//    }

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

    public static void findUser(String id, UserHandler handler) {
        if (userMap.containsKey(id))
            handler.onResponse((User) userMap.get(id));
        else
            UserHttp.getUser(id, handler);
    }
}
