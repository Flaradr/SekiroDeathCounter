package gui;

import controller.FileReaderController;
import domain.character.FromSoftwareCharacter;
import domain.file.FileInformation;
import util.FileWriterWrapper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ParameterSelectionGUI {

    private final static int PERIOD_BETWEEN_READING_IN_SECONDS = 5;
    public static final String PARAMETERS = "Paramètres";
    public static final String START_PROGRAM = "Démarrage du programme";
    public static final String PAUSE_PROGRAM = "Pause du programme";
    public static final String RESUME_PROGRAM = "Relance du programme";

    private JPanel parameterSelectionPanel;

    private final OutputFileSelectionGUI outputFileSelectionGUI;
    private final InputFileSelectionGUI inputFileSelectionGUI;


    private JPanel startPanel;
    private JButton startButton;
    private JSpinner spinner;
    private JButton resetDeathCounterToZeroButton;
    private int numberOfDeathInSaveFile;

    enum ProgramStatus {RUNNING, PAUSED, STOPPED}

    private ProgramStatus deathCounterStatus;

    FileInformation charFileInformation;
    ScheduledFuture future;
    PausableSwingWorker worker;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    public ParameterSelectionGUI(FileInformation myFileInformation) {
        outputFileSelectionGUI = new OutputFileSelectionGUI();

        inputFileSelectionGUI = new InputFileSelectionGUI();

        deathCounterStatus = ProgramStatus.STOPPED;
        charFileInformation = myFileInformation;
        worker = new PausableSwingWorker();

        numberOfDeathInSaveFile = 0;
        spinner = new JSpinner();
        spinner.setPreferredSize(new Dimension(100, 20));
        initComponents();
    }

    public void initComponents() {
        parameterSelectionPanel = new JPanel();
        parameterSelectionPanel.setLayout(new GridLayout(3, 0));
        startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        Border uploadFile = BorderFactory.createTitledBorder(PARAMETERS);

        startButton = new JButton(START_PROGRAM);
        startButton.addActionListener(actionListener -> onPressStartButton());
        startButton.setEnabled(true);

        resetDeathCounterToZeroButton = new JButton("Décaler nombre de mort à 0");
        resetDeathCounterToZeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDeathCounterToZero();
            }
        });
        spinner.setToolTipText("Permet d'ajouter ou retirer des morts");
        resetDeathCounterToZeroButton.setEnabled(true);
        startPanel.add(resetDeathCounterToZeroButton);
        startPanel.add(new JLabel("Modifier nombre de mort"));
        startPanel.add(spinner);
        startPanel.add(startButton);

        parameterSelectionPanel.setBorder(uploadFile);

        parameterSelectionPanel.add(outputFileSelectionGUI.getPanel());
        parameterSelectionPanel.add(inputFileSelectionGUI.getInputFileSelectionPanel());
        parameterSelectionPanel.add(startPanel);
    }

    public JPanel getParameterSelectionPanel() {
        return this.parameterSelectionPanel;
    }


    private void setDeathCounterToZero() {
        FileReaderController fileReaderController = new FileReaderController(charFileInformation.getChosenGame(), this.inputFileSelectionGUI.getChosenSaveFilePath());
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            numberOfDeathInSaveFile = fromSoftwareCharacter.getDeathCount();
            SpinnerModel model = new SpinnerNumberModel(numberOfDeathInSaveFile, Integer.MIN_VALUE + numberOfDeathInSaveFile, numberOfDeathInSaveFile, 1);
            spinner.setModel(model);
        } catch (NullPointerException exception) {
            charFileInformation.setStringifiedData("Solution pas encore développée pour : " + charFileInformation.getChosenGame().getFullName());
        }
    }

    private void updateGameInfo() {
        FileReaderController fileReaderController = new FileReaderController(charFileInformation.getChosenGame(), this.inputFileSelectionGUI.getChosenSaveFilePath());
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            charFileInformation.setStringifiedData("Nombre de mort : " + fromSoftwareCharacter.getDeathCount());
            numberOfDeathInSaveFile = fromSoftwareCharacter.getDeathCount();
            System.out.println("Number of death : " + computeDeathWithOffset());
            SpinnerModel model = new SpinnerNumberModel((int) spinner.getValue(), Integer.MIN_VALUE + numberOfDeathInSaveFile, numberOfDeathInSaveFile, 1);
            spinner.setModel(model);
            FileWriterWrapper.writeIntInFile(this.outputFileSelectionGUI.getNumberOfDeathFilePath(), computeDeathWithOffset());
        } catch (NullPointerException exception) {
            charFileInformation.setStringifiedData("Solution pas encore développée pour : " + charFileInformation.getChosenGame().getFullName());
        }
    }

    private int computeDeathWithOffset() {
        return numberOfDeathInSaveFile - (int) spinner.getValue();
    }

    private void onPressStartButton() {
        if (null == charFileInformation.getChosenGame()) {
            startButton.setEnabled(false);
            displayError("Le jeu n'a pas été sélectionné");
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