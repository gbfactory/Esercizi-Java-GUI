import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class CodiceFiscale extends JFrame implements ActionListener {

    JTextField tfCognome, tfNome, tfGiorno, tfAnno, tfComune, codicefiscale;
    JLabel lCognome, lNome, lSesso, lGiorno, lComune;
    JComboBox cb;
    JRadioButton m, f;
    JButton genera;
    ButtonGroup mf;
    String mesi[] = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

    CodiceFiscaleGUI() {
        super("Codice Fiscale");

        lCognome = new JLabel("Cognome");
        lCognome.setBounds(30, 30, 100, 25);

        tfCognome = new JTextField("");
        tfCognome.setBounds(100, 30, 200, 25);


        lNome = new JLabel("Nome");
        lNome.setBounds(30, 65, 100, 25);

        tfNome = new JTextField("");
        tfNome.setBounds(100, 65, 200, 25);


        lSesso = new JLabel("Sesso");
        lSesso.setBounds(30, 100, 100, 25);

        m = new JRadioButton("Maschio");
        m.setBounds(100, 100, 100, 25);

        f = new JRadioButton("Femmina");
        f.setBounds(215, 100, 100, 25);

        mf = new ButtonGroup();

        mf.add(m); mf.add(f);


        lGiorno = new JLabel("Data");
        lGiorno.setBounds(30, 135, 100, 25);

        tfGiorno = new JTextField("");
        tfGiorno.setBounds(100, 135, 35, 25);

        cb = new JComboBox(mesi);
        cb.setBounds(142, 135, 100, 25);

        tfAnno = new JTextField("");
        tfAnno.setBounds(250, 135, 50, 25);


        lComune = new JLabel("Comune");
        lComune.setBounds(30, 170, 100, 25);

        tfComune = new JTextField("");
        tfComune.setBounds(100, 170, 200, 25);

        genera = new JButton("Calcola Codice Fiscale");
        genera.setBounds(30, 215, 270, 30);
        genera.addActionListener(this);

        codicefiscale = new JTextField("");
        codicefiscale.setBounds(30, 255, 270, 30);
        codicefiscale.setHorizontalAlignment(JTextField.CENTER);
        codicefiscale.setEditable(false);

        add(lCognome);
        add(tfCognome);
        add(lNome);
        add(tfNome);
        add(lSesso);
        add(m); add(f);
        add(lGiorno);
        add(tfGiorno);
        add(cb);
        add(tfAnno);
        add(lComune);
        add(tfComune);
        add(genera);
        add(codicefiscale);

        setSize(350, 350);
        setLayout(null);
        setVisible(true);
        setResizable(false);

    }

    public void actionPerformed(ActionEvent e) {
        String cognome = tfCognome.getText();
        String nome = tfNome.getText();
        String sesso = "";
        if (m.isSelected()) {
            sesso = "M";
        } else if (f.isSelected()) {
            sesso = "F";
        }
        int dataGiorno = Integer.parseInt(tfGiorno.getText());
        int dataMese = cb.getSelectedIndex() + 1;
        int dataAnno = Integer.parseInt(tfAnno.getText());
        String comune = tfComune.getText();

        String cf = "";

        // CODICE COGNOME
        cognome = cognome.toUpperCase();
        String cCons = "";
        String cVocs = "";

        for (int i = 0; i < cognome.length(); i++) {

            char c = cognome.charAt(i);

            if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                if (cVocs.length() < 3) {
                    cVocs += c;
                }
            } else {
                cCons += c;
                if (cCons.length() == 3) {
                    cVocs = "";
                    break;
                }
            }
        }

        if (cCons.length() < 3) {
            if (cCons.length() == 2) {
                cCons += cVocs.charAt(0);
            }
            if (cCons.length() == 1) {
                if (cVocs.length() > 1) {
                    cCons += cVocs.charAt(0) + cVocs.charAt(1);
                } else {
                    cCons += cVocs.charAt(0) + "X";
                }
            }
            if (cCons.length() == 0) {
                if (cVocs.length() < 3) {
                    cCons = cVocs + "X";
                } else {
                    cCons = cVocs;
                }
            }
        }

        cf += cCons;

        // CODICE NOME
        nome = nome.toUpperCase();
        String vCons = "";
        String nVocs = "";

        for (int i = 0; i < nome.length(); i++) {

            char c = nome.charAt(i);

            if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                if (nVocs.length() < 3) {
                    nVocs += c;
                }
            } else {
                vCons += c;
                if (vCons.length() == 4) {
                    nVocs = "";
                    break;
                }
            }
        }

        if (vCons.length() < 3) {
            if (vCons.length() == 2) {
                vCons += nVocs.charAt(0);
            }
            if (vCons.length() == 1) {
                if (nVocs.length() > 1) {
                    vCons += nVocs.charAt(0) + nVocs.charAt(1);
                } else {
                    vCons += nVocs.charAt(0) + "X";
                }
            }
            if (vCons.length() == 0) {
                if (nVocs.length() < 3) {
                    vCons = nVocs + "X";
                } else {
                    vCons = nVocs;
                }
            }
        }

        if (vCons.length() > 3) {
            String temp = vCons;
            vCons = "";
            vCons += Character.toString(temp.charAt(0)) + Character.toString(temp.charAt(2)) + Character.toString(temp.charAt(3));
        }

        cf += vCons;

        // ANNO
        String dataAnnoString = "";

        dataAnno = dataAnno % 100;

        if (dataAnno <= 9) {
            dataAnnoString = "0" + dataAnno;
        } else {
            dataAnnoString = dataAnno + "";
        }

        cf += dataAnnoString;

        // MESE
        String dataMeseCode = "";

        switch (dataMese) {
            case 1: dataMeseCode = "A"; break;
            case 2: dataMeseCode = "B"; break;
            case 3: dataMeseCode = "C"; break;
            case 4: dataMeseCode = "D"; break;
            case 5: dataMeseCode = "E"; break;
            case 6: dataMeseCode = "H"; break;
            case 7: dataMeseCode = "L"; break;
            case 8: dataMeseCode = "M"; break;
            case 9: dataMeseCode = "P"; break;
            case 10: dataMeseCode = "R"; break;
            case 11: dataMeseCode = "S"; break;
            case 12: dataMeseCode = "T"; break;
        }

        cf += dataMeseCode;

        // GIORNO
        String dataGiornoString = "";

        if (sesso.equalsIgnoreCase("F")) {
            dataGiorno += 40;
        }

        if (dataGiorno <= 9) {
            dataGiornoString = "0" + dataGiorno;
        } else {
            dataGiornoString = dataGiorno + "";
        }

        cf += dataGiornoString;

        // CODICE CATASTALE
        String codCat="";

        try {
            Scanner fileScanner = new Scanner(new File("comuni.txt"));
            fileScanner.useDelimiter("\r\n");
            while(fileScanner.hasNext()) {
                String s1 = fileScanner.nextLine();
                String s2 = s1.substring(0,s1.indexOf('-')-1);
                if(s2.equalsIgnoreCase(comune)) {
                    codCat = s1.substring(s1.lastIndexOf(' ')+1);
                    break;
                }
            }
            fileScanner.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        cf += codCat;

        // CARATTERE DI CONTROLLO
        // caratteri pari
        String pari = "";
        for (int i = 0; i < cf.length(); i++) {
            if((i + 1) % 2 == 0) {
                pari += Character.toString(cf.charAt(i));
            }
        }

        // caratteri dispari
        String dispari = "";
        for (int i = 0; i < cf.length(); i++) {
            if ((i + 1) % 2 == 1) {
                dispari += Character.toString(cf.charAt(i));
            }
        }

        // conversione valori dispari
        int rDispari = 0;

        for (int i = 0; i < dispari.length(); i++) {
            char c = dispari.charAt(i);
            switch (c) {
                case '0':
                case 'A':
                    rDispari += 1;
                    break;
                case '1':
                case 'B':
                    rDispari += 0;
                    break;
                case '2':
                case 'C':
                    rDispari += 5;
                    break;
                case '3':
                case 'D':
                    rDispari += 7;
                    break;
                case '4':
                case 'E':
                    rDispari += 9;
                    break;
                case '5':
                case 'F':
                    rDispari += 13;
                    break;
                case '6':
                case 'G':
                    rDispari += 15;
                    break;
                case '7':
                case 'H':
                    rDispari += 17;
                    break;
                case '8':
                case 'I':
                    rDispari += 19;
                    break;
                case '9':
                case 'J':
                    rDispari += 21;
                    break;
                case 'K':
                    rDispari += 2;
                    break;
                case 'L':
                    rDispari += 4;
                    break;
                case 'M':
                    rDispari += 18;
                    break;
                case 'N':
                    rDispari += 20;
                    break;
                case 'O':
                    rDispari += 11;
                    break;
                case 'P':
                    rDispari += 3;
                    break;
                case 'Q':
                    rDispari += 6;
                    break;
                case 'R':
                    rDispari += 8;
                    break;
                case 'S':
                    rDispari += 12;
                    break;
                case 'T':
                    rDispari += 14;
                    break;
                case 'U':
                    rDispari += 16;
                    break;
                case 'V':
                    rDispari += 10;
                    break;
                case 'W':
                    rDispari += 22;
                    break;
                case 'X':
                    rDispari += 25;
                    break;
                case 'Y':
                    rDispari += 24;
                    break;
                case 'Z':
                    rDispari += 23;
                    break;
            }
        }

        // conversione valori pari
        int rPari = 0;

        for (int i = 0; i < pari.length(); i++) {
            char c = pari.charAt(i);
            int n = Character.getNumericValue(c);

            if (Character.isLetter(c)) {
                n = c - 65;
                rPari += n;
            } else {
                rPari += n;
            }
        }

        // somma
        int somma = rDispari + rPari;
        int resto = (int) somma % 26;
        char restoConv = (char) (resto + 65);
        cf += Character.toString(restoConv);

        codicefiscale.setText(cf);
    }

    public static void main(String[] args) {
        new CodiceFiscale();
    }

}
