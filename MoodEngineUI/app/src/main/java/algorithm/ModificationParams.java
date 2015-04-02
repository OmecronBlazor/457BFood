package algorithm;

/**
 * Created by Michael on 3/19/2015.
 */
public class ModificationParams {

    private ModificationType sourness = null;
    private ModificationType spiciness = null;
    private ModificationType sweetness = null;
    private ModificationType bitterness = null;
    private ModificationType fattiness = null;

    public ModificationParams(){}

    public ModificationParams(ModificationType sourness, ModificationType spiciness, ModificationType sweetness, ModificationType bitterness, ModificationType fattiness){
        this.sourness = sourness;
        this.spiciness = spiciness;
        this.sweetness = sweetness;
        this.bitterness = bitterness;
        this.fattiness = fattiness;
    }
    public ModificationType sourness() {
        return sourness;
    }
    public void setSourness(ModificationType sourness) {
        this.sourness = sourness;
    }
    public ModificationType spiciness() {
        return spiciness;
    }
    public void setSpiciness(ModificationType spiciness) {
        this.spiciness = spiciness;
    }
    public ModificationType sweetness() {
        return sweetness;
    }
    public void setSweetness(ModificationType sweetness) {
        this.sweetness = sweetness;
    }
    public ModificationType bitterness() {
        return bitterness;
    }
    public void setBitterness(ModificationType bitterness) {
        this.bitterness = bitterness;
    }
    public ModificationType fattiness() {
        return fattiness;
    }
    public void setFattiness(ModificationType fattiness) {
        this.fattiness = fattiness;
    }

    public boolean isNull(){
        return (sourness==null&&spiciness==null&&sweetness==null&&bitterness==null&&fattiness==null);
    }
}