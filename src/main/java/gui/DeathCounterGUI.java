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
        setLayout(new GridBagLayout());

        GameUploadGUI gameUploadGUI = new GameUploadGUI(myFileInformation);
        GameInformationGUI gameInformationGUI = new GameInformationGUI(myFileInformation);

        //Add panels to  the frame
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;
        c.ipady = 0;
        add(gameUploadGUI.getGameUploadPanel(), c);


        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 2;
        add(gameInformationGUI.getInformationPanel(), c);
    }
}
