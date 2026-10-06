package irrgarten;

/**
 *
 * @author Usuario
 */
public class ShieldCardDeck extends CardDeck<Shield> {
    private static final int NUM_CARDS = 5;
    
    @Override
    protected void addCards(){
        for(int i = 0; i < NUM_CARDS; i++){
            Shield s = new Shield(Dice.shieldPower(), Dice.usesLeft());
            this.addCard(s);
        }
    }
}
