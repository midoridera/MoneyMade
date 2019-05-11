package midoridera.io.moneymade;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import java.util.Calendar;


public class TimePick extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener{

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(),
                    (MainActivity)getActivity(), hour, minute, true);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    }
