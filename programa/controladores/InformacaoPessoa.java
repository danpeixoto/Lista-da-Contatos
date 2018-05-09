package programa.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import programa.Pessoa;

public class InformacaoPessoa {
    @FXML
    private TextField novoNome;
    @FXML
    private TextField novoSobrenome;
    @FXML
    private TextField novoCelular;
    @FXML
    private TextField novaNota;

    public Pessoa processarDados(){
        String nome = novoNome.getText().trim();
        String sobrenome = novoSobrenome.getText().trim();
        String celular = novoCelular.getText().trim();
        String nota = novaNota.getText();

        if(nome.isEmpty()){
            nome = "Cachorro";
        }
        System.out.println(nota);
        if(sobrenome.isEmpty()){
            sobrenome = "Gato";
        }
        if(celular.isEmpty()){
            celular = "xxxx-xxxx";
        }
        if(nota.trim().isEmpty()){
            nota = "------";
        }

        Pessoa pessoa = new Pessoa(nome , sobrenome , celular , nota);
        return pessoa;
    }

    public Pessoa processarDados(Pessoa pessoa){


        pessoa.setNota(novaNota.getText());
        pessoa.setCelular(novoCelular.getText());
        pessoa.setSobrenome(novoSobrenome.getText());
        pessoa.setNome(novoNome.getText());

        if(pessoa.getNome().isEmpty()){
            pessoa.setNome("Cachorro");
        }

        if(pessoa.getSobrenome().isEmpty()){
            pessoa.setSobrenome("Gato");
        }
        if(pessoa.getCelular().isEmpty()){
            pessoa.setCelular("xxxx-xxxx");
        }
        if(pessoa.getNota().trim().isEmpty()){
            pessoa.setNota("------");
        }



        return pessoa;
    }

    public void modificarDados(Pessoa pessoa){
        novoNome.setText(pessoa.getNome());
        novoSobrenome.setText(pessoa.getSobrenome());
        novoCelular.setText(pessoa.getCelular());
        novaNota.setText(pessoa.getNota());
    }
}
