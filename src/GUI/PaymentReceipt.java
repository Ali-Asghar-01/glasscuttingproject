package GUI;

import Classes.Order;
import Classes.Vendor;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

public class PaymentReceipt extends javax.swing.JFrame {

    public PaymentReceipt() {
        this.setUndecorated(true);
        initComponents();
    }
    
    String glassType, glassShape, thickness, date, order_Price,myuser,mypass;
    int GlassTypePrice, GlassShapePrice, width, widthPrice, height,
            length,lengthPrice, heightPrice, thicknessPrice,total, qty, uid;

    public PaymentReceipt(String glassType, int glassTypePrice, String glassShape, int glassShapePrice,
            int length, int lengthPrice, int width, int widthPrice, int height, int heightPrice,
            String thickness, int thicknessPrice, String date, int uid,String user,String pass){
        
        this.setUndecorated(true);
        initComponents();     
        
        this.GlassShapePrice=glassShapePrice;
        this.GlassTypePrice=glassTypePrice;
        this.glassType=glassType;
        this.glassShape=glassShape;
        this.length=length;
        this.lengthPrice=lengthPrice;
        this.width=width;
        this.widthPrice=widthPrice;
        this.height=height;
        this.heightPrice=heightPrice;
        this.thickness=thickness;
        this.thicknessPrice=thicknessPrice;
        this.date=date;
        this.uid=uid;
        myuser=user;
        mypass=pass;
       
        qty = QuantityComboBox.getSelectedIndex()+1;
        
        total = (GlassShapePrice+GlassTypePrice+lengthPrice+widthPrice+heightPrice+thicknessPrice)*qty;
        G_Price_txt.setText(String.valueOf(Math.round(total)));
        G_Tax.setText(String.valueOf(Math.round(total*0.17)));
        G_Total_txt.setText(String.valueOf(Math.round(total*1.17)));
        G_Type_txt.setText(glassType); 
        G_Shape_txt.setText(glassShape);
        G_Thickness_txt.setText(thickness);
        G_Dim_txt.setText(String.valueOf(length)+"x"+String.valueOf(width));
        G_Date_txt.setText(date);
        useridtf.setText(String.valueOf(uid));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        QuantityComboBox = new javax.swing.JComboBox<>();
        PlaceOrderBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Details_txt = new javax.swing.JLabel();
        G_Type_txt = new javax.swing.JTextField();
        G_Shape_txt = new javax.swing.JTextField();
        G_Thickness_txt = new javax.swing.JTextField();
        G_Dim_txt = new javax.swing.JTextField();
        G_Price_txt = new javax.swing.JTextField();
        G_Total_txt = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        G_Tax = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        G_Date_txt = new javax.swing.JTextField();
        MainBtn = new javax.swing.JButton();
        Cross = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        useridtf = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        jLabel8.setText("jLabel8");

        jLabel3.setText("jLabel3");

        jLabel14.setText("jLabel14");

        jLabel16.setText("jLabel16");

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Dimensions:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(440, 290, 130, 50);

        jLabel1.setBackground(new java.awt.Color(51, 51, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ORDER SUMMARY");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(180, 10, 500, 80);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Price:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(440, 350, 120, 40);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Qty. Glass:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(40, 350, 110, 40);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("SubTotal.");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(440, 410, 100, 40);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel11);
        jLabel11.setBounds(390, 150, 0, 0);

        QuantityComboBox.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        QuantityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        QuantityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuantityComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(QuantityComboBox);
        QuantityComboBox.setBounds(220, 350, 160, 40);

        PlaceOrderBtn.setBackground(new java.awt.Color(102, 153, 255));
        PlaceOrderBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        PlaceOrderBtn.setText("Place Order");
        PlaceOrderBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PlaceOrderBtnMouseClicked(evt);
            }
        });
        PlaceOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlaceOrderBtnActionPerformed(evt);
            }
        });
        getContentPane().add(PlaceOrderBtn);
        PlaceOrderBtn.setBounds(220, 490, 180, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Glass Type:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 240, 130, 40);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Glass Shape:");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(440, 240, 120, 40);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Glass Thickness:");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(40, 290, 160, 40);

        Details_txt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Details_txt.setForeground(new java.awt.Color(255, 255, 255));
        Details_txt.setText("Details:");
        getContentPane().add(Details_txt);
        Details_txt.setBounds(20, 100, 170, 40);

        G_Type_txt.setEditable(false);
        G_Type_txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        G_Type_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(G_Type_txt);
        G_Type_txt.setBounds(220, 240, 160, 40);

        G_Shape_txt.setEditable(false);
        G_Shape_txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        G_Shape_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(G_Shape_txt);
        G_Shape_txt.setBounds(600, 240, 150, 40);

        G_Thickness_txt.setEditable(false);
        G_Thickness_txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        G_Thickness_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(G_Thickness_txt);
        G_Thickness_txt.setBounds(220, 290, 160, 40);

        G_Dim_txt.setEditable(false);
        G_Dim_txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        G_Dim_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(G_Dim_txt);
        G_Dim_txt.setBounds(600, 300, 150, 40);

        G_Price_txt.setEditable(false);
        G_Price_txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        G_Price_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        G_Price_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G_Price_txtActionPerformed(evt);
            }
        });
        getContentPane().add(G_Price_txt);
        G_Price_txt.setBounds(600, 350, 150, 40);

        G_Total_txt.setEditable(false);
        G_Total_txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        G_Total_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        G_Total_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G_Total_txtActionPerformed(evt);
            }
        });
        getContentPane().add(G_Total_txt);
        G_Total_txt.setBounds(600, 410, 150, 40);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Tax:");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(40, 410, 80, 40);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("17%");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(340, 410, 40, 40);

        G_Tax.setEditable(false);
        G_Tax.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        G_Tax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(G_Tax);
        G_Tax.setBounds(220, 410, 110, 40);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Date:");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(440, 100, 130, 40);

        G_Date_txt.setEditable(false);
        G_Date_txt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        G_Date_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(G_Date_txt);
        G_Date_txt.setBounds(600, 100, 150, 40);

        MainBtn.setBackground(new java.awt.Color(102, 153, 255));
        MainBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        MainBtn.setText("MainForm");
        MainBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainBtnMouseClicked(evt);
            }
        });
        MainBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainBtnActionPerformed(evt);
            }
        });
        getContentPane().add(MainBtn);
        MainBtn.setBounds(450, 490, 160, 50);

        Cross.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Cross.setForeground(new java.awt.Color(255, 255, 255));
        Cross.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Cross.setText("X");
        Cross.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CrossMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CrossMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                CrossMouseExited(evt);
            }
        });
        getContentPane().add(Cross);
        Cross.setBounds(750, 0, 50, 40);

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

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("User ID:");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(40, 180, 130, 40);

        useridtf.setEditable(false);
        useridtf.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        useridtf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(useridtf);
        useridtf.setBounds(220, 180, 160, 40);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        getContentPane().add(jLabel12);
        jLabel12.setBounds(0, 0, 810, 640);

        setSize(new java.awt.Dimension(818, 599));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
        
    private void QuantityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuantityComboBoxActionPerformed
        // TODO add your handling code here:
        qty = QuantityComboBox.getSelectedIndex()+1;
        
        total = (GlassShapePrice+GlassTypePrice+lengthPrice+widthPrice+heightPrice+thicknessPrice)*qty;
        G_Price_txt.setText(String.valueOf(total));
        G_Tax.setText(String.valueOf(total*0.17));
        G_Total_txt.setText(String.valueOf(total*1.17));
    }//GEN-LAST:event_QuantityComboBoxActionPerformed

    private void PlaceOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlaceOrderBtnActionPerformed
        // TODO add your handling code here:
        
        qty = QuantityComboBox.getSelectedIndex()+1;
        System.out.println("In btn class "+this.myuser+this.mypass);
        try{
            Order o = new Order();
            o.setMyUser(myuser);
            o.setMyPass(mypass);
            o.setGlassType(glassType);
            o.setGlassTypePrice(GlassTypePrice);
            o.setGlassShape(glassShape);
            o.setGlassShapePrice(GlassShapePrice);
            o.setGlassThickness(thickness);
            o.setGlassThicknessPrice(thicknessPrice);
            o.setGlassDimensionsLength(length);
            o.setLengthPrice(lengthPrice);
            o.setGlassDimensionsHeight(height);
            o.setHeightPrice(heightPrice);
            o.setGlassDimensionsWidth(width);
            o.setWidthPrice(widthPrice);
            o.setQuantity(qty);
            o.setTotal_Order_Price((int) (Math.round(total*1.17)));
            o.setDate(date);
            Order.setU_ID(uid);
            o.SaveOrderData(o);
        
            JOptionPane.showMessageDialog(this, "Order Placed.");
            
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(this, e);
        }finally{
            Vendor v = new Vendor();
            v.SubtractGlassQuantity(glassType, qty);
        }
    }//GEN-LAST:event_PlaceOrderBtnActionPerformed

    private void G_Price_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G_Price_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_G_Price_txtActionPerformed

    private void G_Total_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_G_Total_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_G_Total_txtActionPerformed

    private void PlaceOrderBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlaceOrderBtnMouseClicked
        // TODO add your handling code here:
