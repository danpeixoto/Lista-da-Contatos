package programa.controladores;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import programa.Pessoa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorPrincipal implements Initializable {

    /*Variaveis Globais*/
    @FXML
    private TableView tabela;
    @FXML
    private TableColumn<Pessoa , String> nome;
    @FXML
    private TableColumn<Pessoa , String> sobrenome;
    @FXML
    private TableColumn<Pessoa , String> celular;
    @FXML
    private TableColumn<Pessoa , String> nota;
    @FXML
    private BorderPane janelaPrincipal;


    private ObservableList<Pessoa> listaPessoas;
    private static String arquivo = "pessoas.txt";
  /*-----------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            carregarPessoas();
        } catch (IOException e) {
            e.printStackTrace();
        }

        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        sobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        celular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        nota.setCellValueFactory(new PropertyValueFactory<>("nota"));


        tabela.setItems(listaPessoas);
        tabela.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabela.getSelectionModel().selectFirst();


    }
    /*----------------------------------------*/


    public void lidarComNovaPessoa(){
        Dialog<ButtonType> dialogo = new Dialog<>();
        dialogo.initOwner(janelaPrincipal.getScene().getWindow());
        dialogo.setTitle("Nova Pessoa");

        FXMLLoader fl = new FXMLLoader();
        fl.setLocation(getClass().getResource("/programa/fxml/informacaoPessoa.fxml"));

        try {
            dialogo.getDialogPane().setContent(fl.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialogo.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialogo.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> opcao = dialogo.showAndWait();

        if(opcao.isPresent() && opcao.get()== ButtonType.OK){
            InformacaoPessoa controlador = fl.getController();
            Pessoa novaPessoa = controlador.processarDados();
            listaPessoas.add(novaPessoa);
            tabela.getSelectionModel().select(novaPessoa);
        }

    }

    public void lidarComTecla(KeyEvent keyEvent) {

        if(keyEvent.getCode() == KeyCode.DELETE && !tabela.getSelectionModel().isEmpty()) {
            Pessoa pessoa = (Pessoa) tabela.getSelectionModel().getSelectedItem();
            deletarPessoa(pessoa);
        }
    }

    public void deletarPessoa(Pessoa pessoa){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Deletar Pessoa");
        alerta.setHeaderText("Você deseja deletar : "+pessoa.getNome());
        alerta.setContentText("Aperte Ok para confirmar e Cancel para cancelar.");

        Optional<ButtonType> opcao = alerta.showAndWait();

        if(opcao.isPresent() && opcao.get() == ButtonType.OK){
            listaPessoas.remove(pessoa);
        }

    }

    public void modificarPessoa(Pessoa pessoa){
       Dialog<ButtonType> dialogo = new Dialog();
       dialogo.initOwner(janelaPrincipal.getScene().getWindow());
       dialogo.setTitle("Alterar Pessoa");

       FXMLLoader fxmlLoader = new FXMLLoader();

       fxmlLoader.setLocation(getClass().getResource("/programa/fxml/informacaoPessoa.fxml"));

       try{
           dialogo.getDialogPane().setContent(fxmlLoader.load());
       }catch (IOException e){
           e.printStackTrace();
       }


        dialogo.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialogo.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        InformacaoPessoa informacaoPessoa = fxmlLoader.getController();
        informacaoPessoa.modificarDados(pessoa);//Carrega os dados da pessoa para os textField , unica coisa diferente
                                                //do metodo lidarComNovaPessoa()

        Optional<ButtonType> opcao = dialogo.showAndWait();

        if(opcao.isPresent() && opcao.get() == ButtonType.OK){

            informacaoPessoa.processarDados(pessoa);
        }
    }
/*Lida com clique do botão direito feito pelo usuario criando um ContextMenu onde foi clicado
* Se a tabela esta vazia só aparecerá o menu de adicionar
* senão aparecera o menu de adicionar deletar e modificar
* */
    public void lidarComClique(MouseEvent mouseEvent) {
        ContextMenu contextMenu = new ContextMenu();

        if(mouseEvent.getButton() == MouseButton.SECONDARY  && !tabela.getSelectionModel().isEmpty()) {
            Pessoa pessoa = (Pessoa) tabela.getSelectionModel().getSelectedItem();
            MenuItem deletarMenu = new MenuItem("Deletar");
            deletarMenu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    deletarPessoa(pessoa);
                }
            });

            MenuItem modificarMenu = new MenuItem("Modificar");
            modificarMenu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    modificarPessoa(pessoa);
                }
            });

            contextMenu.getItems().add(deletarMenu);
            contextMenu.getItems().add(modificarMenu);

        }

        MenuItem adicionarMenu = new MenuItem("Adicionar");
        adicionarMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lidarComNovaPessoa();
            }
        });
        contextMenu.getItems().add(adicionarMenu);

        tabela.setContextMenu(contextMenu);
    }




    /*Carregar e salvar dados*/
    public void carregarPessoas() throws IOException{
        listaPessoas = FXCollections.observableArrayList();
        Path path = Paths.get(arquivo);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try{
            while (((input = br.readLine()) != null)){
                String[] itemPieces = input.split("\t");
                System.out.println(itemPieces.length);
                String nome = itemPieces[0];
                String sobrenome = itemPieces[1];
                String celular = itemPieces[2];
                String nota = itemPieces[3];



                Pessoa pessoa = new Pessoa(nome , sobrenome , celular , nota);
                listaPessoas.add(pessoa);
            }
        }finally {
            if(br != null){
                br.close();
            }
        }
    }
    /*salvar dados*/
    public  void salvarPessoas() throws IOException {
        Path path =  Paths.get(arquivo);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try{
            Iterator<Pessoa> iterator = listaPessoas.iterator();
            while (iterator.hasNext()){
                Pessoa pessoa = iterator.next();
                bw.write(String.format("%s\t%s\t%s\t%s\t",
                        pessoa.getNome(),
                        pessoa.getSobrenome(),
                        pessoa.getCelular(),
                        pessoa.getNota()));

                bw.newLine();
            }
        }finally {
            if(bw != null){
                bw.close();
            }
        }
        Platform.exit();
    }
}
