package midoridera.io.moneymade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ShareActionProvider;

public class Entry extends AppCompatActivity {

    public EditText jikyuEditText;
    int jikyu;

    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        pref = getSharedPreferences("pref_memo", MODE_PRIVATE);

        jikyuEditText = (EditText) findViewById(R.id.jikyuEditText);
        jikyu = Integer.parseInt(pref.getString("key_jikyu", "0"));
        jikyuEditText.setText(pref.getString("key_jikyu", ""));

    }


    public void entry(View v){

        String jikyuText = jikyuEditText.getText().toString();

        jikyu = Integer.parseInt(jikyuText);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key_jikyu", jikyuText);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("jikyu", jikyu);

        startActivity(intent);
    }
}
