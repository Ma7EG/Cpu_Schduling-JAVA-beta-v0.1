package cpu2;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JPanel;


public class panel extends javax.swing.JFrame {



private FCFS fcfsAlgorithm;

    public panel() {
      
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        setTitle("CPU Scheduling By@EELU_TEAM ");

        initComponents();
        
           Reset_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
          
                Brust_Spinner.setValue(0);
                Prio_Spinner.setValue(0);
                Qunt_Spinner.setValue(0);
                pID_Spinner.setValue(1);

               
                FCFS_CheckBox.setSelected(false);
                SJF_CheckBox.setSelected(false);
                RR_CheckBox.setSelected(false);
                PNP_CheckBox.setSelected(false);

            
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                model.setRowCount(0); 
            }
        });
        
        fcfsAlgorithm = new FCFS(Brust_Spinner, Prio_Spinner, Qunt_Spinner, pID_Spinner, Table);

    

Add_Button.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        // الحصول على القيم المدخلة من المستخدم
        int brustTime = (int) Brust_Spinner.getValue();
        int priority = (int) Prio_Spinner.getValue();
        int quantum = (int) Qunt_Spinner.getValue();
        int processID = (int) pID_Spinner.getValue();

        // تحديث قيمة pID_Spinner بعد إضافة العملية
        int currentValue = (int) pID_Spinner.getValue();
        pID_Spinner.setValue(currentValue + 1);

        // الحصول على النموذج الخاص بالجدول
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        
        if (model != null) {
            // إضافة صف جديد
            model.addRow(new Object[]{processID, brustTime, priority, 0, 0, 0, "Ready"}); // إضافة "Ready" إلى عمود "State"
        } else {
            System.out.println("Error: Table model is not DefaultTableModel.");
        }
    }
});



        Start_Button.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            
            if (FCFS_CheckBox.isSelected()) {
               
                fcfsAlgorithm.applyAlgorithm();
            } else {
               
            }
        }
    });
Start_Button.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
     
        int selectedCount = 0;
        if (FCFS_CheckBox.isSelected()) selectedCount++;
        if (PNP_CheckBox.isSelected()) selectedCount++;
        if (SJF_CheckBox.isSelected()) selectedCount++;
        if (RR_CheckBox.isSelected()) selectedCount++;  

       
        if (selectedCount > 1) {
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "بعد اذنك ياخويا حط خوارزمية واحدة مش فرح هو", "خطأ", JOptionPane.ERROR_MESSAGE);
            return; 
        }

       
        if (selectedCount == 0) {
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "يعني انت مش مختار خورازمية عايزني انفذلك ايه !!", "خطأ", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        int rowCount = model.getRowCount();
        if (rowCount == 0) {
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "انت مش حاطط عمليات ؟ مش هتبطل اهمال بقي !", "خطأ", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        if (FCFS_CheckBox.isSelected()) {
            FCFS fcfs = new FCFS(Brust_Spinner, Prio_Spinner, Qunt_Spinner, pID_Spinner, Table);
            fcfs.applyAlgorithm();
                      GanttChart.createAndShowGanttChart(Table, FCFS_CheckBox, PNP_CheckBox, RR_CheckBox, SJF_CheckBox);

        }

       
        else if (SJF_CheckBox.isSelected()) {
            SJF sjf = new SJF(Brust_Spinner, Prio_Spinner, Qunt_Spinner, pID_Spinner, Table);
            sjf.applyAlgorithm();
                      GanttChart.createAndShowGanttChart(Table, FCFS_CheckBox, PNP_CheckBox, RR_CheckBox, SJF_CheckBox);

        }
        else if (PNP_CheckBox.isSelected()) {
            PNP pnp = new PNP(Brust_Spinner, Prio_Spinner, Qunt_Spinner, pID_Spinner, Table);
            pnp.applyAlgorithm();
                       GanttChart.createAndShowGanttChart(Table, FCFS_CheckBox, PNP_CheckBox, RR_CheckBox, SJF_CheckBox);

        }
       
        else if (RR_CheckBox.isSelected()) {
            RR rr = new RR(Brust_Spinner, Prio_Spinner, Qunt_Spinner, pID_Spinner, Table);
            rr.applyAlgorithm();
            
            
            
        }

       
        List<String> processIDs = new ArrayList<>();
        List<Integer> burstTimes = new ArrayList<>();
        List<Integer> turnaroundTimes = new ArrayList<>();
        List<Integer> waitingTimes = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            processIDs.add("P" + model.getValueAt(i, 0)); 
            burstTimes.add((int) model.getValueAt(i, 1)); 
            turnaroundTimes.add((int) model.getValueAt(i, 3)); 
            waitingTimes.add((int) model.getValueAt(i, 4)); 
        }


       BarChart barChart = new BarChart(processIDs, turnaroundTimes, "Turnaround Time Chart");
     barChart.setVisible(true);

       
       new ProgressBarWindow(Table, Brust_Spinner).setVisible(true);
    }
});

        setupAdditionalComponents(); 
    }

    private void setupAdditionalComponents() {
      
     
    }

    @SuppressWarnings("unchecked")
                         
    private void initComponents() {
        jPanel2 = new javax.swing.JPanel();
  GanttChart_Check = new javax.swing.JCheckBox();
       Progress_Check = new javax.swing.JCheckBox();
       BartChart_Check = new javax.swing.JCheckBox();
       JCheckBox GanttChart_Check = new JCheckBox("Gantt Chart");
JCheckBox Progress_Check = new JCheckBox("Progress");
JCheckBox BartChart_Check = new JCheckBox("Bar Chart");


GanttChart_Check.setFont(new java.awt.Font("Arial", 0, 12));
Progress_Check.setFont(new java.awt.Font("Arial", 0, 12));
BartChart_Check.setFont(new java.awt.Font("Arial", 0, 12));

        Reset_Button =new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        Add_Button = new javax.swing.JButton();
        Qunt_Spinner = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pID_Spinner = new javax.swing.JSpinner();
        Brust_Spinner = new javax.swing.JSpinner();
        Prio_Spinner = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        FCFS_CheckBox = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        SJF_CheckBox = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        RR_CheckBox = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        PNP_CheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        Start_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(38, 130, 241));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Add_Button.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 13)); // NOI18N
        Add_Button.setText("Add Proccess");
        jPanel1.add(Add_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 460, 150, 50));

        Qunt_Spinner.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Qunt_Spinner.setModel(new javax.swing.SpinnerNumberModel());
        jPanel1.add(Qunt_Spinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 160, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Proccess Num :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 140, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Brust Time :");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 140, 40));

        pID_Spinner.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
       pID_Spinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        jPanel1.add(pID_Spinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 160, 40));

        Brust_Spinner.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Brust_Spinner.setModel(new javax.swing.SpinnerNumberModel());
        jPanel1.add(Brust_Spinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 160, 40));

        Prio_Spinner.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Prio_Spinner.setModel(new javax.swing.SpinnerNumberModel());
        jPanel1.add(Prio_Spinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 160, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Priority :");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 140, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Time quantum:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 140, 40));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cpu2/4854348.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 10, 460, 80));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 570));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(930, 500));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        jPanel2.add(Reset_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(780 - 120 - 20, 480));
        Reset_Button.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 15)); // NOI18N
        Reset_Button.setText("Reset");
        Reset_Button.setHorizontalAlignment(SwingConstants.CENTER);
        Reset_Button.setVerticalAlignment(SwingConstants.CENTER); 
        Reset_Button.setPreferredSize(new java.awt.Dimension(120, 50));
        
        jPanel4.setBackground(new java.awt.Color(255, 102, 102));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(775, 400, -1, -1));

        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(51, 102, 255)));

        jLabel2.setFont(new java.awt.Font("Yu Gothic Medium", 1, 18)); 
        jLabel2.setText("(FCFS) ");
        
        jPanel2.add(GanttChart_Check, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, -1, -1));
