package cpu2;
import java.util.Queue;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class FCFS {
    private javax.swing.JSpinner Brust_Spinner;
    private javax.swing.JSpinner Prio_Spinner;
    private javax.swing.JSpinner Qunt_Spinner;
    private javax.swing.JSpinner pID_Spinner;
    private javax.swing.JTable Table;

    // منشئ FCFS
    public FCFS(javax.swing.JSpinner Brust_Spinner, javax.swing.JSpinner Prio_Spinner, javax.swing.JSpinner Qunt_Spinner,
                javax.swing.JSpinner pID_Spinner, javax.swing.JTable Table) {
        this.Brust_Spinner = Brust_Spinner;
        this.Prio_Spinner = Prio_Spinner;
        this.Qunt_Spinner = Qunt_Spinner;
        this.pID_Spinner = pID_Spinner;
        this.Table = Table;
    }


    public void applyAlgorithm() {
        int rowCount = Table.getRowCount();


        Queue<P_Class> readyQueue = new LinkedList<>();

 
        for (int i = 0; i < rowCount; i++) {
         
            int brustTime = (int) Table.getValueAt(i, 1);
            int priority = (int) Table.getValueAt(i, 2);
            int quantum = (int) Table.getValueAt(i, 3); 
            int processID = (int) Table.getValueAt(i, 0); 

 
            P_Class process = new P_Class(brustTime, priority, quantum, processID);
            process.setState("Ready");
            readyQueue.add(process);
        }

        // تطبيق خوارزمية FCFS
        int[] completionTime = new int[rowCount];
        int[] turnaroundTime = new int[rowCount];
        int[] waitingTime = new int[rowCount];

        int currentTime = 0;
        int i = 0;

        while (!readyQueue.isEmpty()) {
            P_Class process = readyQueue.poll();

         
            process.setState("Running");
            updateTable(i, currentTime, process); 

          
            int brustTime = process.getBrustTime();
            currentTime += brustTime;
            completionTime[i] = currentTime;
            turnaroundTime[i] = completionTime[i];
            waitingTime[i] = turnaroundTime[i] - brustTime;

          
            process.setState("Completed");
            updateTable(i, currentTime, process); 

            i++;  
        }

        updateTable(rowCount, completionTime, turnaroundTime, waitingTime);
    }


    private void updateTable(int rowCount, int[] completionTime, int[] turnaroundTime, int[] waitingTime) {
        DefaultTableModel model = (DefaultTableModel) Table.getModel();

        for (int i = 0; i < rowCount; i++) {

            model.setValueAt(turnaroundTime[i], i, 3);  
            model.setValueAt(waitingTime[i], i, 4);     
            model.setValueAt(completionTime[i], i, 5);  

   
            String state = (String) model.getValueAt(i, 6); 

            
        /*    if ("Running".equals(state)) {
                Table.setRowSelectionInterval(i, i);
                Table.setSelectionBackground(Color.YELLOW);  
            } else if ("Completed".equals(state)) {
                Table.setRowSelectionInterval(i, i);
                Table.setSelectionBackground(Color.GREEN);   
            } else if ("Ready".equals(state)) {
                Table.setRowSelectionInterval(i, i);
                Table.setSelectionBackground(Color.RED);     
            }*/
        }
    }


    private void updateTable(int rowIndex, int currentTime, P_Class process) {
        DefaultTableModel model = (DefaultTableModel) Table.getModel();


        model.setValueAt(process.getState(), rowIndex, 6); 

      
        String state = process.getState();
        if ("Running".equals(state)) {
            Table.setSelectionBackground(Color.YELLOW);
        } else if ("Completed".equals(state)) {
            Table.setSelectionBackground(Color.GREEN);
        } else if ("Ready".equals(state)) {
            Table.setSelectionBackground(Color.RED);
        }
    }
}
