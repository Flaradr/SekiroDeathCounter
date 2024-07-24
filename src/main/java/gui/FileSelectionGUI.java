package gui;

import controller.FileReaderController;
import domain.character.FromSoftwareCharacter;
import domain.file.FileInformation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class FileSelectionGUI {

    private JPanel uploadFilePanel;
    private JButton uploadButton;
    private JButton startButton;
    private JLabel chosenGameLabel;

    private Path chosenGamePath;

    enum ProgramStatus {RUNNING, PAUSED, STOPPED}

    private ProgramStatus deathCounterStatus;

    FileInformation charFileInformation;
    ScheduledFuture future;
    PausableSwingWorker worker;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private static final String DEFAULT_SAVE_PARENT_FOLDER = "APPDATA";

    public FileSelectionGUI(FileInformation myFileInformation) {
        deathCounterStatus = ProgramStatus.STOPPED;
        charFileInformation = myFileInformation;
        worker = new PausableSwingWorker();
        initComponents();
    }

    public void initComponents() {
        uploadFilePanel = new JPanel();
        Border uploadFile = BorderFactory.createTitledBorder("");

        uploadButton = new JButton("Choisir un fichier");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getenv(DEFAULT_SAVE_PARENT_FOLDER));
            if (fileChooser.showOpenDialog(uploadButton) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                chosenGamePath = Path.of(selectedFile.getAbsolutePath());
                charFileInformation.setFilePath(chosenGamePath);
                chosenGameLabel.setText("Fichier chargé : " + chosenGamePath.getFileName().toString());
                startButton.setVisible(true);
            } else {
                startButton.setVisible(false);
            }
        });
        startButton = new JButton("Démarrage du programme");
        startButton.addActionListener(actionListener -> onPressStartButton());
        startButton.setVisible(false);

        chosenGameLabel = new JLabel("Pas de fichier chargé");
        chosenGameLabel.setHorizontalAlignment(JLabel.CENTER);

        uploadFilePanel.setLayout(new GridLayout(0, 3));
        uploadFilePanel.setBorder(uploadFile);
        uploadFilePanel.add(uploadButton);
        uploadFilePanel.add(chosenGameLabel);
        uploadFilePanel.add(startButton);
        uploadFilePanel.setPreferredSize(new Dimension(600, 100));

    }

    public JPanel getUploadFilePanel() {
        return this.uploadFilePanel;
    }

    private void updateGameInfo() {
        FileReaderController fileReaderController = new FileReaderController(charFileInformation.getChosenGame(), chosenGamePath);
        try {
            FromSoftwareCharacter fromSoftwareCharacter = fileReaderController.get(0);
            charFileInformation.setStringifiedData("Number of death : " + fromSoftwareCharacter.getDeathCount());
            System.out.println(charFileInformation.getStringifiedData());
        } catch (NullPointerException exception) {
            charFileInformation.setStringifiedData("Solution pas encore développée pour : " + charFileInformation.getChosenGame().getFullName());
        }
    }

    private void onPressStartButton() {
        switch (deathCounterStatus) {
            case STOPPED -> {
                deathCounterStatus = ProgramStatus.RUNNING;
                startButton.setText("Pause du programme !");
                worker.execute();
            }
            case RUNNING -> {
                deathCounterStatus = ProgramStatus.PAUSED;
                startButton.setText("Relance du programme !");
                worker.pause();
            }
            case PAUSED -> {
                deathCounterStatus = ProgramStatus.RUNNING;
                startButton.setText("Pause du programme !");
                worker.resume();
            }
        }
    }

    public void handleDeathCounter() {
        final Runnable updateGameInfoRunnable = new Runnable() {
            public void run() {
                updateGameInfo();
            }
        };
        future = executorService.scheduleAtFixedRate(updateGameInfoRunnable, 0, 2, TimeUnit.SECONDS);
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