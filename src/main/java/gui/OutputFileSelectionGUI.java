package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class OutputFileSelectionGUI {

    private final static String OUTPUT_FILE_NOT_DEFINED = "Pas de fichier de sortie défini";
    public static final String OUTPUT_FILE_BORDER_TITLE = "Informations sur le fichier en sortie";
    public static final String CHOOSE_OUTPUT_FILE = "Choisir le fichier de sortie";
    private static final String DEFAULT_SAVE_PARENT_FOLDER = "APPDATA";

    private JPanel outputFileSelectionPanel;
    private JButton fileOutputSelectionButton;
    private final JLabel outputFileLabel;
    private Path numberOfDeathFilePath;


    public OutputFileSelectionGUI() {
        outputFileLabel = new JLabel(OUTPUT_FILE_NOT_DEFINED);
        initComponents();
    }

    public void initComponents() {
        outputFileSelectionPanel = new JPanel(new GridBagLayout());
        outputFileSelectionPanel.setBorder(BorderFactory.createTitledBorder(OUTPUT_FILE_BORDER_TITLE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 10, 0, 0);

        fileOutputSelectionButton = new JButton(CHOOSE_OUTPUT_FILE);
        fileOutputSelectionButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(fileOutputSelectionButton) == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (isASaveFile(fileToSave.getAbsolutePath())) {
                    displayError("Vous avez sélectionner un fichier de sauvegarde comme fichier où sera sauvegarder le compteur de mort !");
                } else {
                    numberOfDeathFilePath = Path.of(fileToSave.getAbsolutePath());
                    outputFileLabel.setText(numberOfDeathFilePath.toString());
                }

            }
        });

        outputFileSelectionPanel.add(fileOutputSelectionButton, gbc);
        gbc.gridy++;
        outputFileSelectionPanel.add(outputFileLabel, gbc);
    }


    public boolean isASaveFile(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .map(s -> (s.equals("sl2")))
                .orElse(false);
    }


    public JPanel getPanel() {
        return this.outputFileSelectionPanel;
    }

    public Path getNumberOfDeathFilePath() {
        return this.numberOfDeathFilePath;
    }

    private static void displayError(String errorMessage) {
        ErrorDialog errorDialog = new ErrorDialog(errorMessage);
        errorDialog.setVisible(true);
    }

}
