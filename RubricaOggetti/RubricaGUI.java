package rubricaObj;

import org.omg.CORBA.ObjectHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RubricaGUI extends JFrame {

    private JTable tabella;

    RubricaGUI() {
        super("Visualizza Rubrica");

        String col[] = { "Nome", "Cognome", "Numero" };

        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };

        ArrayList<Contatto> dataFromFile = new ArrayList<Contatto>();

        try {
            FileInputStream fi = new FileInputStream("rubricaOggetti.txt");
            ObjectInputStream oi = new ObjectInputStream(fi);

            boolean read = true;

            while (read) {
                try {
                    dataFromFile.add((Contatto) oi.readObject());
                } catch (ClassNotFoundException err) {
                    //err.printStackTrace();
                } catch (EOFException err) {
                    read = false;
                }
            }

            /*boolean eof = true;
            while (eof) {

                Object obj = null;

                try {
                    obj = oi.readObject();
                } catch (ClassNotFoundException err) {
                    err.printStackTrace();
                }

                if (obj != null) {
                    Contatto c = (Contatto) obj;
                    dataFromFile.add(c);
                } else {
                    eof = false;
                }

            }*/

            oi.close();

        } catch (FileNotFoundException err) {
            //err.printStackTrace();
        }catch (IOException err) {
            //err.printStackTrace();
        }

        /*for (Contatto c : dataFromFile) {
            System.out.println(c.toString());
        }*/

        for (Contatto c : dataFromFile) {
            String nomeContatto = c.getNome();
            String cognomeContatto = c.getCognome();
            String telefonoContatto = c.getTelefono();

            Object[] data = {nomeContatto, cognomeContatto, telefonoContatto};

            tableModel.addRow(data);
        }

        JTable table = new JTable(tableModel);

        /**
         * IMPORTANTE
         * La classe ContattoGUI con le opzioni di rimuovere o modificare il contatto non funziona dato che necessita
         * ulteriori modifiche al programma.
         * TODO: Rendere la classe ContattoGUI funzionante.
         */

        /*table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    String nomeSelected = (String) table.getValueAt(table.getSelectedRow(), 0);
                    String cognomeSelected = (String) table.getValueAt(table.getSelectedRow(), 1);
                    String numeroSelected = (String) table.getValueAt(table.getSelectedRow(), 2);
                    //System.out.println(nomeSelected + " " + cognomeSelected + " " + numeroSelected);
                    new ContattoGUI(nomeSelected, cognomeSelected, numeroSelected);
                    setVisible(false);
                    dispose();
                }
            }

        });*/

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());

        table.setRowSorter(rowSorter);

        JTextField ricerca = new JTextField();

        setSize(700, 700);
        setVisible(true);
        setResizable(false);
        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(ricerca, BorderLayout.SOUTH);

        ricerca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = ricerca.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = ricerca.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Errore");
            }
        });

    }
}
