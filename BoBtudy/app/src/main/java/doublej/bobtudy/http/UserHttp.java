package doublej.bobtudy.http;

import com.loopj.android.http.*;

/**
 * Created by Jun on 2014-12-01.
 */
public class UserHttp {
    private static final String ServerUrl = Config.SERVER_URL + "/user";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void join(RequestParams params, JsonHttpResponseHandler handler) {
        client.post(ServerUrl, params, handler);
    }
}
