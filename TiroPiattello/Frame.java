/**
 * Tiro al piattello - classe Game che responsabile di creare il JFrame del gioco
 * e di aggiungere una nuova classe Tiro.java. Inoltre viene rimosso il puntatore
 * del mouse di default che viene poi sostituito da un mirino nella classe Tiro.
 *
 * @author  GB Factory (gbfactory.net)
 * @version 1.1
 * @since   2021-01-05
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends JFrame {
    public Frame() {

        add(new Tiro());

        // rimuove il cursore. viene disegnato come mirino
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        getContentPane().setCursor(blankCursor);

        // icona
        setIconImage(new ImageIcon("./src/img/piattello.png").getImage());

        // impostazioni jframe
        setTitle("Tiro al Piattello");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
