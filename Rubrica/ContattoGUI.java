package rubrica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ContattoGUI extends JFrame {

    JTextField tfNome, tfCognome, tfTelefono;

    JLabel lNome, lCognome, lTelefono;

    JButton modifica, annulla, elimina;

    ContattoGUI(String nome, String cognome, String telefono) {
        super("Modifica Contatto");

        lNome = new JLabel("Nome");
        lNome.setBounds(30, 25, 150, 25);

        tfNome = new JTextField(nome);
        tfNome.setBounds(175, 25, 150, 25);

        lCognome = new JLabel("Cognome");
        lCognome.setBounds(30, 75, 150, 25);

        tfCognome = new JTextField(cognome);
        tfCognome.setBounds(175, 75, 150, 25);

        lTelefono = new JLabel("Telefono");
        lTelefono.setBounds(30, 125, 150, 25);

        tfTelefono = new JTextField(telefono);
        tfTelefono.setBounds(175, 125, 150, 25);

        modifica = new JButton("Modifica");
        modifica.setBounds(30, 170, 300, 30);
        modifica.setHorizontalAlignment(JTextField.CENTER);
        modifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String oldContatto = nome + "--" + cognome + "--" + telefono;
                String newContatto = tfNome.getText() + "--" + tfCognome.getText() + "--" + tfTelefono.getText();

                File file = new File("./src/rubrica/rubrica.txt");
                String oldLista = "";
                BufferedReader reader = null;
                FileWriter writer = null;

                try {
                    reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    while (line != null) {
                        oldLista += line + System.lineSeparator();
                        line = reader.readLine();
                    }
                    String newLista = oldLista.replaceAll(oldContatto, newContatto);
                    writer = new FileWriter(file);
                    writer.write(newLista);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        reader.close();
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                new RubricaGUI();
                setVisible(false);
                dispose();

                JOptionPane optionPane = new JOptionPane("Hai aggiornato il contatto con successo!",JOptionPane.WARNING_MESSAGE);
                JDialog dialog = optionPane.createDialog("Rubrica");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                return;

            }
        });

        annulla = new JButton("Annulla");
        annulla.setBounds(30, 210, 300, 30);
        annulla.setHorizontalAlignment(JTextField.CENTER);
        annulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RubricaGUI();
                setVisible(false);
                dispose();
            }
        });

        elimina = new JButton("Elimina Contatto");
        elimina.setBounds(30, 250, 300, 30);
        elimina.setHorizontalAlignment(JTextField.CENTER);
        elimina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = new File("./src/rubrica/rubrica.txt");
                String oldLista = "";
                BufferedReader reader = null;
                FileWriter writer = null;

                try {
                    reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    while (line != null) {
                        if (line.equals(nome + "--" + cognome + "--" + telefono)) {
                            oldLista = oldLista;
                        } else {
                            oldLista += line + System.lineSeparator();
                        }
                        line = reader.readLine();
                    }
                    writer = new FileWriter(file);
                    writer.write(oldLista);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        reader.close();
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                new RubricaGUI();
                setVisible(false);
                dispose();

                JOptionPane optionPane = new JOptionPane("Hai eliminato il contatto con successo!",JOptionPane.WARNING_MESSAGE);
                JDialog dialog = optionPane.createDialog("Rubrica");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                return;

            }
        });

        add(lNome);
        add(lCognome);
        add(lTelefono);
        add(tfNome);
        add(tfCognome);
        add(tfTelefono);
        add(modifica);
        add(annulla);
        add(elimina);

        setSize(375, 340);
        setLayout(null);
        setVisible(true);
        setResizable(true);
    }

}
