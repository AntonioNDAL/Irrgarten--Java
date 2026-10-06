package irrgarten;
import irrgarten.Player;
import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10;
    private static final int N_MONSTERS = 3;
    private static final int LABYRINTH_ROWS = 7;
    private static final int LABYRINTH_COLS = 5;
    private static final int EXIT_ROW = 4;
    private static final int EXIT_COL = 3;
    
    private Player currentPlayer;
    private int numCurrentPlayer;
    private String log;
    
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Labyrinth labyrinth;
    
    public Game(int nplayers){
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        
        for(int i = 0; i < nplayers; i++){
            this.players.add(new Player((char)('0'+i), Dice.randomIntelligence(), Dice.randomStrength()));
        }
        
        this.numCurrentPlayer = Dice.whoStarts(nplayers);
        this.currentPlayer = this.players.get(this.numCurrentPlayer);
        
        this.log = "";
        this.labyrinth = new Labyrinth(LABYRINTH_ROWS, LABYRINTH_COLS, EXIT_ROW, EXIT_COL);
        
        this.configureLabyrinth();
        this.labyrinth.spreadPlayers(this.players);
    }
    
    public boolean finished(){
        return this.labyrinth.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        this.log = "";
        boolean isDead = this.currentPlayer.dead();
        
        if(isDead){
            this.manageResurrection();
        }else{
            Directions direction = this.actualDirection(preferredDirection);
            
            if(direction != preferredDirection){
                this.logPlayerNoOrders();
            }
            
            Monster monster = this.labyrinth.putPlayer(direction, this.currentPlayer);
            
            if(monster == null){
                this.logNoMonster();
            }else{
                GameCharacter winner = this.combat(monster);
                this.manageReward(winner);
            }
        }
        
        boolean endGame = this.finished();
        if(!endGame){
            this.nextPlayer();
        }
        
        return endGame;
    }
    
    public GameState getGameState() {
    StringBuilder playersStr = new StringBuilder();
    for (Player p : this.players) {
        playersStr.append(p.toString()).append("\n");
    }

    StringBuilder monstersStr = new StringBuilder();
    for (Monster m : this.monsters) {
        monstersStr.append(m.toString()).append("\n");
    }

    return new GameState(
        this.labyrinth.toString(),
        playersStr.toString(),
        monstersStr.toString(),
        this.numCurrentPlayer,
        this.finished(),
        this.log
    );
}
    
    private void configureLabyrinth(){
        this.labyrinth.addBlock(Orientation.VERTICAL, 1, 1, 5);
        this.labyrinth.addBlock(Orientation.VERTICAL, 0, 3, 2);
        this.labyrinth.addBlock(Orientation.HORIZONTAL, 2, 2, 4);
        
        for(int i = 0; i < N_MONSTERS; i++){
            Monster monster = new Monster("Monstruo " + i, Dice.randomIntelligence(),
                                            Dice.randomStrength());
            int[] pos = this.labyrinth.randomEmptyPos();
            int row = pos[0];
            int col = pos[1];
            
            this.labyrinth.addMonster(row, col, monster);
            this.monsters.add(monster);
        }
    }
    
    private void nextPlayer(){
        this.numCurrentPlayer = (this.numCurrentPlayer + 1) % this.players.size();
        this.currentPlayer = this.players.get(this.numCurrentPlayer);
    }
    
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = this.currentPlayer.getRow();
        int currentCol = this.currentPlayer.getCol();
        
        ArrayList<Directions> validMoves = this.labyrinth.validMoves(currentRow, currentCol);
        
        return this.currentPlayer.move(preferredDirection, validMoves);
    }
    
    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        
        float playerAttack = this.currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        
        while(!lose && rounds < MAX_ROUNDS){
            winner = GameCharacter.MONSTER;
            rounds++;
            
            float monsterAttack = monster.attack();
            lose = this.currentPlayer.defend(monsterAttack);
            
            if(!lose){
                winner = GameCharacter.PLAYER;
                playerAttack = this.currentPlayer.attack();
                lose = monster.defend(playerAttack);
            }
        }
        
        this.logRounds(rounds, MAX_ROUNDS);
        return winner;
    }
    
    private void manageReward(GameCharacter winner){
        if(winner == GameCharacter.PLAYER){
            this.currentPlayer.receiveReward();
            this.logPlayerWon();
        }else{
            this.logMonsterWon();
        }
    }
    
    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();
        
        if(resurrect){
            this.currentPlayer.resurrect();
            this.logResurrected();
        }else{
            if(!(currentPlayer instanceof FuzzyPlayer)){
                FuzzyPlayer fuzzy = new FuzzyPlayer(currentPlayer);
                players.set(numCurrentPlayer, fuzzy);
                currentPlayer = fuzzy;
            }
            this.logPlayerSkipTurn();
        }
    }
    
    private void logPlayerWon(){
        this.log += "El jugador ha ganado el combate.\n";
    }
    
    private void logMonsterWon(){
        this.log += "El monstruo ha ganado el combate.\n";
    }
    
    private void logResurrected(){
        this.log += "El jugador ha resucitado.\n";
    }
    
    private void logPlayerSkipTurn(){
        this.log += "El jugador ha perdido el turno por estar muerto.\n";
    }
    
    private void logPlayerNoOrders(){
        this.log += "El jugador no ha seguido las instrucciones del jugador humano.\n";
    }
    
    private void logNoMonster(){
        this.log += "El jugador se ha movido a una celda vacía o no le ha sido posible moverse.\n";
    }
    
    private void logRounds(int rounds, int max){
        this.log += "Se ha producido " + rounds + " de " + max + " rondas de combate.\n";
    }
}
