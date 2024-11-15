/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexapodControlGUI;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author chrros005
 */
public class ComputerControlWindow extends javax.swing.JFrame {

    /**
     * Creates new form ComputerControl
     */
    public ComputerControlWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initComponents();
        
        this.setTitle("Computer Control");
        
        this.setLocationRelativeTo(null);
        
        try{
            //Image image = ImageIO.read(getClass().getResource("/images/arrows/up.png"));
            up.setIcon(new ShrinkIcon(getClass().getResource("/images/arrows/up.png")));
            up.setContentAreaFilled(false);
            up.setBorder(BorderFactory.createEmptyBorder());
            
            down.setIcon(new ShrinkIcon(getClass().getResource("/images/arrows/down.png")));
            down.setContentAreaFilled(false);
            down.setBorder(BorderFactory.createEmptyBorder());
            
            left.setIcon(new ShrinkIcon(getClass().getResource("/images/arrows/left.png")));
            left.setContentAreaFilled(false);
            left.setBorder(BorderFactory.createEmptyBorder());
            
            right.setIcon(new ShrinkIcon(getClass().getResource("/images/arrows/right.png")));
            right.setContentAreaFilled(false);
            right.setBorder(BorderFactory.createEmptyBorder());
            
            left_down.setIcon(new ShrinkIcon(getClass().getResource("/images/arrows/left_down.png")));
            left_down.setContentAreaFilled(false);
            left_down.setBorder(BorderFactory.createEmptyBorder());
            
            left_up.setIcon(new ShrinkIcon(getClass().getResource("/images/arrows/left_up.png")));
            left_up.setContentAreaFilled(false);
            left_up.setBorder(BorderFactory.createEmptyBorder());
            
            right_down.setIcon(new ShrinkIcon(getClass().getResource("/images/arrows/right_down.png")));
            right_down.setContentAreaFilled(false);
            right_down.setBorder(BorderFactory.createEmptyBorder());
            
            right_up.setIcon(new ShrinkIcon(getClass().getResource("/images/arrows/right_up.png")));
            right_up.setContentAreaFilled(false);
            right_up.setBorder(BorderFactory.createEmptyBorder());
            
            stop.setContentAreaFilled(false);
            stop.setBorder(BorderFactory.createEmptyBorder());
            
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public JButton getDown() {
        return down;
    }

    public void setDown(JButton down) {
        this.down = down;
    }

    public JButton getLeft() {
        return left;
    }

    public void setLeft(JButton left) {
        this.left = left;
    }

    public JButton getLeft_down() {
        return left_down;
    }

    public void setLeft_down(JButton left_down) {
        this.left_down = left_down;
    }

    public JButton getLeft_up() {
        return left_up;
    }

    public void setLeft_up(JButton left_up) {
        this.left_up = left_up;
    }

    public JButton getRight() {
        return right;
    }

    public void setRight(JButton right) {
        this.right = right;
    }

    public JButton getRight_down() {
        return right_down;
    }

    public void setRight_down(JButton right_down) {
        this.right_down = right_down;
    }

    public JPanel getArrowPanel() {
        return arrowPanel;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.arrowPanel = jPanel1;
    }

    public JButton getRight_up() {
        return right_up;
    }

    public void setRight_up(JButton right_up) {
        this.right_up = right_up;
    }

    public JButton getStop() {
        return stop;
    }
    
    public JButton getStandButton() {
        return standButton;
    }

    public JButton getLiftBaseButton() {
        return liftBaseButton;
    }
    
    public void setStop(JButton stop) {
        this.stop = stop;
    }

    public JButton getUp() {
        return up;
    }

    public void setUp(JButton up) {
        this.up = up;
    }
    
    public javax.swing.JSlider getSpeedSlider(){
        return this.speedControl;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        arrowPanel = new javax.swing.JPanel();
        right = new javax.swing.JButton();
        stop = new javax.swing.JButton();
        down = new javax.swing.JButton();
        up = new javax.swing.JButton();
        left = new javax.swing.JButton();
        left_down = new javax.swing.JButton();
        left_up = new javax.swing.JButton();
        right_up = new javax.swing.JButton();
        right_down = new javax.swing.JButton();
        standButton = new javax.swing.JButton();
        speedControl = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        liftBaseButton = new javax.swing.JButton();

        arrowPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        arrowPanel.setFocusCycleRoot(true);

        right.setMaximumSize(new java.awt.Dimension(40, 40));
        right.setMinimumSize(new java.awt.Dimension(40, 40));
        right.setPreferredSize(new java.awt.Dimension(40, 40));

        stop.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        stop.setMaximumSize(new java.awt.Dimension(40, 40));
        stop.setMinimumSize(new java.awt.Dimension(40, 40));
        stop.setPreferredSize(new java.awt.Dimension(40, 40));

        down.setMaximumSize(new java.awt.Dimension(40, 40));
        down.setMinimumSize(new java.awt.Dimension(40, 40));
        down.setPreferredSize(new java.awt.Dimension(40, 40));

        up.setMaximumSize(new java.awt.Dimension(40, 40));
        up.setMinimumSize(new java.awt.Dimension(40, 40));
        up.setPreferredSize(new java.awt.Dimension(40, 40));

        left.setMaximumSize(new java.awt.Dimension(40, 40));
        left.setMinimumSize(new java.awt.Dimension(40, 40));
        left.setPreferredSize(new java.awt.Dimension(40, 40));
        left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftActionPerformed(evt);
            }
        });

        left_down.setMaximumSize(new java.awt.Dimension(40, 40));
        left_down.setMinimumSize(new java.awt.Dimension(40, 40));
        left_down.setPreferredSize(new java.awt.Dimension(40, 40));

        left_up.setMaximumSize(new java.awt.Dimension(40, 40));
        left_up.setMinimumSize(new java.awt.Dimension(40, 40));
        left_up.setPreferredSize(new java.awt.Dimension(40, 40));

        right_up.setMaximumSize(new java.awt.Dimension(40, 40));
        right_up.setMinimumSize(new java.awt.Dimension(40, 40));
        right_up.setPreferredSize(new java.awt.Dimension(40, 40));

        right_down.setMaximumSize(new java.awt.Dimension(40, 40));
        right_down.setMinimumSize(new java.awt.Dimension(40, 40));
        right_down.setPreferredSize(new java.awt.Dimension(40, 40));
        right_down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                right_downActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout arrowPanelLayout = new javax.swing.GroupLayout(arrowPanel);
        arrowPanel.setLayout(arrowPanelLayout);
        arrowPanelLayout.setHorizontalGroup(
            arrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(arrowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(arrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(left_down, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(left_up, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(arrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(arrowPanelLayout.createSequentialGroup()
                        .addComponent(up, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(right_up, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(arrowPanelLayout.createSequentialGroup()
                        .addComponent(down, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(right_down, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(arrowPanelLayout.createSequentialGroup()
                        .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(right, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        arrowPanelLayout.setVerticalGroup(
            arrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(arrowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(arrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(up, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(left_up, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(right_up, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(arrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(right, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(arrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(down, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(left_down, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(right_down, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        standButton.setText("Stand");

        speedControl.setMajorTickSpacing(10);
        speedControl.setMinorTickSpacing(5);
        speedControl.setOrientation(javax.swing.JSlider.VERTICAL);
        speedControl.setPaintLabels(true);
        speedControl.setPaintTicks(true);
        speedControl.setSnapToTicks(true);

        jLabel1.setText("Speed");

        liftBaseButton.setText("Lift For Base");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(arrowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(standButton)
                    .addComponent(liftBaseButton))
                .addGap(18, 18, 18)
                .addComponent(speedControl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(speedControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(arrowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(standButton)
                        .addGap(18, 18, 18)
                        .addComponent(liftBaseButton)
                        .addGap(0, 37, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(139, 139, 139))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void right_downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_right_downActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_right_downActionPerformed

    private void leftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_leftActionPerformed

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
            java.util.logging.Logger.getLogger(ComputerControlWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ComputerControlWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ComputerControlWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ComputerControlWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ComputerControlWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel arrowPanel;
    private javax.swing.JButton down;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton left;
    private javax.swing.JButton left_down;
    private javax.swing.JButton left_up;
    private javax.swing.JButton liftBaseButton;
    private javax.swing.JButton right;
    private javax.swing.JButton right_down;
    private javax.swing.JButton right_up;
    private javax.swing.JSlider speedControl;
    private javax.swing.JButton standButton;
    private javax.swing.JButton stop;
    private javax.swing.JButton up;
    // End of variables declaration//GEN-END:variables
}
