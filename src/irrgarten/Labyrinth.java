package irrgarten;
import irrgarten.Player;
import java.util.ArrayList;
import java.util.HashSet;

public class Labyrinth {
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;
    
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    private char[][] labyrinth;
    private Monster[][] monsters;
    private Player[][] players;
    
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        
        this.labyrinth = new char[nRows][nCols];
        this.monsters = new Monster[nRows][nCols];
        this.players = new Player[nRows][nCols];
        
        for(int i = 0; i < nRows; i++){
            for(int j = 0; j < nCols; j++){
                this.labyrinth[i][j] = EMPTY_CHAR;
            }
        }
        
        this.labyrinth[exitRow][exitCol] = EXIT_CHAR;
    }
    
    public void spreadPlayers(ArrayList<Player> players){
        for(Player p : players){
            int[]pos = this.randomEmptyPos();
            
            this.putPlayer2D(-1, -1,pos[ROW], pos[COL], p);
        }
    }
    
    public boolean haveAWinner(){
        return this.players[exitRow][exitCol] != null;
    }
    
    @Override
public String toString() {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < this.nRows; i++) {
        for (int j = 0; j < this.nCols; j++) {
            output.append("[").append(this.labyrinth[i][j]).append("]");
        }
        output.append("\n");
    }
    return output.toString();
}
    
    public void addMonster(int row, int col, Monster monster){
        if(this.emptyPos(row,col)){
            this.labyrinth[row][col] = MONSTER_CHAR;
            this.monsters[row][col] = monster;
            monster.setPos(row,col);
        }
    }
    
    public Monster putPlayer(Directions direction, Player player){
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        
        int[] newPos = this.dir2Pos(oldRow, oldCol, direction);
        
        return this.putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
    }
    
    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        int incRow, incCol;
        if(orientation == Orientation.VERTICAL){
            incRow = 1;
            incCol = 0;
        } else{
            incRow = 0;
            incCol = 1;
        }
        
        int row = startRow;
        int col = startCol;
        
        while(this.posOk(row,col) && this.emptyPos(row,col) && length > 0){
            this.labyrinth[row][col] = BLOCK_CHAR;
            length--;
            row += incRow;
            col += incCol;
        }
    }
    
    public ArrayList<Directions> validMoves(int row, int col){
        ArrayList<Directions> output = new ArrayList<>();
        
        if(this.canStepOn(row + 1, col))
            output.add(Directions.DOWN);
        if(this.canStepOn(row - 1, col))
            output.add(Directions.UP);
        if(this.canStepOn(row, col + 1))
            output.add(Directions.RIGHT);
        if(this.canStepOn(row, col - 1))
            output.add(Directions.LEFT);
        
        return output;
    }
    
    private boolean posOk(int row, int col){
        return row >= 0 && row < this.nRows && col >= 0 && col < this.nCols;
    }
    
    private boolean emptyPos(int row, int col){
        return this.posOk(row,col) && this.labyrinth[row][col] == EMPTY_CHAR;
    }
    
    private boolean monsterPos(int row, int col){
        return this.posOk(row,col) && this.labyrinth[row][col] == MONSTER_CHAR;
    }
    
    private boolean exitPos(int row, int col){
        return this.posOk(row,col) && this.labyrinth[row][col] == EXIT_CHAR;
    }
    
    private boolean combatPos(int row, int col){
        return this.posOk(row,col) && this.labyrinth[row][col] == COMBAT_CHAR;
    }
    
    private boolean canStepOn(int row, int col){
        return this.posOk(row,col) && (this.emptyPos(row,col) || this.monsterPos(row,col) || 
                this.exitPos(row, col));
    }
    
    private void updateOldPos(int row, int col){
        if(this.posOk(row,col)){
            if(this.combatPos(row,col)){
                this.labyrinth[row][col] = MONSTER_CHAR;
            } else{
                this.labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }
    
    private int[] dir2Pos(int row, int col, Directions direction){
        int newRow = row;
        int newCol = col;
        
        switch(direction){
            case UP:
                newRow--;
                break;
            case DOWN:
                newRow++;
                break;
            case LEFT:
                newCol--;
                break;
            case RIGHT  :
                newCol++;
                break;
        }
        
        return new int[]{newRow, newCol};
    }
    
    public int[] randomEmptyPos(){
        int row, col;
        do{
            row = Dice.randomPos(this.nRows);
            col = Dice.randomPos(this.nCols);
        }while(!this.emptyPos(row,col));
        
        return new int[]{row, col};
    }
    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output = null;
        
        if (this.canStepOn(row, col)){
            if(this.posOk(oldRow,oldCol)){
                Player p = this.players[oldRow][oldCol];
                if(p == player){
                    this.updateOldPos(oldRow, oldCol);
                    this.players[oldRow][oldCol] = null;
                }
            }
            
            if(this.monsterPos(row,col)){
                this.labyrinth[row][col] = COMBAT_CHAR;
                output = this.monsters[row][col];
            } else {
                char number = player.getNumber();
                this.labyrinth[row][col] = number;
            }
            
            this.players[row][col] = player;
            player.setPos(row,col);
        }
        
        return output;
    }

    public void replacePlayer(Player newPlayer) {
        int r = newPlayer.getRow();
        int c = newPlayer.getCol();
        if (this.posOk(r, c)) {
            this.players[r][c] = newPlayer;
        }
    }
}
