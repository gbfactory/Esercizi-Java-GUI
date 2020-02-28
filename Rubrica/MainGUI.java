package rubrica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainGUI extends JFrame {

    JTextField tfNome, tfCognome, tfTelefono;

    JLabel lNome, lCognome, lTelefono;

    JButton aggiungiContatto, visualizzaRubrica;

    MainGUI() {
        super("Rubrica");

        lNome = new JLabel("Nome");
        lNome.setBounds(30, 25, 150, 25);

        tfNome = new JTextField();
        tfNome.setBounds(175, 25, 150, 25);

        lCognome = new JLabel("Cognome");
        lCognome.setBounds(30, 75, 150, 25);

        tfCognome = new JTextField();
        tfCognome.setBounds(175, 75, 150, 25);

        lTelefono = new JLabel("Telefono");
        lTelefono.setBounds(30, 125, 150, 25);

        tfTelefono = new JTextField();
        tfTelefono.setBounds(175, 125, 150, 25);

        aggiungiContatto = new JButton("Aggiungi Contatto");
        aggiungiContatto.setBounds(30, 170, 300, 30);
        aggiungiContatto.setHorizontalAlignment(JTextField.CENTER);
        aggiungiContatto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = tfNome.getText();
                String cognome = tfCognome.getText();
                String telefono = tfTelefono.getText();

                String data = nome + "--" + cognome + "--" + telefono;

                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./src/rubrica/rubrica.txt", true)));
                    out.println(data);
                    out.flush();
                    out.close();
                }
                catch (IOException ex) {
                    System.out.println(ex);
                }

            }
        });

        visualizzaRubrica = new JButton("Visualizza Rubrica");
        visualizzaRubrica.setBounds(30, 230, 300, 50);
        visualizzaRubrica.setHorizontalAlignment(JTextField.CENTER);
        visualizzaRubrica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RubricaGUI();

            }
        });

        add(lNome);
        add(lCognome);
        add(lTelefono);
        add(tfNome);
        add(tfCognome);
        add(tfTelefono);
        add(aggiungiContatto);
        add(visualizzaRubrica);

        setSize(375, 500);
        setLayout(null);
        setVisible(true);
        setResizable(true);

    }
}
