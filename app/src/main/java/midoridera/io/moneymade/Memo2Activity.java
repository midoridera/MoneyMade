package midoridera.io.moneymade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
    int nanko;
    int shou5;
    int shou10;
    int amari5;
    int amari10;


    public Realm realm;
    public ListView wantList;

    SharedPreferences pref;

    Wacth clock;
    Wacth clock5;
    Wacth clock10;
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

        clock = new Wacth(R.drawable.clock1day);
        clock5 = new Wacth(R.drawable.clock5days);
        clock10 = new Wacth(R.drawable.clock10days);

        jikyu = getIntent().getIntExtra("jikyu", 0);
        hour = getIntent().getIntExtra("hour",0);
        minute = getIntent().getIntExtra("minute", 0);
        goukei = getIntent().getIntExtra("goukei",0);
        kaisu = getIntent().getIntExtra("kaisu",0);


        kaisu = pref.getInt("key_nankai", 0);


        jikyuGoalText.setText("合計" + String.valueOf(goukei) + "円");
        dayText.setText(String.valueOf(kaisu) + "日");


        addWacth(clock5);
        addWacth(clock5);
        addWacth(clock10);

    }

    public void addWacth(Wacth wacth){

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        amari5 = 0;
        amari10 = 0;
        shou5 = 0;
        shou10 =0;

        if (kaisu >= 10) {
            amari10 = kaisu % 10;
            shou10 = (kaisu - amari10) / 10;
            amari5 = amari10 % 5;
            shou5 = (amari10 - amari5) / 5;
        }else if (kaisu >= 5) {
            amari5 = kaisu % 5;
            shou5 = (kaisu - amari5) / 5;
        }

        nanko = kaisu - 5 * shou5 - 10 * shou10;

        for (int i = 0; i < shou5; i++) {

            image = new ImageView[shou5];

            image[i] = new ImageView(this);
            image[i].setImageResource(clock5.resId);

            int imageWidth = 0;

            //画像サイズを変える
            layoutParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1.0f;
            image[i].setLayoutParams(layoutParams);

            layout.addView(image[i]);

        }

        for (int i = 0; i < shou10; i++) {

            image = new ImageView[shou10];

            image[i] = new ImageView(this);
            image[i].setImageResource(clock10.resId);

            int imageWidth = 0;

            //画像サイズを変える
            layoutParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1.0f;
            image[i].setLayoutParams(layoutParams);

            layout.addView(image[i]);

        }


        for (int i = 0; i < nanko; i++) {

                image = new ImageView[nanko];

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
            dayText.setText(String.valueOf(kaisu)+"日");
        } else{
            kaisu = getIntent().getIntExtra("kaisu",0);
            dayText.setText(String.valueOf(kaisu));
        }

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("key_nankai", kaisu);
        editor.commit();

        addWacth(clock);
        addWacth(clock5);
        addWacth(clock10);

    }

    public void back(View v) {
        kaisu = getIntent().getIntExtra("kaisu",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("key_nankai", kaisu);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
