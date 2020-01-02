package GUI;

import java.awt.Color;
import javax.swing.JLabel;

public class AdminPaneldba extends javax.swing.JFrame {

    public AdminPaneldba() {
        this.setUndecorated(true);  
        initComponents();   
    }
    String user,pass;
    public AdminPaneldba(String user, String pass) {
        this.setUndecorated(true);  
        initComponents();
        this.user=user;
        this.pass=pass;
        System.out.println("AP "+user+pass);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        loggedin = new javax.swing.JLabel();
        Logout = new javax.swing.JLabel();
        vendor = new javax.swing.JLabel();
        order = new javax.swing.JLabel();
        record = new javax.swing.JLabel();
        sales = new javax.swing.JLabel();
        record1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        loggedin.setFont(new java.awt.Font("Tahoma", 1, 72)); // NOI18N
        loggedin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        loggedin.setText("DBA");
        loggedin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loggedinMouseEntered(evt);
            }
        });
        jLayeredPane1.add(loggedin);
        loggedin.setBounds(30, 10, 330, 110);

        Logout.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Logout.setForeground(new java.awt.Color(255, 255, 255));
        Logout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
        Logout.setText("Logout");
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LogoutMouseExited(evt);
            }
        });
        jLayeredPane1.add(Logout);
        Logout.setBounds(550, 0, 160, 50);

        vendor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        vendor.setForeground(new java.awt.Color(255, 255, 255));
        vendor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vendor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_miscellaneous_3517.png"))); // NOI18N
        vendor.setText("VENDOR");
        vendor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        vendor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vendorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                vendorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                vendorMouseExited(evt);
            }
        });
        jLayeredPane1.add(vendor);
        vendor.setBounds(50, 150, 180, 60);

        order.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        order.setForeground(new java.awt.Color(255, 255, 255));
        order.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        order.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_sales-report_49617.png"))); // NOI18N
        order.setText("ORDERS");
        order.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                orderMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                orderMouseExited(evt);
            }
        });
        jLayeredPane1.add(order);
        order.setBounds(50, 250, 180, 60);

        record.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        record.setForeground(new java.awt.Color(255, 255, 255));
        record.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        record.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/medical-records (1).png"))); // NOI18N
        record.setText("RECORDS");
        record.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        record.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                recordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                recordMouseExited(evt);
            }
        });
        jLayeredPane1.add(record);
        record.setBounds(350, 250, 180, 60);

        sales.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        sales.setForeground(new java.awt.Color(255, 255, 255));
        sales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_sales-report_49617.png"))); // NOI18N
        sales.setText("SALES");
        sales.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                salesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                salesMouseExited(evt);
            }
        });
        jLayeredPane1.add(sales);
        sales.setBounds(330, 150, 180, 60);

        record1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        record1.setForeground(new java.awt.Color(255, 255, 255));
        record1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        record1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/medical-records (1).png"))); // NOI18N
        record1.setText("REPORT");
        record1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        record1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                record1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                record1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                record1MouseExited(evt);
            }
        });
        jLayeredPane1.add(record1);
        record1.setBounds(30, 340, 230, 60);

        getContentPane().add(jLayeredPane1);
        jLayeredPane1.setBounds(10, 10, 720, 460);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 750, 480);

        setSize(new java.awt.Dimension(757, 521));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void User(String user1){
       //showUser.setText(user1);
    }
    
    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        // TODO add your handling code here:
        AdminForm a = new AdminForm();
        a.setVisible(true);
        dispose();
        this.hide();
    }//GEN-LAST:event_LogoutMouseClicked

    private void LogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseEntered
        // TODO add your handling code here:        
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,0,0));
        lbl.setText(String.format("Logout",Logout));
    }//GEN-LAST:event_LogoutMouseEntered

    private void LogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseExited
        // TODO add your handling code here:
       JLabel lbl = (JLabel)evt.getComponent();
       lbl.setForeground(new Color(255,255,255));
       lbl.setText(String.format("Logout",Logout)); 

    }//GEN-LAST:event_LogoutMouseExited

    private void recordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordMouseClicked
        new FetchUserdatadba(user,pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_recordMouseClicked

    private void recordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordMouseEntered
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,0,0));
        lbl.setText(String.format("RECORDS",record));
    }//GEN-LAST:event_recordMouseEntered

    private void recordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordMouseExited
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(255,255,255));
        lbl.setText(String.format("RECORDS",record));
    }//GEN-LAST:event_recordMouseExited

    private void vendorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vendorMouseClicked
        // TODO add your handling code here:
        new VFdba(user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_vendorMouseClicked

    private void vendorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vendorMouseEntered
        // TODO add your handling code here:
                JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,0,0));
        lbl.setText(String.format("VENDORS",vendor));

    }//GEN-LAST:event_vendorMouseEntered

    private void vendorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vendorMouseExited
        // TODO add your handling code here:
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(255,255,255));
        lbl.setText(String.format("VENDOR",vendor));
    }//GEN-LAST:event_vendorMouseExited

    private void salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesMouseClicked
        // TODO add your handling code here:
        new SalesFdba(user,pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_salesMouseClicked

    private void salesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesMouseEntered
        // TODO add your handling code here:
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,0,0));
        lbl.setText(String.format("SALES",sales));

    }//GEN-LAST:event_salesMouseEntered

    private void salesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesMouseExited
        // TODO add your handling code here:
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(255,255,255));
        lbl.setText(String.format("SALES",sales));
    }//GEN-LAST:event_salesMouseExited

    private void orderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderMouseClicked
        // TODO add your handling code here:
        new OrderDetailsFdba(user,pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_orderMouseClicked

    private void orderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderMouseEntered
        // TODO add your handling code here:
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,0,0));
        lbl.setText(String.format("ORDERS",order));

    }//GEN-LAST:event_orderMouseEntered

    private void orderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderMouseExited
        // TODO add your handling code here:
                JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(255,255,255));
        lbl.setText(String.format("ORDERS",order));
    }//GEN-LAST:event_orderMouseExited

    private void loggedinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loggedinMouseEntered
        // TODO add your handling code here:
//        JLabel lbl = (JLabel)evt.getComponent();
  //      lbl.setForeground(new Color(0,0,0));
    //    lbl.setText(String.format("DBA",loggedin));

    }//GEN-LAST:event_loggedinMouseEntered

    private void record1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_record1MouseClicked
        // TODO add your handling code here:
        new TopReportF(user,pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_record1MouseClicked

    private void record1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_record1MouseEntered
        // TODO add your handling code here:
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,0,0));
        lbl.setText(String.format("REPORT",record1));

    }//GEN-LAST:event_record1MouseEntered

    private void record1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_record1MouseExited
        // TODO add your handling code here:
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(255,255,255));
        lbl.setText(String.format("REPORT",record1));
    }//GEN-LAST:event_record1MouseExited

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPaneldba().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logout;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel loggedin;
    private javax.swing.JLabel order;
    private javax.swing.JLabel record;
    private javax.swing.JLabel record1;
    private javax.swing.JLabel sales;
    private javax.swing.JLabel vendor;
    // End of variables declaration//GEN-END:variables
}
