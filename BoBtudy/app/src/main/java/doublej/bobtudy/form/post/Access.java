package doublej.bobtudy.form.post;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import doublej.bobtudy.util.ISODate;

/**
 * Created by Jun on 2014-12-08.
 */
public class Access {
    private String id;
    private String userId;
    private HashMap<String, Boolean> votes;
    private ISODate date;
    private boolean result;

    public Access(String id, String userId, String isoString, boolean result) {
        this.id = id;
        this.userId = userId;
        this.date = ISODate.getInstanceByIsoString(isoString);
        this.result = result;
    }

    public void setVotes(HashMap votes) {
        this.votes = votes;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.userId + " - " + Boolean.toString(this.result) + " - " + date.toString();
    }

    public static Access parseJsonAccess(JSONObject accessJsonObj) {
        String id = accessJsonObj.optString("_id");
        String userId = accessJsonObj.optString("userId");
        String isoString = accessJsonObj.optString("date");
        boolean result = accessJsonObj.optBoolean("result");

        Access access = new Access(id, userId, isoString, result);

        HashMap votes = new HashMap();
        JSONObject votesJsonObj = accessJsonObj.optJSONObject("result");
        if (votesJsonObj != null) {
            Iterator<String> it = votesJsonObj.keys();
            while (it.hasNext()) {
                String key = it.next();
                boolean value = votesJsonObj.optBoolean(key);

                votes.put(key, value);
            }
        }
        access.setVotes(votes);

        return access;
    }
}
