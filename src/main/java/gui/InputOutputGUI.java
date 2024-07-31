package gui;

import domain.file.SaveFileInformation;

import javax.swing.*;
import java.awt.*;

public class InputOutputGUI {

    private JPanel gameUploadPanel;

    public InputOutputGUI(SaveFileInformation mySaveFileInformation) {
        initComponents(mySaveFileInformation);
    }

    public void initComponents(SaveFileInformation mySaveFileInformation) {
        gameUploadPanel = new JPanel(new BorderLayout());
        GameSelectionGUI gameSelectionGUI = new GameSelectionGUI(mySaveFileInformation);
        ParameterSelectionGUI parameterSelectionGUI = new ParameterSelectionGUI(mySaveFileInformation);
        gameUploadPanel.add(gameSelectionGUI.getGameSelectionPanel(), BorderLayout.NORTH);
        gameUploadPanel.add(parameterSelectionGUI.getParameterSelectionPanel(), BorderLayout.CENTER);
    }

    public JPanel getGameUploadPanel() {
        return gameUploadPanel;
    }

}
