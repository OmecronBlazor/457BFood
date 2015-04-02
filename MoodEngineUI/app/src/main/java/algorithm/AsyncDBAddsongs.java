package algorithm;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;

//Adds songs to the database initially when first starting the app.

public class AsyncDBAddsongs extends AsyncTask<String, String, Boolean> {

    static Cursor mCursor = null;
    static ContentResolver contentResolver = null;
    boolean isMobileConnected = false;
    boolean isLocalConnected = false;

    // constructor
    public AsyncDBAddsongs(Cursor cursor, ContentResolver contentResolver, boolean isLocalConnected, boolean isMobileConnected) {
        this.mCursor = cursor;
        this.contentResolver = contentResolver;
        this.isLocalConnected = isLocalConnected;
        this.isMobileConnected = isMobileConnected;
    }
    @Override
    protected  Boolean doInBackground(String... params) {

        //add the food to the DB
        return true;
    }
}
