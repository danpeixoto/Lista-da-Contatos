package programa;

import javafx.beans.property.SimpleStringProperty;

public class Pessoa {

    private SimpleStringProperty nome;
    private SimpleStringProperty sobrenome;
    private SimpleStringProperty celular;
    private SimpleStringProperty nota;

    public Pessoa(String nome, String sobrenome, String celular, String nota) {
        this.nome = new SimpleStringProperty(nome);
        this.sobrenome = new SimpleStringProperty(sobrenome);
        this.celular = new SimpleStringProperty(celular);
        this.nota = new SimpleStringProperty(nota);
    }

    public String getNome() {
        return nome.get();
    }



    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getSobrenome() {
        return sobrenome.get();
    }



    public void setSobrenome(String sobrenome) {
        this.sobrenome.set(sobrenome);
    }

    public String getCelular() {
        return celular.get();
    }



    public void setCelular(String celular) {
        this.celular.set(celular);
    }

    public String getNota() {
        return nota.get();
    }


    public void setNota(String nota) {
        this.nota.set(nota);
    }
}
