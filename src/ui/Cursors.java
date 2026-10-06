package ui;

import irrgarten.Directions;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Usuario
 */
public class Cursors extends JDialog {
    private Directions direction;

    public Cursors(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent); // Centrar sobre la ventana padre
    }

    public Directions getDirection() {
        this.setVisible(true); // AQUÍ SE DETIENE LA EJECUCIÓN HASTA QUE SE CIERRE LA VENTANA
        return direction;
    }

    private void initComponents() {
        this.setTitle("Elige dirección");
        this.setLayout(new GridLayout(2, 3)); // Diseño simple tipo rejilla
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Obligar a pulsar botón

        // Crear botones
        JButton up = new JButton("UP");
        JButton down = new JButton("DOWN");
        JButton left = new JButton("LEFT");
        JButton right = new JButton("RIGHT");

        // Añadirlos con un layout básico (puedes mejorarlo visualmente)
        // Fila 1
        this.add(new JLabel("")); // Hueco vacío
        this.add(up);
        this.add(new JLabel("")); // Hueco vacío
        
        // Fila 2
        this.add(left);
        this.add(down);
        this.add(right);

        // Listeners: Qué pasa al pulsar
        up.addActionListener(e -> returnDirection(Directions.UP));
        down.addActionListener(e -> returnDirection(Directions.DOWN));
        left.addActionListener(e -> returnDirection(Directions.LEFT));
        right.addActionListener(e -> returnDirection(Directions.RIGHT));
    }

    private void returnDirection(Directions dir) {
        this.direction = dir;
        this.dispose(); // Cierra la ventana y devuelve el control al programa principal
    }
}
