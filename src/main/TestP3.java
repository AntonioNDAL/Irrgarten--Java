package main;

import ui.GraphicalUI; // Usamos la nueva vista gráfica
import ui.TextUI;      // (Opcional) Por si quieres volver a probar texto
import control.Controller;
import irrgarten.Game;

public class TestP3{
    public static void main(String[] args){
        int n_players = 1; // Puedes probar con más si quieres
        
        // 1. Crear la VISTA (Ahora usamos GraphicalUI)
        // TextUI view = new TextUI(); // <-- ANTIGUO
        GraphicalUI view = new GraphicalUI(); // <-- NUEVO
        
        // 2. Crear el JUEGO
        Game game = new Game(n_players);
        
        // 3. Crear el CONTROLADOR (funciona igual gracias a la interfaz UI)
        Controller controller = new Controller(game, view);
        
        // 4. JUGAR
        controller.play();
    }
}
