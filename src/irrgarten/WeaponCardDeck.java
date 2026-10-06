package irrgarten;

/**
 *
 * @author Usuario
 */
public class WeaponCardDeck extends CardDeck<Weapon>{
    private static final int NUM_CARDS = 5;
    
    @Override
    protected void addCards(){
        for(int i = 0; i < NUM_CARDS; i++){
            Weapon w = new Weapon(Dice.weaponPower(), Dice.usesLeft());
            this.addCard(w);
        }
    }
    
}
