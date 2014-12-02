package doublej.bobtudy;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;

import doublej.bobtudy.http.UserHttp;


public class JoinActivity extends Activity {

    Button certifyID, confirmJoin, cancelJoin;

    ArrayAdapter<String> adapter;

    String[] items = {"apple", "lemon", "orange"};

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

        Spinner spin = (Spinner) findViewById(R.id.deptspinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(adapter);


        certifyID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        confirmJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
            }
        });

        cancelJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
