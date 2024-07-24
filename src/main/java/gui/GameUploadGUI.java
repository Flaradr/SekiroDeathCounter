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
        gameUploadPanel = new JPanel(new GridLayout(2, 2));
        GameSelectionGUI gameSelectionGUI = new GameSelectionGUI(myFileInformation);
        ParameterSelectionGUI parameterSelectionGUI = new ParameterSelectionGUI(myFileInformation);
        gameUploadPanel.add(gameSelectionGUI.getGameSelectionPanel());
        gameUploadPanel.add(parameterSelectionGUI.getParameterSelectionPanel());

    }


    public JPanel getGameUploadPanel() {
        return gameUploadPanel;
    }

}
