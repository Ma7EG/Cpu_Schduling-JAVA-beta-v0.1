package cpu2;

import java.awt.Color;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class RR {
    private javax.swing.JSpinner Brust_Spinner;
    private javax.swing.JSpinner Prio_Spinner;
    private javax.swing.JSpinner Qunt_Spinner;
    private javax.swing.JSpinner pID_Spinner;
    private javax.swing.JTable Table;

    public RR(javax.swing.JSpinner Brust_Spinner, javax.swing.JSpinner Prio_Spinner, javax.swing.JSpinner Qunt_Spinner,
              javax.swing.JSpinner pID_Spinner, javax.swing.JTable Table) {
        this.Brust_Spinner = Brust_Spinner;
        this.Prio_Spinner = Prio_Spinner;
        this.Qunt_Spinner = Qunt_Spinner;
        this.pID_Spinner = pID_Spinner;
        this.Table = Table;
    }

    public void applyAlgorithm() {
        int rowCount = Table.getRowCount();
      

        int quantum = (int) Qunt_Spinner.getValue();

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

        int[] completionTime = new int[rowCount];
        int[] turnaroundTime = new int[rowCount];
        int[] waitingTime = new int[rowCount];
        int[] remainingTime = new int[rowCount];

   
        for (int i = 0; i < rowCount; i++) {
            remainingTime[i] = processes.get(i).getBrustTime();
        }

        int currentTime = 0;
        Queue<P_Class> queue = new LinkedList<>();


        for (P_Class process : processes) {
            queue.add(process);
        }

    
        while (!queue.isEmpty()) {
            P_Class process = queue.poll();
            process.setState("Running");  
            updateTable(process, currentTime);

            int timeToExecute = Math.min(remainingTime[process.getProcessID() - 1], quantum);
            remainingTime[process.getProcessID() - 1] -= timeToExecute;
            currentTime += timeToExecute;

            if (remainingTime[process.getProcessID() - 1] > 0) {
                queue.add(process);
            } else {
                completionTime[process.getProcessID() - 1] = currentTime;
                turnaroundTime[process.getProcessID() - 1] = completionTime[process.getProcessID() - 1];
                waitingTime[process.getProcessID() - 1] = turnaroundTime[process.getProcessID() - 1] - process.getBrustTime();
                process.setState("Completed");  
                updateTable(process, currentTime);
            }
        }

        updateTable(rowCount, processes, completionTime, turnaroundTime, waitingTime);
    }

   
    private void updateTable(P_Class process, int currentTime) {
        DefaultTableModel model = (DefaultTableModel) Table.getModel();

       
        int rowIndex = process.getProcessID() - 1;
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
