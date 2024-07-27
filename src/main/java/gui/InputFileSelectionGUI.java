package gui;

import domain.file.SaveFileInformation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class InputFileSelectionGUI {
    private final static String INPUT_FILE_NOT_LOADED = "Pas de fichier chargé";
    private static final String DEFAULT_SAVE_PARENT_FOLDER = "APPDATA";
    public static final String INPUT_FILE_BORDER_TITLE = "Fichier de sauvegarde";
    public static final String CHOOSE_SAVE_FILE = "Choisir un fichier de sauvegarde";
    public static final String LOADED_FILE_COLON = "Fichier chargé : ";

    private JPanel inputFileSelectionPanel;
    private JButton uploadButton;
    private JLabel chosenGameLabel;

    private SaveFileInformation saveFileInformation;

    public InputFileSelectionGUI(SaveFileInformation saveFileInformation) {
        this.saveFileInformation = saveFileInformation;
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
        gbc.insets = new Insets(0,10,0,0);

        uploadButton = new JButton(CHOOSE_SAVE_FILE);
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(uploadButton) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.saveFileInformation.setSaveFilePath(Path.of(selectedFile.getAbsolutePath()));
                chosenGameLabel.setText(LOADED_FILE_COLON + saveFileInformation.getSaveFilePath().getFileName().toString());
            }
        });
        chosenGameLabel.setHorizontalAlignment(JLabel.CENTER);

        inputFileSelectionPanel.add(uploadButton, gbc);
        gbc.gridy++;
        inputFileSelectionPanel.add(chosenGameLabel, gbc);
    }

    public JPanel getInputFileSelectionPanel() {
        return this.inputFileSelectionPanel;
    }
}
