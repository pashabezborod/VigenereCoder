package main;

import main.connector.Connector;

import java.net.URISyntaxException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String path = null;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            path = Path.of(Main.class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
                            .getPath())
                    .getParent()
                    .toString();
            switch (os) {
                case "windows":
                    path += "\\coderData.s3db";
                    break;
                case "linux":
                case "mac":
                    path += "/coderData.s3db";
                    break;
                default:
                    throw new URISyntaxException("Unknown", "OS");
            }
        } catch (URISyntaxException e) {
            System.out.println("Unknown OS!");
            System.exit(1);
        }
        new Connector(path);
    }
}
