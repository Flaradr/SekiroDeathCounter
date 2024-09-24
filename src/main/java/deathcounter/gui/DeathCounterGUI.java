package deathcounter.gui;

import deathcounter.domain.file.SaveFileInformation;

import javax.swing.*;
import java.awt.*;

//Source : https://www.guru99.com/fr/java-swing-gui.html?utm_campaign=click&utm_medium=referral&utm_source=relatedarticles
public class DeathCounterGUI extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;

    private static final String APPLICATION_TITLE = "FromSoftware Deaths";

    public DeathCounterGUI(SaveFileInformation mySaveFileInformation) {
        SwingUtilities.invokeLater(() -> init(mySaveFileInformation));
    }

    public void init(SaveFileInformation mySaveFileInformation) {
        setTitle(APPLICATION_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);
        setResizable(false);
        setLayout(new GridBagLayout());

        InputOutputGUI inputOutputGUI = new InputOutputGUI(mySaveFileInformation);
        GameInformationGUI gameInformationGUI = new GameInformationGUI(mySaveFileInformation);


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridy = 0;
        getContentPane().add(inputOutputGUI.getGameUploadPanel(), constraints);

        constraints.gridy = 1;
        getContentPane().add(gameInformationGUI.getInformationPanel(), constraints);

        centerDialogOnTheScreen();
    }


    private void centerDialogOnTheScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = this.getSize();
        int centerPosX = (screenSize.width - dialogSize.width) / 2;
        int centerPosY = (screenSize.height - dialogSize.height) / 2;
        setLocation(centerPosX, centerPosY);
    }
}
