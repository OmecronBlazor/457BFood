package algorithm;

/**
 * Created by Adnan on 4/01/15.
 */
public enum EventType{
    //"#ffc0392b", "#ffcf000f", "#ffe08283", "#ff674172", "#ff3a539b", "#ff26a65b", "#fff7ca18", "#fff9690e", "#ff674172"

    //TODO: FIGURE OUT WHAT VALUES GO IN HERE
    DINNER( -3, 0, 3, 0, 0, "#ff26a65b", "Dinner" ),
    BREAKFAST( 0, 3, 3, 0, 0, "#fff64747", "Breakfast" ),
    LUNCH( 3, 4, 3, 0, 0, "#ffcf000f", "Lunch" ),
    SNACK( -3, -3, 0, 0, 0, "#ff1abc9c", "Snack" ),
    DIET( 0, 0, 0, 0, 0, "#fff7ca18", "Diet" ),
    WEEKEND( 3, 3, 0, 0, 0, "#fff9690e", "Weekend" ),
    STUDYING( -3, -4, -3, 0, 0, "#ff3a539b", "Studying" ),
    BIG_DAY( 0, -3, -3, 0, 0, "#ff3498db", "Big Day" ),
    EXOTIC( 3, 0, -3, 0, 0, "#ff8e44ad", "Exotic" );

    private final String event_name;
    private final String event_colour;
    private final double sourness_factor;
    private final double saltiness_factor;
    private final double sweetness_factor;
    private final double bitterness_factor;
    private final double fattiness_factor;

    EventType( double sourness_factor, double saltiness_factor, double sweetness_factor, double bitterness_factor,
               double fattiness_factor, String event_colour, String event_name )
    {
        this.event_name = event_name;
        this.event_colour = event_colour;
        this.sourness_factor = sourness_factor;
        this.saltiness_factor = saltiness_factor;
        this.sweetness_factor = sweetness_factor;
        this.bitterness_factor = bitterness_factor;
        this.fattiness_factor = fattiness_factor;
    }

    public final String event_name(){ return event_name; }
    public final String event_colour(){ return event_colour; }
    public final double sourness_factor(){ return sourness_factor; }
    public final double saltiness_factor(){ return saltiness_factor; }
    public final double sweetness_factor(){ return sweetness_factor; }
    public final double bitterness_factor(){ return bitterness_factor; }
    public final double fattiness_factor(){ return fattiness_factor; }
}