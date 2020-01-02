
package GUI;

import Classes.*;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JLabel;

public class OrderDetailsF extends javax.swing.JFrame {

    public OrderDetailsF() {
        this.setUndecorated(true);
        initComponents();
    }
    String user,pass;
    public OrderDetailsF(String user, String pass) {
        this.setUndecorated(true);
        initComponents();
        this.user=user;
        this.pass=pass;
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        oid = new javax.swing.JFormattedTextField();
        type = new javax.swing.JFormattedTextField();
        shape = new javax.swing.JFormattedTextField();
        DelTextField = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        uid = new javax.swing.JTextField();
        thickness = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        dimension = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        totalamount = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        quantity = new javax.swing.JTextField();
        datec = new javax.swing.JTextField();
        monthcombo = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        monthcombouser = new javax.swing.JComboBox<>();
        appear = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        AddRecordBtn = new javax.swing.JButton();

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
        SearchButton.setBounds(850, 570, 160, 32);

        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "User ID", "Glass Type", "Glass Shape", "Glass Thickness", "Glass Dimension", "Date", "Quantity", "Total"
            }
        ));
        jTable1.setCellSelectionEnabled(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 117, 781, 430);

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
        DeleteButton.setBounds(850, 610, 160, 34);

        SearchTextField.setForeground(new java.awt.Color(153, 153, 153));
        SearchTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SearchTextField.setText("Search Via ID");
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
        SearchTextField.setBounds(1040, 570, 111, 32);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fetch By Month");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(860, 120, 140, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("User ID:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(860, 220, 80, 32);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Type:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(860, 300, 100, 25);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Shape:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(860, 340, 103, 25);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Thickness:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(860, 380, 100, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Date:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(860, 260, 52, 29);

        oid.setBackground(new java.awt.Color(240, 240, 240));
        oid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        oid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        oid.setEnabled(false);
        oid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oidActionPerformed(evt);
            }
        });
        getContentPane().add(oid);
        oid.setBounds(1010, 180, 139, 28);

        type.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        type.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        type.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        type.setEnabled(false);
        getContentPane().add(type);
        type.setBounds(1010, 300, 139, 30);

        shape.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        shape.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        shape.setEnabled(false);
        shape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeActionPerformed(evt);
            }
        });
        getContentPane().add(shape);
        shape.setBounds(1010, 340, 139, 25);

        DelTextField.setForeground(new java.awt.Color(153, 153, 153));
        DelTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        DelTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        DelTextField.setText("Del Via ID");
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
        DelTextField.setBounds(1040, 610, 111, 34);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Order Details");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(109, 18, 358, 94);

        uid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        uid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        uid.setEnabled(false);
        getContentPane().add(uid);
        uid.setBounds(1010, 220, 140, 30);

        thickness.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        thickness.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        thickness.setEnabled(false);
        getContentPane().add(thickness);
        thickness.setBounds(1010, 380, 140, 30);

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
        jLabel9.setBounds(10, 50, 70, 40);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Dimension:");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(860, 420, 100, 32);

        dimension.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dimension.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dimension.setEnabled(false);
        getContentPane().add(dimension);
        dimension.setBounds(1010, 420, 140, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Quantity:");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(860, 460, 100, 32);

        totalamount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalamount.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totalamount.setEnabled(false);
        getContentPane().add(totalamount);
        totalamount.setBounds(1010, 500, 140, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Total:");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(860, 500, 100, 32);

        quantity.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        quantity.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        quantity.setEnabled(false);
        getContentPane().add(quantity);
        quantity.setBounds(1010, 460, 140, 30);

        datec.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        datec.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        datec.setEnabled(false);
        getContentPane().add(datec);
        datec.setBounds(1010, 260, 140, 30);

        monthcombo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        monthcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August ", "September", "October", "November", "December" }));
        monthcombo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                monthcomboMouseClicked(evt);
            }
        });
        monthcombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthcomboActionPerformed(evt);
            }
        });
        getContentPane().add(monthcombo);
        monthcombo.setBounds(1010, 120, 140, 30);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_edit-clear_118917.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel13);
        jLabel13.setBounds(1020, 70, 40, 30);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_save_accept_44368.png"))); // NOI18N
        jLabel14.setToolTipText("");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel14);
        jLabel14.setBounds(1090, 70, 30, 30);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("User Appears");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(500, 580, 120, 30);

        monthcombouser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        monthcombouser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August ", "September", "October", "November", "December" }));
        monthcombouser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                monthcombouserMouseClicked(evt);
            }
        });
        monthcombouser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthcombouserActionPerformed(evt);
            }
        });
        getContentPane().add(monthcombouser);
        monthcombouser.setBounds(650, 580, 140, 30);

        appear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appearActionPerformed(evt);
            }
        });
        getContentPane().add(appear);
        appear.setBounds(580, 620, 140, 30);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Order ID:");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(860, 180, 80, 28);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jLabel8.setText("d");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(0, 0, 1200, 690);

        AddRecordBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AddRecordBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        AddRecordBtn.setText("Add Record");
        AddRecordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRecordBtnActionPerformed(evt);
            }
        });
        getContentPane().add(AddRecordBtn);
        AddRecordBtn.setBounds(350, 640, 150, 40);

        setSize(new java.awt.Dimension(1211, 720));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
     
    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
    
        Order o = new Order();
        o.setMyUser(user);
        o.setMyPass(pass);
        int s = Integer.parseInt(SearchTextField.getText());
        o.searchorder(jTable1, s);
    }//GEN-LAST:event_SearchButtonActionPerformed
    
    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        
        Order o = new Order();
        o.setMyUser(user);
        o.setMyPass(pass);
        o.deleteorder(Integer.parseInt(DelTextField.getText()));
        o.fetchorder(jTable1);
    }//GEN-LAST:event_DeleteButtonActionPerformed
    
    private void AddRecordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRecordBtnActionPerformed
        // TODO add your handling code here:
      
           /* String name = uid.getText();
            String CNIC = type.getText();
            String contact = shape.getText();
            String city = String.valueOf(thickness.getText());
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String date = df.format(datec.getDate());        

            if(!(name.isEmpty()) && !(CNIC.isEmpty()) && !(contact.isEmpty()) && !(date.isEmpty()) && !(city.isEmpty())){
                User u = new User();
                u.setCNIC(CNIC);
                u.setCity(city);
                u.setContactNo(contact);
                u.setName(name);
                u.setDate(date);
                u.InsertUser(u);
                Order o = new Order();
                o.fetchorder(jTable1);
            }
            else{
                JOptionPane.showMessageDialog(null, "Feilds cannot be empty. ");
            }*/
    }//GEN-LAST:event_AddRecordBtnActionPerformed

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
            Order o= new Order();
            o.setMyUser(user);
            o.setMyPass(pass);
            o.viewData(jTable1);
            
                int oidd = Order.getO_ID();
                oid.setText(String.valueOf(oidd));
            
                int uidd = Order.getU_ID();
                uid.setText(String.valueOf(uidd));
                
                datec.setText(o.getDate());
                type.setText(o.getGlassType());
                shape.setText(o.getGlassShape());
                thickness.setText(o.getGlassThickness());
                dimension.setText(o.getGlassDimension());
                quantity.setText(String.valueOf(o.getQuantity()));
                totalamount.setText(String.valueOf(o.getTotal_Order_Price()));
                
    }//GEN-LAST:event_jTable1MouseClicked

    private void shapeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shapeActionPerformed

    private void oidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oidActionPerformed

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
        Order o = new Order();
        o.setMyUser(user);
        o.setMyPass(pass);
        o.fetchorder(jTable1);
    }//GEN-LAST:event_formWindowOpened

    private void monthcomboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthcomboMouseClicked
        // TODO add your handling code here:        
    }//GEN-LAST:event_monthcomboMouseClicked

    private void monthcomboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthcomboActionPerformed
        // TODO add your handling code here:
        String datestart, dateend;
        String month = String.valueOf(monthcombo.getSelectedItem());
        Order o = new Order();
        o.setMyUser(user);
        o.setMyPass(pass);
        if("January".equals(month))
        {
            datestart="01-JAN-2018";
            dateend="31-JAN-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("February".equals(month)){
            datestart="01-Feb-2018";
            dateend="28-Feb-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("March".equals(month)){
            datestart="01-MAR-2018";
            dateend="31-MAR-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("April".equals(month)){
            datestart="01-APR-2018";
            dateend="30-APR-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("May".equals(month)){
            datestart="01-MAY-2018";
            dateend="31-MAY-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("June".equals(month)){
            datestart="01-JUN-2018";
            dateend="30-JUN-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("July".equals(month)){
            datestart="01-JUL-2018";
            dateend="31-JUL-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("August".equals(month)){
            datestart="01-AUG-2018";
            dateend="31-AUG-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("September".equals(month)){
            datestart="01-SEP-2018";
            dateend="30-SEP-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("October".equals(month)){
            datestart="01-OCT-2018";
            dateend="31-OCT-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("November".equals(month)){
            datestart="01-NOV-2018";
            dateend="30-NOV-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }
        else if("December".equals(month)){
            datestart="01-DEC-2018";
            dateend="31-DEC-2018";
            o.FetchOrderByMonth(jTable1,datestart,dateend);
        }

    }//GEN-LAST:event_monthcomboActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
                oid.setText("");
        uid.setText("");
        type.setText("");
        shape.setText("");
        datec.setText("");
        thickness.setText("");
        dimension.setText("");
        quantity.setText("");
        totalamount.setText("");
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        Order o = new Order();
        o.setMyUser(user);
        o.setMyPass(pass);
        o.fetchorder(jTable1);
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String datestart, dateend;
        String month = String.valueOf(monthcombouser.getSelectedItem());
        String idd = uid.getText();
        int id = Integer.parseInt(idd);

        Products o = new Products();
        o.setMyUser(user);
        o.setMyPass(pass);
        if("January".equals(month))
        {
            datestart="01-Jan-2018";
            dateend="31-Jan-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("February".equals(month)){
            datestart="01-Feb-2018";
            dateend="28-Feb-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("March".equals(month)){
            datestart="01-Mar-2018";
            dateend="31-Mar-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("April".equals(month)){
            datestart="01-Apr-2018";
            dateend="30-Apr-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("May".equals(month)){
            datestart="01-May-2018";
            dateend="31-May-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("June".equals(month)){
            datestart="01-Jun-2018";
            dateend="30-Jun-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("July".equals(month)){
            datestart="01-Jul-2018";
            dateend="31-Jul-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("August".equals(month)){
            datestart="01-Aug-2018";
            dateend="31-Aug-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("September".equals(month)){
            datestart="01-Sep-2018";
            dateend="30-Sep-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("October".equals(month)){
            datestart="01-Oct-2018";
            dateend="31-Oct-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("November".equals(month)){
            datestart="01-Nov-2018";
            dateend="30-Nov-2018";
            o.userappears(id,appear,datestart, dateend);
        }
        else if("December".equals(month)){
            datestart="01-Dec-2018";
            dateend="31-Dec-2018";
            o.userappears(id,appear,datestart, dateend);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void monthcombouserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthcombouserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_monthcombouserMouseClicked

    private void monthcombouserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthcombouserActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_monthcombouserActionPerformed

    private void appearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_appearActionPerformed

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
            java.util.logging.Logger.getLogger(OrderDetailsF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderDetailsF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderDetailsF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderDetailsF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderDetailsF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddRecordBtn;
    private javax.swing.JFormattedTextField DelTextField;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JLabel RecordLabel;
    private javax.swing.JButton SearchButton;
    private javax.swing.JFormattedTextField SearchTextField;
    private javax.swing.JTextField appear;
    private javax.swing.JTextField datec;
    private javax.swing.JTextField dimension;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> monthcombo;
    private javax.swing.JComboBox<String> monthcombouser;
    private javax.swing.JFormattedTextField oid;
    private javax.swing.JTextField quantity;
    private javax.swing.JFormattedTextField shape;
    private javax.swing.JTextField thickness;
    private javax.swing.JTextField totalamount;
    private javax.swing.JFormattedTextField type;
    private javax.swing.JTextField uid;
    // End of variables declaration//GEN-END:variables
}
