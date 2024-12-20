package cpu2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GanttChart extends JPanel {

    private final List<String> processIDs;
    private final List<Integer> startTimes;
    private final List<Integer> endTimes;

    public GanttChart(List<String> processIDs, List<Integer> startTimes, List<Integer> endTimes) {
        this.processIDs = processIDs;
        this.startTimes = startTimes;
        this.endTimes = endTimes;
        setPreferredSize(new Dimension(800, processIDs.size() * 60)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int chartWidth = getWidth();
        int chartHeight = getHeight();
        int barHeight = 40;
        int margin = 20;


        int maxTime = endTimes.stream().max(Integer::compareTo).orElse(0);


        for (int i = 0; i < processIDs.size(); i++) {
            int startX = (int) ((startTimes.get(i) / (double) maxTime) * (chartWidth - 2 * margin)) + margin;
            int endX = (int) ((endTimes.get(i) / (double) maxTime) * (chartWidth - 2 * margin)) + margin;
            int y = margin + i * (barHeight + margin);

            g.setColor(new Color(38, 130, 241));
            g.fillRect(startX, y, endX - startX, barHeight);

          
            g.setColor(Color.WHITE);
            g.drawString(processIDs.get(i), startX + 5, y + barHeight / 2 + 5);

   
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(startTimes.get(i)), startX - 10, y + barHeight + 15);
            g.drawString(String.valueOf(endTimes.get(i)), endX - 10, y + barHeight + 15);
        }

     
        g.setColor(Color.BLACK);
        g.drawLine(margin, chartHeight - margin, chartWidth - margin, chartHeight - margin);
    }


    public static void createAndShowGanttChart(JTable table, JCheckBox FCFS_CheckBox, JCheckBox PNP_CheckBox,
                                               JCheckBox RR_CheckBox, JCheckBox SJF_CheckBox) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();


        List<String> processIDs = new ArrayList<>();
        List<Integer> burstTimes = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            processIDs.add("P" + model.getValueAt(i, 0));
            burstTimes.add((int) model.getValueAt(i, 1));
        }


        List<Integer> startTimes = new ArrayList<>();
        List<Integer> endTimes = new ArrayList<>();

        if (FCFS_CheckBox.isSelected()) {
            startTimes = fcfsAlgorithm(burstTimes);
        } else if (PNP_CheckBox.isSelected()) {
            startTimes = pnpAlgorithm(burstTimes, model);
        } else if (SJF_CheckBox.isSelected()) {
            startTimes = sjfAlgorithm(burstTimes);
        } else if (RR_CheckBox.isSelected()) {
            startTimes = rrAlgorithm(burstTimes, model);
        }


        endTimes = calculateEndTimes(startTimes, burstTimes);


        System.out.println("Processes: " + processIDs);
        System.out.println("Start Times: " + startTimes);
        System.out.println("End Times: " + endTimes);


        GanttChart.showGanttChart(processIDs, startTimes, endTimes);
    }

    private static List<Integer> fcfsAlgorithm(List<Integer> burstTimes) {
        List<Integer> startTimes = new ArrayList<>();
        int time = 0;
        for (int burstTime : burstTimes) {
            startTimes.add(time);
            time += burstTime;
        }
        return startTimes;
    }

    private static List<Integer> pnpAlgorithm(List<Integer> burstTimes, DefaultTableModel model) {
        List<Integer> startTimes = new ArrayList<>();
        List<Integer> priorities = new ArrayList<>();
        for (int i = 0; i < burstTimes.size(); i++) {
            priorities.add((int) model.getValueAt(i, 2));
        }

        for (int i = 0; i < burstTimes.size(); i++) {
            for (int j = i + 1; j < burstTimes.size(); j++) {
                if (priorities.get(i) > priorities.get(j)) {
                    int tempBurst = burstTimes.get(i);
                    burstTimes.set(i, burstTimes.get(j));
                    burstTimes.set(j, tempBurst);

                    int tempPriority = priorities.get(i);
                    priorities.set(i, priorities.get(j));
                    priorities.set(j, tempPriority);
                }
            }
        }

        int time = 0;
        for (int burstTime : burstTimes) {
            startTimes.add(time);
            time += burstTime;
        }
        return startTimes;
    }

    private static List<Integer> sjfAlgorithm(List<Integer> burstTimes) {
        List<Integer> startTimes = new ArrayList<>();
        burstTimes.sort(Integer::compareTo);
        int time = 0;
        for (int burstTime : burstTimes) {
            startTimes.add(time);
            time += burstTime;
        }
        return startTimes;
    }

    private static List<Integer> rrAlgorithm(List<Integer> burstTimes, DefaultTableModel model) {
        List<Integer> startTimes = new ArrayList<>();
        int time = 0;
        int quantum = 4;
        while (!burstTimes.isEmpty()) {
            for (int i = 0; i < burstTimes.size(); i++) {
                int burstTime = burstTimes.get(i);
                if (burstTime > quantum) {
                    startTimes.add(time);
                    time += quantum;
                    burstTimes.set(i, burstTime - quantum);
                } else {
                    startTimes.add(time);
                    time += burstTime;
                    burstTimes.remove(i);
                    i--;
                }
            }
        }
        return startTimes;
    }

    private static List<Integer> calculateEndTimes(List<Integer> startTimes, List<Integer> burstTimes) {
        List<Integer> endTimes = new ArrayList<>();
        for (int i = 0; i < startTimes.size(); i++) {
            endTimes.add(startTimes.get(i) + burstTimes.get(i));
        }
        return endTimes;
    }

    public static void showGanttChart(List<String> processIDs, List<Integer> startTimes, List<Integer> endTimes) {
        JFrame frame = new JFrame("Gantt Chart");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GanttChart chart = new GanttChart(processIDs, startTimes, endTimes);
        frame.add(chart, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> frame.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
