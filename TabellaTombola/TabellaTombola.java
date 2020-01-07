import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabellaTombola extends Frame {

    int ranPrev = 999;

    public TabellaTombola() {
        JFrame f = new JFrame("Tabella Tombola");

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        // Tabella tombola
        JPanel tabellaTombola = new JPanel();
        tabellaTombola.setLayout(new GridLayout(9, 10, 0, 0));

        JButton[] buttons = new JButton[90];

        int[] btnCheck = new int[90];

        for (int i=0; i < 90; i++) {
            buttons[i] = new JButton(String.valueOf(i + 1));
            buttons[i].setEnabled(false);

            

        }

        for (int i=0; i < 90; i++) {
            tabellaTombola.add(buttons[i]);
        }

        content.add(tabellaTombola, BorderLayout.CENTER);

        // Bottone estrai
        JButton estraiNum = new JButton();
        estraiNum.setText("ESTRAI");
        content.add(estraiNum, BorderLayout.SOUTH);

        estraiNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ran = (int) (1 + Math.random() * 90 - 1);
                buttons[ran].setBackground(Color.RED);

                if (ranPrev != 999) {
                    buttons[ranPrev].setBackground(Color.ORANGE);
                }
                ranPrev = ran;
            }
        });

        // Frame
        f.setContentPane(content);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(550, 550);
        f.setVisible(true);
        f.setResizable(false);
    }

    public static void main(String[] args) {
        new TabellaTombola();
    }

}
