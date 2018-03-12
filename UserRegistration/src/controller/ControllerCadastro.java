package controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Usuario;

public class ControllerCadastro implements Telas{

    private Usuario usuario;

    @FXML
    private PasswordField tfSenha;

    @FXML
    private DatePicker dpData;

    @FXML
    private ComboBox cbGenero;

    @FXML
    private TextField tfUsuario;

    @FXML
    private TextField tfNome;

    @FXML
    public void initialize(){
        cbGenero.getItems().add("F");
        cbGenero.getItems().add("M");
        dpData.setEditable(false);


        MudarTelas.addNaTrocaDeTelaListiner(new MudarTelas.trocaDeTela() {
            @Override
            public void trocaDeTela(String novaTela, Object dadosTela) {

                if(dadosTela != null){
                    usuario = (Usuario)dadosTela;

                    tfNome.setText(usuario.getNome());
                    cbGenero.getSelectionModel().select(usuario.getGenero());
                    dpData.getEditor().setText(usuario.getData().toLocalDate().toString());
                    tfUsuario.setText(usuario.getUsuario());
                    tfSenha.setText(usuario.getSenha());
                }else{
                    usuario = null;
                    limpaCampos();
                }

            }
        });

    }

    @FXML
    void btCancelarAction(ActionEvent event) {
        MudarTelas.trocarTela(Telas.TELA_INICIAL);
        limpaCampos();

    }

    @FXML
    void btSalvarAction(ActionEvent event) {
        try{
            if(tfNome.getText().isEmpty())
                throw new RuntimeException("O campo NOME não pode ser vazio!");
            if(cbGenero.getValue() == null)
                throw new RuntimeException("O campo GENERO não pode ser vazio!");
            if(tfUsuario.getText().isEmpty())
                throw new RuntimeException("O campo USUARIO não pode ser vazio!");
            if(tfSenha.getText().isEmpty())
                throw new RuntimeException("O campo SENHA não pode ser vazio!");
            if(dpData.getEditor().getText().isEmpty())
                throw new RuntimeException("O campo DATA não pode ser vazio!");





            if(usuario != null){
                usuario.setNome(tfNome.getText());
                usuario.setGenero(cbGenero.getValue().toString());
                usuario.setData(java.sql.Date.valueOf(dpData.getValue()));
                usuario.setUsuario(tfUsuario.getText());
                usuario.setSenha(tfSenha.getText());

                usuario.salvar();



            }else {
                 usuario = new Usuario(tfNome.getText(),
                        cbGenero.getValue().toString(),
                        java.sql.Date.valueOf(dpData.getValue()),
                        tfUsuario.getText(),
                        tfSenha.getText());

            }

            usuario.salvar();

            MudarTelas.trocarTela(Telas.TELA_INICIAL, usuario);
            limpaCampos();

        }catch (RuntimeException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro!!");
            alert.setHeaderText("Erro ao cadastrar pessoa");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    private void limpaCampos(){
        tfNome.clear();
        tfSenha.clear();
        tfUsuario.clear();
        cbGenero.getSelectionModel().clearSelection();
        dpData.getEditor().clear();

    }


}
