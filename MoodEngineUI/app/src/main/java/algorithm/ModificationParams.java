package algorithm;

/**
 * Created by Michael on 3/19/2015.
 */
public class ModificationParams {

    private ModificationType sourness = null;
    private ModificationType saltiness = null;
    private ModificationType sweetness = null;
    private ModificationType bitterness = null;
    private ModificationType fattiness = null;

    public ModificationParams(){}

    public ModificationParams(ModificationType sourness, ModificationType saltiness, ModificationType sweetness, ModificationType bitterness, ModificationType fattiness){
        this.sourness = sourness;
        this.saltiness = saltiness;
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
    public ModificationType saltiness() {
        return saltiness;
    }
    public void setSaltiness(ModificationType saltiness) {
        this.saltiness = saltiness;
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
        return (sourness==null&&saltiness==null&&sweetness==null&&bitterness==null&&fattiness==null);
    }
}