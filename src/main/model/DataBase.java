package main.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DataBase extends AutoCloseable {
    ArrayList<String> readAllNames() throws SQLException;
    String readPassword(String name) throws SQLException, IOException;
    void addNewPass(String name, String pass) throws SQLException;
    void deletePass(String name) throws SQLException;
    void updatePass(String name, String newPass) throws SQLException;
    void clear() throws SQLException;
}
