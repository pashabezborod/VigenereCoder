package main.model;


import java.sql.*;
import java.util.ArrayList;

public class DataBase implements AutoCloseable {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DataBase(String sqlPath) throws Exception {
        connect(sqlPath);
        initializeDB();
    }

    private void connect(String sqlPath) throws Exception {
        Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
        connection = DriverManager.getConnection("jdbc:sqlite:" + sqlPath);
    }

    private void initializeDB() throws Exception {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS nameAndPass(name TEXT NOT NULL UNIQUE PRIMARY KEY, pass TEXT NOT NULL)");
        resultSet = statement.executeQuery("SELECT * FROM nameAndPass;");
    }

    public ArrayList<String> readAllNames() throws Exception {
        resultSet = statement.executeQuery("SELECT * FROM nameAndPass;");
        ArrayList<String> list = new ArrayList<>();
        while(resultSet.next()) {
            list.add(resultSet.getString("name"));
        }
        resultSet.close();
        return list;
    }

    public String readPassword(String name) throws Exception {
        resultSet = statement.executeQuery("SELECT * FROM nameAndPass;");
        while (resultSet.next()) {
            if(resultSet.getString("name").equals(name)) return resultSet.getString("pass");
        }
        resultSet.close();
        return null; //TODO process exception
    }

    public void addNewPass(String name, String pass) throws SQLException {
        statement.execute(String.format("INSERT INTO nameAndPass VALUES ('%s','%s');", name, pass));
    }

    public void deletePass(String name) throws Exception {
        statement.execute(String.format("DELETE FROM nameAndPass WHERE name='%s';", name));
    }

    public void updatePass(String name, String newPass) throws Exception {
        statement.execute(String.format("UPDATE nameAndPass SET pass='%s' WHERE name='%s';", newPass, name));
    }

    public void clearBase() throws Exception {
        statement.execute("DELETE FROM nameAndPass;");
    }

    public void close() {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
