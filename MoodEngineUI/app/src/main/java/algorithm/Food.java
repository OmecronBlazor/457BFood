package algorithm;

public class Food {

    private Preference food_preference;

    private int food_counter = 0;
    private int id = 0;
    private final double TEMP = 0.98;
    private final double USER_INFLUENCE = 0.95;
    private String name = "";

    public Food( int id, String name, double sourness, double saltiness, double sweetness, double bitterness, double fattiness, int food_counter){

        this.id = id;
        this.name = name;
        this.food_preference = new Preference( sourness, saltiness, sweetness, bitterness, fattiness );
        this.food_counter = food_counter;
    }

    public Food( String name , String artist, Preference food_preference ){

        this.food_preference = food_preference;
        this.name = name;
    }

    public Food( String name, double sourness, double saltiness, double sweetness, double bitterness, double fattiness, int food_counter){

        this.name = name;
        this.food_preference = new Preference( sourness, saltiness, sweetness, bitterness, fattiness );
    }

    public Preference preference(){ return food_preference; }

    public int counter(){ return food_counter; }

    public void setCounter(int new_counter){ this.food_counter = new_counter; }

    public int id(){ return id; }

    public String name(){ return name; }

    public double sourness(){ return food_preference.sourness(); }

    public void setSourness( double sourness) { this.food_preference.SetSourness(sourness); }

    public double saltiness(){ return food_preference.saltiness(); }

    public void setSaltiness( double saltiness) { this.food_preference.SetSaltiness(saltiness); }

    public double sweetness(){ return food_preference.sweetness(); }

    public void setSweetness( double sweetness) { this.food_preference.SetSweetness(sweetness); }

    public double bitterness(){ return food_preference.bitterness(); }

    public void setBitterness( double bitterness) { this.food_preference.SetBitterness(bitterness); }

    public double fattiness(){ return food_preference.fattiness(); }

    public void setFattiness( double fattiness) { this.food_preference.SetFattiness(fattiness); }

    public void UpdateSourness( double user_sourness, ModificationType mod_type, int user_counter )
    {
        double sourness;

        sourness = sourness() + UserToFoodInfluenceFactor( user_counter, sourness(), user_sourness, mod_type );
        food_preference.SetSourness( sourness );

        food_counter++;
    }

    public void UpdateSaltiness( double user_saltiness, ModificationType mod_type, int user_counter )
    {
        double saltiness;

        saltiness = saltiness() + UserToFoodInfluenceFactor( user_counter, saltiness(), user_saltiness, mod_type );
        food_preference.SetSaltiness( saltiness );

        food_counter++;
    }

    public void UpdateSweetness( double user_sweetness, ModificationType mod_type, int user_counter )
    {
        double sweetness;

        sweetness = sweetness() + UserToFoodInfluenceFactor( user_counter, sweetness(), user_sweetness, mod_type );
        food_preference.SetSweetness( sweetness );

        food_counter++;
    }

    public void UpdateBitterness( double user_bitterness, ModificationType mod_type, int user_counter )
    {
        double bitterness;

        bitterness = bitterness() + UserToFoodInfluenceFactor( user_counter, bitterness(), user_bitterness, mod_type );
        food_preference.SetBitterness( bitterness );

        food_counter++;
    }

    public void UpdateFattiness( double user_fattiness, ModificationType mod_type, int user_counter )
    {
        double fattiness;

        fattiness = fattiness() + UserToFoodInfluenceFactor( user_counter, fattiness(), user_fattiness, mod_type );
        food_preference.SetFattiness( fattiness );

        food_counter++;
    }

    private double UserToFoodInfluenceFactor( int user_counter, double food_value, double user_value, ModificationType mod_type )
    {
        double influence_factor = 0;

        switch( mod_type ){

            case PERFECT:
            {
                influence_factor = ( food_value + user_value ) / 2 - food_value;
                break;
            }
            case TOO_LOW:
            {
                influence_factor = -Math.abs( user_value - food_value );
                if(influence_factor>-1.0){
                    //Preference is incorrect, compensate
                    influence_factor = -2.0;
                }
                break;
            }
            case TOO_MUCH:
            {
                influence_factor = Math.abs( user_value - food_value );
                if(influence_factor<1.0){
                    //Preference is incorrect, compensate
                    influence_factor = 2.0;
                }
                break;
            }
        }

        double result = //( 1 - Math.pow( USER_INFLUENCE, user_counter ) ) *
                Math.pow(TEMP, food_counter) * influence_factor;

        return result;
    }
}
