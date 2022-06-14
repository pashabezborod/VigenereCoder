package test.model;

import main.model.DataBaseSQLite;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DataBaseSQLiteTest {

    private DataBaseSQLite dataBase;
    private final Map<String, String> testData = new HashMap<>();
    Random random = new Random();

    @Before
    public void initialize() throws Exception {
        dataBase = new DataBaseSQLite("/home/paul/coderTest.s3db");
        dataBase.clear();

        for(int i = 0; i < 100; i++) {
            StringBuilder name = new StringBuilder(), pass = new StringBuilder();
            for(int j = 0; j < random.nextInt(20) + 1; j++) {
                name.append(getRandomChar());
                pass.append(getRandomChar());
            }
            testData.put(name.append(i).toString(), pass.toString());
        }
        for(Map.Entry<String, String> entry : testData.entrySet())
            dataBase.addNewPass(entry.getKey(), entry.getValue());
    }

    @Test
    public void readAllNames() throws Exception {
        for (String name : dataBase.readAllNames()) {
            assert testData.containsKey(name) : "ReadAllNames failed!";
        }
    }
    @Test
    public void deletePassword() throws Exception {
        for (Map.Entry<String, String> entry : testData.entrySet()) {
            dataBase.deletePass(entry.getKey());
            try {
                assert !entry.getValue().equals(dataBase.readPassword(entry.getKey()));
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void updatePass() throws Exception {
        for (Map.Entry<String, String> entry : testData.entrySet()) {
            StringBuilder newPass = new StringBuilder();
            for(int i = 0; i < random.nextInt(20) + 1; i++)
                newPass.append(getRandomChar());
            testData.replace(entry.getKey(), newPass.toString());
            dataBase.updatePass(entry.getKey(), newPass.toString());
        }
        readPassword();
    }

    private void readPassword() throws Exception {
        for (Map.Entry<String, String> entry : testData.entrySet()) {
            assert entry.getValue().equals(dataBase.readPassword(entry.getKey())) : "Read password failed!";
        }
    }

    private char getRandomChar() {
        return (char) (random.nextInt(93) + 33);
    }


}