package doublej.bobtudy.Control;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by YeomJi on 14. 12. 8..
 */
public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "bobtudy2.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}