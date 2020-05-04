package memory;

/**
 * Gioco Memory
 *
 * Classe Tabellone: responsabile di creare le carte, disporle su una griglia e gestire l'interazione con l'utente.
 *
 * @author gbfactory
 * @since 03/05/2020
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Tabellone extends JPanel{

    private List<Card> carte;

    private Card card1;
    private Card card2;

    private Timer timer;

    private int tentativi;

    private TentativoListener tentativoListener;

    /**
     * Tabellone del gioco principale
     * Crea le carte, le dispone e gestice l'interazione
     */
    public Tabellone() {

        // Layout
        setLayout(new GridLayout(4, 5));

        // Numero di coppie di carte
        int coppieCards = 8;

        // Lista delle carte
        List<Card> listaCards = new ArrayList<Card>();
        // Lista dei valori assegnati alle carte
        List<Integer> valoriCards = new ArrayList<Integer>();

        // Aggiunge per ogni coppia di carte due valori alla lista
        for (int i = 0; i < coppieCards; i++) {
            valoriCards.add(i);
            valoriCards.add(i);
        }

        // Mescola le carte (https://www.tutorialspoint.com/shuffle-elements-of-arraylist-with-java-collections)
        Collections.shuffle(valoriCards);

        // Per ogni valore nella lista crea una nuova carta, assegna l'action listener e aggiunge la carta alla lista
        for (int val : valoriCards) {
            Card c = new Card(val);
            c.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    giraCard(c);
                }
            });
            listaCards.add(c);
        }

        carte = listaCards;

        // Timer
        timer = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("timer");
                controlloCard();
            }
        });

        timer.setRepeats(false);

        // Aggiunge le carte al tabellone
        for (Card c : carte){
            add(c);
        }

    }

    /**
     * Tentativo listener
     * @param listener TentativoListener
     */
    public void setTentativoListener(TentativoListener listener) {
        this.tentativoListener = listener;
    }

    /**
     * Viene eseguito quando una carta viene girata
     * Se entrambe le carte sono nulle, card1 viene impostata alla carta cliccata dall'utente
     * Se la card1 è nulla e diversa da quella cliccata, card2 viene impostata alla carta cliccata dall'utente
     * Il testo viene mostrato sulle carte
     * @param cartaCliccata Card cliccata dall'utente
     */
    private void giraCard(Card cartaCliccata) {
        if (card1 == null && card2 == null) {
            card1 = cartaCliccata;
            card1.showText();
        } else if (card1 != null && card1 != cartaCliccata && card2 == null) {
            card2 = cartaCliccata;
            card2.showText();
            timer.start();
        }
    }

    /**
     * Controlla le carte: se l'id della card1 e card2 sono uguali, imposta entrambe le carte come abbinate. Inoltre controlla
     * se l'utnete ha vinto, in quel caso mostra il messaggio di vittora e il gioco viene bloccato.
     * Se gli id sono diversi nasconde il testo delle carte
     * Resetta a null le carte e aggiunge un tentativo
     */
    private void controlloCard() {
        if (card1.controlloId(card2)) {
            card1.abbinata();
            card2.abbinata();
            if (controlloVittoria()) {
                JOptionPane.showMessageDialog(this, "Hai vinto con " + tentativi + " tentativi!");
                return;
            }
        } else {
            card1.hideText();
            card2.hideText();
        }

        card1 = null;
        card2 = null;

        tentativi++;
        tentativoListener.tentativoAggiunto(tentativi);

    }

    /**
     * Controlla se l'utente ha vinto passando tutte le carte e controllando se tutte sono abbinate
     * @return boolean
     */
    private boolean controlloVittoria() {
        for(Card c : carte) {
            if (!c.getAbbinata()) {
                return false;
            }
        }
        return true;
    }

}