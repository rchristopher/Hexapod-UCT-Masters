/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexapodControlGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author chrros005
 */
public class PlotFrameWindow extends javax.swing.JFrame {

    
    /** The time series data. */
    public XYSeries pitchSeries;
    public XYSeries rollSeries;
    public XYSeries yawSeries;
   
    private List<Double> timestampData = new ArrayList<Double>();
    private List<Double> pitchDataY = new ArrayList<Double>();
    private List<Double> rollDataY = new ArrayList<Double>();
    private List<Double> yawDataY = new ArrayList<Double>();
    
    public XYSeries slopePitchSeries;
    public XYSeries slopeRollSeries;
    
    private List<Double> slopePitchData = new ArrayList<Double>();
    private List<Double> slopeRollData = new ArrayList<Double>();
    

    private boolean dataPaused = false;
    private int dataCount = 0;
    private int pausePos = 0;
    
    /**
     * Creates new form PlotFrameWindow
     */
    public PlotFrameWindow() {
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initComponents();
        
        this.setTitle("Data Plot");
        
        this.setLocationRelativeTo(null);
        
        this.pitchSeries = new XYSeries("Hexapod Pitch Data");
        this.rollSeries = new XYSeries("Hexapod Roll Data");
        this.yawSeries = new XYSeries("Hexapod Yaw Data");
        
        this.slopePitchSeries = new XYSeries("Slope Pitch Data");
        this.slopeRollSeries = new XYSeries("Slope Roll Data");
                
        
        
        final XYSeriesCollection dataset = new XYSeriesCollection (this.pitchSeries);
        dataset.addSeries(this.rollSeries);
       // dataset.addSeries(this.yawSeries);
       // dataset.addSeries(this.slopePitchSeries);
        //dataset.addSeries(this.slopeRollSeries);
        final JFreeChart chart = createChart(dataset);
        
        
        final JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    if(pauseButton.getText().equals("Pause")){
                       pauseButton.setText("Resume"); 
                       pausePos=dataCount;
                       dataPaused = true;
                    }else{
                        pauseButton.setText("Pause");
                        for(int i = pausePos; i < dataCount; i++){
                            pitchSeries.add(timestampData.get(i), pitchDataY.get(i));
                            rollSeries.add(timestampData.get(i), rollDataY.get(i));
                            yawSeries.add(timestampData.get(i), yawDataY.get(i));
                            slopePitchSeries.add(timestampData.get(i), slopePitchData.get(i));
                            slopeRollSeries.add(timestampData.get(i), slopeRollData.get(i));
                        }
                        dataPaused = false;
                    } 
                }
          } );
        
        final JButton clearbutton = new JButton("Clear");
        clearbutton.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    if(JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the log?") == 0){
                        pitchSeries.clear();
                        rollSeries.clear();
                        yawSeries.clear();
                        slopePitchSeries.clear();
                        slopeRollSeries.clear();
                        dataCount = 0;
                        pausePos = 0;
                    }

                }
          } );
        
        final JButton saveButton = new JButton("Save CSV");
        saveButton.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    JFrame parentFrame = new JFrame();

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save.");   
                    fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File","csv"));
                    
                    int userSelection = fileChooser.showSaveDialog(parentFrame);
                  
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        String filename = fileToSave.toString();
                        if (!fileToSave.toString().endsWith(".csv")){
                            filename += ".csv";
                        }
                        
                        FileWriter writer = null;
                        try {
                            writer = new FileWriter(filename);
                        } catch (IOException ex) {
                            Logger.getLogger(PlotFrameWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                         
                        try {
                            CSVUtils.writeLine(writer, Arrays.asList("Timestamp", "Pitch (rad)", "Roll (rad)", "Slope Pitch (rad)", "Slope Roll (rad)"));
                            for(int i = 0; i < timestampData.size(); i++){
                                List<String> list = new ArrayList<String>();

                                list.add(timestampData.get(i).toString());
                                list.add(pitchDataY.get(i).toString());
                                list.add(rollDataY.get(i).toString());
                                list.add(yawDataY.get(i).toString());
                                
                                list.add(slopePitchData.get(i).toString());
                                list.add(slopeRollData.get(i).toString());

                                CSVUtils.writeLine(writer, list);
                            }
                        
                            writer.flush();
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(PlotFrameWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                                
                    }
                }
          } );

        final ChartPanel chartPanel = new ChartPanel(chart);

        final JPanel bottomButtons = new JPanel(new FlowLayout());
        bottomButtons.add(pauseButton);
        bottomButtons.add(clearbutton);
        bottomButtons.add(saveButton);
        
        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
        content.add(bottomButtons, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);
       
    }
    
    public void addData(double timestamp, double pY, double rY, double yY, double slopeP, double slopeR){
        dataCount++;
        
        double timestampSeconds = timestamp/10;
        
        timestampData.add(timestampSeconds);

        pitchDataY.add(pY);
        rollDataY.add(rY);
        yawDataY.add(yY);
        
        slopePitchData.add(slopeP);
        slopeRollData.add(slopeR);
        
        if(!dataPaused){
            pitchSeries.add(timestampSeconds, pY);
            rollSeries.add(timestampSeconds, rY);
            yawSeries.add(timestampSeconds, yY);
            
            slopePitchSeries.add(timestampSeconds, slopeP);
            slopeRollSeries.add(timestampSeconds, slopeR);
        }
        
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return A sample chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createXYLineChart(
            "Hexapod Pitch and Roll", 
            "Accelerometer Timestamp (s)", 
            "Angle (rad)",
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            false,
            false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        //axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(-1, 1); 
        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlotFrameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlotFrameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlotFrameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlotFrameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlotFrameWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
