package ui;

import irrgarten.Directions;
import irrgarten.GameState;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Usuario
 */
public class GraphicalUI extends JFrame implements UI {
    private JTextArea taLabyrinth;
    private JTextArea taMonsters;
    private JTextArea taPlayers;
    private JTextArea taLog;
    private JLabel lCurrentPlayer;
    private JLabel lWinner;
    
    private Cursors cursors; // Referencia a la ventana de movimiento

    public GraphicalUI() {
        initComponents();
        this.setVisible(true);
        // Inicializamos el diálogo de cursores (modal)
        this.cursors = new Cursors(this, true);
    }

    @Override
    public void showGame(GameState gameState) {
        this.taLabyrinth.setText(gameState.getLabyrinth());
        this.taMonsters.setText("Monsters:\n" + gameState.getMonsters());
        this.taPlayers.setText("Players:\n" + gameState.getPlayers());
        this.taLog.setText("Log:\n" + gameState.getLog());
        
        this.lCurrentPlayer.setText("Current Player: " + gameState.getCurrentPlayer());
        
        if (gameState.getWinner()) {
            this.lWinner.setText("Winner: YES - Player " + gameState.getCurrentPlayer());
            JOptionPane.showMessageDialog(this, "Player " + gameState.getCurrentPlayer() + " WINS!");
        } else {
            this.lWinner.setText("Winner: NO");
        }
        
        this.repaint(); // Refrescar visualmente
    }

    @Override
    public Directions nextMove() {
        // Delegamos en el diálogo Cursors la obtención de la dirección
        return cursors.getDirection();
    }

    private void initComponents() {
        this.setTitle("Irrgarten - Graphical UI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());

        // Panel Central: Laberinto
        taLabyrinth = new JTextArea();
        // IMPORTANTE: Fuente monoespaciada para que el mapa cuadre
        taLabyrinth.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16)); 
        taLabyrinth.setEditable(false);
        this.add(new JScrollPane(taLabyrinth), BorderLayout.CENTER);

        // Panel Derecho: Info
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3, 1));
        
        taPlayers = new JTextArea();
        taPlayers.setEditable(false);
        rightPanel.add(new JScrollPane(taPlayers));
        
        taMonsters = new JTextArea();
        taMonsters.setEditable(false);
        rightPanel.add(new JScrollPane(taMonsters));

        taLog = new JTextArea();
        taLog.setEditable(false);
        rightPanel.add(new JScrollPane(taLog));
        
        this.add(rightPanel, BorderLayout.EAST);

        // Panel Superior: Estado
        JPanel topPanel = new JPanel();
        lCurrentPlayer = new JLabel("Player: ");
        lWinner = new JLabel("Winner: ");
        topPanel.add(lCurrentPlayer);
        topPanel.add(new JLabel("   |   ")); // Separador
        topPanel.add(lWinner);
        
        this.add(topPanel, BorderLayout.NORTH);
    }
}
