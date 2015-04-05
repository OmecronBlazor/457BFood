package algorithm;

import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.example.app.MainActivity;


import java.util.ArrayList;
import java.util.Map;

//This class is used to modify songs marked as perfect for a mood in "tabulist" style when a mood gets modified.
//Songs will follow that mood around so they stay within selection range.

public class AsyncTabuMod extends AsyncTask<String, String, Boolean> {

    double sourness_mod = 0;
    double saltiness_mod = 0;
    double sweetness_mod = 0;
    double bitterness_mod = 0;
    double fattiness_mod = 0;
    EventElement event = null;
    // constructor
    public AsyncTabuMod(EventElement event, double sourness_mod, double saltiness_mod, double sweetness_mod, double bitterness_mod, double fattiness_mod) {
        this.event = event;
        this.sourness_mod = sourness_mod;
        this.saltiness_mod = saltiness_mod;
        this.sweetness_mod = sweetness_mod;
        this.bitterness_mod = bitterness_mod;
        this.fattiness_mod = fattiness_mod;
    }
    @Override
    protected Boolean doInBackground(String... params) {
        MainActivity.dbhandler.updatePerfectAssessments(event.id(), this.sourness_mod, this.saltiness_mod, this.sweetness_mod, this.bitterness_mod, this.fattiness_mod);
        return true;
    }
}
