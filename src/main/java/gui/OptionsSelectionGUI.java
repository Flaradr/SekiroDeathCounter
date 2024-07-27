package gui;

import controller.FileReaderController;
import domain.character.FromSoftwareCharacter;
import domain.file.SaveFileInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OptionsSelectionGUI {

    public static final String RESET_TO_ZERO = "Mise à 0 du compteur";
    public static final String OPTIONS = "Options";
    public static final String LABEL_SPINNER_INCREASE_OR_DECREASE_DEATH = "Ajouter ou retirer des morts";
    public static final String SPINNER_TOOLTIP_INCREASE_OR_DECREASE_NUMBER_OF_DEATH = "Permet d'ajouter ou retirer des morts";
    private final JPanel optionPanel;
    private JButton resetDeathCounterToZeroButton;
    private final JLabel label;
    private final JSpinner spinner;
    private final SaveFileInformation saveFileInformation;

    public OptionsSelectionGUI(SaveFileInformation saveFileInformation) {
        this.saveFileInformation = saveFileInformation;
        saveFileInformation.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setDeathCounterToZero();
            }
        });

        optionPanel = new JPanel();
        spinner = new JSpinner();
        spinner.setPreferredSize(new Dimension(100, 20));
        label = new JLabel(LABEL_SPINNER_INCREASE_OR_DECREASE_DEATH);

        initComponents();
    }

    public void initComponents() {
        optionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        optionPanel.setBorder(BorderFactory.createTitledBorder(OPTIONS));
        resetDeathCounterToZeroButton = new JButton(RESET_TO_ZERO);
        resetDeathCounterToZeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDeathCounterToZero();
            }
        });
        spinner.setToolTipText(SPINNER_TOOLTIP_INCREASE_OR_DECREASE_NUMBER_OF_DEATH);
        resetDeathCounterToZeroButton.setEnabled(true);

        optionPanel.add(resetDeathCounterToZeroButton);
        optionPanel.add(label);
        optionPanel.add(spinner);
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

}
