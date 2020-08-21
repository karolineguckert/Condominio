package br.univali.poo.karoline.model;

import br.univali.poo.karoline.view.screen.ConnexionRoute;

import java.sql.*;

public abstract class Connexion {
    private String url;
    private ResultSet resultSet;
    private Statement statement;
    private Connection connection;
    private ConnexionRoute connexionRoute;

    public Connexion(ConnexionRoute connexionRoute) {
        try{
            Class.forName(connexionRoute.getSgbdType());
            this.connexionRoute = connexionRoute;
            this.url = connexionRoute.getGetUrl();
            this.connection = DriverManager.getConnection(url);
            this.statement = connection.createStatement();
        }catch (ClassNotFoundException e){
            System.out.println("Driver não econtrado ou não foi adicionado ao path");
        }catch (SQLException a){
            System.out.println("Não foi possível conectar no banco");
        }
    }

    public void command(ResultSet command) throws SQLException {
        this.resultSet = command;
        action();
        closeConnexion();
    }


    private void closeConnexion() {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String table,String atributes, String object) {
        try {
            this.resultSet = statement.executeQuery(" INSERT INTO \"" + connexionRoute.getNameOfDataBase() + "\"" + ".public." + table + "("+ atributes + ") values " + object);

            action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnexion();
    }

    public void select(String table, String atributes, String nickName){
        try {
            this.resultSet = statement.executeQuery("SELECT " + atributes + " from " + table + " " + nickName);
            action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnexion();
    }

    public void selectWithWhere(String table, String atributes, String nickName, String condition){
        try {
            this.resultSet = statement.executeQuery("SELECT " + atributes + " from " + table + " " + nickName + " where " + condition);
            action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnexion();
    }

    public void selectWithInnerJoin(String table, String atributes, String innerJoin){
        try {
            this.resultSet = statement.executeQuery("SELECT " + atributes + " from " + table  +  " inner join " + innerJoin);
            action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnexion();
    }

    public void selectWithInnerJoinAndWhere(String baseTable, String firstTableWithInner,
                                            String atributes, String condition){
        try {
            this.resultSet = statement.executeQuery(
                    "SELECT " + atributes + " from \"" + connexionRoute.getNameOfDataBase()
                            + "\".public." + baseTable + " inner join \"" + connexionRoute.getNameOfDataBase()
                            + "\".public." + firstTableWithInner  + " where " + condition);
            action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnexion();
    }

    public void selectWithInnerJoinAndWhere(String baseTable,String firstTableWithInner,
                                            String secondTableWithInner, String atributes,String condition){
        try {
            this.resultSet = statement.executeQuery(
                    "SELECT " + atributes + " from \"" + connexionRoute.getNameOfDataBase() + "\".public." + baseTable + " inner join \""  + connexionRoute.getNameOfDataBase() + "\"" +".public." + firstTableWithInner  +
                    " inner join \"" + connexionRoute.getNameOfDataBase() + "\"" + ".public." + secondTableWithInner + " where " + condition);
            action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnexion();
    }

    public void update(String table,String atributes,String condition){
        try {
            this.resultSet = statement.executeQuery("UPDATE " + table + " set " + atributes +  " where " + condition);
            action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnexion();
    }

    public void deleteWithWhere(String table,String condition){
        try {
            this.resultSet = statement.executeQuery(" DELETE from " + table  + " where " + condition);
            action();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnexion();
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public abstract void action() throws SQLException;
}
