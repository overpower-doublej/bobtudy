package doublej.bobtudy.http.post;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import doublej.bobtudy.form.post.Access;
import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.form.user.User;
import doublej.bobtudy.http.Config;
import doublej.bobtudy.http.Http;
import doublej.bobtudy.http.handler.AccessHandler;
import doublej.bobtudy.http.handler.PostHandler;
import doublej.bobtudy.http.handler.ResponseHandler;

/**
 * Created by Jun on 2014-12-11.
 */
public class PostIdHttp extends Http {
    private static final String tag = "PostIdHttp";
    private static final String url = Config.SERVER_URL + "/post";

    public static void getPost(String postId, final PostHandler handler) {
        client.get(url + "/" + postId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        JSONObject postJsonObj = res.getJSONObject("data");
                        Post post = Post.parseJsonObject(postJsonObj);

                        handler.onResponse(post);
                    } else if (failure == 1)
                        Log.e(tag, "list failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Fetch one access object.
     *
     * @param postId
     * @param accessId
     * @param handler
     */
    public static void getAccess(final String postId, String accessId, final AccessHandler handler) {
        client.get(url + "/" + postId + "/acs/" + accessId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        Access access = Access.parseJsonAccess(postId, res.getJSONObject("data"));
                        handler.onResponse(access);
                    } else if (failure == 1)
                        Log.e(tag, "Internal server failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Fetch every accesses that post has
     *
     * @param post
     * @param handler
     */
    public static void listAccess(final Post post, final PostHandler handler) {
        client.get(url + "/" + post.getId() + "/acs", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        JSONArray acsJsonArr = res.getJSONArray("data");
                        for (int i = 0; i < acsJsonArr.length(); i++) {
                            JSONObject acsJsonObj = acsJsonArr.getJSONObject(i);
                            Access acs = Access.parseJsonAccess(post.getId(), acsJsonObj);
                            post.addAccess(acs);
                        }

                        handler.onResponse(post);
                    } else if (failure == 1)
                        Log.e(tag, "list failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void voteAccess(String postId, String accessId, String userId, boolean vote, final ResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("vote", vote);

        client.post(url + "/" + postId + "/acs/" + accessId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        handler.onResponse(res);
                    } else if (failure == 1)
                        Log.e(tag, "Internal server failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void createAccess(Post post, User user, final ResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("userId", user.getId());
        client.post(url + "/" + post.getId() + "/acs", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        handler.onResponse(res);
                    } else if (failure == 1)
                        Log.e(tag, "Internal server failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void createAccess(Post post, String userId, final ResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        client.post(url + "/" + post.getId() + "/acs", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res) {
                try {
                    int success = res.getInt("success");
                    int failure = res.getInt("failure");

                    if (success == 1) {
                        handler.onResponse(res);
                    } else if (failure == 1)
                        Log.e(tag, "Internal server failure");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
