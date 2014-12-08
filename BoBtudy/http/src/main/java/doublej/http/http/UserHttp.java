package doublej.http.http;

/**
 * Created by Jun on 2014-12-01.
 */
public class UserHttp {
    private static final String ServerUrl = "http://192.168.1.10/user";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void post(RequestParams params, JsonHttpResponseHandler handler) {
        client.post(ServerUrl, params, handler);
    }
}
