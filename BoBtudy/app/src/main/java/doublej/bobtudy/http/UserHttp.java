package doublej.bobtudy.http;

import com.loopj.android.http.*;

/**
 * Created by Jun on 2014-12-01.
 */
public class UserHttp {
    private static final String ServerUrl = "http://ktj7147.iptime.org/user";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(ServerUrl, params, responseHandler);
    }
}
