import connector.Connector;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String argsDescription = "\n-G to run with SWING GUI\n-C to run in console mode";

    public static void main(String... args) {
        String sqlPath = System.getProperty("user.home") + File.separator + ".s3db";
        if (args.length == 0) runSwing(sqlPath);
        switch (args[0]) {
            case "-G" -> runSwing(sqlPath);
            case "-C" -> runConsole(sqlPath, false);
            case "-D" -> runConsole(sqlPath, true);
            default -> errExit("Wrong argument. Need only one argument:" + argsDescription);
        }
    }

    private static void runSwing(String sqlPath) {
        new Connector(sqlPath, "SWING");
    }

    private static void runConsole(String sqlPath, boolean isDocker) {
        if (isDocker) {
            sqlPath = "/app/.s3db";
            if (Files.notExists(Path.of(sqlPath))) errExit("""
                    Can't find password DB.
                    Use following docker command:
                    docker run -it -v path_to_DB.s3db:/app/.s3db pashabezborod/vigenereCoder""");
        }
        new Connector(sqlPath, "CONSOLE");
    }

    public static void errExit(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
