package github.vigenerecoder.model;

import github.vigenerecoder.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

@Component
public class Model {
    private DataBase dataBase;
    private Connector connector;
    private Coding coding;

    @Autowired
    public void setConnector(Connector connector) {
        this.connector = connector;
    }
    @Autowired
    public void setCoding(Coding coding) {
        this.coding = coding;
    }

    public Coding getCoding() {
        return coding;
    }

    @Autowired
    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public ArrayList<String> readAllNames() {
        try {
            ArrayList<String> names = dataBase.readAllNames();
            names.sort(Comparator.naturalOrder());
            return names;
        } catch (MyCriticalException e) {
            connector.onErrorMessage(e);
        }
        return null;
    }

    public String readPassword(String name) {
        try {
            return coding.encodeString(dataBase.readPassword(name));
        } catch (MyCriticalException e) {
            connector.onErrorMessage(e);
        }
        return null;
    }

    public void addNewPass(String name, String pass) {
        try {
            dataBase.addNewPass(name, coding.codeString(pass));
        } catch (MyCriticalException e) {
            connector.onErrorMessage(e);
        }
    }

    public void deletePass(String name) {
        try {
            dataBase.deletePass(name);
        } catch (MyCriticalException e) {
            connector.onErrorMessage(e);
        }
    }

    public void changeCrypt(String newCrypt) {
        TreeMap<String, String> data = new TreeMap<>();
        for (String name : readAllNames()) {
            data.put(name, readPassword(name));
        }
        coding.setCrypt(newCrypt);
        try {
            dataBase.beginTransaction();
            for (String name : readAllNames())
                updatePass(name, data.get(name));
            dataBase.endTransaction();
        } catch (MyCriticalException e) {
            dataBase.failedTransaction();
            connector.onErrorMessage(e);
        }
        connector.onInfoMessage("Crypt changed. All passwords refreshed.");
    }

    public void updatePass(String name, String newPass) {
        try {
            dataBase.updatePass(name, coding.codeString(newPass));
        } catch (MyCriticalException e) {
            connector.onErrorMessage(e);
        }
    }
}
