package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Usuario;
import model.dao.UsuarioDAO;

import java.sql.Date;


public class Main extends Application {
    protected static Stage stage;
    protected static Scene telaInicial;
    protected static Scene telaCadastro;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        Parent caminhoTelaInicial = FXMLLoader.load(getClass().getResource("../view/tela_inicial.fxml"));
        Parent caminhoTelaCadastro = FXMLLoader.load(getClass().getResource("../view/tela_cadastro.fxml"));

        telaInicial = new Scene(caminhoTelaInicial);
        telaCadastro = new Scene(caminhoTelaCadastro);

        stage.setScene(telaInicial);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
