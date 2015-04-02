package algorithm;

import android.os.AsyncTask;
import android.os.Build;

import java.util.Arrays;

/**
 * Created by Adnan on 4/01/15.
 */
public class EventElement {

    public int modification_counter_sour = 0;
    public int modification_counter_spicy = 0;
    public int modification_counter_sweet = 0;
    public int modification_counter_bitter = 0;
    public int modification_counter_fatty = 0;

    public int range_counter = 0;

    private final double TEMP = 0.98;

    private int id;
    private int event_position;
    private Preference EventElementPreference;
    private String event_name;
    private String event_colour;

    public EventElement( EventType event_type, Preference preference, int position )
    {
        event_name = event_type.event_name();
        event_colour = event_type.event_colour();
        double sourness = preference.sourness() + event_type.sourness_factor();
        double spiciness = preference.spiciness() + event_type.spiciness_factor();
        double sweetness = preference.sweetness() + event_type.sweetness_factor();
        double bitterness = preference.bitterness() + event_type.bitterness_factor();
        double fattiness = preference.fattiness() + event_type.fattiness_factor();

        EventElementPreference = new Preference( sourness, spiciness, sweetness, bitterness, fattiness );

        event_position = position;
    }

    public EventElement( String name, Preference preference, String colour, int pos)
    {
        event_name = name;
        EventElementPreference = preference;
        event_colour = colour;
        event_position = pos;
    }

    /*TODO Create a colour object for mood instead of just string*/
    public EventElement( int id, String name, Preference preference, int mod_counter_sour, int mod_counter_spicy, int mod_counter_sweet, int mod_counter_bitter, int mod_counter_fatty, String colour, int pos )
    {
        this.id = id;
        event_name = name;
        EventElementPreference = preference;
        modification_counter_sour = mod_counter_sour;
        modification_counter_spicy = mod_counter_spicy;
        modification_counter_sweet = mod_counter_sweet;
        modification_counter_bitter = mod_counter_bitter;
        modification_counter_fatty = mod_counter_fatty;
        event_colour = colour;
        event_position = pos;
    }

    public int id() { return id; }

    public void setID( int id ){ this.id = id; }

    public String event_name(){ return event_name; }

    public double sourness(){ return EventElementPreference.sourness(); }

    public double UpdateSourness( Food food, ModificationType mod_type)
    {
        double sourness;
        double food_sourness = food.sourness();
        double sourness_factor = FoodToUserInfluenceFactor( modification_counter_sour, sourness(), food_sourness, mod_type );

        sourness = sourness() + sourness_factor;

        food.UpdateSourness( sourness(), mod_type, modification_counter_sour );

        EventElementPreference.SetSourness( sourness );

        modification_counter_sour++;

        return sourness_factor;
    }

    public double spiciness(){ return EventElementPreference.spiciness(); }

    public double UpdateSpiciness( Food food, ModificationType mod_type)
    {
        double spiciness;
        double food_spiciness = food.spiciness();
        double spiciness_factor = FoodToUserInfluenceFactor( modification_counter_spicy, spiciness(), food_spiciness, mod_type );

        spiciness = spiciness() + spiciness_factor;

        food.UpdateSpiciness( spiciness(), mod_type, modification_counter_spicy );

        EventElementPreference.SetSpiciness( spiciness );

        modification_counter_spicy++;

        return spiciness_factor;
    }


    public double sweetness(){ return EventElementPreference.sweetness(); }

    public double UpdateSweetness( Food food, ModificationType mod_type)
    {
        double sweetness;
        double food_sweetness = food.sweetness();
        double sweetness_factor = FoodToUserInfluenceFactor( modification_counter_sweet, sweetness(), food_sweetness, mod_type );

        sweetness = sweetness() + sweetness_factor;

        food.UpdateSweetness( sweetness(), mod_type, modification_counter_sweet );

        EventElementPreference.SetSweetness( sweetness );

        modification_counter_sweet++;

        return sweetness_factor;
    }

    public double bitterness(){ return EventElementPreference.bitterness(); }

    public double UpdateBitterness( Food food, ModificationType mod_type)
    {
        double bitterness;
        double food_bitterness = food.bitterness();
        double bitterness_factor = FoodToUserInfluenceFactor( modification_counter_bitter, bitterness(), food_bitterness, mod_type );

        bitterness = bitterness() + bitterness_factor;

        food.UpdateBitterness( bitterness(), mod_type, modification_counter_bitter );

        EventElementPreference.SetBitterness( bitterness );

        modification_counter_bitter++;

        return bitterness_factor;
    }

    public double fattiness(){ return EventElementPreference.fattiness(); }

    public double UpdateFattiness( Food food, ModificationType mod_type)
    {
        double fattiness;
        double food_fattiness = food.fattiness();
        double fattiness_factor = FoodToUserInfluenceFactor( modification_counter_bitter, fattiness(), food_fattiness, mod_type );

        fattiness = fattiness() + fattiness_factor;

        food.UpdateFattiness( fattiness(), mod_type, modification_counter_fatty );

        EventElementPreference.SetFattiness( fattiness );

        modification_counter_fatty++;

        return fattiness_factor;
    }

    public Preference preference()
    {
        return EventElementPreference;
    }

    public String event_colour(){ return event_colour; }

    public int event_position(){ return event_position; }

    public void UpdateAllPreferences(Food food, ModificationType mod_sourness, ModificationType mod_spiciness,
                                     ModificationType mod_sweetness, ModificationType mod_bitterness, ModificationType mod_fattiness)
    {
        double sourness_factor = UpdateSourness(food, mod_sourness);
        double spiciness_factor = UpdateSpiciness(food, mod_spiciness);
        double sweetness_factor = UpdateSweetness(food, mod_sweetness);
        double bitterness_factor = UpdateBitterness(food, mod_bitterness);
        double fattiness_factor = UpdateFattiness(food, mod_fattiness);

        boolean isPerfect = mod_sourness == ModificationType.PERFECT && mod_spiciness == ModificationType.PERFECT && mod_sweetness == ModificationType.PERFECT
                            && mod_sweetness == ModificationType.PERFECT && mod_bitterness == ModificationType.PERFECT && mod_fattiness == ModificationType.PERFECT;

        if(isPerfect)
            range_counter++;

        /*AsyncTabuMod async_update = new AsyncTabuMod( this, sourness_factor, spiciness_factor, sweetness_factor, bitterness_factor, fattiness_factor );
        if (Build.VERSION.SDK_INT >= 11) {
            //--post GB use serial executor by default --
            async_update.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            async_update.execute();
        }*/
    }

    private double FoodToUserInfluenceFactor(double counter, double user_value, double food_value, ModificationType mod_type )
    {
        double influence_factor = 0;

        switch( mod_type ){

            case PERFECT:
            {
                influence_factor = ( user_value + food_value ) / 2 - user_value;
                break;
            }
            case TOO_LOW:
            {
                influence_factor = Math.abs( food_value - user_value );
                if(influence_factor<1.0){
                    //Preference is incorrect, compensate
                    influence_factor = 2.0;
                }
                break;
            }
            case TOO_MUCH:
            {
                influence_factor = -Math.abs( food_value - user_value );
                if(influence_factor>-1.0){
                    //Preference is incorrect, compensate
                    influence_factor = -2.0;
                }
                break;
            }
        }

        double result = Math.pow(TEMP, counter) * influence_factor;

        return result;
    }
}
