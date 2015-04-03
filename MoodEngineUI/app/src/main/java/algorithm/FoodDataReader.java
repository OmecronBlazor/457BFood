package algorithm;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adnan on 4/02/15.
 */
public class FoodDataReader {

    private final double FOOD_BOUND = 6.0;
    private final double USER_BOUND = 10.0;

    public FoodDataReader(){}

    public final List<Food> readFoodData(Context context, String filename)
    {
        List<Food> foodList = new ArrayList<Food>();
        AssetManager assetManager = context.getAssets();
        try {

            InputStream csvStream = assetManager.open(filename);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            // throw away the header
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                foodList.add(new Food(line[0], scaleValue(line[1]),
                            scaleValue(line[2]), scaleValue(line[3]),
                            scaleValue(line[4]), scaleValue(line[5]), 0));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    private double scaleValue(String value)
    {
        double num = Double.parseDouble(value);
        double scaledNum = ( num / FOOD_BOUND ) * USER_BOUND;
        return (scaledNum > 10 ? 10 : scaledNum);
    }

}
