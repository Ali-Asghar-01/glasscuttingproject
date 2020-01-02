package GUI;

import javax.swing.JOptionPane;

public class GlassShape extends javax.swing.JFrame {

    public GlassShape() {
        
        initComponents();
    }

    String GlassType, date, user, pass;
    int GlassTypePrice, uid;
    public GlassShape(String a, int b, String date, int uid, String user, String pass){
        this.setUndecorated(true);
        initComponents();
        this.GlassType = a;
        this.GlassTypePrice = b;
        this.date=date;
        this.uid = uid;
        this.user = user;
        this.pass = pass;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Square = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        Circle = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Pentagon = new javax.swing.JRadioButton();
        RoundedCorners = new javax.swing.JRadioButton();
        Diamond = new javax.swing.JRadioButton();
        Triangle = new javax.swing.JRadioButton();
        ContinueBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_Square_1597478.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(60, 110, 150, 100);

        Square.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Square.setText("Square ");
        Square.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SquareActionPerformed(evt);
            }
        });
        getContentPane().add(Square);
        Square.setBounds(70, 220, 130, 23);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_Circle_1597486.png"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(250, 100, 160, 110);

        Circle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Circle.setText("Circle");
        Circle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CircleActionPerformed(evt);
            }
        });
        getContentPane().add(Circle);
        Circle.setBounds(300, 220, 60, 23);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_pentagon_1597480.png"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(470, 100, 140, 110);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_186_FormRoundedBorder_183304.png"))); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(50, 260, 160, 150);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_Diamond_1597485.png"))); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(270, 260, 160, 150);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_Triangle_1597477.png"))); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(470, 300, 160, 120);

        Pentagon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Pentagon.setText("Pentagon");
        Pentagon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PentagonActionPerformed(evt);
            }
        });
        getContentPane().add(Pentagon);
        Pentagon.setBounds(500, 220, 90, 23);

        RoundedCorners.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RoundedCorners.setText("Rounded Corners");
        RoundedCorners.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RoundedCornersActionPerformed(evt);
            }
        });
        getContentPane().add(RoundedCorners);
        RoundedCorners.setBounds(60, 410, 140, 23);

        Diamond.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Diamond.setText("Diamond");
        Diamond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiamondActionPerformed(evt);
            }
        });
        getContentPane().add(Diamond);
        Diamond.setBounds(300, 410, 130, 23);

        Triangle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Triangle.setText("Triangle");
        Triangle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TriangleActionPerformed(evt);
            }
        });
        getContentPane().add(Triangle);
        Triangle.setBounds(510, 410, 90, 23);

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
        ContinueBtn.setBounds(290, 480, 150, 40);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("GLASS SHAPE");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(180, 0, 310, 90);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel20);
        jLabel20.setBounds(30, 30, 50, 40);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/bggg.jpg"))); // NOI18N
        jLabel8.setText("jLabel8");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(0, 0, 680, 540);

        setSize(new java.awt.Dimension(697, 572));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void SquareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SquareActionPerformed
        // TODO add your handling code here:
        if(Square.isSelected()){
            Circle.setSelected(false);
            Pentagon.setSelected(false);
            RoundedCorners.setSelected(false);
            Diamond.setSelected(false);
            Triangle.setSelected(false);
        }
    }//GEN-LAST:event_SquareActionPerformed

    private void DiamondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiamondActionPerformed
        // TODO add your handling code here:
        if(Diamond.isSelected()){
            Circle.setSelected(false);
            Pentagon.setSelected(false);
            RoundedCorners.setSelected(false);
            Square.setSelected(false);
            Triangle.setSelected(false);
        }
    }//GEN-LAST:event_DiamondActionPerformed

    private void CircleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CircleActionPerformed
        // TODO add your handling code here:
        if(Circle.isSelected()){
            Square.setSelected(false);
            Pentagon.setSelected(false);
            RoundedCorners.setSelected(false);
            Diamond.setSelected(false);
            Triangle.setSelected(false);
        }
    }//GEN-LAST:event_CircleActionPerformed

    private void TriangleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TriangleActionPerformed
        // TODO add your handling code here:
        if(Triangle.isSelected()){
            Circle.setSelected(false);
            Pentagon.setSelected(false);
            RoundedCorners.setSelected(false);
            Diamond.setSelected(false);
            Square.setSelected(false);
        } 
    }//GEN-LAST:event_TriangleActionPerformed

    private void PentagonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PentagonActionPerformed
        // TODO add your handling code here:
        if(Pentagon.isSelected()){
            Circle.setSelected(false);
            Square.setSelected(false);
            RoundedCorners.setSelected(false);
            Diamond.setSelected(false);
            Triangle.setSelected(false);
        } 
    }//GEN-LAST:event_PentagonActionPerformed

    private void RoundedCornersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RoundedCornersActionPerformed
        // TODO add your handling code here:
        if(RoundedCorners.isSelected()){
            Circle.setSelected(false);
            Pentagon.setSelected(false);
            Square.setSelected(false);
            Diamond.setSelected(false);
            Triangle.setSelected(false);
        }        
    }//GEN-LAST:event_RoundedCornersActionPerformed
    
    String GlassShape;
    int GlassShapePrice;
    private void ContinueBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinueBtnActionPerformed
        // TODO add your handling code here:
        if(Square.isSelected()){
            GlassShape = Square.getText();
            GlassShapePrice = 400;
        }
        else if (Circle.isSelected()){
            GlassShape = Circle.getText();
            GlassShapePrice = 600;
        }
        else if (Pentagon.isSelected()){
            GlassShape = Pentagon.getText();
            GlassShapePrice = 1100;
        }
        else if (RoundedCorners.isSelected()){
            GlassShape = RoundedCorners.getText();
            GlassShapePrice = 800;
        }
        else if (Diamond.isSelected()){
            GlassShape = Diamond.getText();
            GlassShapePrice = 2000;
        }
        else if (Triangle.isSelected()){
            GlassShape = Triangle.getText();
            GlassShapePrice = 900;
        }
        
        try{
            if(!GlassShape.isEmpty()){
                new Dimensions(GlassType, GlassTypePrice, GlassShape, GlassShapePrice, date, uid, user, pass).setVisible(true);
                dispose();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "First Select the Shape Of Glass");
        }
        
    }//GEN-LAST:event_ContinueBtnActionPerformed

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // TODO add your handling code here:
        new GlassType(date, uid, user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel20MouseClicked

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
            java.util.logging.Logger.getLogger(GlassShape.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GlassShape.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GlassShape.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GlassShape.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GlassShape().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Circle;
    private javax.swing.JButton ContinueBtn;
    private javax.swing.JRadioButton Diamond;
    private javax.swing.JRadioButton Pentagon;
    private javax.swing.JRadioButton RoundedCorners;
    private javax.swing.JRadioButton Square;
    private javax.swing.JRadioButton Triangle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}
