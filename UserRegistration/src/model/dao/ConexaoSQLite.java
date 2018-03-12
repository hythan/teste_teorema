package model.dao;

import java.sql.*;

public class ConexaoSQLite {

    protected Connection con;

    public Connection open() {
        try {
            con =  DriverManager.getConnection("jdbc:sqlite:projeto_database");
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void close(){
        try {
            if(con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
