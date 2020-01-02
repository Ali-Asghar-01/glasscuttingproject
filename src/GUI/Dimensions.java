package GUI;

import javax.swing.JOptionPane;

public class Dimensions extends javax.swing.JFrame {

    public Dimensions() {
         this.setUndecorated(true);
        initComponents();
    }
    
    String glassType, glassShape, date, user, pass; 
    int GlassTypePrice, GlassShapePrice, uid;
    public Dimensions(String glassType, int GlassTypePrice, String glassShape, 
            int GlassShapePrice, String date, int uid, String user, String pass) {
        
        this.setUndecorated(true);
        initComponents();
        this.glassType = glassType;
        this.GlassTypePrice = GlassTypePrice;
        this.glassShape = glassShape;
        this.GlassShapePrice = GlassShapePrice;
        this.date=date;
        this.uid=uid;
        this.user=user;
        this.pass=pass;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lengthCB = new javax.swing.JComboBox<>();
        widthCB = new javax.swing.JComboBox<>();
        heightCB = new javax.swing.JComboBox<>();
        ContinueBtn = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DIMENSIONS");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(190, 0, 470, 90);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/Octagon Shape.png"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(40, 90, 350, 330);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Octagon");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(160, 90, 100, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Note: Dimensions must be provided in Inches.");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(-20, 450, 480, 40);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Edge Length (E):");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(30, 530, 140, 40);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("WIDTH (W):");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(30, 570, 140, 40);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Height (H):");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(30, 610, 140, 40);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Inches");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(190, 500, 60, 30);

        lengthCB.setBackground(new java.awt.Color(153, 153, 153));
        lengthCB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lengthCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40" }));
        lengthCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lengthCBActionPerformed(evt);
            }
        });
        getContentPane().add(lengthCB);
        lengthCB.setBounds(190, 540, 60, 23);

        widthCB.setBackground(new java.awt.Color(153, 153, 153));
        widthCB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        widthCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40" }));
        widthCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                widthCBActionPerformed(evt);
            }
        });
        getContentPane().add(widthCB);
        widthCB.setBounds(190, 580, 60, 23);

        heightCB.setBackground(new java.awt.Color(153, 153, 153));
        heightCB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        heightCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40" }));
        heightCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heightCBActionPerformed(evt);
            }
        });
        getContentPane().add(heightCB);
        heightCB.setBounds(190, 620, 60, 23);

        ContinueBtn.setBackground(new java.awt.Color(153, 153, 153));
        ContinueBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ContinueBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_continue_60172.png"))); // NOI18N
        ContinueBtn.setText("Continue");
        ContinueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinueBtnActionPerformed(evt);
            }
        });
        getContentPane().add(ContinueBtn);
        ContinueBtn.setBounds(710, 550, 140, 40);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel18);
        jLabel18.setBounds(30, 30, 50, 40);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/minimalism-1152.jpg"))); // NOI18N
        getContentPane().add(jLabel10);
        jLabel10.setBounds(0, 0, 870, 680);

        setSize(new java.awt.Dimension(885, 717));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    int length;
    int lengthPrice;
    private void lengthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lengthCBActionPerformed
        // TODO add your handling code here:
        length = lengthCB.getSelectedIndex()+1;
        if(length<20){
            lengthPrice = 700;
        }
        else if(length>20 && length<=30){
            lengthPrice = 1000;
        }
        else if(length>30 && length<=40){
            lengthPrice = 1700;
        }
        
    }//GEN-LAST:event_lengthCBActionPerformed

    int width;
    int widthPrice;
    private void widthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_widthCBActionPerformed
        // TODO add your handling code here:
        width = widthCB.getSelectedIndex()+1;
        if(width<20){
            widthPrice = 500;
        }
        else if(width>=20 && width<30){
            widthPrice = 1000;
        }
        else if(width>=30 && width<=40){
            widthPrice = 1700;
        }
    }//GEN-LAST:event_widthCBActionPerformed

    int height;
    int heightPrice;
    private void heightCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heightCBActionPerformed
        // TODO add your handling code here:
        height = heightCB.getSelectedIndex()+1;
        if(height<20){
            heightPrice = 500;
        }
        else if(height>=20 && height<30){
            heightPrice = 1000;
        }
        else if(height>=30 && height<=40){
            heightPrice = 1700;
        }
    }//GEN-LAST:event_heightCBActionPerformed

    private void ContinueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinueBtnActionPerformed
        // TODO add your handling code here:
        //System.out.println(GlassShapePrice + " " + GlassTypePrice);
        
        length = lengthCB.getSelectedIndex()+1;
        if(length<20){
            lengthPrice = 700;
        }
        else if(length>20 && length<=30){
            lengthPrice = 1000;
        }
        else if(length>30 && length<=40){
            lengthPrice = 1700;
        }
        
        width = widthCB.getSelectedIndex()+1;
        if(width<20){
            widthPrice = 500;
        }
        else if(width>=20 && width<30){
            widthPrice = 1000;
        }
        else if(width>=30 && width<=40){
            widthPrice = 1700;
        }
        
        height = heightCB.getSelectedIndex()+1;
        if(height<20){
            heightPrice = 500;
        }
        else if(height>=20 && height<30){
            heightPrice = 1000;
        }
        else if(height>=30 && height<=40){
            heightPrice = 1700;
        }
        
        try{
            new GlassThickness(glassType, GlassTypePrice, glassShape, GlassShapePrice, length,
                lengthPrice, width, widthPrice, height, heightPrice, date, uid, user,pass).setVisible(true);
        
            dispose();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Select all the Dimensions to Proceed");
        }
        
    }//GEN-LAST:event_ContinueBtnActionPerformed

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        new GlassShape(date, uid, date, uid, user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

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
            java.util.logging.Logger.getLogger(Dimensions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dimensions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dimensions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dimensions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dimensions().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ContinueBtn;
    private javax.swing.JComboBox<String> heightCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<String> lengthCB;
    private javax.swing.JComboBox<String> widthCB;
    // End of variables declaration//GEN-END:variables
}
