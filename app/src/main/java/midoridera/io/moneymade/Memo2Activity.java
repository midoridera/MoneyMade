package midoridera.io.moneymade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Memo2Activity extends AppCompatActivity {

    LinearLayout.LayoutParams layoutParams;

    TextView jikyuGoalText;
    TextView dayText;

    ImageView[] image;

    int jikyu;
    int hour;
    int minute;

    int goukei;

    int kaisu;

    public Realm realm;
    public ListView wantList;

    SharedPreferences pref;

    Wacth clock;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo2);

        realm = Realm.getDefaultInstance();
        pref = getSharedPreferences("pref_memo", MODE_PRIVATE);

        container = (LinearLayout) findViewById(R.id.container);
        jikyuGoalText = (TextView) findViewById(R.id.jikyuGoalText);
        dayText = (TextView) findViewById(R.id.dayText);
        wantList = (ListView) findViewById(R.id.wantList);

        clock = new Wacth(R.drawable.clock2);


        jikyu = getIntent().getIntExtra("jikyu", 0);
        hour = getIntent().getIntExtra("hour",0);
        minute = getIntent().getIntExtra("minute", 0);
        goukei = getIntent().getIntExtra("goukei",0);
        kaisu = getIntent().getIntExtra("kaisu",0);


        kaisu = pref.getInt("key_nankai", 0);


        jikyuGoalText.setText("合計" + String.valueOf(goukei) + "円");
        dayText.setText(String.valueOf(kaisu));


        addWacth(clock);

    }

    public void addWacth(Wacth wacth){

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < kaisu; i++) {

                image = new ImageView[kaisu];


                image[i] = new ImageView(this);
                image[i].setImageResource(clock.resId);

                int imageWidth = 0;

                //画像サイズを変える
                layoutParams = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1.0f;
                image[i].setLayoutParams(layoutParams);

                layout.addView(image[i]);

            }

        container.addView(layout);


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

    public void work(View v){

        if (kaisu > 0) {
            kaisu = kaisu - 1;
            dayText.setText(String.valueOf(kaisu));
        } else{
            kaisu = getIntent().getIntExtra("kaisu",0);
            dayText.setText(String.valueOf(kaisu));
        }

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("key_nankai", kaisu);
        editor.commit();

        clock = new Wacth(R.drawable.clock2);
        addWacth(clock);

    }

    public void back(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
