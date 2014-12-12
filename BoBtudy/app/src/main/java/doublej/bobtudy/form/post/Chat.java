package doublej.bobtudy.form.post;

import org.json.JSONObject;

import doublej.bobtudy.form.user.User;
import doublej.bobtudy.util.ISODate;

/**
 * Created by Jun on 2014-12-12.
 */
public class Chat {
    private ISODate date;
    private User user;
    private String msg;

    public Chat(ISODate date, User user, String msg) {
        this.date = date;
        this.user = user;
        this.msg = msg;
    }

    public static Chat parseJsonObj(JSONObject jsonObject) {
        String dateString = jsonObject.optString("date");
        ISODate date = ISODate.getInstanceByIsoString(dateString);

        String userId = jsonObject.optString("userId");
        String msg = jsonObject.optString("msg");

        User user = User.findUser(userId);

        return new Chat(date, userId, msg);
    }
}
