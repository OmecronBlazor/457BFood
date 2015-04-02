package algorithm;

public class Preference {

    private final double LOWER_BOUND = 0.0;
    private final double UPPER_BOUND = 10.0;

    private double sourness;
    private double spiciness;
    private double sweetness;
    private double bitterness;
    private double fattiness;

    private double heaviness;
    private double tempo;
    private double complexity;

    public Preference( double sweetness, double spiciness, double sourness, double bitterness, double fattiness)
    {
        this.sweetness = sweetness;
        this.spiciness = spiciness;
        this.sourness = sourness;
        this.bitterness = bitterness;
        this.fattiness = fattiness;
    }

    public Preference( double heaviness, double tempo, double complexity )
    {
        this.heaviness = heaviness;
        this.tempo = tempo;
        this.complexity = complexity;
    }

    public double sweetness(){ return sweetness; }

    public void SetSweetness( double sweetness )
    {
        this.sweetness = sweetness;

        if( this.sweetness < LOWER_BOUND ) this.sweetness = 1;
        if( this.sweetness > UPPER_BOUND ) this.sweetness = 10;
    }

    public double spiciness() { return spiciness; }

    public void SetSpiciness( double spiciness )
    {
        this.spiciness = spiciness;

        if( this.spiciness < LOWER_BOUND ) this.spiciness = 1;
        if( this.spiciness > UPPER_BOUND ) this.spiciness = 10;
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


    //All below deprecated
    //TODO: Delete everything below
    public double heaviness()
    {
        return heaviness;
    }

    public void SetHeaviness( double heaviness)
    {
        this.heaviness = heaviness;

        if( this.heaviness < LOWER_BOUND ) this.heaviness = 1;
        if( this.heaviness > UPPER_BOUND ) this.heaviness = 10;
    }

    public double tempo()
    {
        return tempo;
    }

    public void SetTempo( double tempo)
    {
        this.tempo = tempo;

        if( this.tempo < LOWER_BOUND ) this.tempo = 1;
        if( this.tempo > UPPER_BOUND ) this.tempo = 10;
    }

    public double complexity()
    {
        return complexity;
    }

    public void SetComplexity( double complexity)
    {
        this.complexity = complexity;

        if( this.complexity < LOWER_BOUND ) this.complexity = 1;
        if( this.complexity > UPPER_BOUND ) this.complexity = 10;
    }

}