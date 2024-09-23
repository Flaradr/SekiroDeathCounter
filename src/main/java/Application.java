import domain.file.SaveFileInformation;
import gui.DeathCounterGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        setupGlobalExceptionHandling();
        SaveFileInformation mySaveFileInformation = new SaveFileInformation();
        new DeathCounterGUI(mySaveFileInformation);
    }

    public static void setupGlobalExceptionHandling() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> handleException(e));
    }

    private static void handleException(Throwable e) {
        logger.error("Runtime exception", e);
    }
}
