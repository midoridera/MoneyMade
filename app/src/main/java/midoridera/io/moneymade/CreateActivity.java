package midoridera.io.moneymade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class CreateActivity extends AppCompatActivity {

    public Realm realm;

    public EditText titleEditText;
    public EditText timeH1EditText;
    public EditText timeM1EditText;
    public EditText timeH2EditText;
    public EditText timeM2EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        realm = Realm.getDefaultInstance();

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        timeH1EditText = (EditText) findViewById(R.id.timeH1EditText);
        timeM1EditText = (EditText) findViewById(R.id.timeM1EditText);
        timeH2EditText = (EditText) findViewById(R.id.timeH2EditText);
        timeM2EditText = (EditText) findViewById(R.id.timeM2EditText);
    }

    public void save(final String title, final String updateDate, final String content1, final String content2, final String content3, final String content4){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Memo memo = realm.createObject(Memo.class);
                memo.title = title;
                memo.updateDate = updateDate;
                memo.content1 = content1;
                memo.content2 = content2;
                memo.content3 = content3;
                memo.content4 = content4;
            }
        });
    }

    public void create(View view) {
        String title = thingEditText.getText().toString();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPANESE);
        String updateDate = sdf.format(date);

        String content = moneyEditText.getText().toString();
        String content1 = timeH1EditText.getText().toString();
        String content2 = timeM1EditText.getText().toString();
        String content3 = timeH2EditText.getText().toString();
        String content4 = timeM2EditText.getText().toString();

//        check(title,updateDate,content);

        save(title, updateDate, content1, content2, content3, content4);

        finish();
    }

    private void check(String title, String updateDate, String content1, String content2, String content3, String content4) {
        Memo memo = new Memo();

        memo.title = title;
        memo.updateDate = updateDate;
        memo.content1 = content1;
        memo.content2 = content2;
        memo.content3 = content3;
        memo.content4 = content4;

        Log.d("Memo", memo.title);
        Log.d("Memo", memo.updateDate);
        Log.d("Memo", memo.content);
        Log.d("Memo", memo.content1);
        Log.d("Memo", memo.content2);
        Log.d("Memo", memo.content3);
        Log.d("Memo", memo.content4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();

    }
}