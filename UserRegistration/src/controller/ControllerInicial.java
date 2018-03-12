package controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Usuario;

import java.util.ArrayList;
import java.util.Optional;

public class ControllerInicial implements Telas {

    @FXML
    private Button btLogin;

    @FXML
    private Button btInfo;

    @FXML
    private PasswordField tfSenha;

    @FXML
    private Label lbSenha;

    @FXML
    private Button btPesquisar;

    @FXML
    private Button btDeletar;

    @FXML
    private Label lbUsuarioLogado;

    @FXML
    private ComboBox cbUsuarios;

    @FXML
    private Label lbNomeUsuario;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private ListView<Usuario> listView;

    @FXML
    private Button btEditar;

    @FXML
    private Label lbUsuario;



    @FXML
    private Button btLogout;

    @FXML
    private Button btNovo;

    private Usuario usuarioLogado = null;


    @FXML
    public void initialize(){
        MudarTelas.addNaTrocaDeTelaListiner(new MudarTelas.trocaDeTela() {
            @Override
            public void trocaDeTela(String novaTela, Object dadosTela) {

                if(dadosTela != null){
                    atualizaUsuarios();
                    atualizaLista();
                    tfPesquisar.clear();
                }

            }
        });
        atualizaUsuarios();
        atualizaLista();

    }


    @FXML
    protected void btNovoAction(ActionEvent e){
        MudarTelas.trocarTela(Telas.TELA_CADASTRO);
    }

    @FXML
    protected void btEditarAction(ActionEvent e){
        MudarTelas.trocarTela(Telas.TELA_CADASTRO,usuarioLogado);
    }

    @FXML
    protected void btDeletarAction(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletar");
        alert.setHeaderText("Deletar usuário logado.");
        alert.setContentText("Deseja mesmo deletar: \n"+
        usuarioLogado.getNome()+"\n"
        +"Usuário: "+usuarioLogado.getUsuario()+"\n"
        +"Data nascimento: "+ usuarioLogado.getData().toLocalDate());

        Optional resultado = alert.showAndWait();
        if(resultado.get() == ButtonType.OK){
            usuarioLogado.delete();
            mudaStatus(false);
            atualizaUsuarios();
            atualizaLista();
            tfSenha.clear();

        }
    }

    @FXML
    protected void btInfoAction(ActionEvent e){
        if(listView.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro!");
            alert.setContentText("Selecione um contato para ver informações.");
            alert.showAndWait();
        }

        else {
            Usuario usuario = Usuario.pesquisar(listView.getSelectionModel().getSelectedItem().get_id());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setContentText("Nome: " + usuario.getNome() + "\n" +
                    "Genero: " + usuario.getGenero() + "\n" +
                    "Data nascimento: " + usuario.getData().toLocalDate() + "\n" +
                    "Usuario: " + usuario.getUsuario());
            alert.showAndWait();

        }
    }

    @FXML
    protected void btPesquisarAction(ActionEvent e){

        listView.getItems().clear();

        for (Usuario u:Usuario.pesquisarNome(tfPesquisar.getText())) {
            listView.getItems().add(u);
        }


    }

    @FXML
    protected void btLoginAction(ActionEvent e){
       if(cbUsuarios.getSelectionModel().isEmpty()){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Info");
           alert.setHeaderText("Login Falhou!");
           alert.setContentText("Verifique se selecionou um usuário.");
           alert.showAndWait();
       }else if(validaLogin(cbUsuarios.getSelectionModel().getSelectedItem().toString())){
           mudaStatus(true);
       }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Login Falhou!");
            alert.setContentText("Verifique sua senha.");
            alert.showAndWait();
        }

    }



    @FXML
    protected void btLogoutAction(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("Logout");
        alert.setContentText("Deseja mesmo sair?");

        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.get() == ButtonType.OK){
            cbUsuarios.getSelectionModel().clearSelection();
            tfSenha.clear();
            mudaStatus(false);
        }
    }


    private void mudaStatus(Boolean boleano){
        if(boleano == true){

            tfSenha.setVisible(false);
            tfSenha.setDisable(true);
            lbSenha.setVisible(false);
            lbSenha.setDisable(true);
            lbNomeUsuario.setVisible(true);
            btLogin.setDisable(true);
            btLogin.setVisible(false);
            btLogout.setDisable(false);
            btLogout.setVisible(true);
            lbNomeUsuario.setText(cbUsuarios.getSelectionModel().getSelectedItem().toString());
            lbUsuarioLogado.setVisible(true);
            cbUsuarios.setVisible(false);
            btNovo.setDisable(true);
            btEditar.setDisable(false);
            btDeletar.setDisable(false);
            lbUsuario.setVisible(false);


        }else{

            tfSenha.setVisible(true);
            tfSenha.setDisable(false);
            lbSenha.setVisible(true);
            lbSenha.setDisable(false);
            lbNomeUsuario.setVisible(false);
            btLogin.setDisable(false);
            btLogin.setVisible(true);
            btLogout.setDisable(true);
            btLogout.setVisible(false);
            lbUsuarioLogado.setVisible(false);
            cbUsuarios.setVisible(true);
            btNovo.setDisable(false);
            btEditar.setDisable(true);
            btDeletar.setDisable(true);
            lbUsuario.setVisible(true);

        }
    }


    private void atualizaUsuarios(){
        cbUsuarios.getItems().clear();
        for(Usuario u:Usuario.todos()){
            cbUsuarios.getItems().add(u.getUsuario().toString());
        }
    }

    private void atualizaLista(){
        listView.getItems().clear();

        for (Usuario u:Usuario.todos()) {
            listView.getItems().add(u);
        }
    }

    private Boolean validaLogin(String text){

       if(Usuario.pesquisar(Usuario.senhaLogin(text)).getSenha().equals(tfSenha.getText())){
            usuarioLogado = Usuario.pesquisar(Usuario.senhaLogin(text));
            return true;
        }else
            usuarioLogado = null;

        return false;
    }


}
