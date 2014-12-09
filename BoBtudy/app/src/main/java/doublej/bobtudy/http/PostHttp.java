package doublej.bobtudy.http;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.form.user.User;
import doublej.bobtudy.util.ISODate;

/**
 * Created by Jun on 2014-12-02.
 */
public class PostHttp extends Http {
    private static final String tag = "PostHttp";
    private static final String url = Config.SERVER_URL + "/post";

    public static void list(JsonHttpResponseHandler handler) {
        client.get(url, handler);
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
                Post post = null;
                try {
                    int success = response.getInt("success");
                    int failure = response.getInt("failure");
                    JSONObject data = response.getJSONObject("data");

                    String id = data.getString("_id");
                    String title = data.getString("title");
                    String dateString = data.getString("date");
                    ISODate date = ISODate.getInstanceByIsoString(dateString);
                    String menu = data.getString("menu");
                    String place = data.getString("place");
                    String content = data.getString("content");
                    String bossId = data.getString("boss");
                    String postedDateString = data.getString("postedDate");
                    ISODate postedDate = ISODate.getInstanceByIsoString(postedDateString);

                    post = new Post(id, title, date, menu, place, content, bossId, postedDate);
                    post.addUser(User.getUser(bossId));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                postHandler.onSuccess(post);
            }
        };

        client.post(url, params, handler);
    }

    public interface PostHandler {
        void onSuccess(Post post);
    }
}
