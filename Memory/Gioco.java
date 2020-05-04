package memory;

/**
 * Gioco Memory
 *
 * Class Gioco: responsabile di generare il JFrame principale del gioco, creando un nuovo Tabellone e mostrando
 *              i tentativi all'utente sotto il tabellone.
 *
 * @author gbfactory
 * @since 04/05/2020
 */

import javax.swing.*;
import java.awt.*;

public class Gioco extends JFrame {

    private Tabellone tabellone;

    private JLabel labelTentativi;

    public Gioco() {

        // Titolo & layout
        super("Gioco Memory");
        setLayout(new BorderLayout());

        // Tabellone
        tabellone = new Tabellone();

        // Label tentativi
        labelTentativi = new JLabel("Tentativi: 0");

        tabellone.setTentativoListener(new TentativoListener() {
            @Override
            public void tentativoAggiunto(int tentativo) {
                labelTentativi.setText("Tentativi: " + tentativo);
            }
        });

        // Aggiunta componenti
        add(tabellone, BorderLayout.CENTER);
        add(labelTentativi, BorderLayout.PAGE_END);

        // Impostazioni finestra
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

}
