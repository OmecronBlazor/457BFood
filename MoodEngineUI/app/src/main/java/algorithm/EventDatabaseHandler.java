package algorithm;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.MediaStore;

import com.example.app.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**

 * Created by Adnan
 */
public class EventDatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ASSESSMENTEngineManager";

    // table names
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_ASSESSMENTS = "assessments";
    private static final String TABLE_SETTINGS = "settings";
    private final double TEMP = 0.98;

    // Food Table Column Names
    private static final String KEY_FOOD_ID = "id";
    private static final String KEY_FOOD_NAME = "name";
    private static final String KEY_FOOD_SOURNESS = "sourness";
    private static final String KEY_FOOD_SPICINESS = "spiciness";
    private static final String KEY_FOOD_SWEETNESS = "sweetness";
    private static final String KEY_FOOD_BITTERNESS = "bitterness";
    private static final String KEY_FOOD_FATTINESS = "fattiness";
    private static final String KEY_FOOD_COUNTER = "counter";

    //Event Table Column Names
    private static final String KEY_EVENT_ID = "id";
    private static final String KEY_EVENT_NAME = "name";
    private static final String KEY_EVENT_SOURNESS = "sourness";
    private static final String KEY_EVENT_SPICINESS = "spiciness";
    private static final String KEY_EVENT_SWEETNESS = "sweetness";
    private static final String KEY_EVENT_BITTERNESS = "bitterness";
    private static final String KEY_EVENT_FATTINESS = "fattiness";
    private static final String KEY_EVENT_COUNTER_SOUR = "sourness_counter";
    private static final String KEY_EVENT_COUNTER_SPICY = "spiciness_counter";
    private static final String KEY_EVENT_COUNTER_SWEET = "sweetness_counter";
    private static final String KEY_EVENT_COUNTER_BITTER = "bitterness_counter";
    private static final String KEY_EVENT_COUNTER_FATTY = "fattiness_counter";
    private static final String KEY_EVENT_COLOUR = "colour";
    private static final String KEY_EVENT_POSITION =  "position";


    //Assessment Table Column Names
    private static final String KEY_ASSESSMENT_ID = "id";
    private static final String KEY_ASSESSMENT_EVENT_ID = "event_id";
    private static final String KEY_ASSESSMENT_FOOD_ID = "food_id";
    private static final String KEY_ASSESSMENT_SOURNESS = "sourness";
    private static final String KEY_ASSESSMENT_SPICINESS = "spiciness";
    private static final String KEY_ASSESSMENT_SWEETNESS = "sweetness";
    private static final String KEY_ASSESSMENT_BITTERNESS = "bitterness";
    private static final String KEY_ASSESSMENT_FATTINESS = "fattiness";

    //Settings Table Column Names
    private static final String KEY_SETTINGS_ID = "id";
    private static final String KEY_SETTINGS_NAME = "settings_name";
    private static final String KEY_SETTINGS_STATE = "settings_state";

    //Table Create Statements
    //Song table create statement
    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD + "("
            + KEY_FOOD_ID + " INTEGER PRIMARY KEY," + KEY_FOOD_NAME + " NVARCHAR,"
            + KEY_FOOD_SOURNESS + " REAL," + KEY_FOOD_SPICINESS + " REAL,"
            + KEY_FOOD_SWEETNESS + " REAL," + KEY_FOOD_BITTERNESS + " REAL,"
            + KEY_FOOD_FATTINESS + " REAL," + KEY_FOOD_COUNTER + " INTEGER)";

    //Mood table create statement
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + "("
            + KEY_EVENT_ID + " INTEGER PRIMARY KEY," + KEY_EVENT_NAME + " TEXT,"
            + KEY_EVENT_SOURNESS + " REAL," + KEY_EVENT_SPICINESS + " REAL,"
            + KEY_EVENT_SWEETNESS + " REAL," + KEY_EVENT_BITTERNESS + " REAL,"
            + KEY_EVENT_FATTINESS + " REAL," + KEY_EVENT_COUNTER_SOUR + " INTEGER,"
            + KEY_EVENT_COUNTER_SPICY + " INTEGER," + KEY_EVENT_COUNTER_SWEET + " INTEGER,"
            + KEY_EVENT_COUNTER_BITTER + " INTEGER," + KEY_EVENT_COUNTER_FATTY + " INTEGER,"
            + KEY_EVENT_COLOUR + " STRING," + KEY_EVENT_POSITION + " INTEGER" + ")";

    //Assessment Table create statement
    private static final String CREATE_TABLE_ASSESSMENTS = "CREATE TABLE " + TABLE_ASSESSMENTS + "("
            + KEY_ASSESSMENT_ID + " INTEGER PRIMARY KEY," + KEY_ASSESSMENT_EVENT_ID + " INTEGER,"
            + KEY_ASSESSMENT_FOOD_ID + " INTEGER," + KEY_ASSESSMENT_SPICINESS + " INTEGER,"
            + KEY_ASSESSMENT_SWEETNESS + " INTEGER," + KEY_ASSESSMENT_BITTERNESS + " INTEGER,"
            + KEY_ASSESSMENT_FATTINESS + " INTEGER)";


    //Settings Table create statement
    private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE " + TABLE_SETTINGS + " ("
            + KEY_SETTINGS_ID + " INTEGER PRIMARY KEY," + KEY_SETTINGS_NAME + "STRING,"
            + KEY_SETTINGS_STATE + " BOOLEAN" + ")";

    public EventDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_ASSESSMENTS);
        db.execSQL(CREATE_TABLE_SETTINGS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        // Create tables again
        onCreate(db);
    }


    // Adding new food
    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_FOOD_NAME, food.name() ); // Food Name
        values.put( KEY_FOOD_SOURNESS, food.sourness() ); // Food's Sourness
        values.put( KEY_FOOD_SPICINESS, food.spiciness() ); // Food's Spiciness
        values.put( KEY_FOOD_SWEETNESS, food.sweetness() ); // Food's Sweetness
        values.put( KEY_FOOD_BITTERNESS, food.bitterness() ); // Food's Bitterness
        values.put( KEY_FOOD_FATTINESS, food.fattiness() ); // Food's Fattiness
        values.put( KEY_FOOD_COUNTER, food.counter() ); // Food's Counter

        // Inserting Row
        db.insert(TABLE_FOOD, null, values);
        db.close(); // Closing database connection
    }

    // Adding new foods
    public void addInitialFood(Cursor foodCursor) {
        SQLiteDatabase db = this.getWritableDatabase();
        foodCursor.moveToFirst();

        ContentValues values = new ContentValues();
        while(!foodCursor.isAfterLast()) {

            //TODO: CSV SHIT FIGURE OUT FOR GETTING DATA TO PUT INTO DATABASE
            /*values.put(KEY_FOOD_NAME, foodCursor.getString(foodCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))); // Song Name
            values.put( KEY_FOOD_SOURNESS, foodCursor.getString() ); // Food's Sourness
            values.put( KEY_FOOD_SPICINESS, foodCursor.getString() ); // Food's Spiciness
            values.put( KEY_FOOD_SWEETNESS, foodCursor.getString() ); // Food's Sweetness
            values.put( KEY_FOOD_BITTERNESS, foodCursor.getString() ); // Food's Bitterness
            values.put( KEY_FOOD_FATTINESS, foodCursor.getString() ); // Food's Fattiness*/

            // Inserting Row
            db.insert(TABLE_FOOD, null, values);
            foodCursor.moveToNext();
        }
        System.out.println("Done DB add");
        db.close(); // Closing database connection
    }

    // Getting single food
    public Food getFood(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOOD, new String[] { KEY_FOOD_ID, KEY_FOOD_NAME, KEY_FOOD_SOURNESS,
                                 KEY_FOOD_SPICINESS, KEY_FOOD_SWEETNESS, KEY_FOOD_BITTERNESS, KEY_FOOD_FATTINESS,
                                 KEY_FOOD_COUNTER }, KEY_FOOD_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Food food = new Food(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble( cursor.getString(2) ),
                Double.parseDouble( cursor.getString(3) ), Double.parseDouble( cursor.getString(4) ), Double.parseDouble( cursor.getString(5)),
                Double.parseDouble( cursor.getString(6) ), Integer.parseInt( cursor.getString(7)));

        cursor.close();

        // return food
        return food;
    }

    // Getting All Food
    public List<Food> getAllFood() {
        List<Food> foodList = new ArrayList<Food>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble( cursor.getString(2) ),
                        Double.parseDouble( cursor.getString(3) ), Double.parseDouble( cursor.getString(4) ), Double.parseDouble( cursor.getString(5)),
                        Double.parseDouble( cursor.getString(6) ), Integer.parseInt( cursor.getString(7)));
                // Adding food to list
                foodList.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return song list
        return foodList;
    }

    public ArrayList<Food> getRecommendation(String event){
        //TODO: CHANGE SHIT FROM MAIN ACTIIVTY
        Preference eventpref = MainActivity.table.getEvent(event).preference();
        Cursor cursor = null;
        ArrayList<Food> foodList = null;
        double rval = PrefRangeValue(MainActivity.table.getEvent(event).range_counter);
        int food_count = 0;

        while(food_count == 0) {
            String selectQuery = "SELECT  * FROM " + TABLE_FOOD
                    + " WHERE " + KEY_FOOD_SOURNESS + " BETWEEN " + String.valueOf(eventpref.sourness() - rval) + " AND "
                    + String.valueOf(eventpref.sourness() + rval) + " AND "
                    + KEY_FOOD_SPICINESS + " BETWEEN " + String.valueOf(eventpref.spiciness() - rval) + " AND "
                    + String.valueOf(eventpref.spiciness() + rval) + " AND "
                    + KEY_FOOD_SWEETNESS + " BETWEEN " + String.valueOf(eventpref.sweetness() - rval) + " AND "
                    + String.valueOf(eventpref.sweetness() + rval) + " AND "
                    + KEY_FOOD_BITTERNESS + " BETWEEN " + String.valueOf(eventpref.bitterness() - rval) + " AND "
                    + String.valueOf(eventpref.bitterness() + rval) + " AND "
                    + KEY_FOOD_FATTINESS + " BETWEEN " + String.valueOf(eventpref.fattiness() - rval) + " AND "
                    + String.valueOf(eventpref.fattiness() + rval);
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);

            food_count = cursor.getCount();

            if(food_count != 0) {
                foodList = new ArrayList<Food>();
                if (cursor.moveToFirst()) {
                    do {
                        Food food = new Food(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble( cursor.getString(2) ),
                                Double.parseDouble( cursor.getString(3) ), Double.parseDouble( cursor.getString(4) ), Double.parseDouble( cursor.getString(5)),
                                Double.parseDouble( cursor.getString(6) ), Integer.parseInt( cursor.getString(7)));
                        // Adding song to list
                        foodList.add(food);
                    } while (cursor.moveToNext());
                }
            }
            else
                rval += 1;
        }

        cursor.close();
        return foodList;
    }

    private double PrefRangeValue( int counter )
    {
        return ( 2 * Math.pow(TEMP, counter ) + 1 );
    }

    // Getting food Count
    public int getFoodCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FOOD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single food
    public int updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_FOOD_NAME, food.name() ); // Food Name
        values.put( KEY_FOOD_SOURNESS, food.sourness() ); // Food's Sourness
        values.put( KEY_FOOD_SPICINESS, food.spiciness() ); // Food's Spiciness
        values.put( KEY_FOOD_SWEETNESS, food.sweetness() ); // Food's Sweetness
        values.put( KEY_FOOD_BITTERNESS, food.bitterness() ); // Food's Bitterness
        values.put( KEY_FOOD_FATTINESS, food.fattiness() ); // Food's Fattiness
        values.put( KEY_FOOD_COUNTER, food.counter() ); // Food's Counter

        // updating row
        return db.update(TABLE_FOOD, values, KEY_FOOD_ID + " = ?",
                new String[] { String.valueOf(food.id()) });
    }

    public int updateFoodPrefsFromName(Food food, int isAnalyzed) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_FOOD_SOURNESS, food.sourness() ); // Food's Sourness
        values.put( KEY_FOOD_SPICINESS, food.spiciness() ); // Food's Spiciness
        values.put( KEY_FOOD_SWEETNESS, food.sweetness() ); // Food's Sweetness
        values.put( KEY_FOOD_BITTERNESS, food.bitterness() ); // Food's Bitterness
        values.put( KEY_FOOD_FATTINESS, food.fattiness() ); // Food's Fattiness

        // updating row
        return db.update(TABLE_FOOD, values, KEY_FOOD_NAME + " = ?",
                new String[] { food.name() });
    }

    // Deleting single food
    public void deleteFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD, KEY_FOOD_ID + " = ?",
                new String[] { String.valueOf(food.id()) });
        db.close();
    }




    // Adding new event
    public int addEvent(EventElement event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_EVENT_NAME, event.event_name() ); // Event's Name
        values.put( KEY_EVENT_SOURNESS, event.sourness() ); // Event's Sourness
        values.put( KEY_EVENT_SPICINESS, event.spiciness() ); // Event's Spiciness
        values.put( KEY_EVENT_SWEETNESS, event.sweetness() ); // Event's Sweetness
        values.put( KEY_EVENT_BITTERNESS, event.bitterness() ); // Event's Bitterness
        values.put( KEY_EVENT_FATTINESS, event.fattiness() ); // Event's Fattiness
        values.put( KEY_EVENT_COUNTER_SOUR, event.modification_counter_sour ); // Event's Sourness Counter
        values.put( KEY_EVENT_COUNTER_SPICY, event.modification_counter_spicy ); // Event's Spiciness Counter
        values.put( KEY_EVENT_COUNTER_SWEET, event.modification_counter_sweet ); // Event's Sweetness Counter
        values.put( KEY_EVENT_COUNTER_BITTER, event.modification_counter_bitter ); // Event's Bitterness Counter
        values.put( KEY_EVENT_COUNTER_FATTY, event.modification_counter_fatty ); // Event's Fattiness Counter
        values.put( KEY_EVENT_COLOUR, event.event_colour() ); // Event's Colour
        values.put( KEY_EVENT_POSITION, event.event_position() ); //Event's Position

        // Inserting Row
        db.insert(TABLE_EVENTS, null, values);



        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_EVENT_ID }, KEY_EVENT_NAME + "=?",
                new String[] { event.event_name() }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        int eventID = Integer.parseInt( cursor.getString(0) );

        db.close(); // Closing database connection
        // return mood ID
        return eventID;
    }

    // Getting single event
    public EventElement getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_EVENT_ID, KEY_EVENT_NAME, KEY_EVENT_SOURNESS,
                        KEY_EVENT_SPICINESS, KEY_EVENT_SWEETNESS, KEY_EVENT_BITTERNESS, KEY_EVENT_FATTINESS,
                        KEY_EVENT_COUNTER_SOUR, KEY_EVENT_COUNTER_SPICY, KEY_EVENT_COUNTER_SWEET, KEY_EVENT_COUNTER_BITTER,
                        KEY_EVENT_COUNTER_FATTY, KEY_EVENT_COLOUR, KEY_EVENT_POSITION }, KEY_EVENT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        EventElement event = new EventElement(Integer.parseInt( cursor.getString(0) ), cursor.getString(1),
                new Preference( Double.parseDouble( cursor.getString(2) ), Double.parseDouble( cursor.getString(3) ),
                        Double.parseDouble( cursor.getString(4) ), Double.parseDouble( cursor.getString(5) ),
                        Double.parseDouble( cursor.getString(6) ) ), Integer.parseInt(cursor.getString(7)),
                Integer.parseInt( cursor.getString(8)), Integer.parseInt( cursor.getString(9)),
                Integer.parseInt( cursor.getString(10)), Integer.parseInt( cursor.getString(11)),
                cursor.getString(13), Integer.parseInt(cursor.getString(14)) );

        cursor.close();
        // return event
        return event;
    }

    // Getting All Events
    public List<EventElement> getAllEvents() {
        List<EventElement> eventList = new ArrayList<EventElement>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EventElement event = new EventElement(Integer.parseInt( cursor.getString(0) ), cursor.getString(1),
                        new Preference( Double.parseDouble( cursor.getString(2) ), Double.parseDouble( cursor.getString(3) ),
                                Double.parseDouble( cursor.getString(4) ), Double.parseDouble( cursor.getString(5) ),
                                Double.parseDouble( cursor.getString(6) ) ), Integer.parseInt(cursor.getString(7)),
                        Integer.parseInt( cursor.getString(8)), Integer.parseInt( cursor.getString(9)),
                        Integer.parseInt( cursor.getString(10)), Integer.parseInt( cursor.getString(11)),
                        cursor.getString(13), Integer.parseInt(cursor.getString(14)) );

                // Adding event to list
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return event list
        return eventList;
    }

    // Getting event Count
    public int getEventCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single event
    public int updateEvent(EventElement event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_EVENT_NAME, event.event_name() ); // Event's Name
        values.put( KEY_EVENT_SOURNESS, event.sourness() ); // Event's Sourness
        values.put( KEY_EVENT_SPICINESS, event.spiciness() ); // Event's Spiciness
        values.put( KEY_EVENT_SWEETNESS, event.sweetness() ); // Event's Sweetness
        values.put( KEY_EVENT_BITTERNESS, event.bitterness() ); // Event's Bitterness
        values.put( KEY_EVENT_FATTINESS, event.fattiness() ); // Event's Fattiness
        values.put( KEY_EVENT_COUNTER_SOUR, event.modification_counter_sour ); // Event's Sourness Counter
        values.put( KEY_EVENT_COUNTER_SPICY, event.modification_counter_spicy ); // Event's Spiciness Counter
        values.put( KEY_EVENT_COUNTER_SWEET, event.modification_counter_sweet ); // Event's Sweetness Counter
        values.put( KEY_EVENT_COUNTER_BITTER, event.modification_counter_bitter ); // Event's Bitterness Counter
        values.put( KEY_EVENT_COUNTER_FATTY, event.modification_counter_fatty ); // Event's Fattiness Counter
        values.put( KEY_EVENT_COLOUR, event.event_colour() ); // Event's Colour
        values.put( KEY_EVENT_POSITION, event.event_position() ); //Event's Position


        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EVENT_ID + " = ?",
                new String[]{String.valueOf(event.id())});
    }

    // Deleting single event
    public void deleteEvent(EventElement event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_EVENT_ID + " = ?",
                new String[] { String.valueOf(event.id()) });
        db.close();
    }

    //Adding new assessment
    public void addAssessment( int event_id, int food_id, ModificationType sourness_preference, ModificationType spiciness_preference,
                               ModificationType sweetness_preference, ModificationType bitterness_preference, ModificationType fattiness_preference )
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( KEY_ASSESSMENT_EVENT_ID, event_id );
        values.put( KEY_ASSESSMENT_FOOD_ID, food_id );
        values.put( KEY_ASSESSMENT_SOURNESS, sourness_preference.mod_id() );
        values.put( KEY_ASSESSMENT_SPICINESS, spiciness_preference.mod_id() );
        values.put( KEY_ASSESSMENT_SWEETNESS, sweetness_preference.mod_id() );
        values.put( KEY_ASSESSMENT_BITTERNESS, bitterness_preference.mod_id() );
        values.put( KEY_ASSESSMENT_FATTINESS, fattiness_preference.mod_id() );

        // Inserting Row
        db.insert(TABLE_ASSESSMENTS, null, values);
        db.close(); // Closing database connection
    }

    //Get assessment preferences
    /*
        ArrayList Indices
        -----------------
        0 - Sourness Preference
        1 - Spiciness Preference
        2 - Sweetess Preference
        3 - Bitterness Preference
        4 - Fattiness Preference
     */
    public ArrayList<ModificationType> getAssessment( int event_id, int food_id )
    {
        ArrayList<ModificationType> preference_list = new ArrayList<ModificationType>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ASSESSMENTS, new String[] { KEY_ASSESSMENT_SOURNESS, KEY_ASSESSMENT_SPICINESS,
                        KEY_ASSESSMENT_SWEETNESS, KEY_ASSESSMENT_BITTERNESS, KEY_ASSESSMENT_FATTINESS },
                KEY_ASSESSMENT_EVENT_ID + " = ? and " + KEY_ASSESSMENT_FOOD_ID + " = ?",
                new String[] { String.valueOf(event_id), String.valueOf(food_id) }, null, null, null);

        if(cursor.getCount() == 0)
            return null;

        if (cursor != null)
            cursor.moveToFirst();

        for( int i = 0; i < 5; i++ ) {
            int type_id = Integer.parseInt(cursor.getString(i));
            ModificationType type = null;

            if ( type_id == ModificationType.PERFECT.mod_id() )
                type = ModificationType.PERFECT;
            else if( type_id == ModificationType.TOO_LOW.mod_id() )
                type = ModificationType.TOO_LOW;
            else if( type_id == ModificationType.TOO_MUCH.mod_id() )
                type = ModificationType.TOO_MUCH;

            preference_list.add(type);
        }

        cursor.close();

        // return preferences
        return preference_list;
    }

    //Check if assessment exists
    public boolean checkAssessment( int event_id, int food_id )
    {
        ArrayList<ModificationType> preference_list = new ArrayList<ModificationType>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ASSESSMENTS, new String[] { KEY_ASSESSMENT_ID }, KEY_ASSESSMENT_EVENT_ID
                        + " = ? and " + KEY_ASSESSMENT_FOOD_ID + " = ?",
                new String[] { String.valueOf(event_id), String.valueOf(food_id) }, null, null, null);

        boolean exists = (cursor.getCount() != 0);
        cursor.close();
        return exists;
    }

    //Check for assessments for this event of the given format
    public void updatePerfectAssessments( int event_id, double sournessmod, double spicinessmod,
                                          double sweetnessmod, double bitternessmod, double fattinessmod ) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_FOOD +
                " SET " + KEY_FOOD_SOURNESS + " = "
                + "CASE WHEN " + KEY_FOOD_SOURNESS + "+('" + sournessmod + "')>10 THEN 10"
                + " WHEN " + KEY_FOOD_SOURNESS + "+('" + sournessmod + "')<0 THEN 0"
                + " ELSE " + KEY_FOOD_SOURNESS + "+('" + sournessmod + "') END, " +

                KEY_FOOD_SPICINESS + " = "
                + "CASE WHEN " + KEY_FOOD_SPICINESS + "+('" + spicinessmod + "')>10 THEN 10"
                + " WHEN " + KEY_FOOD_SPICINESS + "+('" + spicinessmod + "')<0 THEN 0"
                + " ELSE " + KEY_FOOD_SPICINESS + "+('" + spicinessmod + "') END, " +

                KEY_FOOD_SWEETNESS + " = "
                + "CASE WHEN " + KEY_FOOD_SWEETNESS + "+('" + sweetnessmod + "')>10 THEN 10"
                + " WHEN " + KEY_FOOD_SWEETNESS + "+('" + sweetnessmod + "')<0 THEN 0"
                + " ELSE " + KEY_FOOD_SWEETNESS + "+('" + sweetnessmod + "') END, " +

                KEY_FOOD_BITTERNESS + " = "
                + "CASE WHEN " + KEY_FOOD_BITTERNESS + "+('" + bitternessmod + "')>10 THEN 10"
                + " WHEN " + KEY_FOOD_BITTERNESS + "+('" + bitternessmod + "')<0 THEN 0"
                + " ELSE " + KEY_FOOD_BITTERNESS + "+('" + bitternessmod + "') END, " +

                KEY_FOOD_FATTINESS + " = "
                + "CASE WHEN " + KEY_FOOD_FATTINESS + "+('" + fattinessmod + "')>10 THEN 10"
                + " WHEN " + KEY_FOOD_FATTINESS + "+('" + fattinessmod + "')<0 THEN 0"
                + " ELSE " + KEY_FOOD_FATTINESS + "+('" + fattinessmod + "') END, " +

                " WHERE " + KEY_FOOD_ID + " IN (SELECT " + KEY_ASSESSMENT_FOOD_ID + " FROM " + TABLE_ASSESSMENTS +
                " WHERE " + KEY_EVENT_ID + " = " + event_id +
                " AND " + KEY_ASSESSMENT_SOURNESS + " = " + ModificationType.PERFECT.mod_id() +
                " AND " + KEY_ASSESSMENT_SPICINESS + " = " + ModificationType.PERFECT.mod_id() +
                " AND " + KEY_ASSESSMENT_SWEETNESS + " = " + ModificationType.PERFECT.mod_id() +
                " AND " + KEY_ASSESSMENT_BITTERNESS + " = " + ModificationType.PERFECT.mod_id() +
                " AND " + KEY_ASSESSMENT_FATTINESS + " = " + ModificationType.PERFECT.mod_id() + ")";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        cursor.close();

    }

    //Updating single assessment
    public void updateAssessment( int event_id, int food_id, ModificationType sourness_preference, ModificationType spiciness_preference,
                                  ModificationType sweetness_preference, ModificationType bitterness_preference, ModificationType fattiness_preference )
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQuery = "INSERT OR REPLACE INTO " + TABLE_ASSESSMENTS + " ( " + KEY_ASSESSMENT_ID + ", "
                + KEY_ASSESSMENT_EVENT_ID + ", "
                + KEY_ASSESSMENT_FOOD_ID + ", "
                + KEY_ASSESSMENT_SOURNESS + ", "
                + KEY_ASSESSMENT_SPICINESS + ", "
                + KEY_ASSESSMENT_SWEETNESS + ", "
                + KEY_ASSESSMENT_BITTERNESS + ", "
                + KEY_ASSESSMENT_FATTINESS + ") values ("
                + "(select " + KEY_ASSESSMENT_ID + " from " + TABLE_ASSESSMENTS + " where " + KEY_ASSESSMENT_EVENT_ID + " = " + event_id
                + " AND " + KEY_ASSESSMENT_FOOD_ID + " = " + food_id + "),"
                + event_id + ", " + food_id + ", " + sourness_preference.mod_id() + ", "
                                                   + spiciness_preference.mod_id() + ", "
                                                   + sweetness_preference.mod_id() + ", "
                                                   + bitterness_preference.mod_id() + ", "
                                                   + fattiness_preference.mod_id() + ")";

        try {
            db.execSQL(sqlQuery);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Deleting single assessment
    public void deleteAssessment( int event_id, int food_id )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSESSMENTS, KEY_ASSESSMENT_EVENT_ID + " = ? and " + KEY_ASSESSMENT_FOOD_ID + " = ?",
                new String[] { String.valueOf(event_id), String.valueOf(food_id) });
        db.close();
    }

}
