import domain.file.SaveFileInformation;
import gui.DeathCounterGUI;

import javax.swing.*;

public class Application {

    private static JFrame gui;

    public static void main(String[] args) {
        setupGlobalExceptionHandling();
        SaveFileInformation mySaveFileInformation = new SaveFileInformation();
        gui = new DeathCounterGUI(mySaveFileInformation);
    }

    public static void setupGlobalExceptionHandling() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                handleException(e);
            }
        });
    }

    private static void handleException(Throwable e) {
        JOptionPane.showMessageDialog(gui, e.getMessage());
    }

}
