
package GUI;

import Classes.*;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JLabel;

public class FetchUserdata extends javax.swing.JFrame {

    public FetchUserdata() {
        this.setUndecorated(true);
        initComponents();
        //User u = new User();
        //u.fetchuser(jTable1);
        //RecordLabel.setText(String.valueOf("Total Records found: " + u.count(jTable1)));
    }
    String user,pass;
    public FetchUserdata(String user, String pass) {
        this.setUndecorated(true);
        initComponents();
        this.user=user;
        this.pass=pass;
        
        //System.out.println("USER "+user+pass);
        User u = new User();
        u.setMyUser(user);
        u.setMyPass(pass);
        u.fetchuser(jTable1);
        RecordLabel.setText(String.valueOf("Total Records found: " + u.count(jTable1)));
    }

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        SearchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        RecordLabel = new javax.swing.JLabel();
        DeleteButton = new javax.swing.JButton();
        SearchTextField = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        id_Txt = new javax.swing.JFormattedTextField();
        contact_Txt = new javax.swing.JFormattedTextField();
        DelTextField = new javax.swing.JFormattedTextField();
        is_type_txt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Name_Txt1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        SearchButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        SearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_search_126577 (1).png"))); // NOI18N
        SearchButton.setText("Search Record");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });
        getContentPane().add(SearchButton);
        SearchButton.setBounds(870, 470, 150, 32);

        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "CNIC", "Contact", "City", "Date"
            }
        ));
        jTable1.setUpdateSelectionOnSort(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 117, 781, 490);

        RecordLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        RecordLabel.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(RecordLabel);
        RecordLabel.setBounds(30, 620, 240, 31);

        DeleteButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DeleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_f-cross_256_282471.png"))); // NOI18N
        DeleteButton.setText("Delete Record");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });
        getContentPane().add(DeleteButton);
        DeleteButton.setBounds(870, 520, 153, 34);

        SearchTextField.setForeground(new java.awt.Color(153, 153, 153));
        SearchTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SearchTextField.setText("Search Via ID");
        SearchTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        SearchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchTextFieldMouseClicked(evt);
            }
        });
        SearchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchTextFieldActionPerformed(evt);
            }
        });
        getContentPane().add(SearchTextField);
        SearchTextField.setBounds(1040, 470, 111, 32);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(870, 160, 60, 28);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(870, 200, 60, 32);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TYPE:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(870, 260, 60, 25);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Contact No:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(870, 310, 103, 25);

        id_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        id_Txt.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        id_Txt.setEnabled(false);
        id_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_TxtActionPerformed(evt);
            }
        });
        getContentPane().add(id_Txt);
        id_Txt.setBounds(1010, 160, 139, 28);

        try {
            contact_Txt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-#######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        contact_Txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contact_Txt.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        contact_Txt.setEnabled(false);
        contact_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contact_TxtActionPerformed(evt);
            }
        });
        getContentPane().add(contact_Txt);
        contact_Txt.setBounds(1010, 310, 139, 25);

        DelTextField.setForeground(new java.awt.Color(153, 153, 153));
        DelTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        DelTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DelTextField.setText("Del Via ID");
        DelTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        DelTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DelTextFieldMouseClicked(evt);
            }
        });
        DelTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelTextFieldActionPerformed(evt);
            }
        });
        getContentPane().add(DelTextField);
        DelTextField.setBounds(1040, 520, 111, 34);

        is_type_txt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        is_type_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        is_type_txt.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        is_type_txt.setEnabled(false);
        getContentPane().add(is_type_txt);
        is_type_txt.setBounds(1010, 260, 140, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("User Details");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(109, 18, 358, 94);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });
        getContentPane().add(jLabel9);
        jLabel9.setBounds(10, 40, 70, 50);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_edit-clear_118917.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel10);
        jLabel10.setBounds(1030, 580, 70, 30);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_save_accept_44368.png"))); // NOI18N
        jLabel14.setToolTipText("");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel14);
        jLabel14.setBounds(1100, 580, 30, 30);

        Name_Txt1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Name_Txt1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Name_Txt1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Name_Txt1.setEnabled(false);
        getContentPane().add(Name_Txt1);
        Name_Txt1.setBounds(1010, 210, 140, 30);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jLabel8.setText("d");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(0, 0, 1190, 680);

        setSize(new java.awt.Dimension(1211, 720));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
     
    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
    
        User u = new User();
        int s = Integer.parseInt(SearchTextField.getText());
        System.out.println(user+pass);
        u.setMyUser(user);
        u.setMyPass(pass);
        u.search(jTable1, s);
    }//GEN-LAST:event_SearchButtonActionPerformed
    
    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        
        User u = new User();
        int id = Integer.parseInt(DelTextField.getText());
        u.setMyUser(user);
        u.setMyPass(pass);
        u.delete(id);
        u.fetchuser(jTable1);
    }//GEN-LAST:event_DeleteButtonActionPerformed
    
    private void SearchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchTextFieldActionPerformed
        // TODO add your handling code here:
        SearchTextField.setText("");
    }//GEN-LAST:event_SearchTextFieldActionPerformed

    private void SearchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchTextFieldMouseClicked
        // TODO add your handling code here:
        SearchTextField.setText("");
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_SearchTextFieldMouseClicked

    private void DelTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelTextFieldActionPerformed
        // TODO add your handling code here:
        DelTextField.setText("");
    }//GEN-LAST:event_DelTextFieldActionPerformed

    private void DelTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DelTextFieldMouseClicked
        // TODO add your handling code here:
        DelTextField.setText("");
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,0,0));
        
    }//GEN-LAST:event_DelTextFieldMouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
            User u= new User();
            u.setMyUser(user);
            u.setMyPass(pass);
            u.viewinFields(jTable1);
            
                int add = User.getU_ID();
                String s = String.valueOf(add);
                id_Txt.setText(s);
                
                String add1=u.getName();
                Name_Txt1.setText(add1);
                
