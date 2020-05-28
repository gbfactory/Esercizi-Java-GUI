package timer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer Frame class
 *
 * @author gbfactory
 * @since 26.05.2020
 */

public class Frame extends JFrame implements ActionListener {

    private JPanel pnlToolbar;

    private JLabel lblInfo;
    private JTextField tfTime;
    private JButton btnStart;
    private JButton btnStop;

    private JLabel lblTimer;

    private boolean started = false;

    private Timer timer = new Timer();

    private Countdown glbCount;

    private Checkbox cbAudio;

    public Frame() {

        super("Countdown");

        setLayout(new BorderLayout());

        // TOOLBAR
        pnlToolbar = new JPanel();
        pnlToolbar.setLayout(new GridLayout(1, 4));

        lblInfo = new JLabel("Tempo countdown: ");
        lblInfo.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        tfTime = new JTextField();

        btnStart = new JButton("AVVIA");
        btnStart.addActionListener(this);

        btnStop = new JButton("STOP");
        btnStop.addActionListener(this);
        btnStop.setEnabled(false);

        pnlToolbar.add(lblInfo);
        pnlToolbar.add(tfTime);
        pnlToolbar.add(btnStart);
        pnlToolbar.add(btnStop);

        add(pnlToolbar, BorderLayout.PAGE_START);

        // TIMER
        lblTimer = new JLabel("0");

        lblTimer.setForeground(Color.BLACK);
        lblTimer.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 96));

        add(lblTimer, BorderLayout.CENTER);

        // CHECKBOX
        cbAudio = new Checkbox("Audio");
        cbAudio.setState(true);
        add(cbAudio, BorderLayout.PAGE_END);

        // Finestea
        setSize(500, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnStart) {

            try {
                Integer.parseInt(tfTime.getText());
            } catch (Exception er) {
                JOptionPane.showMessageDialog(this, "Tempo inserito non corretto!");
                return;
            }

            if (Integer.parseInt(tfTime.getText()) <= 0) {
                JOptionPane.showMessageDialog(this, "Tempo inserito non corretto!");
                return;
            }

            Countdown count = new Countdown();
            glbCount = count;
            timer.schedule(count, 0, 1000);
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        }

        if (e.getSource() == btnStop) {
            glbCount.stopTimer();
        }
    }

    class Countdown extends TimerTask {

        int countdown = Integer.parseInt(tfTime.getText())+ + 1;

        public void run() {
            countdown = countdown - 1;

            lblTimer.setText(String.valueOf(countdown));

            if (countdown <= 3) {
                lblTimer.setForeground(Color.RED);

                if (cbAudio.getState()) {
                    audio(new File("timer.wav"));   // posizionare file nella dir del progetto
                }

            } else {
                lblTimer.setForeground(Color.BLACK);
            }

            if (countdown == 0) {
                stopTimer();
            }

        }

        public void stopTimer() {
            cancel();
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
        }

    }

    private static void audio(File audio) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audio));
            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
