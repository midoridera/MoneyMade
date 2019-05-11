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

    public EditText thingEditText;
    public EditText moneyEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        realm = Realm.getDefaultInstance();

        thingEditText = (EditText) findViewById(R.id.thingEditText);
        moneyEditText = (EditText) findViewById(R.id.moneyEditText);
    }

    public void save(final String title, final String updateDate, final String content){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Memo memo = realm.createObject(Memo.class);
                memo.title = title;
                memo.updateDate = updateDate;
                memo.content = content;
            }
        });
    }

    public void create(View view) {
        String title = thingEditText.getText().toString();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPANESE);
        String updateDate = sdf.format(date);

        String content = moneyEditText.getText().toString();

        save(title, updateDate, content);

        finish();
    }

    private void check(String title, String updateDate, String content) {
        Memo memo = new Memo();
        memo.title = title;
        memo.updateDate = updateDate;
        memo.content = content;

        Log.d("Memo", memo.title);
        Log.d("Memo", memo.updateDate);
        Log.d("Memo", memo.content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();

    }
}