package model;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseSQLite implements DataBase {
    private Connection connection;

    public DataBaseSQLite(String sqlPath) throws MyCriticalException {
        connect(sqlPath);
        initializeDB();
    }

    private void connect(String sqlPath) throws MyCriticalException {
        try {
            Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection("jdbc:sqlite:" + sqlPath);
        } catch (Exception e) {
            throw new MyCriticalException("Data Base connection failed!", e);
        }

    }

    private void initializeDB() throws MyCriticalException {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS nameAndPass" + "(name TEXT NOT NULL UNIQUE PRIMARY KEY, pass TEXT NOT NULL);")) {

            statement.execute();
        } catch (SQLException e) {
            throw new MyCriticalException("Data Base initialization error!", e);
        }
    }

    public ArrayList<String> readAllNames() throws MyCriticalException {
        try (ResultSet resultSet = connection.prepareStatement("SELECT name FROM nameAndPass;").executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
            return list;
        } catch (SQLException e) {
            throw new MyCriticalException("Data Base reading error!", e);
        }
    }

    public String readPassword(String name) throws MyCriticalException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT pass FROM nameAndPass WHERE name=?;")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String result = resultSet.getString("pass");
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new MyCriticalException("Data Base reading error!", e);
        }
    }

    public void addNewPass(String name, String pass) throws MyCriticalException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO nameAndPass VALUES (?,?);")) {
            statement.setString(1, name);
            statement.setString(2, pass);
            statement.execute();
        } catch (SQLException e) {
            throw new MyCriticalException("Data base writing error!", e);
        }
    }

    public void deletePass(String name) throws MyCriticalException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM nameAndPass WHERE name=?;")) {
            statement.setString(1, name);
            statement.execute();
        } catch (SQLException e) {
            throw new MyCriticalException("Data base writing error!", e);
        }
    }

    public void updatePass(String name, String newPass) throws MyCriticalException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE nameAndPass SET pass=? WHERE name=?;")) {
            statement.setString(1, newPass);
            statement.setString(2, name);
            statement.execute();
        } catch (SQLException e) {
            throw new MyCriticalException("Data base writing error!", e);
        }
    }

    public void beginTransaction() throws MyCriticalException {
        try (PreparedStatement statement = connection.prepareStatement("BEGIN TRANSACTION;")) {
            statement.execute();
        } catch (SQLException e) {
            throw new MyCriticalException("Data Base connection interrupted!", e);
        }
    }

    public void endTransaction() throws MyCriticalException {
        try (PreparedStatement statement = connection.prepareStatement("END TRANSACTION;")) {
            statement.execute();
        } catch (SQLException e) {
            throw new MyCriticalException("Data Base connection interrupted!", e);
        }
    }

    public void failedTransaction() {
        try (PreparedStatement statement = connection.prepareStatement("ROLLBACK;")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