//                String add2=u.getCNIC();
//                cnic_Txt.setText(add2);
//                
                String add3 = u.getContactNo();
                contact_Txt.setText(add3);
                
//                String add4 = u.getDate();
//                datec.setText(add4);
                
//                String add5 = u.getCity();
//                city_Txt.setText(add5);
                String add5 = u.getIs_type();
                is_type_txt.setText(add5);
                
    }//GEN-LAST:event_jTable1MouseClicked

    private void contact_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contact_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contact_TxtActionPerformed

    private void id_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_TxtActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        new AdminPanel(user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        // TODO add your handling code here: 
    /*   JLabel lbl = (JLabel)evt.getComponent();
       lbl.setForeground(new Color(255,255,255));
       lbl.setText(String.format("",jLabel9));   */
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // TODO add your handling code here:
     /*  JLabel lbl = (JLabel)evt.getComponent();
       lbl.setForeground(new Color(0,0,0));
       lbl.setText(String.format("",jLabel9));  */
    }//GEN-LAST:event_jLabel9MouseExited

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        User u = new User();
        u.setMyUser(user);
        u.setMyPass(pass);
        u.fetchuser(jTable1);
    }//GEN-LAST:event_formWindowOpened

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        id_Txt.setText("");
        Name_Txt1.setText("");
        is_type_txt.setText("");
//        cnic_Txt.setText("");
        contact_Txt.setText("");
//        datec.setText("");
//        city_Txt.setText("");
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        User u = new User();
        u.setMyUser(user);
        u.setMyPass(pass);
        u.fetchuser(jTable1);
    }//GEN-LAST:event_jLabel14MouseClicked

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
            java.util.logging.Logger.getLogger(FetchUserdata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FetchUserdata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FetchUserdata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FetchUserdata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FetchUserdata().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField DelTextField;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JTextField Name_Txt1;
    private javax.swing.JLabel RecordLabel;
    private javax.swing.JButton SearchButton;
    private javax.swing.JFormattedTextField SearchTextField;
    private javax.swing.JFormattedTextField contact_Txt;
    private javax.swing.JFormattedTextField id_Txt;
    private javax.swing.JTextField is_type_txt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
