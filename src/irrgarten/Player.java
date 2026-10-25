package irrgarten;
import java.util.ArrayList;

public class Player extends LabyrinthCharacter{
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    
    private char number;
    private int consecutiveHits = 0;
    
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    private WeaponCardDeck weaponCardDeck;
    private ShieldCardDeck shieldCardDeck;
    
    public Player(char number, float intelligence, float strength){
        super("Player #" + number, intelligence, strength, INITIAL_HEALTH);
        this.number = number;
        this.weapons = new ArrayList<>();
        this.shields = new ArrayList<>();
        this.weaponCardDeck = new WeaponCardDeck();
        this.shieldCardDeck = new ShieldCardDeck();
    }
    
    public Player(Player other){
        super(other);
        this.number = other.number;
        this.consecutiveHits = other.consecutiveHits;
        this.weapons = new ArrayList<>(other.weapons);
        this.shields = new ArrayList<>(other.shields);
        this.weaponCardDeck = other.weaponCardDeck;
        this.shieldCardDeck = other.shieldCardDeck;
    }
    
    public void resurrect(){
        this.setHealth(INITIAL_HEALTH);
        this.consecutiveHits = 0;
        this.weapons.clear();
        this.shields.clear();
    }
    
    @Override
    public String toString(){
        return "Jugador " + super.toString() + " Armas(" + this.weapons.size() +
                        "), Escudos(" + this.shields.size() + ")";
    }
    
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        
        if(size > 0 && !contained){
            return validMoves.get(0);
        }else{
            return direction;
        }
    }
    
    @Override
    public float attack(){
        return this.getStrength() + this.sumWeapons();
    }
    
    @Override
    public boolean defend(float receivedAttack){
        return this.manageHit(receivedAttack);
    }
    
    public void receiveReward(){
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        
        for(int i = 0; i < wReward; i++){
            Weapon wnew = weaponCardDeck.nextCard();
            this.receiveWeapon(wnew);
        }
        
        for(int i = 0; i < sReward; i++){
            Shield snew = shieldCardDeck.nextCard();
            this.receiveShield(snew);
        }
        
        int extraHealth = Dice.healthReward();
        this.setHealth(this.getHealth() + extraHealth);
    }
    
    private void receiveWeapon(Weapon w){
        for(int i = this.weapons.size() - 1; i >= 0; i--){
            Weapon wi = this.weapons.get(i);
            if(wi.discard()){
                this.weapons.remove(i);
            }
        }
        
        if(this.weapons.size() < MAX_WEAPONS){
            this.weapons.add(w);
        }
    }
    
    private void receiveShield(Shield s){
        for(int i = this.shields.size() - 1; i >= 0; i--){
            Shield si = this.shields.get(i);
            if(si.discard()){
                this.shields.remove(i);
            }
        }
        
        if(this.shields.size() < MAX_SHIELDS){
            this.shields.add(s);
        }
    }
    
    private Weapon newWeapon(){
        float power = Dice.weaponPower();
        int uses = Dice.usesLeft();
        
        return new Weapon(power, uses);
    }
    
    private Shield newShield(){
        float protection = Dice.shieldPower();
        int uses = Dice.usesLeft();
        
        return new Shield(protection, uses);
    }
    
    protected float sumWeapons(){
        float total = 0.0f;
        for(Weapon w : this.weapons){
            total += w.attack();
        }
        return total;
    }
    
    protected float sumShields(){
        float total = 0.0f;
        for(Shield s : this.shields){
            total += s.protect();
        }
        return total;
    }
    
    protected float defensiveEnergy(){
        return this.getIntelligence() + this.sumShields();
    }
    
    private boolean manageHit(float receivedAttack){
        float defense = this.defensiveEnergy();
        
        if(defense < receivedAttack){
            this.gotWounded();
            this.incConsecutiveHits();
        } else{
            this.resetHits();
        }
        
        boolean lose = false;
        if(this.consecutiveHits >= HITS2LOSE || this.dead()){
            this.resetHits();
            lose = true;
        }
        
        return lose;
    }
    
    private void resetHits(){
        this.consecutiveHits = 0;   
    }
    
    private void incConsecutiveHits(){
        this.consecutiveHits += 1;
    }

    public char getNumber() {
        return this.number;
    }
}
