package model;

import javafx.fxml.FXML;
import model.dao.UsuarioDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class Usuario {

    private Integer _id;
    private String nome;
    private String genero;
    private Date data;
    private String usuario;
    private String senha;


    public Usuario(int _id, String nome, String genero, Date data, String usuario, String senha) {

        this._id = _id;
        this.nome = nome;
        this.genero = genero;
        this.data = data;
        this.usuario = usuario;
        this.senha = senha;
    }


    public Usuario(String nome, String genero, Date data, String usuario, String senha) {
        this.nome = nome;
        this.genero = genero;
        this.data = data;
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    public Date getData() {
        return data;
    }

    @FXML
    public void setData(Date data) {
        this.data = data;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int get_id() {
        return _id;
    }

    @Override
    public String toString() {
        return "Nome: "+nome+"\n"+
                "Data nascimento: "+data.toLocalDate();
    }

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void salvar(){
        if(_id != null && usuarioDAO.pesquisarId(_id) != null){
            usuarioDAO.atualizaUsuario(this);
        }else
            usuarioDAO.criaUsuario(this);
    }

    public void delete(){
        if( usuarioDAO.pesquisarId(_id) != null){
            usuarioDAO.deletar(this);
        }
    }

    public static List<Usuario> pesquisarNome(String nome){
        return usuarioDAO.pesquisarNome(nome);
    }

    public static List<Usuario> todos(){
        return usuarioDAO.todos();
    }

    public static Usuario pesquisar(int pk){
        return usuarioDAO.pesquisarId(pk);
    }

    public static Integer senhaLogin(String usuario){
        return usuarioDAO.idUsuarioLogado(usuario);
    }





}
