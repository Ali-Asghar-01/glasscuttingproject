package GUI;

import Classes.Order;
import javax.swing.JOptionPane;
public class GlassType extends javax.swing.JFrame {

    String date;
    int uid;
    public GlassType() {
       //this.setUndecorated(true);
       initComponents();
    }
    String user,pass;
    public GlassType(String date, int uid, String user, String pass) {
        initComponents();
        this.setUndecorated(true);
        this.date = date;
        this.uid=uid;
        this.user=user;
        this.pass=pass;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TemperedGlass = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        CeramicGlass = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        AnnealedGlass = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        MirrorGlass = new javax.swing.JRadioButton();
        Label = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ContinueButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Glass Order");
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Glass Type");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(280, 0, 320, 80);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/Tempered.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 80, 150, 150);

        TemperedGlass.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TemperedGlass.setText("Tempered Glass");
        TemperedGlass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TemperedGlassActionPerformed(evt);
            }
        });
        getContentPane().add(TemperedGlass);
        TemperedGlass.setBounds(200, 90, 150, 30);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/Ceramic.jpg"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(540, 270, 140, 130);

        CeramicGlass.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CeramicGlass.setText("Ceramic Glass");
        CeramicGlass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CeramicGlassActionPerformed(evt);
            }
        });
        getContentPane().add(CeramicGlass);
        CeramicGlass.setBounds(690, 280, 140, 25);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/Annealed.jpg"))); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(40, 280, 160, 130);

        AnnealedGlass.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AnnealedGlass.setText("Annealed Glass");
        AnnealedGlass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnealedGlassActionPerformed(evt);
            }
        });
        getContentPane().add(AnnealedGlass);
        AnnealedGlass.setBounds(210, 290, 140, 25);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/Mirror.jpg"))); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(520, 90, 160, 120);

        MirrorGlass.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        MirrorGlass.setText("Mirror Glass");
        MirrorGlass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MirrorGlassActionPerformed(evt);
            }
        });
        getContentPane().add(MirrorGlass);
        MirrorGlass.setBounds(700, 90, 120, 25);

        Label.setText("<HTML> This product is used most often for<br>sheleves, some fire places and"
            + "table tops, <br> Tempered Glass breaks into small pieces <br> when broken and usually "
            + "cracks.</HTML>");
        Label.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        //Label.setText("<HTML> Line1<br> Line2</HTML>");
        getContentPane().add(Label);
        Label.setBounds(200, 130, 310, 140);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("<HTML> Often used in small pieces. This product is"
            + "<br> not considered a safety glass. Annealed <br> Glass doesn't shatter"
            + "into small pieces. If <br> broken, the piece will crack into large <br>"
            + "shards. Maximum annealed glass size: <br> 28 x 28. For pieces larger"
            + "than this please <br> select Tempered Glass. </HTML>");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel6);
        jLabel6.setBounds(220, 320, 310, 140);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("<HTML>This product is used to replace any mirror. <br>"
            + "Mirrors are not tempered and if broken <br> will brake into large"
            + " shards. </HTML>");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel7);
        jLabel7.setBounds(700, 120, 260, 90);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("<HTML>These products are made to withstand <br>"
            + "very high temperature and are often <br> used in woodstoves, gas stoves, ovens,"
            + " <br> halogen lamps, and laboratories. Ceramic <br> glasses donot shatter. If broken,"
            + "the piece <br> will crack into large shards. </HTML>");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel8);
        jLabel8.setBounds(700, 310, 270, 160);

        ContinueButton.setBackground(new java.awt.Color(204, 204, 204));
        ContinueButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ContinueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_continue_60172.png"))); // NOI18N
        ContinueButton.setText("Continue");
        ContinueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinueButtonActionPerformed(evt);
            }
        });
        getContentPane().add(ContinueButton);
        ContinueButton.setBounds(260, 480, 140, 40);

        jLabel10.setText("jLabel10");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(680, 130, 40, 14);
        getContentPane().add(jPanel1);
        jPanel1.setBounds(530, 200, 10, 10);

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
        jLabel18.setBounds(30, 20, 50, 40);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/minimalism-1152.jpg"))); // NOI18N
        getContentPane().add(jLabel9);
        jLabel9.setBounds(0, 0, 990, 560);

        setSize(new java.awt.Dimension(1002, 590));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    int GlassTypePrice;
    private void TemperedGlassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TemperedGlassActionPerformed
        // TODO add your handling code here:
        if(TemperedGlass.isSelected()){
            CeramicGlass.setSelected(false);
            AnnealedGlass.setSelected(false);
            MirrorGlass.setSelected(false);
            //GlassType = TemperedGlass.getText();
        }
       // GlassTypePrice = 1700;
    }//GEN-LAST:event_TemperedGlassActionPerformed

    private void MirrorGlassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MirrorGlassActionPerformed
        // TODO add your handling code here:
        if(MirrorGlass.isSelected()){
            CeramicGlass.setSelected(false);
            AnnealedGlass.setSelected(false);
            TemperedGlass.setSelected(false);
          //  GlassType = MirrorGlass.getText();
        }
        //GlassTypePrice = 700;
    }//GEN-LAST:event_MirrorGlassActionPerformed

    private void AnnealedGlassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnealedGlassActionPerformed
        // TODO add your handling code here:
        if(AnnealedGlass.isSelected()){
            CeramicGlass.setSelected(false);
            TemperedGlass.setSelected(false);
            MirrorGlass.setSelected(false);
            //GlassType = AnnealedGlass.getText();
        }
        //GlassTypePrice = 900;
    }//GEN-LAST:event_AnnealedGlassActionPerformed

    private void CeramicGlassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CeramicGlassActionPerformed
        // TODO add your handling code here:
        if(CeramicGlass.isSelected()){
            TemperedGlass.setSelected(false);
            AnnealedGlass.setSelected(false);
            MirrorGlass.setSelected(false);
            //GlassType = CeramicGlass.getText();
        }
        //GlassTypePrice = 1500;
    }//GEN-LAST:event_CeramicGlassActionPerformed
    
    String GlassType;
    private void ContinueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinueButtonActionPerformed
        // TODO add your handling code here:
        
        if(TemperedGlass.isSelected()){
            GlassType = TemperedGlass.getText();
            //GlassTypePrice = 1700;
        }
        else if(CeramicGlass.isSelected()){
            GlassType = CeramicGlass.getText();
            //GlassTypePrice = 1500;
        }
        else if(AnnealedGlass.isSelected()){
            GlassType = AnnealedGlass.getText();
            //GlassTypePrice = 1100;
        }
        else if(MirrorGlass.isSelected()){
            GlassType = MirrorGlass.getText();
            //GlassTypePrice = 700;
        }
       
        Order o = new Order();
        o.setMyUser(user);
        o.setMyPass(pass);
        GlassTypePrice = o.fetchglasstypeprice(GlassType);
        //System.out.println(glassprice*1.25);
        
        try{
            if(!GlassType.isEmpty()){
                new GlassShape(GlassType, GlassTypePrice, date, uid,user,pass).setVisible(true);
                dispose();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "First Select the Type Of Glass");
        }
    }//GEN-LAST:event_ContinueButtonActionPerformed

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        new UserForm(user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GlassType().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton AnnealedGlass;
    private javax.swing.JRadioButton CeramicGlass;
    private javax.swing.JButton ContinueButton;
    private javax.swing.JLabel Label;
    private javax.swing.JRadioButton MirrorGlass;
    public javax.swing.JRadioButton TemperedGlass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
