package gui;

import domain.FromSoftwareGames;
import domain.file.FileInformation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameSelectionGUI {
    private JPanel gameSelectionPanel;
    private FromSoftwareGames chosenGame;


    public GameSelectionGUI(FileInformation myFileInformation) {
        initComponents(myFileInformation);
    }

    public void initComponents(FileInformation myFileInformation) {
        gameSelectionPanel = new JPanel(new GridLayout(0, 1));
        Border gameSelectionBorder = BorderFactory.createTitledBorder("Liste des jeux");
        gameSelectionPanel.setBorder(gameSelectionBorder);
        final ButtonGroup gameSelectionButtons = new ButtonGroup();

        for (FromSoftwareGames game : FromSoftwareGames.values()) {
            AbstractButton jButton = new JRadioButton(game.getFullName());
            jButton.addActionListener(actionListener -> {
                chosenGame = game;
                myFileInformation.setChosenGame(game);
            });
            gameSelectionPanel.add(jButton);
            gameSelectionButtons.add(jButton);
        }
    }

    public JPanel getGameSelectionPanel() {
        return gameSelectionPanel;
    }
}
