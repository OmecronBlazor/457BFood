package algorithm;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;

//This class is used to modify songs marked as perfect for a mood in "tabulist" style when a mood gets modified.
//Songs will follow that mood around so they stay within selection range.

public class AsyncSyncProcess extends AsyncTask<String, String, Boolean> {

    private ContentResolver contentResolver;
    private Context context;
    // constructor
    public AsyncSyncProcess(ContentResolver contentResolver, Context context) {
        this.contentResolver = contentResolver;
        this.context = context;
    }
    @Override
    protected Boolean doInBackground(String... params) {
        return true;
    }


    //MainActivity.dbhandler.addSong()
}
