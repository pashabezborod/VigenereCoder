package main.model;

import main.connector.Connector;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class Model {
    private DataBase dataBase;
    private Connector connector;
    private Coding coding;
    private final String DATA_BASE_RUNTIME_ERROR = "DataBase error! Restart the app.";

    public Model(String sqlPath, Connector connector, String crypt) {
        try {
            dataBase = new DataBaseSQLite(sqlPath);
            coding = new VigenereCoding(crypt);
            this.connector = connector;
        } catch (SQLException e) {
            connector.onErrorMessage("DataBase connection error", true, e);
        } catch (ClassNotFoundException e) {
            connector.onErrorMessage(e.getMessage(), true, e);
        }
    }

    public ArrayList<String> readAllNames() {
        try {
            ArrayList<String> names = dataBase.readAllNames();
            names.sort(Comparator.naturalOrder());
            return names;
        } catch (SQLException e) {
            connector.onErrorMessage(DATA_BASE_RUNTIME_ERROR, true, e);
        }
        return null;
    }

    public String readPassword(String name) {
        try {
            return coding.encodeString(dataBase.readPassword(name));
        } catch (SQLException | IOException e) {
            connector.onErrorMessage(DATA_BASE_RUNTIME_ERROR, true, e);
        }
        return null;
    }

    public void addNewPass(String name, String pass) {
        if (checkString(name) || checkString(pass)) {
            connector.onInfoMessage("Wrong input data! English letters and symbols only.");
            return;
        }
        try {
            dataBase.addNewPass(name, coding.codeString(pass));
        } catch (SQLException e) {
            connector.onErrorMessage("Can't add this pass. Try again", false, e);
        }
    }

    public void deletePass(String name) {
        try {
            dataBase.deletePass(name);
        } catch (SQLException e) {
            connector.onErrorMessage(DATA_BASE_RUNTIME_ERROR, true, e);
        }
    }

    public void changeCrypt(String newCrypt) {
        if (newCrypt == null || newCrypt.equals(""))
            connector.onErrorMessage("Crypt is incorrect!", false, new NullPointerException());
        else {
            TreeMap<String, String> data = new TreeMap<>();
            for(String name : readAllNames()) {
                data.put(name, readPassword(name));
            }
            coding.setCrypt(newCrypt);
            for(String name : readAllNames()) {
                updatePass(name, data.get(name));
            }
            connector.onInfoMessage("Crypt changed. All passwords refreshed.");
        }
    }

    private void updatePass(String name, String newPass) {
        try {
            dataBase.updatePass(name, coding.codeString(newPass));
        } catch (SQLException e) {
            connector.onErrorMessage(DATA_BASE_RUNTIME_ERROR, true, e);
        }
    }
    private boolean checkString(String string) {
        if (string.contains(" ") || string.isEmpty()) return true;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) < 33 || string.charAt(i) > 127) return true;
        }
        return false;
    }

    public void clear() {
        try {
            dataBase.clear();
        } catch (SQLException e) {
            connector.onErrorMessage(DATA_BASE_RUNTIME_ERROR, true, e);
        }
    }
}
