package github.vigenerecoder.connector;


import github.vigenerecoder.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import github.vigenerecoder.view.*;

import java.util.ArrayList;

@Component
@Lazy
public class Connector {


    private Model model;
    private UserView userView;

    private final String WRONG_INPUT_DESCRIPTION =
            "Incorrect input data!\nUse digits, English letters and symbols only.";

    public void startApp(String sqlPath) {
        String crypt = userView.initializeCrypt();
        if (checkString(crypt)) userView.callErrorMessage(WRONG_INPUT_DESCRIPTION);
        model.getCoding().setCrypt(crypt);
        try {
            model.getDataBase().connect(sqlPath);
        } catch (MyCriticalException e) {
            onErrorMessage(e);
        }
        userView.initializeUI();
    }

    @Autowired
    public void setModel(Model model) {
        this.model = model;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public void deletePassword(String name) {
        model.deletePass(name);
        userView.refreshData();
    }

    public void changeCrypt(String newCrypt) {
        if (checkString(newCrypt)) {
            userView.callInfoMessage(WRONG_INPUT_DESCRIPTION);
            return;
        }
        model.changeCrypt(newCrypt);
        userView.refreshData();
    }

    public void addNewPassword(String name, String pass) {
        if (checkString(name) || checkString(pass)) {
            userView.callInfoMessage(WRONG_INPUT_DESCRIPTION);
            return;
        }
        model.addNewPass(name, pass);
        userView.refreshData();
    }

    public String getPassword(String name) {
        return model.readPassword(name);
    }

    public ArrayList<String> getAllNames() {
        return model.readAllNames();
    }

    public void changePassword(String passName, String newPass) {
        if (checkString(newPass)) {
            userView.callInfoMessage(WRONG_INPUT_DESCRIPTION);
            return;
        }
        model.updatePass(passName, newPass);
        userView.refreshData();
    }

    public void onErrorMessage(Exception e) {
        userView.callErrorMessage(e.getMessage());
    }

    public void onInfoMessage(String message) {
        userView.callInfoMessage(message);
    }

    private boolean checkString(String string) {
        if (string == null || string.contains(" ") || string.isEmpty()) return true;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) < 33 || string.charAt(i) > 127) return true;
        }
        return false;
    }
}
