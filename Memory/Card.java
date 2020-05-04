package memory;

/**
 * Gioco Memory
 *
 * Classe Card: responsabile di generare le carte del gioco estendendo il JButton.
 *
 * @author gbfactory
 * @since 03/05/2020
 */

import javax.swing.JButton;

public class Card extends JButton{

    private final int id;
    private boolean abbinata = false;

    /**
     * Costruttore
     * @param id id della carta
     */
    public Card(int id) {
        this.id = id;
    }

    /**
     * Controlla se l'id della carta passata è uguale all'id di questa carta
     * @param card carta di cui controllare l'id
     * @return boolean
     */
    public boolean controlloId(Card card) {
        if (this.id == card.id) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Imposta questa carta abbinata in base al booleano passato
     * @param abbinata boolean
     */
    public void setAbbinata(boolean abbinata){
        this.abbinata = abbinata;
    }

    /**
     * Restituisce un valore booleano: true se la carta è abbinata, false se non è
     * @return boolean
     */
    public boolean getAbbinata(){
        return this.abbinata;
    }

    /**
     * Mostra l'id sulla carta
     * Utilizzato quando la carta viene girata
     */
    public void showText() {
        this.setText(String.valueOf(id));
    }

    /**
     * Nasconte il testo mostrato sulla carta
     */
    public void hideText() {
        this.setText("");
    }

    /**
     * Metodo che viene eseguito quando una carta viene abbinata
     */
    public void abbinata() {
        this.setEnabled(false);
        this.setAbbinata(true);
    }
}