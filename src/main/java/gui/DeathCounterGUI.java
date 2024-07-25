package gui;

import domain.file.FileInformation;

import javax.swing.*;
import java.awt.*;

//Source : https://www.guru99.com/fr/java-swing-gui.html?utm_campaign=click&utm_medium=referral&utm_source=relatedarticles
public class DeathCounterGUI extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;

    private static final String APPLICATION_TITLE = "FromSoftware Deaths";

    public DeathCounterGUI(FileInformation myFileInformation) {
        SwingUtilities.invokeLater(() -> init(myFileInformation));
    }

    public void init(FileInformation myFileInformation) {
        setTitle(APPLICATION_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);
        setLayout(new GridLayout(0, 1));

        GameUploadGUI gameUploadGUI = new GameUploadGUI(myFileInformation);
        GameInformationGUI gameInformationGUI = new GameInformationGUI(myFileInformation);

        add(gameUploadGUI.getGameUploadPanel());
        add(gameInformationGUI.getInformationPanel());
    }
}
