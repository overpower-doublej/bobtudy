package doublej.bobtudy.http.post;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import doublej.bobtudy.form.post.Chat;
import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.http.Config;
import doublej.bobtudy.http.Http;
import doublej.bobtudy.http.handler.ChatListHandler;
import doublej.bobtudy.http.handler.ResponseHandler;
import doublej.bobtudy.util.ISODate;

/**
 * Created by Jun on 2014-12-12.
 */
public class PostChatHttp extends Http {
    private static final String tag = "PostHttp";
    private static final String url = Config.SERVER_URL + "/post";

    public static void listChatAfter(String postId, ISODate after, final ChatListHandler handler) {
        client.get(url + "/" + postId + "/chat/after/" + after.toISOString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        JSONArray chatJsonArr = res.getJSONArray("data");

                        ArrayList<Chat> chats = new ArrayList<Chat>();
                        for (int i = 0; i < chatJsonArr.length(); i++) {
                            JSONObject chatJsonObj = chatJsonArr.getJSONObject(i);
                            Chat chat = Chat.parseJsonObj(chatJsonObj);
                            chats.add(chat);
                        }
                        // Callback
                        handler.onResponse(chats);
                    } else if (failure == 1)
                        Log.e(tag, "list failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void sendChat(String postId, String userId, String msg, final ResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("msg", msg);

        client.post(url + "/" + postId + "/chat", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                handler.onResponse(response);
            }
        });
    }
}
