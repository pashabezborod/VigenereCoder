package main.model;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseSQLite implements DataBase {
    private Connection connection;

    public DataBaseSQLite(String sqlPath) throws SQLException, ClassNotFoundException {
        connect(sqlPath);
        initializeDB();
    }

    private void connect(String sqlPath) throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Can't find JDBC driver: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ClassNotFoundException("Wrong JDBC driver: " + e.getMessage(), e);
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + sqlPath);
    }

    private void initializeDB() throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement("CREATE TABLE IF NOT EXISTS nameAndPass" +
                             "(name TEXT NOT NULL UNIQUE PRIMARY KEY, pass TEXT NOT NULL);")) {

            statement.execute();
        }
    }

    public ArrayList<String> readAllNames() throws SQLException {
        try (ResultSet resultSet = connection.prepareStatement("SELECT name FROM nameAndPass;").executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
            return list;
        }
    }

    public String readPassword(String name) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT pass FROM nameAndPass WHERE name=?;")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String result = resultSet.getString("pass");
            resultSet.close();
            return result;
        }
    }

    public void addNewPass(String name, String pass) throws SQLException {
        try (PreparedStatement statement =
                connection.prepareStatement("INSERT INTO nameAndPass VALUES (?,?);")) {
            statement.setString(1, name);
            statement.setString(2, pass);
            statement.execute();
        }
    }

    public void deletePass(String name) throws SQLException {
        try (PreparedStatement statement =
                connection.prepareStatement("DELETE FROM nameAndPass WHERE name=?;")) {
            statement.setString(1, name);
            statement.execute();
        }
    }

    public void updatePass(String name, String newPass) throws SQLException {
        try (PreparedStatement statement =
                connection.prepareStatement("UPDATE nameAndPass SET pass=? WHERE name=?;")) {
            statement.setString(1, newPass);
            statement.setString(2, name);
            statement.execute();
        }
    }

    public void clear() throws SQLException {
        try (PreparedStatement statement =
                connection.prepareStatement("DELETE FROM nameAndPass;")) {
            statement.execute();
        }
    }  //TODO make this method private

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
