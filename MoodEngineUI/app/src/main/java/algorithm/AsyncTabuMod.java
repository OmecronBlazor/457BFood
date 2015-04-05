package algorithm;

import android.os.AsyncTask;
import com.example.app.MainActivity;

//This class is used to modify foods marked as perfect for a events in "tabulist" style when an event gets modified.
//Foods will follow that event around so they stay within selection range.

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
