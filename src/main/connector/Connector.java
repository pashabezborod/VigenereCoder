package main.connector;

import main.model.Model;
import main.view.SwingUserView;
import main.view.UserView;

import java.util.ArrayList;

public class Connector {
    private Model model;
    private UserView userView;

    public Connector(String sqlPath) {
        userView = new SwingUserView(this);
        String crypt = userView.callInputMessage("Enter your crypt", "Coder 1.2");
        if(crypt == null || crypt.equals("")) userView.callErrorMessage("Incorrect crypt!", true, new RuntimeException());
        model = new Model(sqlPath,this, crypt);
        userView.initialize();
    }

    public void deletePassword(String name) {
        model.deletePass(name);
        userView.refreshData();
    }

    public void changeCrypt(String newCrypt) {
        model.changeCrypt(newCrypt);
        userView.refreshData();
    }

    public void addNewPassword(String name, String pass) {
        model.addNewPass(name, pass);
        userView.refreshData();
    }

    public String getPassword(String name) {
        return model.readPassword(name);
    }

    public ArrayList<String> getAllNames() {
        return model.readAllNames();
    }

    public void onErrorMessage(String message, boolean isCritical, Exception e) {
        userView.callErrorMessage(message, isCritical, e);
    }

    public void onInfoMessage(String message) {
        userView.callInfoMessage(message);
    }

    public String onInputData(String message, String title) {
        return userView.callInputMessage(message, title);
    }
}
