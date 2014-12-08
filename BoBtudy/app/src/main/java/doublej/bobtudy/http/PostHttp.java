package doublej.bobtudy.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Jun on 2014-12-02.
 */
public class PostHttp {
    private static final String url = Config.SERVER_URL + "/post";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void list(JsonHttpResponseHandler handler) {
        client.get(url, handler);
    }

    public static void create(RequestParams params, JsonHttpResponseHandler handler) {
        client.post(url, params, handler);
    }
}
