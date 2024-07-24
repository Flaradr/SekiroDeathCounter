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
        gameUploadPanel = new JPanel(new GridLayout(2, 0));
        GameSelectionGUI gameSelectionGUI = new GameSelectionGUI(myFileInformation);
        FileSelectionGUI fileSelectionGUI = new FileSelectionGUI(myFileInformation);
        gameUploadPanel.add(gameSelectionGUI.getGameSelectionPanel());
        gameUploadPanel.add(fileSelectionGUI.getUploadFilePanel());

    }


    public JPanel getGameUploadPanel() {
        return gameUploadPanel;
    }

}
