package midoridera.io.moneymade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    public Realm realm;
  
    public EditText thingText;
    public EditText moneyText;
    public EditText titleText;
    public EditText time1Text;
    public EditText time2Text;
    public EditText time3Text;
    public EditText time4Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();

        thingText = (EditText) findViewById(R.id.thingEditText);
        moneyText = (EditText) findViewById(R.id.moneyEditText);
        titleText = (EditText) findViewById(R.id.titleEditText);
        time1Text = (EditText) findViewById(R.id.timeH1EditText);
        time2Text = (EditText) findViewById(R.id.timeM1EditText);
        time3Text = (EditText) findViewById(R.id.timeH2EditText);
        time4Text = (EditText) findViewById(R.id.timeM2EditText);

        showDate();

    }

    public void showDate() {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();

        thingText.setText(memo.title);
        moneyText.setText(memo.content);
        titleText.setText(memo.title);
        time1Text.setText(memo.content1);
        time2Text.setText(memo.content2);
        time3Text.setText(memo.content3);
        time4Text.setText(memo.content4);
    }

    public void update(View view) {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                memo.title = thingText.getText().toString();
                memo.content = moneyText.getText().toString();
                memo.content1 = time1Text.getText().toString();
                memo.content2 = time2Text.getText().toString();
                memo.content3 = time3Text.getText().toString();
                memo.content4 = time4Text.getText().toString();
            }
        });

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }
}