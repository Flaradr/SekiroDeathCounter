import domain.file.SaveFileInformation;
import gui.DeathCounterGUI;

import javax.swing.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Application {

    private static JFrame gui;
    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        setupGlobalExceptionHandling();
        SaveFileInformation mySaveFileInformation = new SaveFileInformation();
        gui = new DeathCounterGUI(mySaveFileInformation);
    }

    public static void setupGlobalExceptionHandling() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> handleException(e));
    }

    private static void handleException(Throwable e) {
        logger.error(e);
    }
}
