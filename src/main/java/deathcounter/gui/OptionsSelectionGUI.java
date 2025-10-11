package deathcounter.gui;

import deathcounter.domain.file.SaveFileInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static deathcounter.domain.file.SaveFileInformation.*;
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
    private boolean canBeEnabled;

    public OptionsSelectionGUI(SaveFileInformation saveFileInformation) {
        this.saveFileInformation = saveFileInformation;
        saveFileInformation.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(SAVE_FILE_PATH_FIELD_NAME) || evt.getPropertyName().equals(CHOSEN_GAME_FIELD_NAME)) {
                    if (canOptionsBeUsed()) {
                        enableComponents();
                    } else {
                        disableComponents();
                    }
                } else if (evt.getPropertyName().equals(CHARACTER_FIELD_NAME)) {
                    currentNumberOfDeath = saveFileInformation.getNumberOfDeath();
                }
            }
        });

        this.canBeEnabled = true;
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
        disableComponents();
    }

    public int getOffsetValue() {
        return (int) spinner.getValue();
    }

    public JPanel getPanel() {
        return this.optionPanel;
    }


    private void setDeathCounterToZero() {
        SpinnerModel model = new SpinnerNumberModel(-currentNumberOfDeath, -currentNumberOfDeath, Integer.MAX_VALUE - currentNumberOfDeath, 1);
        spinner.setModel(model);
        saveFileInformation.updateNumberOfDeath(currentNumberOfDeath + (int) spinner.getValue());
    }

    private void reinitializeSpinner() {
        SpinnerModel model = new SpinnerNumberModel(0, -currentNumberOfDeath, Integer.MAX_VALUE - currentNumberOfDeath, 1);
        spinner.setModel(model);
        saveFileInformation.updateNumberOfDeath(currentNumberOfDeath);
    }

    public void setEnabled(boolean enabled) {
        this.canBeEnabled = enabled;
        asList(optionPanel.getComponents())
                .forEach(component -> component.setEnabled(enabled));
    }

    private void enableComponents() {
        asList(optionPanel.getComponents())
                .forEach(component -> component.setEnabled(true));
    }


    private void disableComponents() {
        asList(optionPanel.getComponents())
                .forEach(component -> component.setEnabled(false));
    }

    /**
     * Check if the game and the save as been selected to use options.
     *
     * @return True if flagged as allowed to be enabled and if the game and save file have been chosen.
     * False otherwise.
     */
    private boolean canOptionsBeUsed() {
        return !(null == this.saveFileInformation.getChosenGame()) &&
                !(null == this.saveFileInformation.getSaveFilePath()) &&
                canBeEnabled;
    }
}
