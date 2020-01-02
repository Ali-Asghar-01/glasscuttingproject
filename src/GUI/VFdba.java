/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Classes.Vendor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class VFdba extends javax.swing.JFrame {

    public VFdba() {
        initComponents();
        setUndecorated(true);
    }
    
    String user,pass;
    public VFdba(String user, String pass) {
        initComponents();
        setUndecorated(true);
        this.user=user;
        this.pass=pass;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cno2 = new javax.swing.JTextField();
        VName2 = new javax.swing.JTextField();
        Id2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        showdetails2 = new javax.swing.JButton();
        AddRecordBtn2 = new javax.swing.JButton();
        Clear_Btn2 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        AddRecordBtn3 = new javax.swing.JButton();
        o_no1 = new javax.swing.JTextField();
        cno3 = new javax.swing.JTextField();
        TM3 = new javax.swing.JTextField();
        APG3 = new javax.swing.JTextField();
        Quantity3 = new javax.swing.JTextField();
        VName3 = new javax.swing.JTextField();
        Id3 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        showdetails3 = new javax.swing.JButton();
        Clear_Btn3 = new javax.swing.JButton();
        date = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tg = new javax.swing.JTextField();
        mg = new javax.swing.JTextField();
        ag = new javax.swing.JTextField();
        cg = new javax.swing.JTextField();
        Glassmaterialcombo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cgp = new javax.swing.JTextField();
        tgp = new javax.swing.JTextField();
        mgp = new javax.swing.JTextField();
        agp = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel5.setLayout(null);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Vendor ID:");
        jPanel5.add(jLabel16);
        jLabel16.setBounds(10, 15, 74, 20);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Vendor Name:");
        jPanel5.add(jLabel17);
        jLabel17.setBounds(10, 70, 110, 30);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Contact No:");
        jPanel5.add(jLabel18);
        jLabel18.setBounds(10, 130, 110, 30);

        cno2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel5.add(cno2);
        cno2.setBounds(124, 131, 129, 30);

        VName2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel5.add(VName2);
        VName2.setBounds(124, 71, 129, 30);

        Id2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Id2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Id2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Id2.setEnabled(false);
        jPanel5.add(Id2);
        Id2.setBounds(123, 11, 130, 30);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Vendor ID", "Vendor Name", "Vendor CNo"
            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel5.add(jScrollPane3);
        jScrollPane3.setBounds(271, 11, 884, 217);

        showdetails2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        showdetails2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_save_accept_44368.png"))); // NOI18N
        showdetails2.setText("Refresh");
        showdetails2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showdetails2ActionPerformed(evt);
            }
        });
        jPanel5.add(showdetails2);
        showdetails2.setBounds(935, 250, 210, 40);

        AddRecordBtn2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddRecordBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        AddRecordBtn2.setText("Add Vendor");
        AddRecordBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRecordBtn2ActionPerformed(evt);
            }
        });
        jPanel5.add(AddRecordBtn2);
        AddRecordBtn2.setBounds(124, 198, 129, 30);

        Clear_Btn2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Clear_Btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_edit-clear_118917.png"))); // NOI18N
        Clear_Btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Clear_Btn2ActionPerformed(evt);
            }
        });
        jPanel5.add(Clear_Btn2);
        Clear_Btn2.setBounds(940, 310, 210, 30);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel5.add(jLabel28);
        jLabel28.setBounds(-20, -30, 1200, 530);

        jTabbedPane3.addTab("Vendors", jPanel5);

        jPanel6.setLayout(null);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Vendor ID:");
        jPanel6.add(jLabel19);
        jLabel19.setBounds(10, 48, 74, 20);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Vendor Name:");
        jPanel6.add(jLabel20);
        jLabel20.setBounds(10, 84, 110, 30);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Purchasing Date: ");
        jPanel6.add(jLabel21);
        jLabel21.setBounds(10, 167, 120, 30);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Glass Material:");
        jPanel6.add(jLabel22);
        jLabel22.setBounds(10, 207, 110, 30);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Quantity");
        jPanel6.add(jLabel23);
        jLabel23.setBounds(10, 247, 80, 30);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Amount Per Glass:");
        jPanel6.add(jLabel24);
        jLabel24.setBounds(10, 287, 120, 30);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Total Amount:");
        jPanel6.add(jLabel25);
        jLabel25.setBounds(10, 330, 110, 30);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Contact No:");
        jPanel6.add(jLabel26);
        jLabel26.setBounds(10, 125, 110, 30);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Order No:");
        jPanel6.add(jLabel27);
        jLabel27.setBounds(10, 7, 110, 30);

        AddRecordBtn3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddRecordBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        AddRecordBtn3.setText("Add Record");
        AddRecordBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRecordBtn3ActionPerformed(evt);
            }
        });
        jPanel6.add(AddRecordBtn3);
        AddRecordBtn3.setBounds(160, 367, 129, 30);

        o_no1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        o_no1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        o_no1.setEnabled(false);
        jPanel6.add(o_no1);
        o_no1.setBounds(160, 8, 129, 30);

        cno3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel6.add(cno3);
        cno3.setBounds(160, 126, 129, 30);

        TM3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TM3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TM3MouseClicked(evt);
            }
        });
        TM3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TM3ActionPerformed(evt);
            }
        });
        jPanel6.add(TM3);
        TM3.setBounds(160, 330, 129, 30);

        APG3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        APG3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                APG3MouseClicked(evt);
            }
        });
        APG3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                APG3ActionPerformed(evt);
            }
        });
        jPanel6.add(APG3);
        APG3.setBounds(160, 290, 129, 30);

        Quantity3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Quantity3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Quantity3ActionPerformed(evt);
            }
        });
        jPanel6.add(Quantity3);
        Quantity3.setBounds(160, 250, 129, 30);

        VName3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel6.add(VName3);
        VName3.setBounds(160, 84, 129, 30);

        Id3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Id3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Id3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Id3.setEnabled(false);
        jPanel6.add(Id3);
        Id3.setBounds(160, 44, 129, 30);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Order No", "Vendor ID", "Name", "Contact No", "Glass Material", "Quantity", "Amount Per Glass", "Total Amount", "Date"
            }
        ));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jPanel6.add(jScrollPane4);
        jScrollPane4.setBounds(307, 12, 846, 230);

        showdetails3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        showdetails3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_save_accept_44368.png"))); // NOI18N
        showdetails3.setText("Show Details");
        showdetails3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showdetails3ActionPerformed(evt);
            }
        });
        jPanel6.add(showdetails3);
        showdetails3.setBounds(943, 290, 210, 40);

        Clear_Btn3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Clear_Btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_edit-clear_118917.png"))); // NOI18N
        Clear_Btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Clear_Btn3ActionPerformed(evt);
            }
        });
        jPanel6.add(Clear_Btn3);
        Clear_Btn3.setBounds(940, 340, 210, 30);
        jPanel6.add(date);
        date.setBounds(160, 167, 129, 32);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tempered Glass");
        jPanel6.add(jLabel2);
        jLabel2.setBounds(430, 320, 120, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mirror Glass");
        jPanel6.add(jLabel3);
        jLabel3.setBounds(430, 360, 100, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Annealed Glass");
        jPanel6.add(jLabel4);
        jLabel4.setBounds(430, 400, 110, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ceramic Glass");
        jPanel6.add(jLabel5);
        jLabel5.setBounds(430, 280, 110, 30);

        tg.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tg.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tg.setEnabled(false);
        jPanel6.add(tg);
        tg.setBounds(560, 320, 70, 30);

        mg.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mg.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mg.setEnabled(false);
        jPanel6.add(mg);
        mg.setBounds(560, 360, 70, 30);

        ag.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        ag.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ag.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ag.setEnabled(false);
        jPanel6.add(ag);
        ag.setBounds(560, 400, 70, 30);

        cg.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cg.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cg.setEnabled(false);
        jPanel6.add(cg);
        cg.setBounds(560, 280, 70, 30);

        Glassmaterialcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tempered Glass", "Mirror Glass", "Annealed Glass", "Ceramic Glass" }));
        jPanel6.add(Glassmaterialcombo);
        Glassmaterialcombo.setBounds(160, 210, 130, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Quantity");
        jPanel6.add(jLabel6);
        jLabel6.setBounds(570, 260, 60, 15);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Price");
        jPanel6.add(jLabel7);
        jLabel7.setBounds(700, 260, 40, 15);

        cgp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cgp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cgp.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cgp.setEnabled(false);
        cgp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cgpActionPerformed(evt);
            }
        });
        jPanel6.add(cgp);
        cgp.setBounds(680, 280, 70, 30);

        tgp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tgp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tgp.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tgp.setEnabled(false);
        jPanel6.add(tgp);
        tgp.setBounds(680, 320, 70, 30);

        mgp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mgp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mgp.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mgp.setEnabled(false);
        jPanel6.add(mgp);
        mgp.setBounds(680, 360, 70, 30);

        agp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        agp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        agp.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        agp.setEnabled(false);
        jPanel6.add(agp);
        agp.setBounds(680, 400, 70, 30);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel6.add(jLabel29);
        jLabel29.setBounds(-20, -30, 1200, 530);

        jTabbedPane3.addTab("Vendor Orders", jPanel6);

        getContentPane().add(jTabbedPane3);
        jTabbedPane3.setBounds(30, 110, 1160, 520);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("VENDOR");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel15);
        jLabel15.setBounds(100, 30, 238, 58);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
        jLabel8.setText("jLabel8");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel8);
        jLabel8.setBounds(30, 40, 60, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1210, 650);

        setSize(new java.awt.Dimension(1226, 694));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
        Vendor v = new Vendor();
        v.setMyUser(user);
        v.setMyPass(pass);
        v.viewinFields2(jTable3);

        int a= Vendor.getV_ID();
        String s = String.valueOf(a);

        Id2.setText(s);
        VName2.setText(v.getVendorName());
        cno2.setText(v.getContactNo());
    }//GEN-LAST:event_jTable3MouseClicked

    private void showdetails2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showdetails2ActionPerformed
        // TODO add your handling code here:
        Vendor v = new Vendor();
        v.setMyUser(user);
        v.setMyPass(pass);
        v.FetchVendorOnly(jTable3);
    }//GEN-LAST:event_showdetails2ActionPerformed

    private void AddRecordBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRecordBtn2ActionPerformed
        // TODO add your handling code here:
        Vendor vv = new Vendor();
        vv.setMyUser(user);
        vv.setMyPass(pass);
        try{
            if(!(VName2.getText().isEmpty()) && !(cno2.getText().isEmpty())){
                vv.setVendorName(VName2.getText());
                vv.setContactNo(cno2.getText());
                vv.InsertVendor(vv);
                vv.FetchVendorOnly(jTable3);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Fill out all the fields.");
        }
    }//GEN-LAST:event_AddRecordBtn2ActionPerformed

    private void Clear_Btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Clear_Btn2ActionPerformed
        // TODO add your handling code here:
        //del_txt_field2.setText("");
        VName2.setText("");
        cno2.setText("");
        Id2.setText("");
    }//GEN-LAST:event_Clear_Btn2ActionPerformed

    private void AddRecordBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRecordBtn3ActionPerformed
        // TODO add your handling code here:
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Vendor V = new Vendor();
        V.setMyUser(user);
        V.setMyPass(pass);
        String datee = df.format(date.getDate());
        String gm = String.valueOf(Glassmaterialcombo.getSelectedItem());
        int qty = Integer.parseInt(Quantity3.getText());
        
        if (!(Quantity3.getText().isEmpty()) && !(VName3.getText().isEmpty()) && !(datee.isEmpty())
            && !(gm.isEmpty()) && !(cno3.getText().isEmpty()) && !(APG3.getText().isEmpty())
            && !(TM3.getText().isEmpty())) {

            V.setQuantity(Integer.parseInt(Quantity3.getText()));
            V.setVendorName(VName3.getText());
            V.setPurchaseDate(datee);
            V.setGlassMaterial(gm);
            V.setAmountPerGlass(Integer.parseInt(APG3.getText()));
            V.setTotalAmount(Integer.parseInt(TM3.getText()));
            V.setContactNo(cno3.getText());
            V.SaveVendorData(V);
            V.AddGlassQuantity(gm, qty, Integer.parseInt(APG3.getText()));
            V.ShowGlassQuantity(tg, ag, mg, cg, tgp, agp, mgp, cgp);
            V.FetchVendor(jTable4);
        } else {
            JOptionPane.showMessageDialog(null, "Fill out all the fields. ");
        }
    }//GEN-LAST:event_AddRecordBtn3ActionPerformed

    private void TM3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TM3MouseClicked
        // TODO add your handling code here:
        int q = Integer.parseInt(Quantity3.getText());
        int AmountGlass = Integer.parseInt(APG3.getText());
        int store = q * AmountGlass;
        TM3.setText(String.valueOf(store));
    }//GEN-LAST:event_TM3MouseClicked

    private void TM3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TM3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TM3ActionPerformed

    private void APG3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_APG3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_APG3MouseClicked

    private void APG3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_APG3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_APG3ActionPerformed

    private void Quantity3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Quantity3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Quantity3ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
        Vendor V = new Vendor();
        V.setMyUser(user);
        V.setMyPass(pass);
        V.viewinFields(jTable4);

        int a = Vendor.getV_ID();
        String s = String.valueOf(a);
        //System.out.println(""+s);
        Id3.setText(s);

        int o = Vendor.getOrder_No();
        String order = String.valueOf(o);
        o_no1.setText(order);

        String b = V.getVendorName();
        VName3.setText(b);

        String Cno = V.getContactNo();
        cno3.setText(Cno);

        String c = V.getPurchaseDate();
        //date.setDate('');

        String d = V.getGlassMaterial();
        Glassmaterialcombo.setSelectedItem(d);

        int e = V.getQuantity();
        String z = String.valueOf(e);
        Quantity3.setText(z);

        int f = V.getAmountPerGlass();
        String y = String.valueOf(f);
        APG3.setText(y);

        int g = V.getTotalAmount();
        String x = String.valueOf(g);
        TM3.setText(x);
    }//GEN-LAST:event_jTable4MouseClicked

    private void showdetails3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showdetails3ActionPerformed
        // TODO add your handling code here:
        Vendor v = new Vendor();
        v.setMyUser(user);
        v.setMyPass(pass);
        v.FetchVendor(jTable4);
    }//GEN-LAST:event_showdetails3ActionPerformed

    private void Clear_Btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Clear_Btn3ActionPerformed
        // TODO add your handling code here:
        //del_txt_field3.setText("");
        o_no1.setText("");
        Id3.setText("");
        VName3.setText("");
        cno3.setText("");
        date.setDate(null);
        Glassmaterialcombo.setSelectedItem("");
        Quantity3.setText("");
        APG3.setText("");
        TM3.setText("");
    }//GEN-LAST:event_Clear_Btn3ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Vendor vv = new Vendor();
        vv.setMyUser(user);
        vv.setMyPass(pass);
        vv.FetchVendorOnly(jTable3);
        vv.FetchVendor(jTable4);
        vv.ShowGlassQuantity(tg, ag, mg, cg, tgp, agp, mgp, cgp);
    }//GEN-LAST:event_formWindowOpened

    private void cgpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cgpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cgpActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        new AdminPaneldba(user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

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
            java.util.logging.Logger.getLogger(VFdba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VFdba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VFdba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VFdba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VFdba().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField APG3;
    private javax.swing.JButton AddRecordBtn2;
    private javax.swing.JButton AddRecordBtn3;
    private javax.swing.JButton Clear_Btn2;
    private javax.swing.JButton Clear_Btn3;
    private javax.swing.JComboBox<String> Glassmaterialcombo;
    private javax.swing.JTextField Id2;
    private javax.swing.JTextField Id3;
    private javax.swing.JTextField Quantity3;
    private javax.swing.JTextField TM3;
    private javax.swing.JTextField VName2;
    private javax.swing.JTextField VName3;
    private javax.swing.JTextField ag;
    private javax.swing.JTextField agp;
    private javax.swing.JTextField cg;
    private javax.swing.JTextField cgp;
    private javax.swing.JTextField cno2;
    private javax.swing.JTextField cno3;
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField mg;
    private javax.swing.JTextField mgp;
    private javax.swing.JTextField o_no1;
    private javax.swing.JButton showdetails2;
    private javax.swing.JButton showdetails3;
    private javax.swing.JTextField tg;
    private javax.swing.JTextField tgp;
    // End of variables declaration//GEN-END:variables
}
