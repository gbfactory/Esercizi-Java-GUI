/**
 * Tiro al piattello - classe del gioco vero e proprio. Crea lo sfondo, aggiunge
 * il fucile e gestisce il lancio dei piattelli con relativa gestione dei punti.
 * Il punteggio parte da un numero definito per poi salire di 1 se il piattello
 * viene preso o scendere di 1 se il piattello viene mancato.
 *
 * @author  GB Factory (gbfactory.net)
 * @version 2.0
 * @since   2021-01-05
 */

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class Tiro extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    public final int width = 1280;
    public final int height = 720;

    private int mouseX = 0;
    private int mouseY = 0;

    private int clickX = 0;
    private int clickY = 0;

    private int piattelloX = 0;
    private int piattelloY = 0;

    private int punti = 0;
    private int puntiVecchi = 0;

    private Timer timer;

    boolean game = true;

    boolean sparo = false;
    boolean rotto = false;

    public Tiro() {
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(width, height));

        startGame();
    }

    private void startGame() {
        punti = 1;
        puntiVecchi = punti;

        resettaPiattello();

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        drawScenario(g);

        if (game) {
            drawPiattello(g);
            drawFucile(g);
            drawMirino(g);

            g.setFont(new Font("Verdana", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("Punteggio: " + punti, 10, 25);
        } else {
            gameOver(g);
            drawMirino(g);
        }
    }

    private void drawScenario(Graphics g) {
        ImageIcon sfondo = new ImageIcon("./src/img/sfondo.jpg");
        g.drawImage(sfondo.getImage(), 0, 0, width, height, null);
    }

    private void drawFucile(Graphics g) {
        String imgName = sparo ? "fucile_sparo.png" : "fucile.png";
        ImageIcon fucile = new ImageIcon("./src/img/" + imgName);
        g.drawImage(fucile.getImage(), mouseX, height - 264, 450, 264, null);
        sparo = false;
    }

    private void drawMirino(Graphics g) {
        ImageIcon mirino = new ImageIcon("./src/img/mirino.png");
        g.drawImage(mirino.getImage(), mouseX - 40, mouseY - 40, 80, 80, null);
    }

    private void drawPiattello(Graphics g) {
        ImageIcon piattello =  new ImageIcon("./src/img/piattello.png");
        g.drawImage(piattello.getImage(), piattelloX, piattelloY, 100, 100, null);
    }

    private void gameOver(Graphics g) {
        String stringa = "HAI PERSO!";
        String strPunti = "Clicca per ripartire!";

        Font font = new Font("Verdana", Font.BOLD, 20);
        Font fontPunti = new Font("Verdana", Font.BOLD, 15);

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(stringa, (width - getFontMetrics(font).stringWidth(stringa)) / 2, height / 2);

        g.setColor(Color.WHITE);
        g.setFont(fontPunti);
        g.drawString(strPunti, (width - getFontMetrics(fontPunti).stringWidth(strPunti)) / 2, height / 2 + 35);
    }

    private void playAudio(String nomeFile) {
        File audio = new File("./src/audio/" + nomeFile);

        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audio));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!game) {
            game = true;
            startGame();
        }
    }

    private void resettaPiattello() {
        piattelloX = -300;
        piattelloY = new Random().nextInt(400);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();

        System.out.println(timer.getDelay());

        if (clickX >= piattelloX && clickX <= piattelloX + 100 && clickY >= piattelloY && clickY <= piattelloY + 100) {
            playAudio("hit.wav");
            resettaPiattello();
            punti++;
            puntiVecchi = punti;

            if (punti > 0 && timer.getDelay() > 0 && punti % 5 == 0) {
                timer.setDelay(timer.getDelay() - 1);
            }
        }

        sparo = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        piattelloX += 10;

        if (piattelloX > 0 && piattelloX <= 400 ) {
            piattelloY += 1;
        }

        if (piattelloX > 400 && piattelloX <= 800 ) {
            piattelloY += 2;
        }

        if (piattelloX > 800) {
            piattelloY += 3;
        }

        if (piattelloX >= width) {
            playAudio("miss.wav");
            resettaPiattello();
            if (punti == puntiVecchi) {
                punti--;
                puntiVecchi = punti;
            }
        }

        if (punti <= 0) {
            playAudio("stop.wav");
            game = false;
            timer.stop();
        }

        if (sparo) {
            //playAudio("fire.wav");
        }

        repaint();
    }
}
