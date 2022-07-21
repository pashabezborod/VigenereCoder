package view;

import connector.Connector;

import java.util.Scanner;

public class ConsoleUserView implements UserView {
    Connector connector;

    public ConsoleUserView(Connector connector) {
        this.connector = connector;
    }

    private void mainLoop() {
        print("Welcome to the VigenereCoder!");
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

    private void show() {
        for (String temp : connector.getAllNames()) print("- " + temp);
    }

    private void get() {
        String name = callInputMessage("Enter a password name:", null);
        if (!connector.getAllNames().contains(name)) {
            print("No such a password");
            return;
        }
        print("\nYour password:\n\n" + connector.getPassword(name));
    }

    private void create() {
        String name = callInputMessage("Enter new password name:", null);
        String pass = callInputMessage("Enter new password:", null);
        connector.addNewPassword(name, pass);
    }

    private void update() {
        String name = callInputMessage("Enter a password name to change:", null);
        if (!connector.getAllNames().contains(name)) {
            callInfoMessage("No such a password");
            return;
        }
        String newPass = callInputMessage("Enter a new password:", null);
        connector.changePassword(name, newPass);
    }

    private void delete() {
        String name = callInputMessage("Enter a password to delete:", null);
        if (!connector.getAllNames().contains(name)) {
            callInfoMessage("No such a password");
            return;
        }
        connector.deletePassword(name);
    }

    private void change() {
        String crypt = callInputMessage("Enter new crypt:", null);
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

    @Override
    public String callInputMessage(String message, String title) {
        print(message);
        return readString();
    }

    @Override
    public void initialize() {
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
