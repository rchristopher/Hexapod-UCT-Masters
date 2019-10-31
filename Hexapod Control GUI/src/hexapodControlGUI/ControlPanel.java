package hexapodControlGUI;

import java.awt.AWTException;
import java.awt.Graphics2D;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chrros005
 */
public class ControlPanel extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    public ControlPanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
               
        
        initComponents();
        
        this.setResizable(true);
        
        this.setTitle("Hexapod Control 2019");
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true); 
      
    }
    
    public void addController(String name){
        
        if(this.controllerCombo.getItemAt(0).equals("No Controllers Found")){
            this.controllerCombo.removeItemAt(0);
        }
        
        this.controllerCombo.addItem(name);
    }

    public JCheckBox getLatchingJoystick() {
        return latchingJoystick;
    }

    public void setLatchingJoystick(JCheckBox latchingJoystick) {
        this.latchingJoystick = latchingJoystick;
    }
    
    public void addComms(String name){
        if(this.commsPortCombo.getItemAt(0).equals("No Ports Found")){
            this.commsPortCombo.removeItemAt(0);
        }
        
        this.commsPortCombo.addItem(name);
    }
    
    public javax.swing.JComboBox getControllerCombo(){
        return this.controllerCombo;
    }
    
    public javax.swing.JComboBox getCommsCombo(){
        return this.commsPortCombo;
    }
    
    public javax.swing.JButton getConnectButton(){
        return this.connectButton;
    }
    
    public javax.swing.JButton getDisconnectButton(){
        return this.disconnectButton;
    }
    
    public javax.swing.JButton getPlotButton(){
        return this.plotButton;
    }
    
    public javax.swing.JButton getComputerControlButton(){
        return this.computerControlButton;
    }

    public javax.swing.JLabel getRefreshButton(){
        return this.refreshButton;
    }
    
    public javax.swing.JLabel getTransStateLED(){
        return this.transmissionStateLED;
    }
    
    public javax.swing.JLabel getHexapodConnectedLED(){
        return this.hexapodConnectedLED;
    }
    
    public javax.swing.JLabel getChargingLED(){
        return this.chargingLED;
    }
        
    public javax.swing.JLabel getCCLED(){
        return this.CCLED;
    }
            
    public javax.swing.JLabel getCVLED(){
        return this.CVLED;
    }
    
    public javax.swing.JTextArea getChargingTxtLog(){
        return this.chargeTextArea;
    }
    
    public javax.swing.JToggleButton getChargeButton(){
        return this.chargeButton;
    }
    
    public javax.swing.JTextArea getTxtLog(){
        return this.txtLog;
    }
    
    public javax.swing.JLabel getVoltageLabel(){
        return this.batteryVoltage;
    }
    
     public javax.swing.JTextArea getErrorLog(){
        return this.errorTextArea;
    }
    
    public javax.swing.JButton getSendCustomButton(){
        return this.sendCustom;
    }
    
    public javax.swing.JButton getPCControlButton(){
        return this.computerControlButton;
    }
    
    public javax.swing.JTextField getCustomCommand(){
        return this.customCommand;
    }
    
    public javax.swing.JPanel getXYAxis(){
        return this.jPanelXYAxis;
    }
    
    public javax.swing.JCheckBox getShowIMU(){
        return this.jCheckBox1;
    }
    
    public javax.swing.JCheckBox getMotorsEnabled(){
        return this.motorsEnabled;
    }
    
    public javax.swing.JCheckBox getPitchEnabled(){
        return this.enablePitch;
    }
    
    public javax.swing.JCheckBox getRollEnabled(){
        return this.enableRoll;
    }
    
   public javax.swing.JCheckBox getPIDBothEnabled(){
        return this.enabledPIDBoth;
    }
    
    public javax.swing.JButton getSetPitchButton(){
        return this.setPIDPitch;
    }
    
    public javax.swing.JButton getSetRollButton(){
        return this.setPIDRoll;
    }
    
    public javax.swing.JButton getSetPIDBothButton(){
        return this.setPIDBoth;
    }
    
    public javax.swing.JButton getSetIMUFreqButton(){
        return this.setIMUFreq;
    }
    
    
    public javax.swing.JButton getResetButton(){
        return this.resetButton;
    }
        
    public void resetGUI(){
        this.commsPortCombo.removeAllItems();
        this.commsPortCombo.addItem("No Ports Found");
        
        this.controllerCombo.removeAllItems();
        this.controllerCombo.addItem("No Controllers Found");
        
        this.txtLog.setText("");
    }
    
    public void setXYAxis(int xPercentage, int yPercentage){
        if(this.jPanelXYAxis.isEnabled()){
            Graphics2D g2d = (Graphics2D)jPanelXYAxis.getGraphics();
            g2d.clearRect(1, 1, jPanelXYAxis.getWidth() - 2, jPanelXYAxis.getHeight() - 2);
            g2d.fillOval(xPercentage, yPercentage, 10, 10);
        }
    }
    
    public javax.swing.JProgressBar getHeightBar(){
        return this.heightBar;
    }
    
    public javax.swing.JProgressBar getSpeedBar(){
        return this.speedBar;
    }
 
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        controllerCombo = new javax.swing.JComboBox<>();
        commsPortCombo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        disconnectButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        sendCustom = new javax.swing.JButton();
        customCommand = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanelXYAxis = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        heightBar = new javax.swing.JProgressBar();
        jLabel5 = new javax.swing.JLabel();
        speedBar = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        plotButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        transmissionStateLED = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        errorTextArea = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();
        clearErrorLogButton = new javax.swing.JButton();
        computerControlButton = new javax.swing.JButton();
        latchingJoystick = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        hexapodConnectedLED = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        setIMUFreq = new javax.swing.JButton();
        CVLED = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        batteryVoltage = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        CCLED = new javax.swing.JLabel();
        chargingLED = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        chargeButton = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        chargeTextArea = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        motorsEnabled = new javax.swing.JCheckBox();
        enablePitch = new javax.swing.JCheckBox();
        enableRoll = new javax.swing.JCheckBox();
        setPIDRoll = new javax.swing.JButton();
        setPIDPitch = new javax.swing.JButton();
        setPIDBoth = new javax.swing.JButton();
        enabledPIDBoth = new javax.swing.JCheckBox();
        resetButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jLabel7.setText("Transmission State");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Hexapod Control");

        controllerCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Controllers Found" }));

        commsPortCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Ports Found" }));

        jLabel1.setText("Controller:");

        jLabel2.setText("Comms Port:");

        refreshButton.setText("refresh");

        connectButton.setText("Connect");

        txtLog.setEditable(false);
        txtLog.setColumns(20);
        txtLog.setLineWrap(true);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        disconnectButton.setText("Disconnect");

        jLabel3.setText("Custom Command:");

        sendCustom.setText("Send");

        customCommand.setToolTipText("Decimal or Hex, seperated into bytes with a space");

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("X/Y Axis");

        jPanelXYAxis.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelXYAxis.setMinimumSize(new java.awt.Dimension(110, 100));
        jPanelXYAxis.setPreferredSize(new java.awt.Dimension(110, 110));

        javax.swing.GroupLayout jPanelXYAxisLayout = new javax.swing.GroupLayout(jPanelXYAxis);
        jPanelXYAxis.setLayout(jPanelXYAxisLayout);
        jPanelXYAxisLayout.setHorizontalGroup(
            jPanelXYAxisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 108, Short.MAX_VALUE)
        );
        jPanelXYAxisLayout.setVerticalGroup(
            jPanelXYAxisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton2.setText("Clear Text Log");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        heightBar.setOrientation(1);
        heightBar.setValue(50);

        jLabel5.setText("Height");

        speedBar.setOrientation(1);
        speedBar.setToolTipText("");
        speedBar.setValue(50);
        speedBar.setPreferredSize(new java.awt.Dimension(146, 17));

        jLabel6.setText("Speed");

        plotButton.setText("Open IMU Plot");
        plotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("State");

        transmissionStateLED.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        transmissionStateLED.setForeground(new java.awt.Color(255, 0, 0));
        transmissionStateLED.setText("•");

        errorTextArea.setEditable(false);
        errorTextArea.setColumns(20);
        errorTextArea.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        errorTextArea.setLineWrap(true);
        errorTextArea.setRows(4);
        errorTextArea.setTabSize(1);
        errorTextArea.setText(" ______________Error Log_____________");
        errorTextArea.setToolTipText("Hexapod Errors");
        jScrollPane2.setViewportView(errorTextArea);

        jCheckBox1.setText("Show IMU Data");

        clearErrorLogButton.setText("Clear Error Log");
        clearErrorLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearErrorLogButtonActionPerformed(evt);
            }
        });

        computerControlButton.setText("Computer Control");

        latchingJoystick.setText("Latching Joystick");

        jLabel9.setText("<html>Hexapod <br/>\nConnected\n</html>");

        hexapodConnectedLED.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        hexapodConnectedLED.setForeground(new java.awt.Color(255, 0, 0));
        hexapodConnectedLED.setText("•");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        setIMUFreq.setText("Set IMU Freq.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(controllerCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commsPortCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelXYAxis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel4)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(heightBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(speedBar, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel5)
                                        .addGap(65, 65, 65)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(transmissionStateLED))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addGap(62, 62, 62)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(hexapodConnectedLED)))
                                .addGap(18, 18, 18)
                                .addComponent(clearErrorLogButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(plotButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox1)
                            .addComponent(computerControlButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(setIMUFreq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(disconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(latchingJoystick)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customCommand, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(controllerCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commsPortCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(refreshButton))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(disconnectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sendCustom)
                    .addComponent(customCommand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(latchingJoystick)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 16, Short.MAX_VALUE)
                                .addComponent(clearErrorLogButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(transmissionStateLED, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(1, 1, 1))
                                    .addComponent(jLabel6))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(heightBar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(jPanelXYAxis, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                            .addComponent(speedBar, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(hexapodConnectedLED, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(setIMUFreq)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(plotButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(computerControlButton)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CVLED.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        CVLED.setForeground(new java.awt.Color(255, 0, 0));
        CVLED.setText("•");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Voltage: ");

        batteryVoltage.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        batteryVoltage.setText("0.00");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("V");

        CCLED.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        CCLED.setForeground(new java.awt.Color(255, 0, 0));
        CCLED.setText("•");

        chargingLED.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        chargingLED.setForeground(new java.awt.Color(255, 0, 0));
        chargingLED.setText("•");

        jLabel13.setText("Charging");

        jLabel14.setText("CC");

        jLabel15.setText("CV");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Battery Status");

        chargeButton.setText("Charge");

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        chargeTextArea.setColumns(20);
        chargeTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        chargeTextArea.setLineWrap(true);
        chargeTextArea.setRows(5);
        jScrollPane3.setViewportView(chargeTextArea);

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        motorsEnabled.setText("Motors Enabled");

        enablePitch.setSelected(true);
        enablePitch.setText("Pitch Stab. Enabled");

        enableRoll.setSelected(true);
        enableRoll.setText("Roll Stab. Enabled");

        setPIDRoll.setText("Set PID");

        setPIDPitch.setText("Set PID");

        setPIDBoth.setText("Set PID");

        enabledPIDBoth.setSelected(true);
        enabledPIDBoth.setText("Both Stab. Enabled");

        resetButton.setText("RESET HEXAPOD");

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel14)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(chargingLED)
                        .addGap(27, 27, 27)
                        .addComponent(CCLED)
                        .addGap(23, 23, 23)
                        .addComponent(CVLED))
                    .addComponent(jLabel10)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batteryVoltage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(chargeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(enableRoll)
                            .addComponent(motorsEnabled)
                            .addComponent(enablePitch)
                            .addComponent(enabledPIDBoth))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(setPIDPitch)
                            .addComponent(setPIDRoll)
                            .addComponent(setPIDBoth)))
                    .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addGap(4, 4, 4)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chargingLED, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CCLED, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CVLED, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(batteryVoltage)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(chargeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(motorsEnabled)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enablePitch)
                    .addComponent(setPIDPitch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enableRoll)
                    .addComponent(setPIDRoll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(setPIDBoth)
                    .addComponent(enabledPIDBoth))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resetButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearErrorLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearErrorLogButtonActionPerformed
        errorTextArea.setText("");
    }//GEN-LAST:event_clearErrorLogButtonActionPerformed

    private void plotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotButtonActionPerformed

    }//GEN-LAST:event_plotButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        txtLog.setText("");

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        customCommand.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        chargeTextArea.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControlPanel().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CCLED;
    private javax.swing.JLabel CVLED;
    private javax.swing.JLabel batteryVoltage;
    private javax.swing.JToggleButton chargeButton;
    private javax.swing.JTextArea chargeTextArea;
    private javax.swing.JLabel chargingLED;
    private javax.swing.JButton clearErrorLogButton;
    private javax.swing.JComboBox<String> commsPortCombo;
    private javax.swing.JButton computerControlButton;
    private javax.swing.JButton connectButton;
    private javax.swing.JComboBox<String> controllerCombo;
    private javax.swing.JTextField customCommand;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JCheckBox enablePitch;
    private javax.swing.JCheckBox enableRoll;
    private javax.swing.JCheckBox enabledPIDBoth;
    private javax.swing.JTextArea errorTextArea;
    private javax.swing.JProgressBar heightBar;
    private javax.swing.JLabel hexapodConnectedLED;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelXYAxis;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox latchingJoystick;
    private javax.swing.JCheckBox motorsEnabled;
    private javax.swing.JButton plotButton;
    public javax.swing.JLabel refreshButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton sendCustom;
    private javax.swing.JButton setIMUFreq;
    private javax.swing.JButton setPIDBoth;
    private javax.swing.JButton setPIDPitch;
    private javax.swing.JButton setPIDRoll;
    private javax.swing.JProgressBar speedBar;
    private javax.swing.JLabel transmissionStateLED;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables
}
