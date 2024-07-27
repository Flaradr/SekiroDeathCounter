package gui;

import domain.file.SaveFileInformation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameInformationGUI {
    JPanel informationPanel;

    public GameInformationGUI(SaveFileInformation mySaveFileInformation) {
        initComponents(mySaveFileInformation);
    }

    public void initComponents(SaveFileInformation mySaveFileInformation) {
        informationPanel = new JPanel();
        final JLabel gameInformationLabel = new JLabel();
        gameInformationLabel.setVisible(true);
        Border informationBorder = BorderFactory.createTitledBorder("Informations sur la sauvegarde");
        informationPanel.setLayout(new BorderLayout());
        informationPanel.setBorder(informationBorder);
        informationPanel.add(gameInformationLabel, BorderLayout.NORTH);

        gameInformationLabel.setText(mySaveFileInformation.getStringifiedData());
    }

    public JPanel getInformationPanel() {
        return this.informationPanel;
    }
}
