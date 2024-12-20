package cpu2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProgressBarWindow extends JFrame {

    private List<JProgressBar> progressBars;
    private JLabel timerLabel;
    private int elapsedSeconds = 0;

    public ProgressBarWindow(JTable table, JSpinner Brust_Spinner) {
        setTitle("Process Progress");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        List<String> processIDs = new ArrayList<>();
        List<Integer> burstTimes = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            processIDs.add("P" + model.getValueAt(i, 0).toString());
            burstTimes.add(Integer.parseInt(model.getValueAt(i, 1).toString()));
        }

        int newBurstTime = (int) Brust_Spinner.getValue();
        if (newBurstTime > 0) {
            String newProcessID = "P" + findNextAvailableID(processIDs);

            if (!burstTimes.contains(newBurstTime)) {
                processIDs.add(newProcessID); 
                burstTimes.add(newBurstTime);
            } else {
              //  JOptionPane.showMessageDialog(this, "A process with the same Burst Time already exists!", "Duplicate Process", JOptionPane.ERROR_MESSAGE);
            }
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        progressBars = new ArrayList<>();
        for (int i = 0; i < processIDs.size(); i++) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(processIDs.get(i) + " (Burst Time: " + burstTimes.get(i) + ")");
            JProgressBar progressBar = new JProgressBar(0, burstTimes.get(i));
            progressBars.add(progressBar);
            panel.add(label, BorderLayout.WEST);
            panel.add(progressBar, BorderLayout.CENTER);
            mainPanel.add(panel);

            if (i < processIDs.size() - 1) {
                mainPanel.add(Box.createVerticalStrut(10)); 
            }
        }

        timerLabel = new JLabel("Elapsed Time: 0 seconds");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(timerLabel);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton);

        add(mainPanel);

        startTimer(burstTimes);
    }

    private void startTimer(List<Integer> burstTimes) {
        Timer timer = new Timer(1000, e -> {
            elapsedSeconds++;
            timerLabel.setText("Elapsed Time: " + elapsedSeconds + " seconds");

            for (int i = 0; i < progressBars.size(); i++) {
                JProgressBar progressBar = progressBars.get(i);
                if (progressBar.getValue() < burstTimes.get(i)) {
                    progressBar.setValue(progressBar.getValue() + 1);
                }
            }

            boolean allCompleted = true;
            for (int i = 0; i < progressBars.size(); i++) {
                if (progressBars.get(i).getValue() < burstTimes.get(i)) {
                    allCompleted = false;
                    break;
                }
            }

          /*  if (allCompleted) {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(this, "All processes completed!", "Completed", JOptionPane.INFORMATION_MESSAGE);
            }*/
        });

        timer.start();
    }

    private int findNextAvailableID(List<String> processIDs) {
        int id = 1;
        while (processIDs.contains("P" + id)) {
            id++;
        }
        return id;
    }
}
