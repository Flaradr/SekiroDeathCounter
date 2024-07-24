package gui;

import domain.file.FileInformation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameInformationGUI {
    JPanel informationPanel;

    public GameInformationGUI(FileInformation myFileInformation) {
        initComponents(myFileInformation);
    }

    public void initComponents(FileInformation myFileInformation) {
        informationPanel = new JPanel();
        final JLabel gameInformationLabel = new JLabel();
        gameInformationLabel.setVisible(true);
        Border informationBorder = BorderFactory.createTitledBorder("Informations sur la sauvegarde");
        informationPanel.setLayout(new BorderLayout());
        informationPanel.setBorder(informationBorder);
        informationPanel.add(gameInformationLabel, BorderLayout.NORTH);

        gameInformationLabel.setText(myFileInformation.getStringifiedData());
    }

    public JPanel getInformationPanel() {
        return this.informationPanel;
    }
}