//        MainBtn.setEnabled(true);
    }//GEN-LAST:event_PlaceOrderBtnMouseClicked

    private void MainBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainBtnActionPerformed
        // TODO add your handling code here:
        new AdminPanel(myuser, mypass).setVisible(true);
        dispose();
    }//GEN-LAST:event_MainBtnActionPerformed

    private void CrossMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CrossMouseEntered
        // TODO add your handling code here:
      // JLabel lbl = (JLabel)evt.getComponent();
//       lbl.setForeground(new Color(0,0,0));
      // lbl.setText(String.format("",Cross));

    }//GEN-LAST:event_CrossMouseEntered

    private void CrossMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CrossMouseExited
        // TODO add your handling code here:
       //JLabel lbl = (JLabel)evt.getComponent();
       //lbl.setForeground(new Color(255,255,255));
       //lbl.setText(String.format("",Cross));
    }//GEN-LAST:event_CrossMouseExited

    private void CrossMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CrossMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_CrossMouseClicked

    private void MainBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainBtnMouseClicked
        // TODO add your handling code here:
        new AdminPanel(myuser, mypass).setVisible(true);
        dispose();
    }//GEN-LAST:event_MainBtnMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
        new GlassThickness(glassType, GlassTypePrice, glassShape, GlassShapePrice, length, 
                lengthPrice, width, widthPrice, height, heightPrice, date, uid, myuser, mypass).setVisible(true);
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
            java.util.logging.Logger.getLogger(PaymentReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentReceipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Cross;
    private javax.swing.JLabel Details_txt;
    private javax.swing.JTextField G_Date_txt;
    private javax.swing.JTextField G_Dim_txt;
    private javax.swing.JTextField G_Price_txt;
    private javax.swing.JTextField G_Shape_txt;
    private javax.swing.JTextField G_Tax;
    private javax.swing.JTextField G_Thickness_txt;
    private javax.swing.JTextField G_Total_txt;
    private javax.swing.JTextField G_Type_txt;
    private javax.swing.JButton MainBtn;
    private javax.swing.JButton PlaceOrderBtn;
    private javax.swing.JComboBox<String> QuantityComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField useridtf;
    // End of variables declaration//GEN-END:variables
}
