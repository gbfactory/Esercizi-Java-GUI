package rubrica;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RubricaGUI extends JFrame {

    JTable tabella;

    RubricaGUI() {
        super("Visualizza Rubrica");

        String col[] = { "Nome", "Cognome", "Numero" };

        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };

        ArrayList<Contatto> dataFromFile = new ArrayList<Contatto>();

        System.out.println("ciao");

        try {
            Scanner fileScanner = new Scanner(new File("./src/rubrica/rubrica.txt"));
            fileScanner.useDelimiter("\r\n");
            while(fileScanner.hasNext()) {
                String[] dati = fileScanner.nextLine().split("--");
                dataFromFile.add(new Contatto(dati[0], dati[1], dati[2]));
            }
            fileScanner.close();
        } catch(IOException ex) {
            System.out.println(ex);
        }

        for (Contatto c : dataFromFile) {
            String nomeContatto = c.getNome();
            String cognomeContatto = c.getCognome();
            String telefonoContatto = c.getTelefono();

            Object[] data = {nomeContatto, cognomeContatto, telefonoContatto};

            tableModel.addRow(data);

        }


        JTable table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    String selData = (String) table.getValueAt(table.getSelectedRow(), 0);
                    System.out.println(selData);
                }
            }

        });

/*
        jt.setCellSelectionEnabled(true);

        ListSelectionModel select = jt.getSelectionModel();

        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        select.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                String Data = null;
                int[] row = jt.getSelectedRows();
                int[] columns = jt.getSelectedColumns();
                for (int i = 0; i < row.length; i++) {
                    for (int j = 0; j < columns.length; j++) {
                        Data = (String) jt.getValueAt(row[i], columns[j]);
                    }
                }
                System.out.println("Table element selected is: " + Data);
            }
        });

        JScrollPane sp = new JScrollPane(jt);

        add(sp);
*/

        JScrollPane js=new JScrollPane(table);

        add(js);

        setSize(700, 700);
        setVisible(true);
        setResizable(false);

    }
}
