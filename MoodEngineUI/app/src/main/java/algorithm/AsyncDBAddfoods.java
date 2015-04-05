package algorithm;

import android.content.Context;
import android.os.AsyncTask;
import com.example.app.MainActivity;
import java.util.List;

//Adds foods to the database initially when first starting the app.

public class AsyncDBAddfoods extends AsyncTask<String, String, Boolean> {

    Context context;

    // constructor
    public AsyncDBAddfoods(Context context) {
        this.context = context;
    }
    @Override
    protected  Boolean doInBackground(String... params) {
        FoodDataReader reader = new FoodDataReader();
        List<Food> foodList = reader.readFoodData(context, "food_data.csv");
        //add the food to the DB
        for(Food f:foodList){
            MainActivity.dbhandler.addFood(f);
        }

        return true;
    }
}
