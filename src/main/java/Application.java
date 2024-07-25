import domain.file.FileInformation;
import gui.DeathCounterGUI;

import javax.swing.*;

public class Application {

    private static JFrame gui;

    public static void main(String[] args) {
        setupGlobalExceptionHandling();
        FileInformation myFileInformation = new FileInformation();
        gui = new DeathCounterGUI(myFileInformation);
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
