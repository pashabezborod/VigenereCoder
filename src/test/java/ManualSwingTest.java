import connector.Connector;

import java.nio.file.Files;

public class ManualSwingTest {

    public static void main(String[] args) throws Exception {
        String path = Files.createTempFile("VigenereTest", "Manual").toAbsolutePath().toString();
        new Connector(path, "SWING");
    }
}
