package rubricaObj;

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

                if (nome == null || nome.isEmpty()) {
                    JOptionPane optionPane = new JOptionPane("Il nome inserito non è valido!",JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Rubrica");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    return;
                }

                if (cognome == null || cognome.isEmpty()) {
                    JOptionPane optionPane = new JOptionPane("Il cognome inserito non è valido!",JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Rubrica");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    return;
                }

                if (telefono == null || telefono.isEmpty() || telefono.length() < 7 || telefono.length() > 10) {
                    JOptionPane optionPane = new JOptionPane("Il numero inserito non è valido!",JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Rubrica");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    return;
                }

                Contatto c = new Contatto(nome, cognome, telefono);

                System.out.println(c.toString());

                try {
                    FileOutputStream f = new FileOutputStream(new File("rubricaOggetti.txt"), true);

                    /**
                     * IMPORTANTE
                     * Per creare un nuovo file è necessario utilizzare ObjectOutputStream e aggiungere almeno il primo
                     * elemento. Se si vogliono aggiungere altri file è necessario usare AppendingObjectOutputStream,
                     * questa classe NON crea un nuovo header quando si va a serializzare.
                     * Se si prova a leggere un file serializzato in modalità append vengono trovati molteplici header
                     * e da errore!!!
                     *
                     * TODO: Controllare se il file è vuoto o no, utilizzare quindi la classe opportuna.
                     *
                     * Un'altra soluzione è creare un primo contatto e nasconderlo dalla visualizzazione rubrica
                     */

                    //ObjectOutputStream o = new ObjectOutputStream(f);
                    AppendingObjectOutputStream o = new AppendingObjectOutputStream(f);

                    o.writeObject(c);

                    o.flush();
                    o.close();
                    f.flush();
                    f.close();
                } catch (FileNotFoundException err) {
                    JOptionPane optionPane = new JOptionPane("File non trovato!",JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Rubrica");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    return;
                } catch (IOException err) {
                    JOptionPane optionPane = new JOptionPane("Errore inizializzazione flusso!",JOptionPane.ERROR_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Rubrica");
                    dialog.setAlwaysOnTop(true);
                    dialog.setVisible(true);
                    return;
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

        setSize(375, 340);
        setLayout(null);
        setVisible(true);
        setResizable(false);

    }
}
