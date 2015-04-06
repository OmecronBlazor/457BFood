package com.example.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.List;

import algorithm.AsyncDBAddfoods;
import algorithm.EventDatabaseHandler;
import algorithm.EventElement;
import algorithm.EventTable;
import algorithm.Food;
import algorithm.FoodDataReader;
import algorithm.Preference;

public class MainActivity extends ActionBarActivity {
    private String[] mSideTrayOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public static EventDatabaseHandler dbhandler;
    public static Preference userpref;
    public static EventTable table;
    private boolean maintainDataBase = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        if (savedInstanceState == null) {
            if(maintainDataBase==true) {
                if (doesDatabaseExist(context, "foodEngineManager")) {
                    dbhandler = new EventDatabaseHandler(getApplicationContext());
                    List<EventElement> list = dbhandler.getAllEvents();
                    if(!list.isEmpty()) {
                        table = new EventTable(list);
                        for(EventElement get:list){
                            System.out.println(get.id());
                            System.out.println(get.event_name());
                            System.out.println(get.sourness());
                            System.out.println(get.saltiness());
                            System.out.println(get.sweetness());
                            System.out.println(get.bitterness());
                            System.out.println(get.fattiness());
                        }
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, new EventSelectFragment())
                                .commit();
                    }
                    else{
                        dbhandler = new EventDatabaseHandler(getApplicationContext());
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, new EventSelectFragment())
                                .commit();
                    }
                } else {
                    //create DB
                    dbhandler = new EventDatabaseHandler(getApplicationContext());
                    MainActivity.userpref = new Preference(5,5,5,5,5);
                    MainActivity.table = new EventTable( MainActivity.userpref );
                    for(EventElement event : MainActivity.table.getAllEvents()) {
                        int eventID = MainActivity.dbhandler.addEvent(event);
                        MainActivity.table.getEvent(event.event_name()).setID(eventID);
                    }
                    AsyncDBAddfoods addFoods = new AsyncDBAddfoods(this.getApplicationContext());
                    if (Build.VERSION.SDK_INT >= 11) {
                        //--post GB use serial executor by default --
                        addFoods.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        addFoods.execute();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new EventSelectFragment())
                            .commit();
                }
            }
            else{
                if (doesDatabaseExist(context, "foodEngineManager")) {
                    //query preferences
                    //check add/removed foods
                    context.deleteDatabase("foodEngineManager");
                } else {
                    //create DB
                    FoodDataReader reader = new FoodDataReader();
                    dbhandler = new EventDatabaseHandler(getApplicationContext());
                    MainActivity.userpref = new Preference(5,5,5,5,5);
                    MainActivity.table = new EventTable( MainActivity.userpref );
                    for(EventElement event : MainActivity.table.getAllEvents()) {
                        int eventID = MainActivity.dbhandler.addEvent(event);
                        MainActivity.table.getEvent(event.event_name()).setID(eventID);
                    }
                    AsyncDBAddfoods addFoods = new AsyncDBAddfoods(getApplicationContext());
                    if (Build.VERSION.SDK_INT >= 11) {
                        //--post GB use serial executor by default --
                        addFoods.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        addFoods.execute();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new EventSelectFragment())
                            .commit();
                }
            }
        }

        mSideTrayOptions = getResources().getStringArray(R.array.sidebar_option);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        Display dd = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        dd.getMetrics(dm);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mSideTrayOptions));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(getResources().getDrawable(R.drawable.menu_icon));
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case (android.R.id.home):
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawer(mDrawerList);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    public void hideActionBarTitle() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
            mDrawerLayout.closeDrawers();
        }
    }

    private void selectItem(int position) {
        switch (position) {
            case 0:
                switchToFragment(new EventSelectFragment());
                break;
            case 1:
                switchToFragment(new AddEventFragment());
                break;
        }
    }

    public void switchToFragment(Fragment frag){
        switchToFragment(frag, true);
    }

    public void switchToFragment(Fragment frag, boolean addToBackstack) {
        Fragment activeFrag = getSupportFragmentManager().findFragmentByTag("active");

        if (frag.getClass() == FoodRecommendationFragment.class) {
            frag.setHasOptionsMenu(true);
        } else {
            frag.setHasOptionsMenu(false);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, frag, "active");
        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
