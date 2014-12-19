package doublej.bobtudy.form.post;

import org.json.JSONObject;

import doublej.bobtudy.form.user.User;
import doublej.bobtudy.util.ISODate;

/**
 * Created by Jun on 2014-12-12.
 */
public class Chat {
    private ISODate date;
    private String userId;
    private String msg;

    public Chat(ISODate date, String userId, String msg) {
        this.date = date;
        this.userId = userId;
        this.msg = msg;
    }

    public ISODate getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return date.getHours() + ":" + date.getMinutes() + " - " + userId + ": " + msg;
    }

    public static Chat parseJsonObj(JSONObject jsonObject) {
        String dateString = jsonObject.optString("date");
        ISODate date = ISODate.getInstanceByIsoString(dateString);

        String userId = jsonObject.optString("userId");
        String msg = jsonObject.optString("msg");

        return new Chat(date, userId, msg);
    }
}
