package doublej.bobtudy.http.handler;

import org.json.JSONObject;

/**
 * Created by Jun on 2014-12-11.
 */
public interface ResponseHandler {
    void onResponse(JSONObject jsonObject);
}
