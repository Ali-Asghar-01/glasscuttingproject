package GUI;

import Classes.User;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AdminForm extends javax.swing.JFrame {

    public AdminForm() { 
        this.setUndecorated(true);
        initComponents();
      }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        passwordtextfield = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        usernametextfield = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        login = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 255, 102));
        getContentPane().setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Password");
        jLayeredPane1.add(jLabel2);
        jLabel2.setBounds(80, 160, 170, 50);

        passwordtextfield.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordtextfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordtextfield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        passwordtextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordtextfieldActionPerformed(evt);
            }
        });
        passwordtextfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordtextfieldKeyPressed(evt);
            }
        });
        jLayeredPane1.add(passwordtextfield);
        passwordtextfield.setBounds(300, 165, 240, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("UserName");
        jLayeredPane1.add(jLabel1);
        jLabel1.setBounds(80, 110, 170, 40);

        usernametextfield.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernametextfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernametextfield.setAlignmentX(1.0F);
        usernametextfield.setAlignmentY(1.0F);
        usernametextfield.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        usernametextfield.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        usernametextfield.setDoubleBuffered(true);
        usernametextfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernametextfieldFocusLost(evt);
            }
        });
        usernametextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernametextfieldActionPerformed(evt);
            }
        });
        jLayeredPane1.add(usernametextfield);
        usernametextfield.setBounds(300, 110, 240, 40);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 102, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("     LOGIN ");
        jLayeredPane1.add(jLabel4);
        jLabel4.setBounds(-30, 20, 300, 50);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel5.setText("|  REGISTER");
        jLayeredPane1.add(jLabel5);
        jLabel5.setBounds(230, 20, 370, 50);

        login.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        login.setForeground(new java.awt.Color(0, 102, 255));
        login.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        login.setText("Login");
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginMouseExited(evt);
            }
        });
        jLayeredPane1.add(login);
        login.setBounds(300, 220, 130, 60);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Exit");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
        });
        jLayeredPane1.add(jLabel8);
        jLabel8.setBounds(420, 230, 100, 40);

        getContentPane().add(jLayeredPane1);
        jLayeredPane1.setBounds(60, 70, 650, 330);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(0, 0, 740, 450);

        setSize(new java.awt.Dimension(757, 491));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void usernametextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernametextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernametextfieldActionPerformed

    private void usernametextfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernametextfieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_usernametextfieldFocusLost

    private void passwordtextfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordtextfieldKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        
        Connecting c = new Connecting();
        String myUser=String.valueOf(usernametextfield.getText());
        String myPass=String.valueOf(passwordtextfield.getPassword());

        if( c.matchPassword(usernametextfield.getText(),myPass)){
            if("maaz".equals(myUser) || "Maaz".equals(myUser) || "MAAZ".equals(myUser)){
                JOptionPane.showMessageDialog(this,"Login Successful!");
                new AdminPanel(myUser,myPass).setVisible(true);
                dispose();
            }
            else if("azka".equals(myUser) || "Azka".equals(myUser) || "AZKA".equals(myUser)
                    || "momal".equals(myUser) || "Momal".equals(myUser) || "MOMAL".equals(myUser)
                    || "ASSISTANT".equals(myUser) || "Assistant".equals(myUser) || "assistant".equals(myUser)
                    || "HELLO".equals(myUser) || "Hello".equals(myUser) || "hello".equals(myUser)){
                    
                JOptionPane.showMessageDialog(this,"Login Successful!");
                new AdminPanel(myUser,myPass).setVisible(true);
                //new UserForm(myUser,myPass).setVisible(true);
                dispose();
            }
            else if("shahrukh".equals(myUser) || "Shahrukh".equals(myUser) || "SHAHRUKH".equals(myUser)
                    || "sysdba".equals(myUser) || "Sysdba".equals(myUser) || "SYSDBA".equals(myUser)){
                JOptionPane.showMessageDialog(this,"Login Successful!");
                new AdminPaneldba(myUser,myPass).setVisible(true);
                //AdminPaneldba dba = new AdminPaneldba();
                dispose();
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Try Again\nInvalid Password.");
        }
        }
    }//GEN-LAST:event_passwordtextfieldKeyPressed

    private void passwordtextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordtextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordtextfieldActionPerformed

    private void loginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMouseExited
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,102,255));
        lbl.setText(String.format("Login",login));
    }//GEN-LAST:event_loginMouseExited

    private void loginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMouseEntered
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(255,255,255));
        lbl.setText(String.format("Login",login));
    }//GEN-LAST:event_loginMouseEntered

    private void loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMouseClicked
        
        Connecting c = new Connecting();
        String myUser=String.valueOf(usernametextfield.getText());
        String myPass=String.valueOf(passwordtextfield.getPassword());
        
        if(c.matchPassword(myUser, myPass)){
            if("maaz".equals(myUser) || "Maaz".equals(myUser) || "MAAZ".equals(myUser)){
                JOptionPane.showMessageDialog(this,"Login Successful!");
                new AdminPanel(myUser,myPass).setVisible(true);
                //AP.User(myUser);
                dispose();
            }
            else if("azka".equals(myUser) || "Azka".equals(myUser) || "AZKA".equals(myUser)
                    || "momal".equals(myUser) || "Momal".equals(myUser) || "MOMAL".equals(myUser)
                     || "ASSISTANT".equals(myUser) || "Assistant".equals(myUser) || "assistant".equals(myUser)
                    || "HELLO".equals(myUser) || "Hello".equals(myUser) || "hello".equals(myUser)){
                JOptionPane.showMessageDialog(this,"Login Successful!");
                new AdminPanel(myUser,myPass).setVisible(true);
                dispose();
            }
            else if("shahrukh".equals(myUser) || "Shahrukh".equals(myUser) || "SHAHRUKH".equals(myUser)
                    || "sysdba".equals(myUser) || "Sysdba".equals(myUser) || "SYSDBA".equals(myUser)){
                JOptionPane.showMessageDialog(this,"Login Successful!");
                new AdminPaneldba(myUser,myPass).setVisible(true);
                dispose();
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Try Again\nInvalid Password.");
        }
    }//GEN-LAST:event_loginMouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        // TODO add your handling code here:
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(255,255,255));
        lbl.setText(String.format("Exit",jLabel8));
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        // TODO add your handling code here:
        JLabel lbl = (JLabel)evt.getComponent();
        lbl.setForeground(new Color(0,102,255));
        lbl.setText(String.format("Exit",jLabel8));
        
    }//GEN-LAST:event_jLabel8MouseExited
    
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
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel login;
    private javax.swing.JPasswordField passwordtextfield;
    private javax.swing.JTextField usernametextfield;
    // End of variables declaration//GEN-END:variables

}
