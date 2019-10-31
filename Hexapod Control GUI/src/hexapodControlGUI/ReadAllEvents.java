package hexapodControlGUI;

/* Joystick imports */
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/* Serial imports */
import gnu.io.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class shows how to use the event queue system in JInput. It will show
 * how to get the controllers, how to get the event queue for a controller, and
 * how to read and process events from the queue.
 * 
 * @author Endolf
 */

public final class ReadAllEvents extends TimerTask implements SerialPortEventListener{
    
        @Override
        public void run() {
            if(this.timerRunning && !this.shouldCharging){
                sendCommsPing();
            }
        }
        
        private boolean timerRunning = false;
        private Timer timer;
        private int hexapodFailedConnections = 0;
                
        private double xAxis;
        private double yAxis;
        private double zAxis;
        private double oldZAxis;
        private double hatValue;
        private boolean hatDown;
        private int sliderValue;
        private int selectedController;
        private int selectedControllerOffset;
        private double movementError;
        private boolean connected;
        private int button;
        private boolean buttonDown;
        
        private boolean motorsEnabled = false;
        private boolean pitchEnabled = true;
        private boolean rollEnabled = true;
        private float pidKp = 1.08f;
        private float pidKi = 2.2f;
        private float pidKd = 0.027f;
        private float IMUFreq = 1.0f;
        
        private boolean charging = false;
        private boolean shouldCharging = false;
        
        private boolean latching = false;
        
        private boolean dataReturned = false;
        private int dataSendCount = 0;
        
        private int currentDirection = 0;
        private int currentSpeed = 50;
        private int currentHeight = 50;
        
        private static final int BAUD = 19200;
        
        //passed from main GUI
        ControlPanel window = null;
        public ComputerControlWindow compControlWindow = new ComputerControlWindow();

        //for containing the ports that will be found
        private Enumeration ports = null;
        //map the port names to CommPortIdentifiers
        private HashMap portMap = new HashMap();

        //this is the object that contains the opened port
        private CommPortIdentifier selectedPortIdentifier = null;
        private SerialPort serialPort = null;

        //input and output streams for sending and receiving data
        private InputStream input = null;
        private OutputStream output = null;

        //the timeout value for connecting with the port
        final static int TIMEOUT = 2000;

        //some ascii values for for certain things
        final static int SPACE_ASCII = 32;
        final static int DASH_ASCII = 45;
        final static int NEW_LINE_ASCII = 10;

        //a string for recording what goes on in the program
        //this string is written to the GUI
        String logText = "";

        Controller[] controllers;
        
        public PlotFrameWindow plotWindow;
        
        
        private String[] controlTableNames = {"ID","Length","Error","Model Number(L)", "Model Number(H)","Version of Firmware","ID","Baud Rate","Return Delay Time","CW Angle Limit(L)","CW Angle Limit(H)",
                                                "CCW Angle Limit(L)","CCW Angle Limit(H)","NULL", "Highest Limit Temperature","Lowest Limit Voltage","Highest Limit Voltage","Max Torque(L)",
                                                "Max Torque(H)","Status Return Level","Alarm LED","Alarm Shutdown","NULL","NULL","NULL","NULL","NULL","Torque Enable","LED","CW Compliance Margin","CCW Compliance Margin",
                                                "CW Compliance Slope","CCW Compliance Slope","Goal Position(L)","Goal Position(H)","Moving Speed(L)","Moving Speed(H)","Torque Limit(L)",
                                                "Torque Limit(H)","Present Position(L)","Present Position(H)","Present Speed(L)","Present Speed(H)","Present Load(L)","Present Load(H)",
                                                "Present Voltage","Present Temperature","Registered Instruction","NULL","Moving","Lock","Punch(L)","Punch(H)","Checksum"};
               
