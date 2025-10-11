package deathcounter.gui;

import deathcounter.domain.file.SaveFileInformation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameInformationGUI {
    public static final String SAVE_FILE_INFORMATIONS = "Informations sur la sauvegarde";
    public static final String No_INFORMATION_AVAILABLE = "Aucune information disponible";

    private final SaveFileInformation saveFileInformation;
    private JPanel informationPanel;
    private final JLabel gameInformationLabel;

    public GameInformationGUI(SaveFileInformation mySaveFileInformation) {
        gameInformationLabel = new JLabel(No_INFORMATION_AVAILABLE);
        saveFileInformation = mySaveFileInformation;
        saveFileInformation.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateInformations();
            }
        });
        initComponents();
    }

    public void initComponents() {
        informationPanel = new JPanel();
        gameInformationLabel.setVisible(true);
        Border informationBorder = BorderFactory.createTitledBorder(SAVE_FILE_INFORMATIONS);
        informationPanel.setLayout(new BorderLayout());
        informationPanel.setBorder(informationBorder);
        informationPanel.add(gameInformationLabel, BorderLayout.NORTH);
    }

    public JPanel getInformationPanel() {
        return this.informationPanel;
    }

    protected void updateInformations() {
        if (null != saveFileInformation && null != saveFileInformation.getCharacter()) {
            gameInformationLabel.setText(saveFileInformation.getCharacter().toHtmlString());
        }
    }
}
