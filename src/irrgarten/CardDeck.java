package irrgarten;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * @author Usuario
 */
public abstract class CardDeck<T extends CombatElement> {
    private ArrayList<T> cardDeck;
    
    public CardDeck(){
        this.cardDeck = new ArrayList<>();
    }
    
    protected abstract void addCards();
    
    protected void addCard(T card){
        this.cardDeck.add(card);
    }
    
    public T nextCard(){
        if(this.cardDeck.isEmpty()){
            this.addCards();
            java.util.Collections.shuffle(this.cardDeck);
        }
        
        T card = this.cardDeck.remove(0);
        return card;
    }
}
