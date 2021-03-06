package doublej.bobtudy.http.post;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.http.Config;
import doublej.bobtudy.http.Http;
import doublej.bobtudy.http.handler.PostHandler;
import doublej.bobtudy.http.handler.PostListHandler;

/**
 * Created by Jun on 2014-12-02.
 */
public class PostHttp extends Http {
    private static final String tag = "PostHttp";
    private static final String url = Config.SERVER_URL + "/post";

    public static void list(final PostListHandler handler) {
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        JSONArray postJsonArr = res.getJSONArray("data");

                        ArrayList<Post> posts = new ArrayList<Post>();
                        for (int i = 0; i < postJsonArr.length(); i++) {
                            JSONObject postObj = postJsonArr.getJSONObject(i);
                            Post post = Post.parseJsonObject(postObj);
                            posts.add(post);
                        }
                        // Callback
                        handler.onResponse(posts);
                    } else if (failure == 1)
                        Log.e(tag, "list failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * {
     * success: 1,
     * failure: 0,
     * data:
     * {
     * _id: '548596bcb73186a41f67da15'
     * title: 'User3의 밥룸!',
     * date: '2014-12-08T12:17:00.520Z',
     * menu: '돈부우우우리',
     * place: '후우우우우문',
     * content: '밥먹을사라아아아아암',
     * boss: 'user3',
     * postedDate: '2014-12-08T12:17:00.524
     * users: [ 'user3' ],
     * accesses: [],
     * chats: [],
     * attend: {},
     * } }
     *
     * @param newPost
     * @param postHandler
     */
    public static void create(NewPost newPost, final PostHandler postHandler) {
        RequestParams params = new RequestParams();
        params.put("title", newPost.getTitle());
        params.put("date", newPost.getDate());
        params.put("menu", newPost.getMenu());
        params.put("place", newPost.getPlace());
        params.put("content", newPost.getContent());
        params.put("boss", newPost.getBossId());

        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int success = response.getInt("success");
                    int failure = response.getInt("failure");
                    JSONObject data = response.getJSONObject("data");

                    if (success == 1) {
                        Post post = Post.parseJsonObject(data);
                        postHandler.onResponse(post);
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        };

        client.post(url, params, handler);
    }
}
