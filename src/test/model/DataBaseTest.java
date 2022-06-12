package test.model;

import main.model.DataBase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBaseTest {

    private DataBase dataBase;
    private final Map<String, String> testData = new HashMap<>();

    @Before
    public void initialize() throws Exception {
        dataBase = new DataBase("/home/paul/coderTest.s3db");
        dataBase.clearBase();
        for(int i = 0; i < 10; i++) {
            testData.put("name" + i, "password" + i);
            dataBase.addNewPass("name" + i, "password" + i);
        }
    }

    @Test
    public void mainTest() throws Exception {
        readAllNames();
        readPassword();
        addAndRemoveNewPass();
        updatePass();
    }

    private void readAllNames() throws Exception {
        ArrayList<String> list = dataBase.readAllNames();
        for(String string : list) {
            assert testData.containsKey(string) : "Error reading names";
        }
    }

    private void readPassword() throws Exception {
        for(String name : testData.keySet()) {
            String password = dataBase.readPassword(name);
            assert testData.get(name).equals(password) : "Error reading passwords";
        }
    }

    private void addAndRemoveNewPass() throws Exception {
        for(int i = 0; i < 10; i++) {
            dataBase.addNewPass("newPassName" + i, "newPass" + i);
            dataBase.deletePass("newPassName" + i);
        }
    }

    private void updatePass() throws Exception {
        String newPass = "newTestPass123123";
        for(String string : testData.keySet()) {
            dataBase.updatePass(string, newPass);
        }
        for(String string : testData.keySet()) {
            assert dataBase.readPassword(string).equals(newPass) : "Update pass error";
        }
    }


}