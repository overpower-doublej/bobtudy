package doublej.bobtudy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import doublej.bobtudy.http.PostHttp;
import doublej.bobtudy.http.UserHttp;
import doublej.bobtudy.util.ISODate;


public class TestActivity extends Activity {
    private static final String tag = "TestActivity";

    private TextView txt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        txt = (TextView) findViewById(R.id.txt);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonHttpResponseHandler commonHandler = new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        appendTxt(response.toString());
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        appendTxt(response.toString());
                    }
                };

                String _id = "ktj7147";

                RequestParams params = new RequestParams();
                params.put("_id", _id);
                params.put("pwd", "123123");
                params.put("name", "김태준");
                params.put("dept", "전자전기컴퓨터");
                params.put("stuId", "2012440037");
                params.put("info", "자기소개 없음!");
                params.put("regId", "adfadafsdfa");

                // User join
                //UserHttp.join(params, commonHandler);
                // List BoB rooms
                PostHttp.list(commonHandler);

                params = new RequestParams();
                params.put("title", "밥터디터디");
                params.put("date", new ISODate().toISOString());
                params.put("menu", "돈부리");
                params.put("place", "후문");
                params.put("content", "asdfasdfasdfasdfasdf");
                params.put("boss", _id);

                PostHttp.create(params, commonHandler);

            }
        });
    }

    private void appendTxt(String msg) {
        txt.setText(txt.getText() + "\n" + msg);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
