/**
 * Snake - classe responsabile di creare l'area di gioco vera e propria
 * di Snake che permette di muovere lo snake e crescerlo mangiando
 * le mele disposte randomicamente sulla mappa.
 *
 * @author  GB Factory (gbfactory.net)
 * @version 3.0
 * @since   2021-01-02
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Snake extends JPanel implements ActionListener {

    private final int lato = 300;
    private final int dimPunto = 10;
    private final int maxPunti = (lato * lato) / (dimPunto * dimPunto);
    private final int posRandom = ((int) Math.sqrt(maxPunti)) - 1;
    private final int tempo = 140;

    private final int[] x = new int[maxPunti];
    private final int[] y = new int[maxPunti];

    private int pallini;
    private int melaX;
    private int melaY;

    private int punteggio = -1;

    private boolean dirSinistra = false;
    private boolean dirDestra = true;
    private boolean dirSu = false;
    private boolean dirGiu = false;

    private boolean giocando = true;

    private Timer timer;

    /**
     * Costruttore
     */
    public Snake() {
        addKeyListener(new AdapterTastiera());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(lato, lato));
        avviaGioco();
    }

    /**
     * Metodo per l'avvio del gioco che imposta i pallini
     * iniziali dello snake e altre funzioni base
     */
    private void avviaGioco() {
        // Imposta il gioco in esecuzione
        giocando = true;

        // Reimposta le direzioni
        dirSinistra = false;
        dirDestra = true;
        dirSu = false;
        dirGiu = false;

        // Resetta il punteggio
        punteggio = -1;

        // Pallini iniziali dello snake
        pallini = 3;

        // Per ogni pallino assegna una posizione
        for (int i = 0; i < pallini; i++) {
            x[i] = 10 - i * 10;
            y[i] = 10;
        }

        // Disponi la mela sullo schermo randomicamente
        disponiMela();

        // Imposta il timer con la var definita sopra e avvia
        timer = new Timer(tempo, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * Metodo per disegnare un pallino dello snake
     * @param g Graphics per il disegno
     * @param c Colore del pallino
     * @param x Posizione X
     * @param y Posizione Y
     */
    private void disegnaPallino(Graphics g, Color c, int x, int y) {
        g.setColor(c);
        g.drawOval(x, y, dimPunto, dimPunto);
        g.fillOval(x, y, dimPunto, dimPunto);
    }

    /**
     * Metodo per il disegnod del gioco. Se giocando è true (quindi il gioco è
     * in esecuzione) disegna il campo del gioco con lo snake e la mela, se
     * invece giocanndo è false (gioco finito) mostra la schermata di game over.
     * @param g Elemento graphics per il disengo
     */
    private void doDrawing(Graphics g) {
        // Mostra il gioco
        if (giocando) {
            // Disegna la mela con le coordinate random
            disegnaPallino(g, Color.YELLOW, melaX, melaY);

            // Ciclo tra i pallini dello snake e li disegna
            for (int i = 0; i < pallini; i++) {
                // Se l'indice è 1 disegna la testa (rossa)
                if (i == 0) {
                    disegnaPallino(g, Color.RED, x[i], y[i]);
                }
                // Disegna il resto dei pallini del corpo (verdi)
                else {
                    disegnaPallino(g, Color.GREEN, x[i], y[i]);
                }
            }

            // Scrive il punteggio in alto a sx. Il punteggio parte da -1 e aumenta ogni
            // volta che viene generata una nuova posizione random della mela. Passa a 0
            // con la prima mela generata a inizio gioco per poi crescere regolarmente.
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", Font.PLAIN, 12));
            g.drawString("Punteggio: " + punteggio, 5, 15);

            // Assicura che lo schermo sia sempre aggiornato
            Toolkit.getDefaultToolkit().sync();
        }
        // Mostra la schermata di Game Over. Per le scritte viene calcolata il giusto
        // valore per farle apparire al centro della finestra e non decentrate.
        else {
            String strPerso = "HAI PERSO!";
            String strPunti = "Punteggio: " + punteggio;
            String strRestart = "Premi SPACEBAR per ripartire";

            Font font = new Font("Verdana", Font.BOLD, 20);
            Font fontPunti = new Font("Verdana", Font.BOLD, 15);
            Font fontRestart = new Font("Verdana", Font.ITALIC, 13);

            g.setColor(Color.RED);
            g.setFont(font);
            g.drawString(strPerso, (lato - getFontMetrics(font).stringWidth(strPerso)) / 2, lato / 3);

            g.setColor(Color.WHITE);
            g.setFont(fontPunti);
            g.drawString(strPunti, (lato - getFontMetrics(fontPunti).stringWidth(strPunti)) / 2, lato / 3 + 35);

            g.setColor(Color.GREEN);
            g.setFont(fontRestart);
            g.drawString(strRestart, (lato - getFontMetrics(fontRestart).stringWidth(strRestart)) / 2, lato / 3 + 85);

        }
    }

    /**
     * Controlla se la testa dello snake è nella stessa posizione della mela.
     * In tal caso aumenta di uno la lunghezza dello snake e dispone una nuova
     * mela nell'area di gioco, in modo randomico.
     */
    private void collisioneMela() {
        if (x[0] == melaX && y[0] == melaY) {
            pallini++;
            disponiMela();
        }
    }

    /**
     * Si occupa di far muovere lo snake spostando ogni pallino nella posizione del
     * precedente ogni volta che la testa viene spostata con i comandi da tastiera.
     */
    private void muoviSnake() {
        // Sposta tutti i pallini del corpo
        for (int i = pallini; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        // Sposta la testa in base ai comandi da tastiera. Lo spostamento che viene
        // effettuato è sempre della dimensione del punto impostata all'inizio.
        if (dirSinistra) {
            x[0] -= dimPunto;
        }

        if (dirDestra) {
            x[0] += dimPunto;
        }

        if (dirSu) {
            y[0] -= dimPunto;
        }

        if (dirGiu) {
            y[0] += dimPunto;
        }
    }

    /**
     * Controlla le collisioni dello snake.
     */
    private void controlloCollisioni() {
        // Per ciascun pallino, controlla se la sua posizione è uguale a quella della
        // testa dello snake. In tal caso termina il gioco. Attenzione: c'è una tolleranza
        // di due pallini, quindi i primi due pallini del corpo non sono considerati.
        for (int i = pallini; i > 0; i--) {
            if (i > 2 && x[0] == x[i] && y[0] == y[i]) {
                giocando = false;
            }
        }

        // Controlla se la testa dello snake va al di fuori dell'area di gioco definita.
        // In tal caso termina il gioco.
        if (y[0] >= lato) {
            giocando = false;
        }

        if (y[0] < 0) {
            giocando = false;
        }

        if (x[0] >= lato) {
            giocando = false;
        }

        if (x[0] < 0) {
            giocando = false;
        }

        // Se l'escuzione del gioco è terminata (giocando = false) stoppa il timer.
        if (!giocando) {
            timer.stop();
        }
    }

    /**
     * Dispone la mele randomicamente utilizzando il numero calcolato a inizio programma
     * in base alle variabili della dim dell'area di gioco e alla dim del pallino.
     * Attenzione: allo stesso tempo incrementa di 1 il punteggio dato che ogni volta che
     * una mela nuova viene posizionata il giocatore ha raccolto quella precedente.
     */
    private void disponiMela() {
        // Coordinate x e y della mela
        melaX = ((int) (Math.random() * posRandom)) * dimPunto;
        melaY = ((int) (Math.random() * posRandom)) * dimPunto;

        // Incrementa il punteggio
        punteggio++;

        // Aumenta velocità
        if (punteggio > 0 && timer.getDelay() > 3 && punteggio % 3 == 0) {
            timer.setDelay(timer.getDelay() - 3);
        }
    }

    /**
     * Quando viene eseguita un'azione (in questo caso quella del timer), esegue i
     * vari metodi per il funzionamento del gioco. Quindi se il gioco è in esecuzione
     * controlla sempre la collisione con la mela, le collisioni generali e muove lo snake.
     * Mentre viene sempre eseguito il repaint, anche se il giocatore è in game over.
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (giocando) {
            collisioneMela();
            controlloCollisioni();
            muoviSnake();
        }

        repaint();
    }

    /**
     * Classe AdapterTastiera che estende il KeyAdapter e sovrascrive il metodo keyPressed.
     * Si occupa di ottenere il codice del tasto premuto e poi in base al tasto imposta a
     * true o false i vari booleani di movimento.
     * Viene controllato anche che non vengano seguiti movimenti impossibili.
     * I controlli del gioco funzionano sia con le freccette direzionali sia con W A S D.
     */
    private class AdapterTastiera extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            // Ottiene il codice del tasto premuto
            int tasto = e.getKeyCode();

            // Sinistra
            if (tasto == KeyEvent.VK_LEFT || tasto == KeyEvent.VK_A && !dirDestra) {
                dirSinistra = true;
                dirSu = false;
                dirGiu = false;
            }

            // Destra
            if (tasto == KeyEvent.VK_RIGHT || tasto == KeyEvent.VK_D && !dirSinistra) {
                dirDestra = true;
                dirSu = false;
                dirGiu = false;
            }

            // Su
            if (tasto == KeyEvent.VK_UP || tasto == KeyEvent.VK_W && !dirGiu) {
                dirSu = true;
                dirDestra = false;
                dirSinistra = false;
            }

            // Giu
            if (tasto == KeyEvent.VK_DOWN || tasto == KeyEvent.VK_S && !dirSu) {
                dirGiu = true;
                dirDestra = false;
                dirSinistra = false;
            }

            if (tasto == KeyEvent.VK_SPACE && !giocando) {
                avviaGioco();
            }
        }
    }
}