package irrgarten;
import java.util.ArrayList;
/**
 *
 * @author Usuario
 */
public class FuzzyPlayer extends Player{
    public FuzzyPlayer(Player other){
        super(other);
    }
    
    @Override
    public float attack(){
        return this.sumWeapons() + Dice.intensity(this.getStrength());
    }
    
    @Override
    protected float defensiveEnergy(){
        return this.sumShields() + Dice.intensity(this.getIntelligence());
    }
    
    @Override
    public String toString(){
        return "Fuzzy " + super.toString();
    }
    
    @Override
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        return Dice.nextStep(direction, validMoves, this.getIntelligence());
    }
}
