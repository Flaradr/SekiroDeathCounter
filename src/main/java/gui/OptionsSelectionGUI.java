package gui;

import controller.FileReaderController;
import domain.character.FromSoftwareCharacter;
import domain.file.SaveFileInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsSelectionGUI {

    public static final String RESET_TO_ZERO = "Mise à 0 du compteur";
    public static final String OPTIONS = "Options";
    public static final String LABEL_SPINNER_INCREASE_OR_DECREASE_DEATH = "Ajouter ou retirer des morts";
    public static final String SPINNER_TOOLTIP_INCREASE_OR_DECREASE_NUMBER_OF_DEATH = "Le minimum est le nombre de mort existant dans le fichier de sauvegarde";
    public static final String GAME_NOT_SELECTED = "Le jeu n'a pas été sélectionné";
    public static final String SAVE_FILE_NOT_SELECTED = "Le fichier de sauvegarde n'a pas été choisi";

    private final JPanel optionPanel;
    private JButton resetDeathCounterToZeroButton;
    private JButton resetSpinnerDefaultValueButton;
    private final JLabel label;
    private final JSpinner spinner;
    private final SaveFileInformation saveFileInformation;

    public OptionsSelectionGUI(SaveFileInformation saveFileInformation) {
        this.saveFileInformation = saveFileInformation;

        optionPanel = new JPanel();
        spinner = new JSpinner();
        spinner.setPreferredSize(new Dimension(100, 20));
        label = new JLabel(LABEL_SPINNER_INCREASE_OR_DECREASE_DEATH);

        initComponents();
    }

    public void initComponents() {
        optionPanel.setLayout(new GridBagLayout());
        optionPanel.setBorder(BorderFactory.createTitledBorder(OPTIONS));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 10, 0, 0);

        resetDeathCounterToZeroButton = new JButton(RESET_TO_ZERO);
        resetDeathCounterToZeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDeathCounterToZero();
            }
        });

        resetSpinnerDefaultValueButton = new JButton("Reset");
        resetSpinnerDefaultValueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reinitializeSpinner();
            }
        });

        spinner.setToolTipText(SPINNER_TOOLTIP_INCREASE_OR_DECREASE_NUMBER_OF_DEATH);
        resetDeathCounterToZeroButton.setEnabled(true);

        optionPanel.add(label, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(0, -60, 0, 0);
        optionPanel.add(spinner, gbc);
        gbc.gridx = 2;
        gbc.insets = new Insets(0, -90, 0, 0);
        optionPanel.add(resetSpinnerDefaultValueButton, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        optionPanel.add(resetDeathCounterToZeroButton, gbc);
    }

    public int getOffsetValue() {
        return (int) spinner.getValue();
    }

    public void changeSpinnerModel(SpinnerModel model) {
        spinner.setModel(model);
    }

    public JPanel getPanel() {
        return this.optionPanel;
    }


    private void setDeathCounterToZero() {
        if (null == saveFileInformation.getChosenGame()) {
            displayError(GAME_NOT_SELECTED);
            return;
        }

        if (null == saveFileInformation.getSaveFilePath()) {
            displayError(SAVE_FILE_NOT_SELECTED);
            return;
        }

        FileReaderController fileReaderController = new FileReaderController(saveFileInformation.getChosenGame(), saveFileInformation.getSaveFilePath());
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            int numberOfDeathInSaveFile = fromSoftwareCharacter.getDeathCount();
            SpinnerModel model = new SpinnerNumberModel(-numberOfDeathInSaveFile, -numberOfDeathInSaveFile, Integer.MAX_VALUE - numberOfDeathInSaveFile, 1);
            spinner.setModel(model);
        } catch (NullPointerException exception) {
            saveFileInformation.setStringifiedData("Solution pas encore développée pour : " + saveFileInformation.getChosenGame().getFullName());
        }
    }


    private void reinitializeSpinner() {
        spinner.setValue(0);
    }

    private static void displayError(String errorMessage) {
        ErrorDialog errorDialog = new ErrorDialog(errorMessage);
        errorDialog.setVisible(true);
    }

}
