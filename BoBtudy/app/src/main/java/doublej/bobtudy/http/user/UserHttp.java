package doublej.bobtudy.http.user;

import com.loopj.android.http.*;

import doublej.bobtudy.http.Config;
import doublej.bobtudy.http.Http;

/**
 * Created by Jun on 2014-12-01.
 */
public class UserHttp extends Http {
    private static final String url = Config.SERVER_URL + "/user";

    /**
     * res:
     * { success: 1, failure: 0 }
     *
     * @param newUser
     * @param handler
     */
    public static void join(NewUser newUser, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("_id", newUser.getId());
        params.put("pwd", newUser.getPwd());
        params.put("name", newUser.getName());
        params.put("dept", newUser.getDept());
        params.put("stuId", newUser.getStuId());
        params.put("info", newUser.getInfo());
        params.put("regId", newUser.getRegId());

        client.post(url, params, handler);
    }

    /**
     * res:
     * 존재하는 ID --> { success: 1, failure: 0, data: { exists: 1 } }
     * 존재하지 않는 ID --> { success: 1, failure: 0, data: { exists: 0 } }
     *
     * @param id
     * @param handler
     */
    public static void checkId(String id, JsonHttpResponseHandler handler) {
        client.get(url + "/check/" + id, handler);
    }

    /**
     * res:
     * { success: 1,
     * failure: 0,
     * data: {
     * _id: "user id",
     * name: "user name",
     * meetLog: [0, 1]
     * }
     * }
     * meetLog: 크기가 2인 int형 배열. 0번째 인자는 출석횟수, 1번째 인자는 총 약속잡은 횟수
     *
     * @param id
     * @param handler
     */
    public static void getUser(String id, JsonHttpResponseHandler handler) {
        client.get(url + "/" + id, handler);
    }

    /**
     * res:
     * 비밀번호 일치 --> { success: 1, failure: 0, correct: 1 }
     * 비밀번호 틀림 --> { success: 1, failure: 0, correct: 0 }
     *
     * @param id
     * @param pwd
     * @param handler
     */
    public static void login(String id, String pwd, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pwd", pwd);

        client.post(url + "/" + id, params, handler);
    }
}
