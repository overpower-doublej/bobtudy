package doublej.bobtudy.UI.Join;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;
import doublej.bobtudy.gcm.Gcm;
import doublej.bobtudy.http.handler.BoolResultHandler;
import doublej.bobtudy.http.handler.ResponseHandler;
import doublej.bobtudy.http.user.NewUser;
import doublej.bobtudy.http.user.UserHttp;


public class JoinActivity extends Activity {

    Button certifyID, confirmJoin, cancelJoin;

    ArrayAdapter<String> adapter;

    static int isIDvalid = 0;

    String[] items = {"행정학과", "국제관계학과", "경제학과", "사회복지학과", "세무학과", "법학과", "--------------------------------",
            "경영학부", "전자전기컴퓨터공학부", "화학공학과", "기계정보공학과", "신소재공학과", "토목공학과", "컴퓨터과학부", "--------------------------------",
            "영어영문학과", "국어국문학과", "국사학과", "철학과", "중국어문화학과", "--------------------------------", "환경원예학과", "통계학과",
            "수학과", "물리학과", "생명과학과", "--------------------------------", "건축학부", "도시공학과", "교통공학과", "조경학과", "도시행정학과",
            "도시사회학과", "공간정보공학과", "환경공학", "--------------------------------", "음악학과", "산업디자인학과", "환경조각학과", "스포츠과학",
            "--------------------------------", "자유전공학부",};

    private EditText editId;
    private EditText editPwd1;
    private EditText editPwd2;
    private EditText editNickName;
    private EditText editName;
    private EditText editStuId;

    private String _id;
    private String pwd1;
    private String pwd2;
    private String nickName;
    private String name;
    private String stuId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.join_main);

        certifyID = (Button) findViewById(R.id.certifyID);
        confirmJoin = (Button) findViewById(R.id.confirmJoin);
        cancelJoin = (Button) findViewById(R.id.cancelJoin);

        editId = (EditText) findViewById(R.id.editId);
        editPwd1 = (EditText) findViewById(R.id.editPwd1);
        editPwd2 = (EditText) findViewById(R.id.editPwd2);
        editNickName = (EditText) findViewById(R.id.editNickName);
        editName = (EditText) findViewById(R.id.editName);
        editStuId = (EditText) findViewById(R.id.editStuId);

//        MyDatabase myDB = new MyDatabase(this);
//        final SQLiteDatabase db = myDB.getWritableDatabase();

        final String[] dept = new String[1];
        dept[0] = null;

        Spinner spin = (Spinner) findViewById(R.id.deptspinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept[0] = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        certifyID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchId = editId.getText().toString();
                UserHttp.checkIdExists(searchId, new BoolResultHandler() {
                    @Override
                    public void onResponse(Boolean result) {
                        if (!result) {
                            Toast.makeText(JoinActivity.this, "사용하실 수 있습니다.", Toast.LENGTH_SHORT).show();
                            isIDvalid = 1;
                        } else {
                            Toast.makeText(JoinActivity.this, "중복된 ID입니다.", Toast.LENGTH_SHORT).show();
                            isIDvalid = 0;
                        }
                    }
                });


                /*String sql = "SELECT * FROM myInfo WHERE id LIKE ?";

                Cursor cursor = db.rawQuery(sql, new String[]{searchId});

                if (cursor.getCount() == 0) {
                    Toast.makeText(JoinActivity.this, "사용하실 수 있습니다.",
                            Toast.LENGTH_SHORT).show();
                    isIDvalid = 1;
                } else {
                    Toast.makeText(JoinActivity.this, "중복된 ID입니다.",
                            Toast.LENGTH_SHORT).show();
                    isIDvalid = 0;
                }*/
            }
        });

        confirmJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isIDvalid == 1) {
                    if (!(editPwd1.getText().toString().equals(null)) || !(editPwd2.getText().toString().equals(null)) ||
                            !(editNickName.getText().toString().equals(null)) || !(editName.getText().toString().equals(null)) ||
                            !(editStuId.getText().toString().equals(null)) || dept[0] != null) {
                        if (editPwd1.getText().toString()
                                .equals(editPwd2.getText().toString())) {
                            if (editPwd1.getText().toString()
                                    .equals(editPwd2.getText().toString())) {

                                /*

                                ContentValues values = new ContentValues();
                                values.put("id", editId.getText().toString());
                                values.put("pwd", editPwd1.getText().toString());
                                values.put("nickName", editNickName.getText().toString());
                                values.put("name", editName.getText().toString());
                                values.put("dept", dept[0]);
                                values.put("stuId", editStuId.getText().toString());

                                db.insert("myInfo", null, values); */

                                new Gcm(JoinActivity.this).getRegId(new Gcm.RegIdHandler() {
                                    @Override
                                    public void onResponse(String registrationId) {
                                        String id = editId.getText().toString();
                                        String pwd = editPwd1.getText().toString();
                                        String name = editName.getText().toString();
                                        String department = dept[0];
                                        final String stuId = editStuId.getText().toString();
                                        final String regId = registrationId;

                                        NewUser newUser = new NewUser(id, pwd, name, department, stuId, "", regId);

                                        UserHttp.join(newUser, new ResponseHandler() {
                                            @Override
                                            public void onResponse(JSONObject jsonObject) {
                                                finish();
                                                overridePendingTransition(R.anim.leftin, R.anim.leftout);

                                                Toast.makeText(JoinActivity.this, "가입이 완료되었습니다.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(JoinActivity.this, "모든 정보를 입력해 주세요.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JoinActivity.this, "아이디 중복체크를 해 주세요.",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

        cancelJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.leftin, R.anim.leftout);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.leftin, R.anim.leftout);
    }
}
