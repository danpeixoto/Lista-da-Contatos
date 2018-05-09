package programa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mariuszgromada.math.mxparser.Function;

public class Main extends Application{


    Function i ;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/janelaPrincipal.fxml"));
        primaryStage.setTitle("Lista de Contatos");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }





}
