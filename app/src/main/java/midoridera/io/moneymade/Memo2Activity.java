package midoridera.io.moneymade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Memo2Activity extends AppCompatActivity {

    TextView timeGoalText;
    TextView jikyuGoalText;
    TextView dayText;

    int jikyu;
    int hour;
    int minute;

    int goukei;

    int hasu;

    int kaisu;

    public Realm realm;
    public ListView wantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo2);

        realm = Realm.getDefaultInstance();

        timeGoalText = (TextView) findViewById(R.id.timeGoalText);
        jikyuGoalText = (TextView) findViewById(R.id.jikyuGoalText);
        dayText = (TextView) findViewById(R.id.dayText);

        wantList = (ListView) findViewById(R.id.wantList);

        jikyu = getIntent().getIntExtra("jikyu", 0);
        hour = getIntent().getIntExtra("hour",0);
        minute = getIntent().getIntExtra("minute", 0);

        goukei = 0;


        RealmResults<Memo> results = realm.where(Memo.class).findAll();
        List<Memo> items = new ArrayList<Memo>(results);

        for (Memo memos : items ){

            goukei += Integer.parseInt(memos.content) ;
        }


        kaisu = goukei / (jikyu * hour + jikyu * minute / 60);

        hasu = goukei % (jikyu * hour + jikyu * minute / 60);

        if (hasu > 0) {
            kaisu = kaisu + 1;
        }

        timeGoalText.setText(String.valueOf(hour)+":"+ String.valueOf(minute));
        jikyuGoalText.setText(String.valueOf(goukei) + "円");
        dayText.setText("あと" + String.valueOf(kaisu) + "日！");

    }

    public void setMemoList() {
        RealmResults<Memo> results = realm.where(Memo.class).findAll();
        List<Memo> items = realm.copyFromRealm(results);

        MemoAdapter adapter = new MemoAdapter(this, R.layout.layout_item_memo, items);

        wantList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setMemoList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();

    }


    public void back(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
