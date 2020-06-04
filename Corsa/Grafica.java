package net.gbfactory.corsa;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Gioco corsa
 * @author gbfactory
 * @since 04/06/2020
 */

public class Grafica extends JPanel implements ActionListener {

    private int pos1 = 15;
    private int pos2 = 15;
    private int pos3 = 15;
    private int pos4 = 15;
    private int pos5 = 15;
    private int pos6 = 15;
    private int pos7 = 15;
    private int pos8 = 15;

    private int endPos = 750;

    Timer timer;

    BufferedImage imgPista;
    BufferedImage imgRunner;

    public Grafica() {
        super();

        timer = new Timer(50, this);

        try {
            imgPista = ImageIO.read(new File("pista.jpg"));
            imgRunner = ImageIO.read(new File("runner.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }


    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // pista
        g.drawImage(imgPista, 0, 0, getWidth(), getHeight(), this);

        // Omini

        g.drawImage(imgRunner, pos1, 4, 50, 50, this);
        g.drawImage(imgRunner, pos2, 60, 50, 50, this);
        g.drawImage(imgRunner, pos3, 115, 50, 50, this);
        g.drawImage(imgRunner, pos4, 172, 50, 50, this);
        g.drawImage(imgRunner, pos5, 226, 50, 50, this);
        g.drawImage(imgRunner, pos6, 278, 50, 50, this);
        g.drawImage(imgRunner, pos7, 335, 50, 50, this);
        g.drawImage(imgRunner, pos8, 395, 50, 50, this);

/*        g.setColor(Color.BLACK);
        g.drawOval(pos1, 50, 30, 30);
        g.fillOval(pos1, 50, 30, 30);

        g.setColor(Color.YELLOW);
        g.drawOval(pos2, 100, 30, 30);
        g.fillOval(pos2, 100, 30, 30);*/

    }

    public void actionPerformed(ActionEvent e) {

        pos1 += (int) (Math.random() * 15);
        pos2 += (int) (Math.random() * 15);
        pos3 += (int) (Math.random() * 15);
        pos4 += (int) (Math.random() * 15);
        pos5 += (int) (Math.random() * 15);
        pos6 += (int) (Math.random() * 15);
        pos7 += (int) (Math.random() * 15);
        pos8 += (int) (Math.random() * 15);

        if (pos1 >= endPos) {
            endRace(1);
        } else if (pos2 >= endPos) {
            endRace(2);
        } else if (pos3 >= endPos) {
            endRace(3);
        } else if (pos4 >= endPos) {
            endRace(4);
        } else if (pos5 >= endPos) {
            endRace(5);
        } else if (pos6 >= endPos) {
            endRace(6);
        } else if (pos7 >= endPos) {
            endRace(7);
        } else if (pos8 >= endPos) {
            endRace(8);
        }

        repaint();

    }

    public void startRace() {
        pos1 = 0;
        pos2 = 0;
        pos3 = 0;
        pos4 = 0;
        pos5 = 0;
        pos6 = 0;
        pos7 = 0;
        pos8 = 0;

        timer.start();

        try {
            Clip audio = AudioSystem.getClip();
            audio.open(AudioSystem.getAudioInputStream(new File("ColpoDiPistola.wav")));
            audio.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void endRace(int runnerInt) {
        timer.stop();

        try {
            Clip audio = AudioSystem.getClip();
            audio.open(AudioSystem.getAudioInputStream(new File("win.wav")));
            audio.start();
        } catch (Exception e) {
            System.out.println(e);
        }

        JOptionPane.showMessageDialog(null, "Vince giocatore " + runnerInt);
    }

}
