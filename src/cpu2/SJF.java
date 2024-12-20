package cpu2;

import java.awt.Color;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class SJF {
    private javax.swing.JSpinner Brust_Spinner;
    private javax.swing.JSpinner Prio_Spinner;
    private javax.swing.JSpinner Qunt_Spinner;
    private javax.swing.JSpinner pID_Spinner;
    private javax.swing.JTable Table;

    public SJF(javax.swing.JSpinner Brust_Spinner, javax.swing.JSpinner Prio_Spinner, javax.swing.JSpinner Qunt_Spinner,
               javax.swing.JSpinner pID_Spinner, javax.swing.JTable Table) {
        this.Brust_Spinner = Brust_Spinner;
        this.Prio_Spinner = Prio_Spinner;
        this.Qunt_Spinner = Qunt_Spinner;
        this.pID_Spinner = pID_Spinner;
        this.Table = Table;
    }

    public void applyAlgorithm() {
        int rowCount = Table.getRowCount();
       

        List<P_Class> processes = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) Table.getModel();

        for (int i = 0; i < rowCount; i++) {
            int processID = (int) model.getValueAt(i, 0);
            int brustTime = (int) model.getValueAt(i, 1);
            int priority = (int) model.getValueAt(i, 2);
            P_Class process = new P_Class(brustTime, priority, 0, processID);
            process.setState("Ready");  
            processes.add(process);
        }

        Collections.sort(processes, Comparator.comparingInt(P_Class::getBrustTime));


        Queue<P_Class> readyQueue = new LinkedList<>(processes);

        int[] completionTime = new int[rowCount];
        int[] turnaroundTime = new int[rowCount];
        int[] waitingTime = new int[rowCount];
        int currentTime = 0;

        for (int i = 0; i < rowCount; i++) {
            P_Class process = readyQueue.poll();
            process.setState("Running");  
            updateTable(i, currentTime, process);

            completionTime[i] = currentTime + process.getBrustTime();
            turnaroundTime[i] = completionTime[i];
            waitingTime[i] = turnaroundTime[i] - process.getBrustTime();
            currentTime = completionTime[i];
            process.setState("Completed");
            updateTable(i, currentTime, process);
        }

        updateTable(rowCount, processes, completionTime, turnaroundTime, waitingTime);
    }

    private void updateTable(int rowIndex, int currentTime, P_Class process) {
        DefaultTableModel model = (DefaultTableModel) Table.getModel();

        model.setValueAt(process.getState(), rowIndex, 6); 

        String state = process.getState();
     /*     if ("Running".equals(state)) {
            Table.setSelectionBackground(Color.YELLOW);
        } else if ("Completed".equals(state)) {
            Table.setSelectionBackground(Color.GREEN);
        } else if ("Ready".equals(state)) {
            Table.setSelectionBackground(Color.RED);
        }*/
    }

    private void updateTable(int rowCount, List<P_Class> processes, int[] completionTime, int[] turnaroundTime, int[] waitingTime) {
        DefaultTableModel model = (DefaultTableModel) Table.getModel();

        for (int i = 0; i < rowCount; i++) {
            P_Class process = processes.get(i);
            model.setValueAt("P" + process.getProcessID(), i, 0);  
            model.setValueAt(process.getBrustTime(), i, 1);        
            model.setValueAt(process.getPriority(), i, 2);          
            model.setValueAt(turnaroundTime[i], i, 3);             
            model.setValueAt(waitingTime[i], i, 4);                 
            model.setValueAt(completionTime[i], i, 5);             
        }
    }
}

