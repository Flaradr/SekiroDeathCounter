package gui;

import domain.file.FileInformation;

import javax.swing.*;
import java.awt.*;

public class GameUploadGUI {

    private JPanel gameUploadPanel;

    public GameUploadGUI(FileInformation myFileInformation) {
        initComponents(myFileInformation);
    }

    public void initComponents(FileInformation myFileInformation) {
        gameUploadPanel = new JPanel(new BorderLayout());
        GameSelectionGUI gameSelectionGUI = new GameSelectionGUI(myFileInformation);
        ParameterSelectionGUI parameterSelectionGUI = new ParameterSelectionGUI(myFileInformation);
        gameUploadPanel.add(gameSelectionGUI.getGameSelectionPanel(), BorderLayout.NORTH);
        gameUploadPanel.add(parameterSelectionGUI.getParameterSelectionPanel(), BorderLayout.CENTER);

    }


    public JPanel getGameUploadPanel() {
        return gameUploadPanel;
    }

}
