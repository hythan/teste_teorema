package model.dao;

import model.Usuario;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends ConexaoSQLite{



    public UsuarioDAO(){
        open();
        try {
            PreparedStatement statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS Usuario ("+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "nome VARCHAR (50) NOT NULL," +
                    "genero CHAR (1)," +
                    "data_nascimento DATE," +
                    "usuario VARCHAR(30)," +
                    "senha VARCHAR(30));");

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void criaUsuario(Usuario usuario){
        open();
        try {
            PreparedStatement statement = con.prepareStatement("INSERT INTO " +
                    "Usuario VALUES(?,?,?,?,?,?);");



            statement.setString(2,usuario.getNome());
            statement.setString(3,usuario.getGenero());
            statement.setDate(4, usuario.getData());
            statement.setString(5, usuario.getUsuario());
            statement.setString(6, usuario.getSenha());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close();
        }
    }

    public void atualizaUsuario(Usuario usuario){
        open();
        try {
            PreparedStatement statement = con.prepareStatement("UPDATE  Usuario SET " +
                    "nome = ?," +
                    "genero = ?," +
                    "data_nascimento = ?," +
                    "usuario = ?," +
                    "senha = ?" +
                    "WHERE id = ?");

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getGenero());
            statement.setDate(3,  usuario.getData());
            statement.setString(4,usuario.getUsuario());
            statement.setString(5,usuario.getSenha());
            statement.setInt(6, usuario.get_id());


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            close();
        }
    }

    public Usuario pesquisarId(int id){
        Usuario usuario = null;
        open();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Usuario " +
                    "WHERE id = ?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();


            if(resultSet.next()){
                Usuario usuarioAux = new Usuario(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6));

                usuario = usuarioAux;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
        return  usuario;
    }
    public List<Usuario> pesquisarNome(String nome) {
        ArrayList<Usuario> usuarios = new ArrayList<>();


        open();

        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Usuario " +
                    "WHERE nome LIKE  ? ;");
            statement.setString(1,'%'+nome+'%');

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Usuario usuario = new Usuario(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );

                usuarios.add(usuario);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close();
        }

        return usuarios;
    }


    public List<Usuario> todos() {
        ArrayList<Usuario> resultado = new ArrayList<>();

        open();

        try {
            PreparedStatement statement =  con.prepareStatement("SELECT * FROM Usuario ORDER BY nome;");

            ResultSet resultSet =  statement.executeQuery();

            while (resultSet.next()){

                Usuario usuario = new Usuario(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6));

                resultado.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }


        return resultado;
    }

    public void deletar(Usuario usuario){
        con = open();

        try {
            PreparedStatement statement =  con.prepareStatement("DELETE FROM Usuario WHERE id =?;");

            statement.setInt(1,usuario.get_id());



            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public Integer idUsuarioLogado(String usuario){
        Integer idUsuario = null;
        open();

        try {
            PreparedStatement statement = con.prepareStatement("SELECT id FROM Usuario " +
                    "WHERE usuario = ?");
            statement.setString(1,usuario);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                idUsuario = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close();
        }

        return idUsuario;

    }


}
