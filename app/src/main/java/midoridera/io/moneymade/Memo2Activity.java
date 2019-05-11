package midoridera.io.moneymade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Memo2Activity extends AppCompatActivity {

    TextView timeGoalText;

    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo2);

        timeGoalText = (TextView) findViewById(R.id.timeGoalText);

        hour = getIntent().getIntExtra("hour",0);
        minute = getIntent().getIntExtra("minute", 0);

        timeGoalText.setText(String.valueOf(hour)+":"+ String.valueOf(minute));


    }

    public void back(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
