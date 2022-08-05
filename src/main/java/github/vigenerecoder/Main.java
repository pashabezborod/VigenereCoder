package github.vigenerecoder;

import github.vigenerecoder.connector.Connector;
import github.vigenerecoder.view.ConsoleUserView;
import github.vigenerecoder.view.SwingUserView;
import github.vigenerecoder.view.UserView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String ARGS_DESCRIPTION = "\n-G to run with SWING GUI\n-C to run in console mode";
    private static final String DOCKER_ERROR_DESCRIPTION = """
            Can't find password DB.
            Use following docker command:
            docker run -it -v path_to_DB.s3db:/app/.s3db pashabezborod/vigenereCoder""";

    public static void main(String... args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        String sqlPath = System.getProperty("user.home") + File.separator + ".s3db";
        UserView userView;
        Connector connector = context.getBean("connector", Connector.class);

        if (args.length == 0 || args[0].equals("-G"))
            userView = context.getBean("swingUserView", SwingUserView.class);

        else {
            userView = context.getBean("consoleUserView", ConsoleUserView.class);
            switch (args[0]) {
                case "-D":
                    sqlPath = "/app/.s3db";
                    if (Files.notExists(Path.of(sqlPath))) errExit(DOCKER_ERROR_DESCRIPTION);
                case "-C": break;
                default : errExit("Wrong argument. Need only one argument:" + ARGS_DESCRIPTION);
            }
        }
        connector.setUserView(userView);
        connector.startApp(sqlPath);
    }

    private static void errExit(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
