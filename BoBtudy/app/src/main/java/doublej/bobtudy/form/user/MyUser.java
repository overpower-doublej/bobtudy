package doublej.bobtudy.form.user;

import doublej.bobtudy.http.handler.BoolResultHandler;
import doublej.bobtudy.http.user.UserHttp;

/**
 * Created by Jun on 2014-12-12.
 */
public class MyUser extends User {
    private String pwd;
    private String dept;
    private String stuId;

    public MyUser(String id, String name, String pwd, String dept, String stuId) {
        super(id, name);

        this.pwd = pwd;
        this.dept = dept;
        this.stuId = stuId;
    }

    public void login(BoolResultHandler handler) {
        UserHttp.login(this.id, this.pwd, handler);
    }
}
