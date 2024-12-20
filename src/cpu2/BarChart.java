package cpu2;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BarChart extends JFrame {
    private List<String> processIDs; 
    private List<Integer> values;
    private String chartTitle; 

    public BarChart(List<String> processIDs, List<Integer> values, String chartTitle) {
        this.processIDs = processIDs;
        this.values = values;
        this.chartTitle = chartTitle;

        setTitle(chartTitle);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int padding = 50;
                int labelPadding = 30;
                int barWidth = 40;
                int maxBarHeight = getHeight() - padding * 2 - labelPadding;
                int maxValue = values.stream().max(Integer::compareTo).orElse(1);

                g.setColor(Color.BLACK);
                g.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding); 
                g.drawLine(padding, padding, padding, getHeight() - padding); 

           
                int xOffset = padding + barWidth;
                for (int i = 0; i < values.size(); i++) {
                    int barHeight = (int) ((values.get(i) / (double) maxValue) * maxBarHeight);
                    int x = xOffset + i * (barWidth + 10);
                    int y = getHeight() - padding - barHeight;

                    
                    g.setColor(new Color(4, 107, 255)); 
                    g.fillRect(x, y, barWidth, barHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, barWidth, barHeight);

                   
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 12));
                    g.drawString(processIDs.get(i), x + barWidth / 4, getHeight() - padding + 15);

                    
                    g.setColor(Color.DARK_GRAY);
                    g.setFont(new Font("Arial", Font.BOLD, 12));
                    g.drawString(String.valueOf(values.get(i)), x + barWidth / 4, y - 5);
                }

              
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.setColor(Color.BLACK);
                g.drawString(chartTitle, getWidth() / 2 - chartTitle.length() * 3, padding - 10);
            }
        };

        add(mainPanel);

      
        JButton okButton = new JButton("OK");
        okButton.setBounds(350, 430, 100, 30); 
        okButton.addActionListener(e -> {
 
            dispose();
        });

        mainPanel.setLayout(null); 
        mainPanel.add(okButton); 
    }
}
