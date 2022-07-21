import connector.Connector;

import java.nio.file.Files;

public class ManualConsoleTest {
    public static void main(String[] args) throws Exception {
        String path = Files.createTempFile("VigenereTest", "Manual").toAbsolutePath().toString();
        new Connector(path, "CONSOLE");
    }
}
