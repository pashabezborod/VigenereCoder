package github.vigenerecoder.view;

import github.vigenerecoder.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUserView implements UserView {
    Connector connector;

    private void mainLoop() {
        String input = "";
        while (!input.equals("exit")) {
            print("""
                                        
                    *****************************************
                                        
                    Commands to use:
                    - show - show all exists passwords
                    - get - get a password
                    - create - create new password
                    - update - update password
                    - delete - delete
                    - change - change crypt
                    - exit - close app""");
            input = readString();
            print("");
            switch (input) {
                case "show" -> show();
                case "get" -> get();
                case "create" -> create();
                case "update" -> update();
                case "delete" -> delete();
                case "change" -> change();
                case "exit" -> exit();
                default -> print("Unknown command");
            }
        }

    }

    @Override
    public String initializeCrypt() {
        return callInputMessage("Welcome to the Vigenere coder!\nEnter your crypt");
    }

    @Autowired
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    private void show() {
        for (String temp : connector.getAllNames()) print("- " + temp);
    }

    private void get() {
        String name = callInputMessage("Enter a password name:");
        if (!connector.getAllNames().contains(name)) {
            print("No such a password");
            return;
        }
        print("\nYour password:\n\n" + connector.getPassword(name));
    }

    private void create() {
        String name = callInputMessage("Enter new password name:");
        String pass = callInputMessage("Enter new password:");
        connector.addNewPassword(name, pass);
    }

    private void update() {
        String name = callInputMessage("Enter a password name to change:");
        if (!connector.getAllNames().contains(name)) {
            callInfoMessage("No such a password");
            return;
        }
        String newPass = callInputMessage("Enter a new password:");
        connector.changePassword(name, newPass);
    }

    private void delete() {
        String name = callInputMessage("Enter a password to delete:");
        if (!connector.getAllNames().contains(name)) {
            callInfoMessage("No such a password");
            return;
        }
        connector.deletePassword(name);
    }

    private void change() {
        String crypt = callInputMessage("Enter new crypt:");
        connector.changeCrypt(crypt);
    }

    private void exit() {
        print("Good bye!");
        System.exit(0);
    }

    @Override
    public void callErrorMessage(String message) {
        print(message);
        System.exit(1);
    }

    @Override
    public void callInfoMessage(String message) {
        print("\n" + message + "\n");
    }

    public String callInputMessage(String message) {
        print(message);
        return readString();
    }

    @Override
    public void initializeUI() {
        print("Password accepted.\nData Base connected.");
        mainLoop();
    }

    @Override
    public void refreshData() {

    }

    private void print(String string) {
        System.out.println(string);
    }

    private String readString() {
        return new Scanner(System.in).nextLine();
    }
}
