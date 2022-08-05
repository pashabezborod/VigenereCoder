package github.vigenerecoder.model;

import java.util.ArrayList;

public interface DataBase extends AutoCloseable {

    void connect(String sqlPath) throws MyCriticalException;
    ArrayList<String> readAllNames() throws MyCriticalException;

    String readPassword(String name) throws MyCriticalException;

    void addNewPass(String name, String pass) throws MyCriticalException;

    void deletePass(String name) throws MyCriticalException;

    void updatePass(String name, String newPass) throws MyCriticalException;

    void beginTransaction() throws MyCriticalException;

    void endTransaction() throws MyCriticalException;

    void failedTransaction();
}
