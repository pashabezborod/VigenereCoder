package test.model;

import main.connector.Connector;
import main.model.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class ModelSwingTest {

    Model modelSwing;
    TreeMap<String, String> testData = new TreeMap<>();
    Random random = new Random();

    @Before
    public void setUp() {
        /*
        StringBuilder crypt = new StringBuilder();
        for(int i = 0; i < random.nextInt(20) + 1; i ++)
            crypt.append(getRandomChar());
        modelSwing = new Model("/home/paul/coderTest.s3db", new Connector(), crypt.toString());
        modelSwing.clear();

        for(int i = 0; i < 100; i++) {
            StringBuilder name = new StringBuilder(), pass = new StringBuilder();
            for(int j = 0; j < random.nextInt(20) + 1; j++) {
                name.append(getRandomChar());
                pass.append(getRandomChar());
            }
            assert name.length() == pass.length();
            testData.put(name.toString(), pass.toString());
        }

        for(Map.Entry<String, String> entry : testData.entrySet())
            modelSwing.addNewPass(entry.getKey(), entry.getValue());
            */
    }

    @Test
    public void readAllNames() {
        for(String name : modelSwing.readAllNames())
            assert testData.containsKey(name) : "ReadAllNames failed!";
    }

    @Test
    public void changeCrypt() {
        StringBuilder newCrypt = new StringBuilder();
        for(int i = 0; i < random.nextInt(20)+1; i++)
            newCrypt.append(getRandomChar());

        modelSwing.changeCrypt(newCrypt.toString());
        readAllPasswords();
    }

    private void readAllPasswords() {
        for(String name : modelSwing.readAllNames()) {
            System.out.println(name + "     " + modelSwing.readPassword(name) + "     " + testData.get(name));
            assert  modelSwing.readPassword(name).equals(testData.get(name));
        }
    }

    private char getRandomChar() {
        return (char) (random.nextInt(93) + 33);
    }
}