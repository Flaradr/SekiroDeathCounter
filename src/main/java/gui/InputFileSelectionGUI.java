package gui;

import controller.FileReaderController;
import domain.FromSoftwareGames;
import domain.character.FromSoftwareCharacter;
import domain.file.SaveFileInformation;
import gui.dialog.DialogType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static domain.file.SaveFileInformation.CHOSEN_GAME_FIELD_NAME;
import static domain.file.SaveFileInformation.SAVE_FILE_PATH_FIELD_NAME;
import static gui.dialog.Dialog.displayMessage;

public class InputFileSelectionGUI {
    private final static String INPUT_FILE_NOT_LOADED = "Pas de fichier chargé";
    private static final String DEFAULT_SAVE_PARENT_FOLDER = "APPDATA";
    public static final String INPUT_FILE_BORDER_TITLE = "Fichier de sauvegarde";
    public static final String CHOOSE_SAVE_FILE = "Choisir un fichier de sauvegarde";
    public static final String LOADED_FILE_COLON = "Fichier chargé : ";
    public static final String SELECTED_FILE_IS_NOT_A_SAVE_FILE = "Le fichier choisi n'est pas un fichier de sauvegarde." +
            "Veuillez choisir un autre fichier";

    private static Logger logger = LogManager.getLogger(InputFileSelectionGUI.class);

    private JPanel inputFileSelectionPanel;
    private JButton uploadButton;
    private final JLabel chosenGameLabel;
    private JComboBox saveSelection;

    private final SaveFileInformation saveFileInformation;

    public InputFileSelectionGUI(SaveFileInformation saveFileInformation) {
        this.saveFileInformation = saveFileInformation;
        saveFileInformation.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(CHOSEN_GAME_FIELD_NAME) || evt.getPropertyName().equals(SAVE_FILE_PATH_FIELD_NAME)) {
                    if (saveSelection.getSelectedIndex() == -1) {
                        updateCharacterInformationWithId(0);
                    } else {
                        updateCharacterInformationWithId(saveSelection.getSelectedIndex());
                    }
                }
            }
        });


        chosenGameLabel = new JLabel(INPUT_FILE_NOT_LOADED);
        initComponents();
    }

    public void initComponents() {
        inputFileSelectionPanel = new JPanel();
        inputFileSelectionPanel.setLayout(new GridBagLayout());
        inputFileSelectionPanel.setBorder(BorderFactory.createTitledBorder(INPUT_FILE_BORDER_TITLE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 10, 0, 0);

        uploadButton = new JButton(CHOOSE_SAVE_FILE);
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(uploadButton) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                resetComboBox();
                if (!isASaveFile(selectedFile.getAbsolutePath())) {
                    displayMessage(DialogType.WARNING, SELECTED_FILE_IS_NOT_A_SAVE_FILE);
                } else {
                    this.saveFileInformation.setSaveFilePath(Path.of(selectedFile.getAbsolutePath()));
                    chosenGameLabel.setText(LOADED_FILE_COLON + saveFileInformation.getSaveFilePath().getFileName().toString());
                    displayComboBox();
                }

            }
        });
        chosenGameLabel.setHorizontalAlignment(JLabel.CENTER);
        saveSelection = new JComboBox();
        saveSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saveSelection.getSelectedIndex() != -1) {
                    saveFileInformation.setSlotIndex(saveSelection.getSelectedIndex());
                    updateCharacterInformationWithId(saveSelection.getSelectedIndex());
                }
            }
        });
        saveSelection.setVisible(false);
        inputFileSelectionPanel.add(uploadButton, gbc);
        gbc.gridx++;
        inputFileSelectionPanel.add(saveSelection, gbc);
        gbc.gridx--;
        gbc.gridy++;
        inputFileSelectionPanel.add(chosenGameLabel, gbc);
    }

    private void displayComboBox() {
        if (this.saveFileInformation.getChosenGame() == FromSoftwareGames.ELDEN_RING) {
            getAllCharactersName();
            saveSelection.setVisible(true);
        }
    }

    private void resetComboBox() {
        saveSelection.setVisible(false);
        saveSelection.removeAllItems();
    }

    public JPanel getInputFileSelectionPanel() {
        return this.inputFileSelectionPanel;
    }

    public boolean isASaveFile(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .map(s -> (s.equals("sl2")))
                .orElse(false);
    }

    public void updateCharacterInformationWithId(int slotIndex) {
        if (null == this.saveFileInformation.getChosenGame() ||
                (null == this.saveFileInformation.getSaveFilePath())) {
            return;
        }

        FileReaderController fileReaderController = new FileReaderController(saveFileInformation.getChosenGame(), saveFileInformation.getSaveFilePath());
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.getCharacterById(slotIndex);
            saveFileInformation.setCharacter(fromSoftwareCharacter);
        } catch (NullPointerException exception) {
            logger.error("Error while setting the spinner to 0", exception);
        }
    }

    public void getAllCharactersName() {
        if (null == this.saveFileInformation.getChosenGame() ||
                (null == this.saveFileInformation.getSaveFilePath())) {
            return;
        }

        FileReaderController fileReaderController = new FileReaderController(saveFileInformation.getChosenGame(), saveFileInformation.getSaveFilePath());
        try {
            List<String> names = fileReaderController.getAllCharactersNames();
            names.forEach(value -> saveSelection.addItem(value));
            saveSelection.setSelectedIndex(0);
        } catch (NullPointerException exception) {
            logger.error("Error while setting the spinner to 0", exception);
        }
    }
}
