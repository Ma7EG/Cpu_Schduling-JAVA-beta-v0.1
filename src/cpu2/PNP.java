package cpu2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class PNP {
    private JSpinner Brust_Spinner, Prio_Spinner, Qunt_Spinner, pID_Spinner;
    private JTable Table;

    public PNP(JSpinner Brust_Spinner, JSpinner Prio_Spinner, JSpinner Qunt_Spinner, JSpinner pID_Spinner, JTable Table) {
        this.Brust_Spinner = Brust_Spinner;
        this.Prio_Spinner = Prio_Spinner;
        this.Qunt_Spinner = Qunt_Spinner;
        this.pID_Spinner = pID_Spinner;
        this.Table = Table;
    }

    public void applyAlgorithm() {
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        int rowCount = model.getRowCount();

        if (rowCount == 0) {
            JOptionPane.showMessageDialog(null, "الجدول فارغ! يرجى إضافة بيانات.", "خطأ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<P_Class> processes = new ArrayList<>();

    
        for (int i = 0; i < rowCount; i++) {
            int pid = Integer.parseInt(model.getValueAt(i, 0).toString());
            int burstTime = Integer.parseInt(model.getValueAt(i, 1).toString());
            int priority = Integer.parseInt(model.getValueAt(i, 2).toString());
            int quantum = Integer.parseInt(model.getValueAt(i, 3).toString());

            processes.add(new P_Class(burstTime, priority, quantum, pid));
        }


        processes.sort(Comparator.comparingInt(P_Class::getPriority));


        for (P_Class process : processes) {
            process.setState("Ready");
        }

        int currentTime = 0;
        Queue<P_Class> readyQueue = new LinkedList<>();

   
        for (P_Class process : processes) {
            readyQueue.add(process);
        }

   
        while (!readyQueue.isEmpty()) {
            P_Class process = readyQueue.poll();
            process.setState("Running"); 

            int startTime = currentTime;
            int completionTime = startTime + process.getBrustTime();
            int waitingTime = startTime;
            int turnaroundTime = completionTime - 0;

    
            model.setValueAt(waitingTime, process.getProcessID() - 1, 4);
            model.setValueAt(completionTime, process.getProcessID() - 1, 5);
            model.setValueAt(turnaroundTime, process.getProcessID() - 1, 3);

            
            process.setState("Completed");
            currentTime = completionTime;
        }

    
        processes.sort(Comparator.comparingInt(P_Class::getPriority));


        for (P_Class process : processes) {
            model.setValueAt(process.getState(), process.getProcessID() - 1, 6);  
        }

       
        for (int i = 0; i < rowCount; i++) {
            for (int j = i + 1; j < rowCount; j++) {
                if ((int) model.getValueAt(i, 5) > (int) model.getValueAt(j, 5)) {
           
                    for (int k = 0; k < model.getColumnCount(); k++) {
                        Object temp = model.getValueAt(i, k);
                        model.setValueAt(model.getValueAt(j, k), i, k);
                        model.setValueAt(temp, j, k);
                    }
                }
            }
        }
    }
}
