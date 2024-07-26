package gui;

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

    private Path chosenSaveFilePath;

    public InputFileSelectionGUI() {
        chosenGameLabel = new JLabel(INPUT_FILE_NOT_LOADED);
        initComponents();
    }

    public void initComponents() {
        inputFileSelectionPanel = new JPanel();
        inputFileSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        inputFileSelectionPanel.setBorder(BorderFactory.createTitledBorder(INPUT_FILE_BORDER_TITLE));

        uploadButton = new JButton(CHOOSE_SAVE_FILE);
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(uploadButton) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                chosenSaveFilePath = Path.of(selectedFile.getAbsolutePath());
                chosenGameLabel.setText(LOADED_FILE_COLON + chosenSaveFilePath.getFileName().toString());
            }
        });
        chosenGameLabel.setHorizontalAlignment(JLabel.CENTER);

        inputFileSelectionPanel.add(uploadButton);
        inputFileSelectionPanel.add(chosenGameLabel);
    }

    public JPanel getInputFileSelectionPanel() {
        return this.inputFileSelectionPanel;
    }

    public Path getChosenSaveFilePath() {
        return this.chosenSaveFilePath;
    }
}
