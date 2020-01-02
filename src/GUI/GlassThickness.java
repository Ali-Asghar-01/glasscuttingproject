package GUI;

import javax.swing.JOptionPane;

public class GlassThickness extends javax.swing.JFrame {

    public GlassThickness() {
        this.setUndecorated(true);
        initComponents();
    }
   
    String glassType, glassShape, date, user, pass; 
    int GlassTypePrice, GlassShapePrice, length, lengthPrice, width, widthPrice, height, heightPrice, uid;
    public GlassThickness(String glassType, int glassTypePrice, String glassShape, int glassShapePrice,
            int length, int lengthPrice, int width, int widthPrice, int height, int heightPrice, 
            String date, int uid, String user, String pass){
        
        this.setUndecorated(true);
        initComponents();
        
        this.glassType=glassType;
        this.glassShape=glassShape;
        this.GlassTypePrice=glassTypePrice;
        this.GlassShapePrice=glassShapePrice;
        this.length=length;
        this.lengthPrice=lengthPrice;
        this.width=width;
        this.widthPrice=widthPrice;
        this.height=height;
        this.heightPrice=heightPrice;
        this.date=date;
        this.uid=uid;
        this.user=user;
        this.pass=pass;
}    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel18 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TwoMM = new javax.swing.JRadioButton();
        ThreeMM = new javax.swing.JRadioButton();
        ThreeMMM = new javax.swing.JRadioButton();
        SixMM = new javax.swing.JRadioButton();
        EightMM = new javax.swing.JRadioButton();
        TenMM = new javax.swing.JRadioButton();
        TwelveMM = new javax.swing.JRadioButton();
        ContinueBtn = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GLASS THICKNESS");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(120, 0, 530, 120);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thickness Range for Glass is:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(50, 130, 260, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("normally used in picture frames.");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(160, 200, 560, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("used in storm windows, picture frames, end tables, insulated units, etc.");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(160, 240, 560, 20);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("used for small table tops, insulated units, small shelves, etc.");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(160, 280, 560, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("<HTML> used in table tops (as protective covering), insulated units, single-pane windows, shelves,"
            + "<br> framed shower doors, etc. </HTML>");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel6);
        jLabel6.setBounds(160, 320, 640, 40);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("<HTML>used in table tops (as protective covering),"
            + " lightweight shelves,framed shower doors, etc.<br> It is not common in US. </HTML>");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel7);
        jLabel7.setBounds(160, 370, 640, 40);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("used in shower doors, table tops, glass walls, glass partitions, hand rails, door lights, etc.");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(160, 410, 580, 20);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("used for shower doors, table tops, glass walls, glass partitions, hand rails, etc.");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(160, 450, 560, 20);

        TwoMM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TwoMM.setText("3/32\" (2mm)");
        TwoMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoMMActionPerformed(evt);
            }
        });
        getContentPane().add(TwoMM);
        TwoMM.setBounds(50, 200, 100, 23);

        ThreeMM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ThreeMM.setText("1/8\" (3mm)");
        ThreeMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThreeMMActionPerformed(evt);
            }
        });
        getContentPane().add(ThreeMM);
        ThreeMM.setBounds(50, 240, 100, 23);

        ThreeMMM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ThreeMMM.setText("3/16\" (3mm)");
        ThreeMMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThreeMMMActionPerformed(evt);
            }
        });
        getContentPane().add(ThreeMMM);
        ThreeMMM.setBounds(50, 280, 100, 23);

        SixMM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        SixMM.setText("1/4\" (6mm)");
        SixMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SixMMActionPerformed(evt);
            }
        });
        getContentPane().add(SixMM);
        SixMM.setBounds(50, 320, 100, 23);

        EightMM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        EightMM.setText("5/16\" (8mm)");
        EightMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EightMMActionPerformed(evt);
            }
        });
        getContentPane().add(EightMM);
        EightMM.setBounds(50, 370, 100, 23);

        TenMM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TenMM.setText("3/8\" (10mm)");
        TenMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TenMMActionPerformed(evt);
            }
        });
        getContentPane().add(TenMM);
        TenMM.setBounds(50, 410, 100, 23);

        TwelveMM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TwelveMM.setText("1/2\" (12mm)");
        TwelveMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwelveMMActionPerformed(evt);
            }
        });
        getContentPane().add(TwelveMM);
        TwelveMM.setBounds(50, 450, 100, 23);

        ContinueBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ContinueBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_continue_60172.png"))); // NOI18N
        ContinueBtn.setText("Continue");
        ContinueBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinueBtnActionPerformed(evt);
            }
        });
        getContentPane().add(ContinueBtn);
        ContinueBtn.setBounds(640, 510, 140, 40);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel19MouseEntered(evt);
            }
        });
        getContentPane().add(jLabel19);
        jLabel19.setBounds(30, 40, 50, 40);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/minimalism-1513898226715-9320.jpg"))); // NOI18N
        getContentPane().add(jLabel10);
        jLabel10.setBounds(0, -30, 800, 660);

        setSize(new java.awt.Dimension(814, 658));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ThreeMMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThreeMMActionPerformed
        // TODO add your handling code here:

        if(ThreeMM.isSelected()){
            TwoMM.setSelected(false);
            ThreeMMM.setSelected(false);
            SixMM.setSelected(false);
            EightMM.setSelected(false);
            TenMM.setSelected(false);
            TwelveMM.setSelected(false);
        }
    }//GEN-LAST:event_ThreeMMActionPerformed

    private void SixMMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SixMMActionPerformed
        // TODO add your handling code here:
        if(SixMM.isSelected()){
            ThreeMM.setSelected(false);
            TwoMM.setSelected(false);
            ThreeMMM.setSelected(false);
            EightMM.setSelected(false);
            TenMM.setSelected(false);
            TwelveMM.setSelected(false);
        }
    }//GEN-LAST:event_SixMMActionPerformed

    String thickness;
    int thicknessPrice;
    private void ContinueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinueBtnActionPerformed
        // TODO add your handling code here:
        if(TwoMM.isSelected()){
            thickness = TwoMM.getText();
            thicknessPrice=400;
        }
        else if(ThreeMM.isSelected()){
            thickness = ThreeMM.getText();
            thicknessPrice=600;
        }
        else if(ThreeMMM.isSelected()){
            thickness = ThreeMMM.getText();
            thicknessPrice=600;
        }
        else if(SixMM.isSelected()){
            thickness = SixMM.getText();
            thicknessPrice=1100;
        }
        else if(EightMM.isSelected()){
            thickness = EightMM.getText();
            thicknessPrice=1700;
        }
        else if(TenMM.isSelected()){
            thickness = TenMM.getText();
            thicknessPrice=2500;
        }
        else if(TwelveMM.isSelected()){
            thickness = TwelveMM.getText();
            thicknessPrice=3500;
        }
        System.out.println(thicknessPrice);
        System.out.println(GlassTypePrice);
        System.out.println(GlassShapePrice);
        System.out.println(lengthPrice);
        System.out.println(widthPrice);
        System.out.println(heightPrice);
        try{
            if(!thickness.isEmpty()){
                new PaymentReceipt(glassType, GlassTypePrice, glassShape, GlassShapePrice, length, lengthPrice,
                width, widthPrice, height, heightPrice, thickness, thicknessPrice,date,uid,user,pass).setVisible(true);
                dispose();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "First Select the Thickness Of Glass");
        }
        
    }//GEN-LAST:event_ContinueBtnActionPerformed

    private void TwoMMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TwoMMActionPerformed
        // TODO add your handling code here:
        if(TwoMM.isSelected()){
            ThreeMM.setSelected(false);
            ThreeMMM.setSelected(false);
            SixMM.setSelected(false);
            EightMM.setSelected(false);
            TenMM.setSelected(false);
            TwelveMM.setSelected(false);
        }
    }//GEN-LAST:event_TwoMMActionPerformed

    private void ThreeMMMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThreeMMMActionPerformed
        // TODO add your handling code here:
        if(ThreeMMM.isSelected()){
            ThreeMM.setSelected(false);
            TwoMM.setSelected(false);
            SixMM.setSelected(false);
            EightMM.setSelected(false);
            TenMM.setSelected(false);
            TwelveMM.setSelected(false);
        }
    }//GEN-LAST:event_ThreeMMMActionPerformed

    private void EightMMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EightMMActionPerformed
        // TODO add your handling code here:
        if(EightMM.isSelected()){
            ThreeMM.setSelected(false);
            TwoMM.setSelected(false);
            SixMM.setSelected(false);
            ThreeMMM.setSelected(false);
            TenMM.setSelected(false);
            TwelveMM.setSelected(false);
        }
    }//GEN-LAST:event_EightMMActionPerformed

    private void TenMMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TenMMActionPerformed
        // TODO add your handling code here:
        if(TenMM.isSelected()){
            ThreeMM.setSelected(false);
            TwoMM.setSelected(false);
            SixMM.setSelected(false);
            EightMM.setSelected(false);
            ThreeMMM.setSelected(false);
            TwelveMM.setSelected(false);
        }
    }//GEN-LAST:event_TenMMActionPerformed

    private void TwelveMMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TwelveMMActionPerformed
        // TODO add your handling code here:
        if(TwelveMM.isSelected()){
            ThreeMM.setSelected(false);
            TwoMM.setSelected(false);
            SixMM.setSelected(false);
            EightMM.setSelected(false);
            TenMM.setSelected(false);
            ThreeMMM.setSelected(false);
        }
    }//GEN-LAST:event_TwelveMMActionPerformed

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        new GlassThickness().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:
        new Dimensions(glassType, GlassTypePrice, glassShape, GlassShapePrice, date, uid, user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel19MouseEntered

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
            java.util.logging.Logger.getLogger(GlassThickness.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GlassThickness.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GlassThickness.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GlassThickness.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GlassThickness().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ContinueBtn;
    private javax.swing.JRadioButton EightMM;
    private javax.swing.JRadioButton SixMM;
    private javax.swing.JRadioButton TenMM;
    private javax.swing.JRadioButton ThreeMM;
    private javax.swing.JRadioButton ThreeMMM;
    private javax.swing.JRadioButton TwelveMM;
    private javax.swing.JRadioButton TwoMM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
