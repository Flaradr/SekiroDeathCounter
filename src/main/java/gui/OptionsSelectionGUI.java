package gui;

import controller.FileReaderController;
import domain.character.FromSoftwareCharacter;
import domain.file.SaveFileInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.util.Arrays.asList;

public class OptionsSelectionGUI {

    public static final String RESET_TO_ZERO = "Mise à 0 du compteur";
    public static final String OPTIONS = "Options";
    public static final String LABEL_SPINNER_INCREASE_OR_DECREASE_DEATH = "Ajouter ou retirer des morts";
    public static final String SPINNER_TOOLTIP_INCREASE_OR_DECREASE_NUMBER_OF_DEATH = "Le minimum est le nombre de mort existant dans le fichier de sauvegarde";
    public static final String GAME_NOT_SELECTED = "Le jeu n'a pas été sélectionné";
    public static final String SAVE_FILE_NOT_SELECTED = "Le fichier de sauvegarde n'a pas été choisi";
    private static Logger logger = LogManager.getLogger(OptionsSelectionGUI.class);
    private final JPanel optionPanel;
    private JButton resetDeathCounterToZeroButton;
    private JButton resetSpinnerDefaultValueButton;
    private final JLabel label;
    private final JSpinner spinner;
    private final SaveFileInformation saveFileInformation;
    private int currentNumberOfDeath;

    public OptionsSelectionGUI(SaveFileInformation saveFileInformation) {
        this.saveFileInformation = saveFileInformation;
        optionPanel = new JPanel();
        spinner = new JSpinner();

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner s = (JSpinner) e.getSource();
                saveFileInformation.updateNumberOfDeath(currentNumberOfDeath + (int) s.getValue());
            }
        });

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
            saveFileInformation.setCharacter(fromSoftwareCharacter);
            currentNumberOfDeath = fromSoftwareCharacter.getDeathCount();
            SpinnerModel model = new SpinnerNumberModel(-currentNumberOfDeath, -currentNumberOfDeath, Integer.MAX_VALUE - currentNumberOfDeath, 1);
            spinner.setModel(model);
            saveFileInformation.updateNumberOfDeath(currentNumberOfDeath + (int) spinner.getValue());
        } catch (NullPointerException exception) {
            displayError(exception.getMessage());
        }
    }

    private void reinitializeSpinner() {
        FileReaderController fileReaderController = new FileReaderController(saveFileInformation.getChosenGame(), saveFileInformation.getSaveFilePath());
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            saveFileInformation.setCharacter(fromSoftwareCharacter);
            currentNumberOfDeath = fromSoftwareCharacter.getDeathCount();
            SpinnerModel model = new SpinnerNumberModel(0, -currentNumberOfDeath, Integer.MAX_VALUE - currentNumberOfDeath, 1);
            spinner.setModel(model);
            saveFileInformation.updateNumberOfDeath(currentNumberOfDeath);
        } catch (NullPointerException exception) {
            displayError(exception.getMessage());
        }
    }

    private static void displayError(String errorMessage) {
        logger.warn(errorMessage);
        ErrorDialog errorDialog = new ErrorDialog(errorMessage);
        errorDialog.setVisible(true);
    }

    public void setEnabled(boolean isEnabled) {
        asList(optionPanel.getComponents())
                .forEach(component -> component.setEnabled(isEnabled));
    }
}
