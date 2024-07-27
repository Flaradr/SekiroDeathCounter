package gui;

import controller.FileReaderController;
import domain.character.FromSoftwareCharacter;
import domain.file.SaveFileInformation;
import util.FileWriterWrapper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ParameterSelectionGUI {

    private final static int PERIOD_BETWEEN_READING_IN_SECONDS = 5;
    public static final String PARAMETERS = "Paramètres";
    public static final String START_PROGRAM = "Démarrer le compteur";
    public static final String PAUSE_PROGRAM = "Pause";
    public static final String RESUME_PROGRAM = "Reprise";
    public static final String GAME_NOT_SELECTED = "Le jeu n'a pas été sélectionné";
    public static final String SAVE_FILE_NOT_SELECTED = "Le fichier de sauvegarde n'a pas été choisi";

    private JPanel parameterSelectionPanel;

    private final OutputFileSelectionGUI outputFileSelectionGUI;
    private final InputFileSelectionGUI inputFileSelectionGUI;
    private final OptionsSelectionGUI optionsSelectionGUI;

    private JButton startButton;

    enum ProgramStatus {RUNNING, PAUSED, STOPPED}

    private ProgramStatus deathCounterStatus;

    SaveFileInformation charSaveFileInformation;
    ScheduledFuture future;
    PausableSwingWorker worker;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    public ParameterSelectionGUI(SaveFileInformation mySaveFileInformation) {
        outputFileSelectionGUI = new OutputFileSelectionGUI();
        inputFileSelectionGUI = new InputFileSelectionGUI(mySaveFileInformation);

        optionsSelectionGUI = new OptionsSelectionGUI(mySaveFileInformation);

        deathCounterStatus = ProgramStatus.STOPPED;
        charSaveFileInformation = mySaveFileInformation;
        worker = new PausableSwingWorker();

        initComponents();
    }

    public void initComponents() {
        parameterSelectionPanel = new JPanel();
        parameterSelectionPanel.setLayout(new GridLayout(4, 0));

        Border uploadFile = BorderFactory.createTitledBorder(PARAMETERS);

        startButton = new JButton(START_PROGRAM);
        startButton.addActionListener(actionListener -> onPressStartButton());
        startButton.setEnabled(true);


        parameterSelectionPanel.setBorder(uploadFile);

        parameterSelectionPanel.add(outputFileSelectionGUI.getPanel());
        parameterSelectionPanel.add(inputFileSelectionGUI.getInputFileSelectionPanel());
        parameterSelectionPanel.add(optionsSelectionGUI.getPanel());
        parameterSelectionPanel.add(startButton);
    }

    public JPanel getParameterSelectionPanel() {
        return this.parameterSelectionPanel;
    }

    private void updateGameInfo() {
        FileReaderController fileReaderController = new FileReaderController(charSaveFileInformation.getChosenGame(), this.charSaveFileInformation.getSaveFilePath());
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            charSaveFileInformation.setStringifiedData("Nombre de mort : " + fromSoftwareCharacter.getDeathCount());
            charSaveFileInformation.setNumberOfDeath(fromSoftwareCharacter.getDeathCount());
            SpinnerModel model = new SpinnerNumberModel(optionsSelectionGUI.getOffsetValue(),
                    -charSaveFileInformation.getNumberOfDeath(),
                    Integer.MAX_VALUE - charSaveFileInformation.getNumberOfDeath(),
                    1);
            optionsSelectionGUI.changeSpinnerModel(model);

            FileWriterWrapper.writeIntInFile(this.outputFileSelectionGUI.getNumberOfDeathFilePath(), computeDeathWithOffset());
        } catch (NullPointerException exception) {
            charSaveFileInformation.setStringifiedData("Solution pas encore développée pour : " + charSaveFileInformation.getChosenGame().getFullName());
        }
    }

    private int computeDeathWithOffset() {
        return charSaveFileInformation.getNumberOfDeath() + optionsSelectionGUI.getOffsetValue();
    }

    private void onPressStartButton() {
        if (null == charSaveFileInformation.getChosenGame()) {
            displayError(GAME_NOT_SELECTED);
            return;
        }

        if (null == charSaveFileInformation.getSaveFilePath()) {
            displayError(SAVE_FILE_NOT_SELECTED);
            return;
        }

        switch (deathCounterStatus) {
            case STOPPED -> {
                deathCounterStatus = ProgramStatus.RUNNING;
                startButton.setText(PAUSE_PROGRAM);
                worker.execute();
            }
            case RUNNING -> {
                deathCounterStatus = ProgramStatus.PAUSED;
                startButton.setText(RESUME_PROGRAM);
                worker.pause();
            }
            case PAUSED -> {
                deathCounterStatus = ProgramStatus.RUNNING;
                startButton.setText(PAUSE_PROGRAM);
                worker.resume();
            }
        }
    }

    private static void displayError(String errorMessage) {
        ErrorDialog errorDialog = new ErrorDialog(errorMessage);
        errorDialog.setVisible(true);
    }

    public void handleDeathCounter() {
        final Runnable updateGameInfoRunnable = new Runnable() {
            public void run() {
                updateGameInfo();
            }
        };
        future = executorService.scheduleAtFixedRate(updateGameInfoRunnable, 0, PERIOD_BETWEEN_READING_IN_SECONDS, TimeUnit.SECONDS);
    }


    public class PausableSwingWorker<K, V> extends SwingWorker<K, V> {

        private volatile boolean isPaused;

        public final void pause() {
            if (!isPaused()) {
                isPaused = true;
                future.cancel(true);
            }
        }

        public final void resume() {
            if (isPaused()) {
                isPaused = false;
                handleDeathCounter();
            }
        }

        public final boolean isPaused() {
            return isPaused;
        }

        @Override
        protected K doInBackground() {
            handleDeathCounter();
            return null;
        }
    }
}