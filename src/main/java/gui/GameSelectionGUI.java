package gui;

import domain.FromSoftwareGames;
import domain.file.SaveFileInformation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameSelectionGUI {
    public static final String GAME_LIST_TITLE = "Liste des jeux";
    private JPanel gameSelectionPanel;
    private FromSoftwareGames chosenGame;


    public GameSelectionGUI(SaveFileInformation mySaveFileInformation) {
        initComponents(mySaveFileInformation);
    }

    public void initComponents(SaveFileInformation mySaveFileInformation) {
        gameSelectionPanel = new JPanel(new GridLayout(0, 1));
        Border gameSelectionBorder = BorderFactory.createTitledBorder(GAME_LIST_TITLE);
        gameSelectionPanel.setBorder(gameSelectionBorder);
        final ButtonGroup gameSelectionButtons = new ButtonGroup();

        for (FromSoftwareGames game : FromSoftwareGames.values()) {
            AbstractButton jButton = new JRadioButton(game.getFullName());
            jButton.addActionListener(actionListener -> {
                chosenGame = game;
                mySaveFileInformation.setChosenGame(game);
            });
            gameSelectionPanel.add(jButton);
            gameSelectionButtons.add(jButton);
        }
    }

    public JPanel getGameSelectionPanel() {
        return gameSelectionPanel;
    }
}
