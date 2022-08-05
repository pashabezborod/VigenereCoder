import github.vigenerecoder.SpringConfig;
import github.vigenerecoder.connector.Connector;
import github.vigenerecoder.view.ConsoleUserView;
import github.vigenerecoder.view.UserView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Files;

public class ManualConsoleTest {
    public static void main(String[] args) throws Exception {
        String path = Files.createTempFile("VigenereTest", "Manual").toAbsolutePath().toString();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Connector connector = context.getBean("connector", Connector.class);
        UserView userView = context.getBean("consoleUserView", ConsoleUserView.class);
        connector.setUserView(userView);
        connector.startApp(path);
    }
}
