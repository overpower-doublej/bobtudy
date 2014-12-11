package doublej.bobtudy.form.post;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import doublej.bobtudy.http.post.PostIdHttp;
import doublej.bobtudy.util.ISODate;

/**
 * Created by Jun on 2014-12-08.
 */
public class Access {
    private String postId;
    private String id;
    private String userId;
    private HashMap<String, Boolean> votes;
    private ISODate date;
    private Boolean result;

    public Access(String postId, String id, String userId, String isoString, Boolean result) {
        this.postId = postId;
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

    public void refresh(final Post.ResponseHandler handler) {
        PostIdHttp.getAccess(this.postId, this.id, new PostIdHttp.AccessHandler() {
            @Override
            public void onSuccess(Access access) {
                handler.onResponse();
            }
        });
    }

    public void vote(String userId, boolean result, final Post.ResponseHandler handler) {
        PostIdHttp.voteAccess(this.postId, this.id, userId, result, new PostIdHttp.PlainHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                handler.onResponse();
            }
        });
    }

    @Override
    public String toString() {
        if (this.result == null)
            return this.id + " - " + this.userId + " - " + "NULL" + " - " + date.toString();
        else
            return this.id + " - " + this.userId + " - " + Boolean.toString(this.result) + " - " + date.toString();
    }

    public static Access parseJsonAccess(String postId, JSONObject accessJsonObj) {
        String id = accessJsonObj.optString("_id");
        String userId = accessJsonObj.optString("userId");
        String isoString = accessJsonObj.optString("date");
        Boolean result = null;
        if (!accessJsonObj.isNull("result"))
            result = accessJsonObj.optBoolean("result");

        Access access = new Access(postId, id, userId, isoString, result);

        HashMap votes = new HashMap();
        JSONObject votesJsonObj = accessJsonObj.optJSONObject("result");
        if (votesJsonObj != null) {
            Iterator<String> it = votesJsonObj.keys();
            while (it.hasNext()) {
                String key = it.next();
                Boolean value = null;
                if (!votesJsonObj.isNull(key))
                    value = votesJsonObj.optBoolean(key);

                votes.put(key, value);
            }
        }
        access.setVotes(votes);

        return access;
    }
}
