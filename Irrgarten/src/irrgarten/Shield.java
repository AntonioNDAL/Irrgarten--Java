package irrgarten;

/**
 *
 * @author Usuario
 */
public class Shield extends CombatElement{
    
    public Shield(float protection, int uses){
        super(protection, uses);
    }
    
    public float protect(){
        return this.produceEffect();
    }
    
    @Override
    public String toString(){
        return "S" + super.toString();
    }
}
