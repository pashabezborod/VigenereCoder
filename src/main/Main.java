package main;

import main.connector.Connector;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String path = null;
        try {
            path = Path.of(Main.class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
                            .getPath())
                    .getParent()
                    .toString() + File.separator + "coderData.s3db";
        } catch (URISyntaxException e) {
            System.out.println("Unknown OS!");
            System.exit(1);
        }
        new Connector(path);
    }
}
