package doublej.bobtudy.http.post;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.http.Config;
import doublej.bobtudy.http.Http;
import doublej.bobtudy.util.ISODate;

/**
 * Created by Jun on 2014-12-11.
 */
public class PostIdHttp extends Http {
    private static final String tag = "PostIdHttp";
    private static final String url = Config.SERVER_URL + "/post";

    private String postId;

    public PostIdHttp(String postId) {
        this.postId = postId;
    }

    public void get(PostHandler handler) {
        client.get(url + "/" + this.postId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        JSONObject postJsonObj = res.getJSONObject("data");

                        Post post = new Post(id, title, date, menu, place, content, bossId, postedDate);


                        // Callback
                        //handler.onSuccess(posts);
                    } else if (failure == 1)
                        Log.e(tag, "list failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public interface PostHandler {
        void onSuccess(Post post);
    }
}
