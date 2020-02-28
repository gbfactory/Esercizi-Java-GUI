package rubrica;

public class Contatto {

    private String nome;
    private String cognome;
    private String telefono;

    // Costruttore di base senza parametri
    public Contatto() {
        nome = "";
        cognome = "";
        telefono = "";
    }

    // Costruttore con parametri
    public Contatto(String nome, String cognome, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;

    }

    // Metodi SET
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Metodi GET
    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public String getTelefono() {
        return this.telefono;
    }

}