jPanel2.add(Progress_Check, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, -1, -1));
jPanel2.add(BartChart_Check, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 550, -1, -1));





// إضافة الـ CheckBoxes إلى jPanel2


    
      
JLabel creditsLabel = new JLabel("© Developed By: \"Mahmoud Ali, Hassan Abdelnaby,Mohamed Ibrahim, Osama Ahmed\"");

creditsLabel.setFont(new java.awt.Font("Arial", 1, 12)); 
creditsLabel.setForeground(new java.awt.Color(169, 169, 169)); 

jPanel2.add(creditsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480));




        FCFS_CheckBox.setText("ON");
        FCFS_CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FCFS_CheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(FCFS_CheckBox)))
                .addGap(44, 44, 44))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FCFS_CheckBox)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 180, 90));

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(51, 102, 255)));

        jLabel3.setFont(new java.awt.Font("Yu Gothic Medium", 1, 18)); // NOI18N
        jLabel3.setText("(SJF)");

        SJF_CheckBox.setText("ON");
        SJF_CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SJF_CheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(SJF_CheckBox)))
                .addGap(44, 44, 44))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SJF_CheckBox)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 180, 90));

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(51, 102, 255)));

        jLabel4.setFont(new java.awt.Font("Yu Gothic Medium", 1, 18)); 
        jLabel4.setText("(PR)");

        RR_CheckBox.setText("ON");
        RR_CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PR_CheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(RR_CheckBox)))
                .addGap(44, 44, 44))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RR_CheckBox)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 180, 90));

        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(51, 102, 255)));

        jLabel5.setFont(new java.awt.Font("Yu Gothic Medium", 1, 18)); // NOI18N
        jLabel5.setText("(PNP)");

        PNP_CheckBox.setText("ON");
        PNP_CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PNP_CheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(PNP_CheckBox)))
                .addGap(44, 44, 44))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PNP_CheckBox)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 180, 90));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Burst Time", "Priority", "Turnaround", "Waiting", "Completion","State"
            }
        ));
        jScrollPane1.setViewportView(Table);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 640, 370));

        Start_Button.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 13)); // NOI18N
        Start_Button.setText("Start");
        jPanel2.add(Start_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 480, 120, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 930, 570));

        pack();
    }// </editor-fold>                        

    private void FCFS_CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void SJF_CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void PR_CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void PNP_CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new panel().setVisible(true);
            }
        });
        
    }

      private javax.swing.JCheckBox GanttChart_Check;
       private javax.swing.JCheckBox Progress_Check;
       private javax.swing.JCheckBox BartChart_Check;
       
      
    private javax.swing.JButton Add_Button;
   private javax.swing.JCheckBox FCFS_CheckBox;
    private javax.swing.JCheckBox PNP_CheckBox;
    private javax.swing.JCheckBox RR_CheckBox;
    private javax.swing.JCheckBox SJF_CheckBox;
    private javax.swing.JButton Start_Button;
    private javax.swing.JTable Table;
    private javax.swing.JSpinner Brust_Spinner;
    private javax.swing.JSpinner Prio_Spinner;
    private javax.swing.JSpinner Qunt_Spinner;
    private javax.swing.JSpinner pID_Spinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
                  private javax.swing.JButton Reset_Button;
}