	public ReadAllEvents() {
            
            if(timer == null){
                timer = new Timer(true);
                try{
                    timer.scheduleAtFixedRate(this, 0, 1*3000);
                }catch(Exception e){
                    System.out.println(e);
                }
            }
                        
            this.xAxis = 0;
            this.yAxis = 0;
            this.zAxis = 0;
            this.oldZAxis = 0;
            this.hatValue = 0;
            this.hatDown = false;
            this.button = 0;
            this.buttonDown = false;
            this.sliderValue = 50;
            this.selectedController = 0;
            this.selectedControllerOffset = 0;
            this.movementError = 0.0;
            this.connected = false;
            
            this.plotWindow = new PlotFrameWindow();
            
            this.currentSpeed = 50;
            this.currentDirection = 0;
            this.currentHeight = 50;
            
            window = new ControlPanel();
            
            window.getErrorLog().setForeground(Color.red);
            
            controllers = null;
            
            window.getConnectButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    connect();
                    if (connected == true)
                    {
                        if (initIOStream() == true)
                        {
                            initListener();
                        }
                    }
                }
            } );
            
             window.getCommsCombo().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    if (connected == true)
                    {
                        disconnect();
                    }
                }
            } );
            
            window.getDisconnectButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                  disconnect();
                } 
            } );   
            
            window.getComputerControlButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    compControlWindow.setLocationRelativeTo(window);
                    compControlWindow.setVisible(true);
                } 
            } );  
            
            
            window.getPlotButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                  if(plotWindow == null){
                    plotWindow = new PlotFrameWindow();
                  }else{
                    plotWindow.dispose();
                    plotWindow = new PlotFrameWindow();
                  }
                  plotWindow.setVisible(true);
                } 
            } );   
            
            window.getSendCustomButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                  sendCustomCommand(window.getCustomCommand().getText());
                } 
            } ); 
            
            window.getRefreshButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    disconnect();
                    startUpRefresh();
                }
            });
            
            window.getChargeButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    if(window.getChargeButton().isSelected()){
                        window.getChargeButton().setSelected(false);
                        shouldCharging = true;
                        startCharging();
                    }else{
                        window.getChargeButton().setSelected(true);
                        stopCharging();
                        shouldCharging = false;
                    }
                } 
            } ); 
            
            window.getCustomCommand().addKeyListener(new KeyAdapter(){
                public void keyPressed(KeyEvent e){
                    if(Character.isLetter(e.getKeyChar()) || Character.isDigit(e.getKeyChar())){
                    }else{
                        //e.consume();
                        //window.getCustomCommand().setText(window.getCustomCommand().getText().substring(0, window.getCustomCommand().getText().length()-1));
                    }
                    if(e.getKeyCode() == 10){
                        if(connected){
                            sendCustomCommand(window.getCustomCommand().getText());
                        }
                    }
                }
            });
            
            window.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    if(connected){
                        window.getXYAxis().setEnabled(false);
                        disconnect();
                    }
                    System.exit(0);
                }
            });
            
            /** COMPUTER CONTROL BUTTONS */
            compControlWindow.getUp().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    currentDirection = 1;
                    button = 0;
                    sendData(true);
                } 
            } );
            
            compControlWindow.getDown().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    currentDirection = 2;
                    button = 0;
                    sendData(true);
                } 
            } );
             
            compControlWindow.getLeft().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    currentDirection = 3;
                    button = 0;
                    sendData(true);
                } 
            } );
            
            compControlWindow.getRight().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    currentDirection = 4;
                    button = 0;
                    sendData(true);
                } 
            } );
            
            compControlWindow.getStop().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    currentDirection = 0;
                    button = 0;
                    sendData(true);
                } 
            } );
            compControlWindow.getStandButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    currentDirection = 0;
                    button = 2;
                    sendData(true);
                } 
            } );
            
            compControlWindow.getSpeedSlider().addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                  int value = compControlWindow.getSpeedSlider().getValue();
                        currentSpeed = (int)value;
                        sendData(true);
                }

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
                
             });   
            /*****/
            
            window.getResetButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    int dialogResult = JOptionPane.showConfirmDialog (window, "Are you sure you want to reset?","Warning",JOptionPane.YES_NO_OPTION);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        currentDirection = 0;
                        button = 55;
                        sendData(true);
                    }
                } 
            } );
            
            window.getMotorsEnabled().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    if(motorsEnabled != window.getMotorsEnabled().isSelected()){
                        motorsEnabled = window.getMotorsEnabled().isSelected();
                        toggleMotors(motorsEnabled);
                    }
                } 
            } );
            
            window.getPitchEnabled().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    if(pitchEnabled != window.getPitchEnabled().isSelected()){
                        pitchEnabled = window.getPitchEnabled().isSelected();
                        togglePID(pitchEnabled, 1);
                    }
                } 
            } );
            
            window.getRollEnabled().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    if(rollEnabled != window.getRollEnabled().isSelected()){
                        rollEnabled = window.getRollEnabled().isSelected();
                        togglePID(rollEnabled, 2);
                    }
                } 
            } );
            
            window.getPIDBothEnabled().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    togglePID(pitchEnabled, 1);
                    togglePID(rollEnabled, 2);
                } 
            } );
            
            window.getSetPIDBothButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    setPIDParameters(3);
                } 
            } );
            
            window.getSetPitchButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    setPIDParameters(1);
                } 
            } );
            
            
            window.getSetRollButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    setPIDParameters(2);
                } 
            } );
            
            window.getSetIMUFreqButton().addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    setIMUFrequency();
                } 
            } );
            
            compControlWindow.getSpeedSlider().addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    
                }
            });
                        
            startUpRefresh();
                        
            while (true) {
                System.out.print("");
                if(connected){
                                  
                    /* Poll the selected controller */
                    if( !controllers[selectedController].poll() ){
                        JOptionPane.showMessageDialog(null,"Controlled Disconnected");
                        startUpRefresh();
                        break;
                    }
                    
                    /* Get the controllers event queue */
                    EventQueue queue = controllers[selectedController].getEventQueue();

                    /* Create an event object for the underlying plugin to populate */
                    Event event = new Event();
                    
                    /* For each object in the queue */
                    while (queue.getNextEvent(event)) {
                        StringBuffer buffer = new StringBuffer("");
                        buffer.append("Time: ");
                        buffer.append(event.getNanos()).append(", ");
                        Component comp = event.getComponent();
                        buffer.append(comp.getName()).append(" changed to ");
                        double value = Math.round(event.getValue()*100.0)/100.0;

                        buffer.append(value);
                                               
                        if(dataReturned){
                            this.button = 0;
                        }

                        if(event.getComponent().toString().equals("X Axis")){
                            if(Math.abs(this.xAxis - event.getValue()) > this.movementError){
                                this.xAxis = event.getValue();
                            }
                        }else if(event.getComponent().toString().equals("Y Axis")){
                            if(Math.abs(this.yAxis - event.getValue()) > this.movementError){
                                this.yAxis = event.getValue();
                            }
                        }else if(event.getComponent().toString().equals("Z Rotation")){
                            if(Math.abs(this.zAxis - event.getValue()) > this.movementError){
                                this.zAxis = event.getValue();
                            }
                        }else if(event.getComponent().toString().equals("Hat Switch")){
                            this.hatValue = event.getValue();
                        }else if(event.getComponent().toString().equals("Slider")){
                            if(Math.abs(this.sliderValue - event.getValue()) > this.movementError){
                                this.sliderValue = ((int)Math.round(event.getValue()*100.0)-100)/-2;
                            }
                        }else if(event.getComponent().toString().contains("Button")){
                            String[] btn = event.getComponent().toString().split(" ");
                            int buttonNum = Integer.parseInt(btn[1]);
                           
                            this.buttonDown = ((int)event.getValue() != 0);
                           // System.out.println(this.buttonDown);
                                                       
                            switch(buttonNum){
                                case 0:
                                    this.button = 1;
                                    break;
                                    
                                case 1:
                                    this.button = 2;
                                    break;
                                    
                                case 2:
                                    this.button = 3;
                                    break;
                                    
                                case 3:
                                    this.button = 4;
                                    break;
                                    
                                case 4:
                                    this.button = 5;
                                    break;
                                    
                                case 5:
                                    this.button = 6;
                                    break;
                                    
                                case 6:
                                    this.button = 7;
                                    break;
                                    
                                case 7:
                                    this.button = 8;
                                    break;  
                                default:
                                    this.button = 0;
                                    break;
                            }  
                        }
                    }   		

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    /* AT THIS POINT ALL VARIABLES ARE SET TO THEIR CURRENT JOYSTICK VALUES */
                    this.xAxis = Math.round(this.xAxis*100.0)/100.0;
                    this.yAxis = Math.round(this.yAxis*100.0)/100.0;
                    this.zAxis = Math.round(this.zAxis*100.0)/100.0;
                    //slider already adjusted
                    window.getSpeedBar().setValue(this.sliderValue);
                    
                 //  System.out.println("X: "+this.xAxis + ", Y: "+this.yAxis+", Z: "+this.zAxis+", H: "+this.hatValue+", S: "+this.sliderValue);
                               
                   //GUI FOR JOYSTICK
                   window.setXYAxis((int)(((2 - (1 - xAxis)) * 100) / 2), (int)(((2 - (1 - yAxis)) * 100) / 2));
                                   
                    if(!window.getLatchingJoystick().isSelected() || this.button == 1){
                        sendMovementData(false);
                    }
                                      
                }else{
                    disconnect();
                }
            }
	}

        public void setPIDParameters(int pitchRoll){
            JTextField kp = new JTextField();
            JTextField ki = new JTextField();
            JTextField kd = new JTextField();
            Object[] message = {
                "Kp:", kp,
                "Ki:", ki,
                "Kd:", kd
            };
            kp.setText(this.pidKp+"");
            ki.setText(this.pidKi+"");
            kd.setText(this.pidKd+"");

            int option = JOptionPane.showConfirmDialog(window, message, "Enter "+(pitchRoll == 1?"Pitch": pitchRoll == 2?"Roll":"Both")+" Parameters", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (!kp.getText().equals("") && !ki.getText().equals("") && !kd.getText().equals("")) {
                    try{
                        boolean send = false;
                        float _kp = Float.parseFloat(kp.getText());
                        if(_kp != this.pidKp || this.pidKp == 0){
                             this.pidKp = (float)_kp;
                             send = true;
                        }
                        float _ki = Float.parseFloat(ki.getText());
                        if(_ki != this.pidKi || this.pidKi == 0){
                             this.pidKi = (float)_ki;
                             send = true;
                        }
                        float _kd = Float.parseFloat(kd.getText());
                        if(_kd != this.pidKd || this.pidKd == 0){
                             this.pidKd = (float)_kd;
                             send = true;
                        }
                        
                        send = true;
                        
                        if(send){
                            byte[] kpBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(this.pidKp).array();
                            byte[] kiBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(this.pidKi).array();
                            byte[] kdBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(this.pidKd).array();
                            byte[] data1 = {(byte) 255, (byte)222, (byte)pitchRoll,1, kpBytes[0], kpBytes[1],kpBytes[2],kpBytes[3],0,0}; //
                            byte[] data2 = {(byte) 255, (byte)222, (byte)pitchRoll,2, kiBytes[0], kiBytes[1],kiBytes[2],kiBytes[3],0,0}; //
                            byte[] data3 = {(byte) 255, (byte)222, (byte)pitchRoll,3, kdBytes[0], kdBytes[1],kdBytes[2],kdBytes[3],0,0}; //

                            int checksumSend = 0;
                            for (int i = 0; i < data1.length; i++) {
                                checksumSend += data1[i];
                            }
                            data1[data1.length-1] = (byte) checksumSend;
                            
                            checksumSend = 0;
                            for (int i = 0; i < data2.length; i++) {
                                checksumSend += data2[i];
                            }
                            data2[data2.length-1] = (byte) checksumSend;
                            
                            checksumSend = 0;
                            for (int i = 0; i < data3.length; i++) {
                                checksumSend += data3[i];
                            }
                            data3[data3.length-1] = (byte) checksumSend;

                            try {
                                output.write(data1);
                                output.flush();
                                Thread.sleep(100);
                                output.write(data2);
                                output.flush();
                                Thread.sleep(100);
                                output.write(data3);
                                output.flush();
                            } catch (Exception e) {
                                logText = "Failed to write data. (" + e.toString() + ")";
                                window.getChargingTxtLog().setForeground(Color.red);
                                window.getChargingTxtLog().append(logText + "\n");
                                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
                            }
                        }
                        
                    }catch(Exception e){
                         logText = "Failed to write data. (" + e.toString() + ")";
                                window.getChargingTxtLog().setForeground(Color.red);
                                window.getChargingTxtLog().append(logText + "\n");
                                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
                    }
                }
            } else {
               
            }
        }
        
        public void setIMUFrequency(){
            JTextField freq = new JTextField();
            JLabel label = new JLabel("Frequency must be 0.1ms or larger.");
            Object[] message = {
                "Frequency (ms):", freq,
                label
            };
            freq.setText(this.IMUFreq+"");
            
            

            int option = JOptionPane.showConfirmDialog(window, message, "Enter IMU Transmission Frequency (ms)", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (!freq.getText().equals("")) {
                    try{
                        
                        float _freq = Float.parseFloat(freq.getText());
                       
                        if(_freq != this.IMUFreq || this.IMUFreq == 0){
                             this.IMUFreq = (float)_freq;
                        }
                        
                            byte[] freqBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(this.IMUFreq).array();
                            byte[] data1 = {(byte) 255, (byte)221, freqBytes[0], freqBytes[1],freqBytes[2],freqBytes[3],0,0,0,0}; //

                            int checksumSend = 0;
                            for (int i = 0; i < data1.length; i++) {
                                checksumSend += data1[i];
                            }
                            data1[data1.length-1] = (byte) checksumSend;
                            
                            try {
                                output.write(data1);
                                output.flush();                   
                            } catch (Exception e) {
                                logText = "Failed to write data. (" + e.toString() + ")";
                                window.getChargingTxtLog().setForeground(Color.red);
                                window.getChargingTxtLog().append(logText + "\n");
                                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
                            }
                        
                    }catch(Exception e){
                         logText = "Failed to write data. (" + e.toString() + ")";
                                window.getChargingTxtLog().setForeground(Color.red);
                                window.getChargingTxtLog().append(logText + "\n");
                                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
                    }
                }
            } else {
               
            }
        }
        
        public void togglePID(boolean enabled, int pitchRoll){ //pitch roll must be 1 or 2 for pitch or roll respectively.
            byte[] data = {(byte) 255, (byte) 251, (byte)(enabled ? 1 : 0), (byte)pitchRoll,0,0,0,0,0,0}; //toggle motors command

            int checksumSend = 0;
            for (int i = 0; i < data.length; i++) {
                checksumSend += data[i];
            }
            data[data.length-1] = (byte) checksumSend;

            try {
                output.write(data);
                output.flush();
               
            } catch (Exception e) {
                logText = "Failed to write data. (" + e.toString() + ")";
                window.getChargingTxtLog().setForeground(Color.red);
                window.getChargingTxtLog().append(logText + "\n");
                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
            }
        }
        
        public void toggleMotors(boolean enabled){
            byte[] data = {(byte) 255, (byte) 250, (byte)(enabled ? 1 : 0), 0,0,0,0,0,0,0}; //toggle motors command

            int checksumSend = 0;
            for (int i = 0; i < data.length; i++) {
                checksumSend += data[i];
            }
            data[data.length-1] = (byte) checksumSend;

            try {
                output.write(data);
                output.flush();
            } catch (Exception e) {
                logText = "Failed to write data. (" + e.toString() + ")";
                window.getChargingTxtLog().setForeground(Color.red);
                window.getChargingTxtLog().append(logText + "\n");
                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
            }
        }
        
        public void startCharging(){
            byte[] data = {(byte) 255, (byte) 248, 55,1,0,0,0,0,0,0}; //charging start command

            int checksumSend = 0;
            for (int i = 0; i < data.length; i++) {
                checksumSend += data[i];
            }
            data[data.length-1] = (byte) checksumSend;

            try {
                output.write(data);
                output.flush();
                window.getChargingTxtLog().append("Starting Charging...\n");
                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
            } catch (Exception e) {
                logText = "Failed to write data. (" + e.toString() + ")";
                window.getChargingTxtLog().setForeground(Color.red);
                window.getChargingTxtLog().append(logText + "\n");
                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
            }
        }
        
        public void stopCharging(){
            byte[] data = {(byte) 255, (byte) 248, 55,0,0,0,0,0,0,0}; //charging stop command

            int checksumSend = 0;
            for (int i = 0; i < data.length; i++) {
                checksumSend += data[i];
            }
            data[data.length-1] = (byte) checksumSend;

            try {
                output.write(data);
                output.flush();
                window.getChargingTxtLog().append("Stopping Charging...\n");
                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
            } catch (Exception e) {
                logText = "Failed to write data. (" + e.toString() + ")";
                window.getChargingTxtLog().setForeground(Color.red);
                window.getChargingTxtLog().append(logText + "\n");
                window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
            }
        }
        
        /*public void sendComputerControl(int direction, int height, int speed){
                
                this.dataReturned = false;
                this.dataSendCount = 0;
            
                byte[] data = { (byte)0x01, (byte)this.currentDirection, (byte)0x02, (byte)this.currentSpeed, (byte)0x03, (byte)(this.currentHeight), (byte)0x04, (byte)this.button, 0};
                int checksumSend = 0;
                for(int i = 0; i < data.length; i++){
                    checksumSend += data[i];
                }
                data[8] = (byte)checksumSend;
                String showData = "D: "+data[1]+", S: "+data[3]+", H: "+data[5]+", F: "+data[7]+", C: "+data[8];
                try{
                    output.write(data);
                    output.flush();
                    this.dataSendCount++;
                    window.getTxtLog().append("Write: "+showData+"\n");
                    window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                }
                catch (Exception e)
                {
                    logText = "Failed to write data. (" + e.toString() + ")";
                    window.getTxtLog().setForeground(Color.red);
                    window.getTxtLog().append(logText + "\n");
                    window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                }
                
                int counter = 0;
                while(!this.dataReturned && counter < 500){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                }
                
                if(!this.dataReturned){
                    //this.dataReturned = true;
                    window.getTransStateLED().setForeground(Color.red);
                    window.getTxtLog().append("***NO RESPONSE***\n");
                }else{
                    window.getTransStateLED().setForeground(Color.ORANGE);
                }
                
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }*/
                
        private void sendCommsPing(){
            // System.out.println("COMMS PING");
            byte[] pingData = { (byte)55, (byte)55, (byte)55, (byte)55, (byte)55, (byte)55, (byte)55, (byte)55, (byte)55, (byte)55}; 
            try {
                output.write(pingData);
                output.flush();
            } catch (Exception ex) {
                timerRunning = false;
                System.out.println(ex.toString());
            }
            if(hexapodFailedConnections >= 3){
                window.getHexapodConnectedLED().setForeground(Color.red);
                if(connected){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadAllEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    window.getHexapodConnectedLED().setForeground(Color.orange);
                                        try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadAllEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    window.getHexapodConnectedLED().setForeground(Color.red);
                                        try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadAllEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    window.getHexapodConnectedLED().setForeground(Color.orange);
                                        try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadAllEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    window.getHexapodConnectedLED().setForeground(Color.red);
                                        window.getHexapodConnectedLED().setForeground(Color.red);
                                        try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadAllEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    window.getHexapodConnectedLED().setForeground(Color.orange);
                                        try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadAllEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    window.getHexapodConnectedLED().setForeground(Color.red);
                                        window.getHexapodConnectedLED().setForeground(Color.red);
                                        try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadAllEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    window.getHexapodConnectedLED().setForeground(Color.orange);
                                        try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReadAllEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    window.getHexapodConnectedLED().setForeground(Color.red);
                }
            }else{
                hexapodFailedConnections++;
            }
        }  
        
        public void sendMovementData(boolean computerControl){
            
            if(!compControlWindow.isVisible()){

                int direction = 0;
                int height = currentHeight;

                // System.out.println(atan2(-this.yAxis, this.xAxis));  //better all direction control (needs work)

                if(this.xAxis > 0.5){
                    if(this.yAxis > 0.5){
                        //System.out.println("RIGHT-DOWN");
                        direction = 6;
                    }else if(this.yAxis < -0.5){
                        //System.out.println("RIGHT-UP");
                        direction = 5;
                    }else{
                        //System.out.println("RIGHT");
                        direction = 4;
                    }
                }else if(this.xAxis < -0.5){
                    if(this.yAxis > 0.5){
                        //System.out.println("LEFT-DOWN");
                        direction = 8;
                    }else if(this.yAxis < -0.5){
                        //System.out.println("LEFT-UP");
                        direction = 7;
                    }else{
                        //System.out.println("LEFT");
                        direction = 3;
                    }
                }else if(this.yAxis > 0.5){
                    //System.out.println("DOWN");
                    direction = 2;
                }else if(this.yAxis < -0.5){
                    //System.out.println("UP");
                    direction = 1;
                }else{
                    direction = 0;
                }

                if(this.hatValue == 0.25){
                        if(!hatDown)
                            height+=5;
                        hatDown = true;
                    }else if(this.hatValue == 0.5){
                        hatDown = true;
                    }else if(this.hatValue == 0.75){
                        if(!hatDown)
                            height-=5;
                        hatDown = true;
                    }else if(this.hatValue == 1.0){
                        hatDown = true;
                    }else{
                        this.hatDown = false;
                    }

                boolean sendNewData = false;

                if(direction != this.currentDirection){
                    this.currentDirection = direction;
                    sendNewData = true;
                }

                if(Math.abs(this.zAxis) >= 0.1){
                    if(Math.abs(this.zAxis - this.oldZAxis) >= 0.1 ){
                        this.oldZAxis = this.zAxis;
                        sendNewData = true;
                    }
                }else{
                    if(this.oldZAxis != 0){
                        this.zAxis = 0;
                        this.oldZAxis = 0;

                        sendNewData = true;
                    }
                }

                if(this.sliderValue != this.currentSpeed){
                    this.currentSpeed = this.sliderValue;
                    window.getSpeedBar().setValue(this.currentSpeed);
                    sendNewData = true;
                }

                if(height <= 100 && height >= 0 && height != this.currentHeight){
                    this.currentHeight = height;
                    window.getHeightBar().setValue(this.currentHeight);
                    sendNewData = true;
                }

                if(this.button == 1 && this.buttonDown == true){
                    sendNewData = true;
                    this.buttonDown = false;
                }

                if(this.button == 2 && this.buttonDown == true){
                    sendNewData = true;
                    this.buttonDown = false;
                }

                if(this.button == 5 && this.buttonDown == true){
                    sendNewData = true;
                    // sendByte(122);
                    this.buttonDown = false;
                }

                if(this.button == 3 && this.buttonDown == true){
                    sendNewData = true;
                    this.buttonDown = false;
                }

                if(this.button == 4 && this.buttonDown == true){
                    sendNewData = true;
                    this.buttonDown = false;
                }

                if(this.button == 7 && this.buttonDown == true){
                    sendNewData = true;
                    //sendByte(126);
                    this.buttonDown = false;
                }

                if(this.button == 8 && this.buttonDown == true){
                    sendNewData = true;
                    //sendByte(125);
                    this.buttonDown = false;
                }
            
            //do{

                sendData(sendNewData);
            }
           // }while(this.buttonDown);
        }
        
        public void sendData(boolean sendNewData){
            if(sendNewData || !this.dataReturned){
                    if(sendNewData){
                        this.dataReturned = false;
                        this.dataSendCount = 0;
                    }

                    byte[] data = {(byte)0x01, (byte)this.currentDirection, (byte)((this.zAxis+1)*100), (byte)0x02, (byte)this.currentSpeed, (byte)0x03, (byte)(this.currentHeight), (byte)0x04, (byte)this.button, 0};

                    int checksumSend = 0;
                    for(int i = 0; i < data.length; i++){
                        checksumSend += data[i];
                    }
                    data[data.length-1] = (byte)checksumSend;
                    String showData = "D: "+data[1]+", Tw: "+this.zAxis+", S: "+data[4]+", H: "+data[6]+", F: "+data[8]+", C: "+data[9];
                    try{
                        output.write(data);
                        output.flush();
                        this.dataSendCount++;
                        window.getTxtLog().append("Write: "+showData+"\n");
                        window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                    }
                    catch (Exception e)
                    {
                        logText = "Failed to write data. (" + e.toString() + ")";
                        window.getTxtLog().setForeground(Color.red);
                        window.getTxtLog().append(logText + "\n");
                        window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                    }

                    int counter = 0;
                    while(!this.dataReturned && counter < 500){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        counter++;
                    }

                    if(this.dataSendCount >= 1 && !this.dataReturned){
                        this.dataReturned = true;
                        window.getTransStateLED().setForeground(Color.red);
                        window.getTxtLog().append("***NO RESPONSE***\n");
                    }else{
                        window.getTransStateLED().setForeground(Color.ORANGE);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
        }
        
        public void startUpRefresh(){
            this.xAxis = 0;
            this.yAxis = 0;
            this.zAxis = 0;
            this.hatValue = 0;
            this.sliderValue = 0;
            this.selectedController = 0;
            this.selectedControllerOffset = 0;
            this.movementError = 0.0;
            this.connected = false;
            
            window.resetGUI();

            /* Get the available controllers */
            controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

            if (controllers.length == 0) {
                System.out.println("Found no controllers.");
                System.exit(0);
            }else{
                for (int i = 0; i < controllers.length; i++) {
                    window.addController(controllers[i].getName());
                }
            }	
            
            searchForPorts();
            
        }
        
        public void searchForPorts()
        {
            ports = CommPortIdentifier.getPortIdentifiers();

            while (ports.hasMoreElements())
            {
                CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();

                //get only serial ports
                if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL)
                {
                    window.addComms(curPort.getName());
                    portMap.put(curPort.getName(), curPort);
                }
            }
        }
        
        public void connect()
        {
            if(!this.connected){
                this.selectedController = window.getControllerCombo().getSelectedIndex();
                
                String selectedPort = (String)window.getCommsCombo().getSelectedItem();
                selectedPortIdentifier = (CommPortIdentifier)portMap.get(selectedPort);

                CommPort commPort = null;

                try
                {
                    //the method below returns an object of type CommPort
                    commPort = selectedPortIdentifier.open("ReadAllEvents", TIMEOUT);
                    //the CommPort object can be casted to a SerialPort object
                    serialPort = (SerialPort)commPort;

                    serialPort.setSerialPortParams(BAUD, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                    //for controlling GUI elements
                    this.connected = true;
                    
                    timerRunning = true;

                    //logging
                    logText = window.getControllerCombo().getSelectedItem()+" connected.\n"+selectedPort + " opened successfully.\n";
                    window.getTxtLog().setForeground(Color.black);
                    window.getTxtLog().append(logText + "\n");
                    window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                    
                    }
                    catch (PortInUseException e)
                    {
                        logText = selectedPort + " is in use. (" + e.toString() + ")";

                        window.getTxtLog().setForeground(Color.RED);
                        window.getTxtLog().append(logText + "\n");
                        window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                    }
                    catch (Exception e)
                    {
                        logText = "Failed to open " + selectedPort + "(" + e.toString() + ")";
                        window.getTxtLog().append(logText + "\n");
                        window.getTxtLog().setForeground(Color.RED);
                        window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                    }
            }
        }
        
        public boolean initIOStream()
        {
            //return value for whether opening the streams is successful or not
            boolean successful = false;

            try {
                //
                input = serialPort.getInputStream();
                output = serialPort.getOutputStream();
                
                successful = true;
                return successful;
            }
            catch (IOException e) {
                logText = "I/O Streams failed to open. (" + e.toString() + ")";
                window.getTxtLog().setForeground(Color.red);
                window.getTxtLog().append(logText + "\n");
                window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                return successful;
            }
        }
        
        public void initListener()
        {
            try
            {
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
            }
            catch (TooManyListenersException e)
            {
                logText = "Too many listeners. (" + e.toString() + ")";
                window.getTxtLog().setForeground(Color.red);
                window.getTxtLog().append(logText + "\n");
                window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
            }
        }
        
        public void disconnect()
        {
            this.dataReturned = true;
            if(timer != null){
                timerRunning = false;
            }
            if(this.connected){
                //close the serial port
                try
                {
                    writeData(0, 0);

                    serialPort.removeEventListener();
                    serialPort.close();
                    input.close();
                    output.close();
                    //setConnected(false);
                    //window.keybindingController.toggleControls();

                    logText = serialPort.getName().substring(4,serialPort.getName().length())+" disconnected.\n";
                    window.getTxtLog().setForeground(Color.red);
                    window.getTxtLog().append(logText + "\n");
                    window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                }
                catch (Exception e)
                {
                    logText = "Failed to close " + serialPort.getName()
                                      + "(" + e.toString() + ")";
                    window.getTxtLog().setForeground(Color.red);
                    window.getTxtLog().append(logText + "\n");
                    window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                }
            }
            this.connected = false;
        }
        
        public void serialEvent(SerialPortEvent evt) 
        {
            if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE)
            {
                try
                {
                    int[] data = { (int)0x01, (int)this.currentDirection, (byte)((this.zAxis+1)*100), (int)0x02, (int)this.currentSpeed, (int)0x03, (int)(this.currentHeight), (int)0x04, (byte)this.button};
                    
                    int byte1 = (int)input.read();
                    
                    if(byte1 == 255){     //1st byte    //data sent from hexapod
                        
                        int byte2 = (int)input.read();    //2nd byte
                        
                        if(byte2 == 255){               //motor control table incoming
                           /* window.getTxtLog().append("**********MOTOR DATA**********\n");
                            for(int i = 0; i < 54; i++){
                                if(i == 3){
                                    window.getTxtLog().append("__CONTROL TABLE__\n");
                                }
                                
                                window.getTxtLog().append(controlTableNames[i]+": "+(int)input.read()+"\n");
                            }
                            window.getTxtLog().append("=============================\n");*/
                            
                        }else if(byte2 == data[0]+data[1]+data[2]+data[3]+data[4]+data[5]+data[6]+data[7]+data[8]){ //data checksum returned
                            window.getTxtLog().append("RETURN MATCH: "+byte2+"\n");
                            window.getTransStateLED().setForeground(Color.green);
                            this.dataReturned = true;
                        }else if(byte2 == 254){     //IMU data incoming
                            
                            byte[] timestampData = {0,0,0,0};
                            
                            byte[] pitchData = {0,0,0,0};
                            byte[] rollData = {0,0,0,0};
                            byte[] yawData = {0,0,0,0};
                            
                            byte[] slopePitchData = {0,0,0,0};
                            byte[] slopeRollData = {0,0,0,0};
                            
                            for(int i = 0; i < 4; i++){
                               timestampData[i] = (byte)input.read();
                            }
                            for(int i = 0; i < 4; i++){
                               pitchData[i] = (byte)input.read();
                            }
                            for(int i = 0; i < 4; i++){
                               rollData[i] = (byte)input.read();
                            }
                            for(int i = 0; i < 4; i++){
                               yawData[i] = (byte)input.read();
                            }
                            for(int i = 0; i < 4; i++){
                               slopePitchData[i] = (byte)input.read();
                            }
                            for(int i = 0; i < 4; i++){
                               slopeRollData[i] = (byte)input.read();
                            }
                            int timestamp = (int)ByteBuffer.wrap(timestampData).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                            float pitch = ByteBuffer.wrap(pitchData).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                            float roll = ByteBuffer.wrap(rollData).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                            float yaw = ByteBuffer.wrap(yawData).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                            
                            float slopePitch = ByteBuffer.wrap(slopePitchData).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                            float slopeRoll = ByteBuffer.wrap(slopeRollData).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                            
                            if(plotWindow != null && plotWindow.isVisible()){
                                plotWindow.addData(timestamp, pitch, roll, yaw, slopePitch, slopeRoll);
                            }
                            
                            if(window.getShowIMU().isSelected()){
                                window.getTxtLog().append("Timestamp: "+timestamp+", Pitch: "+pitch+", Roll: "+roll+", Yaw: "+yaw+"\n");
                            }
                        }else if(byte2 == 253){         //UART error incoming
                            byte errorLoc = (byte)input.read();
                            byte errorCode = (byte)input.read();
                            String errorStr = "";
                            String errorLocStr = "";
                            if(errorLoc < 4){           // loc < 4 is UART1/2/3 errors
                                switch(errorLoc){
                                    case 1:
                                        errorLocStr = "UART1";
                                        break;
                                    case 2:
                                        errorLocStr = "UART2";
                                        break;
                                    case 3:
                                        errorLocStr = "UART3";
                                        break;    
                                }
                                switch(errorCode){
                                    case 0:
                                        errorStr = "No Error";
                                        break;
                                    case 1:
                                        errorStr = "Parity Error";
                                        break;
                                    case 2:
                                        errorStr = "Noise Error";
                                        break;
                                    case 4:
                                        errorStr = "Frame Error";
                                        break;     
                                    case 8:
                                        errorStr = "Overrun Error";
                                        break;
                                    case 10:
                                        errorStr = "DMA Transfer Error";
                                        break;    
                                }
                            }else if(errorLoc == 11){           //STARTUP MOTOR PING ERROR
                                errorLocStr = "Ping Error";
                                errorStr = "ID: "+errorCode;
                            }
                            Date date = new Date();
                            window.getErrorLog().append(date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+": "+errorStr+" ("+errorLocStr+")\n");
                            window.getErrorLog().setCaretPosition(window.getErrorLog().getDocument().getLength());
                        }else if(byte2 == 252){             //ASCII message incoming
                               int stringSize = (int)input.read();
                               
                               char[] message = new char[stringSize];
                               
                               window.getTxtLog().append("MESSAGE: ");
                               for(int i = 0; i < stringSize; i++){
                                   message[i] = (char)input.read();
                                   window.getTxtLog().append(message[i]+"");
                               }
                               
                                window.getTxtLog().append("\n");
                              
                        }else if(byte2 == 250){         //Hexapod comms check ping.
                            window.getHexapodConnectedLED().setForeground(Color.green);
                            hexapodFailedConnections = 0;
                        }else if(byte2 == 249){ //battery status update
                            
                            int status = (int)input.read();
                            
                           
                                byte[] voltage = {0,0,0,0};
                                for(int i = 0; i < 4; i++){
                                   voltage[i] = (byte)input.read();
                                }

                                for(int i = 0; i < 3; i++){
                                   byte nothing = (byte)input.read();
                                }

                                float batVoltage = ByteBuffer.wrap(voltage).order(ByteOrder.LITTLE_ENDIAN).getFloat();

                                window.getVoltageLabel().setText(Math.round(batVoltage*100.0)/100.0+"");


                                switch(status){
                                    case 0: //not charging
                                        window.getChargingLED().setForeground(Color.red);
                                        window.getCVLED().setForeground(Color.red);
                                        window.getCCLED().setForeground(Color.red);
                                        this.charging = false;
                                        window.getChargeButton().setSelected(false);
                                        window.getChargeButton().setText("Charge");
                                        break;
                                    case 1: //CC
                                        window.getChargingLED().setForeground(Color.green);
                                        window.getCVLED().setForeground(Color.red);
                                        window.getCCLED().setForeground(Color.green);
                                        this.charging = true;
                                        window.getChargeButton().setSelected(true);
                                        window.getChargeButton().setText("Stop Charging");
                                        break;
                                    case 2: //CV
                                        window.getChargingLED().setForeground(Color.green);
                                        window.getCVLED().setForeground(Color.green);
                                        window.getCCLED().setForeground(Color.red);
                                        this.charging = true;
                                        window.getChargeButton().setSelected(true);
                                        window.getChargeButton().setText("Stop Charging");
                                        break;  
                                    case 3: //error
                                        window.getChargingLED().setForeground(Color.red);
                                        window.getCVLED().setForeground(Color.red);
                                        window.getCCLED().setForeground(Color.red);
                                        window.getChargingTxtLog().append("Error: Base not mounted.");
                                        window.getChargingTxtLog().setCaretPosition(window.getChargingTxtLog().getDocument().getLength());
                                        this.charging = false;
                                        break;
                                    default:
                                        break;
                                }
                        }else if(byte2 == 248){
                              /*  int toggleChargingResponse = (int)input.read();
                                                              
                                for(int i = 0; i < 7; i++){
                                   byte nothing = (byte)input.read();
                                }
                                                                                               
                                if(shouldCharging && (toggleChargingResponse == 5)){
                                    charging = true;
                                    window.getChargeButton().setSelected(true);
                                    window.getChargeButton().setText("Stop Charging");
                                }else if(charging && (toggleChargingResponse == 6)){
                                    charging = false;
                                    window.getChargeButton().setSelected(false);
                                    window.getChargeButton().setText("Charge");
                                }*/
                        }else{
                            window.getTxtLog().append("DATA: "+byte2+"\n");
                        }
                        
                    }else if(byte1 == 250){         // data sent from base.
                        
                    }else{
                        window.getTxtLog().append("DATA: "+byte1+"\n");
                    }
                    
                   // window.getTxtLog().append(""+singleData1+"\n");
                    
                    window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());

                }
                catch (Exception e)
                {
                    logText = "Failed to read data. (" + e.toString() + ")";
                   // window.getTxtLog().setForeground(Color.red);
                    window.getTxtLog().append(logText + "\n");
                    window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
                }
            }
        }
               
        public void sendCustomCommand(String command){
            String[] splt = command.split(" ");
            int[] bytes = new int[splt.length];
            boolean error = false;
            String errorLoc = "";
            for(int i = 0; i < splt.length && !error; i++){
                if(splt[i] == "" || splt[i] == " " || splt[i] == null){
                    error = true;
                }else{
                    try{
                        bytes[i] = Integer.parseInt(splt[i]);
                    }catch(Exception e){
                        try{
                         bytes[i] = Integer.parseInt(splt[i], 16);
                        }catch(Exception e2){
                           error = true;
                           errorLoc = splt[i];
                        }
                    }
                    if(bytes[i] > 255){
                        error = true;
                        errorLoc = splt[i];
                    }
                }
            }
            if(!error){
                for(int i = 0; i < bytes.length; i++){
                        sendByte(bytes[i]);
                }
            }else{
                window.getTxtLog().append("CUSTOM COMMAND ERROR AT: "+errorLoc+"\n");
                window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
            }
        }
        
        public void sendByte(int data){
            try{
                output.write(data);
                output.flush();
                window.getTxtLog().append("Write: "+data+"\n");
                window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
            }
            catch (Exception e)
            {
                logText = "Failed to write data. (" + e.toString() + ")";
                window.getTxtLog().setForeground(Color.red);
                window.getTxtLog().append(logText + "\n");
                window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
            }
        }
        
        public void writeData(int leftThrottle, int rightThrottle)
        {
            try
            {
                output.write(leftThrottle);
                output.flush();
                
                //this is a delimiter for the data
                output.write(DASH_ASCII);
                output.flush();

                output.write(rightThrottle);
                output.flush();
                
                //will be read as a byte so it is a space key
                output.write(SPACE_ASCII);
                output.flush();
            }
            catch (Exception e)
            {
                logText = "Failed to write data. (" + e.toString() + ")";
                window.getTxtLog().setForeground(Color.red);
                window.getTxtLog().append(logText + "\n");
                window.getTxtLog().setCaretPosition(window.getTxtLog().getDocument().getLength());
            }
        }
        
       
	public static void main(String[] args) {
		new ReadAllEvents();
	}
}