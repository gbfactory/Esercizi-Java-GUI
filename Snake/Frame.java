/**
 * Snake - classe che estende il JFrame e aggiunge l'area di gioco (classe Game)
 * e aggiunge un tasto che permette in ogni momento di far ripartire nuovamente il
 * gioco, per esempio in caso di Game Over ma utilizzabile anche in altri momenti.
 *
 * @author  GB Factory (gbfactory.net)
 * @version 1.1
 * @since   2021-01-03
 */

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        // Layout
        setLayout(new BorderLayout());

        // Snake
        add(new Snake(), BorderLayout.CENTER);

        // Finestra
        setTitle("Snake");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}