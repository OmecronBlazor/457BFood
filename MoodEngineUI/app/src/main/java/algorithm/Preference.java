package algorithm;

public class Preference {

    private final double LOWER_BOUND = 0.0;
    private final double UPPER_BOUND = 10.0;

    private double sourness;
    private double saltiness;
    private double sweetness;
    private double bitterness;
    private double fattiness;

    public Preference( double sourness, double saltiness, double sweetness, double bitterness, double fattiness)
    {
        this.sweetness = sweetness;
        this.saltiness = saltiness;
        this.sourness = sourness;
        this.bitterness = bitterness;
        this.fattiness = fattiness;
    }

    public double sweetness(){ return sweetness; }

    public void SetSweetness( double sweetness )
    {
        this.sweetness = sweetness;

        if( this.sweetness < LOWER_BOUND ) this.sweetness = 1;
        if( this.sweetness > UPPER_BOUND ) this.sweetness = 10;
    }

    public double saltiness() { return saltiness; }

    public void SetSaltiness( double saltiness )
    {
        this.saltiness = saltiness;

        if( this.saltiness < LOWER_BOUND ) this.saltiness = 1;
        if( this.saltiness > UPPER_BOUND ) this.saltiness = 10;
    }

    public double sourness() { return sourness; }

    public void SetSourness( double sourness )
    {
        this.sourness = sourness;

        if( this.sourness < LOWER_BOUND ) this.sourness = 1;
        if( this.sourness > UPPER_BOUND ) this.sourness = 10;
    }

    public double bitterness(){ return bitterness; }

    public void SetBitterness( double bitterness)
    {
        this.bitterness = bitterness;

        if( this.bitterness < LOWER_BOUND ) this.bitterness = 1;
        if( this.bitterness > UPPER_BOUND ) this.bitterness = 10;
    }

    public double fattiness(){ return fattiness; }

    public void SetFattiness( double fattiness )
    {
        this.fattiness = fattiness;

        if( this.fattiness < LOWER_BOUND ) this.fattiness = 1;
        if( this.fattiness > UPPER_BOUND ) this.fattiness = 10;
    }
}