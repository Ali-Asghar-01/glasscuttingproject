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

public class ManufactureForm extends javax.swing.JFrame {

    public ManufactureForm() {
        initComponents();
        setUndecorated(true);
    }
    
    String user,pass;
    public ManufactureForm(String user, String pass) {
        initComponents();
        setUndecorated(true);
        this.user=user;
        this.pass=pass;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("jdbc:oracle:thin:@localhost:1521:orclPU").createEntityManager();
        vendorMain1Query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT v FROM VendorMain1 v");
        vendorMain1List = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vendorMain1Query.getResultList();
        vendorMain1Query1 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT v FROM VendorMain1 v");
        vendorMain1List1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vendorMain1Query1.getResultList();
        glassManufacturedOrderQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT g FROM GlassManufacturedOrder g");
        glassManufacturedOrderList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : glassManufacturedOrderQuery.getResultList();
        godownPlacement1Query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT g FROM GodownPlacement1 g");
        godownPlacement1List = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : godownPlacement1Query.getResultList();
        consumer1Query = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT c FROM Consumer1 c");
        consumer1List = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : consumer1Query.getResultList();
        consumer1Query1 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT c FROM Consumer1 c");
        consumer1List1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : consumer1Query1.getResultList();
        consumer1Query2 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT c FROM Consumer1 c");
        consumer1List2 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : consumer1Query2.getResultList();
        consumer1Query3 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT c FROM Consumer1 c");
        consumer1List3 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : consumer1Query3.getResultList();
        consumer1Query4 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT c FROM Consumer1 c");
        consumer1List4 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : consumer1Query4.getResultList();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        quantity_txt = new javax.swing.JTextField();
        AddRecordBtn2 = new javax.swing.JButton();
        Clear_Btn2 = new javax.swing.JButton();
        glassMaterial_jComboBox = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cg1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cgp1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tgp1 = new javax.swing.JTextField();
        tg1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        mg1 = new javax.swing.JTextField();
        mgp1 = new javax.swing.JTextField();
        agp1 = new javax.swing.JTextField();
        ag1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cg2 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        tg2 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        mg2 = new javax.swing.JTextField();
        ag2 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        agp2 = new javax.swing.JTextField();
        mgp2 = new javax.swing.JTextField();
        tgp2 = new javax.swing.JTextField();
        cgp2 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        date_manufacture = new com.toedter.calendar.JDateChooser();
        jLabel38 = new javax.swing.JLabel();
        glassShape_jComboBox = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        cg3 = new javax.swing.JTextField();
        cgp3 = new javax.swing.JTextField();
        tgp3 = new javax.swing.JTextField();
        tg3 = new javax.swing.JTextField();
        mg3 = new javax.swing.JTextField();
        mgp3 = new javax.swing.JTextField();
        agp3 = new javax.swing.JTextField();
        ag3 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        AddRecordBtn3 = new javax.swing.JButton();
        Godown_Quantity = new javax.swing.JTextField();
        GName3 = new javax.swing.JTextField();
        Id3 = new javax.swing.JTextField();
        Godown_date = new com.toedter.calendar.JDateChooser();
        Clear_Btn3 = new javax.swing.JButton();
        Godown_Glassmaterialcombo = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        godown_id = new javax.swing.JTextField();
        Gcity1 = new javax.swing.JTextField();
        addGodown_btn = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        Godown_shape = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        Godown_table = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        addshop_btn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        shop_table = new javax.swing.JTable();
        shop_city = new javax.swing.JTextField();
        shop_name = new javax.swing.JTextField();
        shop_id = new javax.swing.JTextField();
        SHOP_ID = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        Shop_Glassmaterialcombo = new javax.swing.JComboBox<>();
        Shop_Quantity = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        Shop_date = new com.toedter.calendar.JDateChooser();
        Shop_shape = new javax.swing.JComboBox<>();
        shop_send_btn = new javax.swing.JButton();
        jLabel63 = new javax.swing.JLabel();
        shop_godown_id = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        shop_stock_table = new javax.swing.JTable();
        jLabel62 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        shop_send_btn1 = new javax.swing.JButton();
        consumer_date = new com.toedter.calendar.JDateChooser();
        consumer_shape_combobox = new javax.swing.JComboBox<>();
        consumer_Glassmaterialcombo = new javax.swing.JComboBox<>();
        consumer_quantity_combo_box = new javax.swing.JComboBox<>();
        consumer_shopID = new javax.swing.JComboBox<>();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        consumer_apg = new javax.swing.JTextField();
        consumer_total_amount = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        consumer_order_details = new javax.swing.JTable();
        jLabel74 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();

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
        jLabel16.setText("Glass Material");
        jPanel5.add(jLabel16);
        jLabel16.setBounds(60, 90, 90, 20);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Quantity");
        jPanel5.add(jLabel18);
        jLabel18.setBounds(60, 130, 110, 30);

        quantity_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel5.add(quantity_txt);
        quantity_txt.setBounds(170, 130, 180, 30);

        AddRecordBtn2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddRecordBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        AddRecordBtn2.setText("Manufacture Glass");
        AddRecordBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRecordBtn2ActionPerformed(evt);
            }
        });
        jPanel5.add(AddRecordBtn2);
        AddRecordBtn2.setBounds(170, 220, 180, 30);

        Clear_Btn2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Clear_Btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_edit-clear_118917.png"))); // NOI18N
        Clear_Btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Clear_Btn2ActionPerformed(evt);
            }
        });
        jPanel5.add(Clear_Btn2);
        Clear_Btn2.setBounds(930, 460, 210, 30);

        glassMaterial_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tempered", "Mirror", "Annealed", "Ceramic" }));
        jPanel5.add(glassMaterial_jComboBox);
        glassMaterial_jComboBox.setBounds(170, 80, 180, 30);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Ceramic Glass");
        jPanel5.add(jLabel13);
        jLabel13.setBounds(20, 330, 110, 30);

        cg1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cg1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cg1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cg1.setEnabled(false);
        jPanel5.add(cg1);
        cg1.setBounds(150, 330, 70, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Before Manufacture");
        jPanel5.add(jLabel9);
        jLabel9.setBounds(150, 240, 200, 60);

        cgp1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cgp1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cgp1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cgp1.setEnabled(false);
        cgp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cgp1ActionPerformed(evt);
            }
        });
        jPanel5.add(cgp1);
        cgp1.setBounds(250, 330, 70, 30);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("After Manufacture");
        jPanel5.add(jLabel10);
        jLabel10.setBounds(760, 10, 200, 40);

        tgp1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tgp1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tgp1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tgp1.setEnabled(false);
        jPanel5.add(tgp1);
        tgp1.setBounds(250, 370, 70, 30);

        tg1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tg1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tg1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tg1.setEnabled(false);
        jPanel5.add(tg1);
        tg1.setBounds(150, 370, 70, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Tempered Glass");
        jPanel5.add(jLabel11);
        jLabel11.setBounds(20, 360, 120, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Mirror Glass");
        jPanel5.add(jLabel12);
        jLabel12.setBounds(20, 400, 100, 30);

        mg1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mg1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mg1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mg1.setEnabled(false);
        jPanel5.add(mg1);
        mg1.setBounds(150, 410, 70, 30);

        mgp1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mgp1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mgp1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mgp1.setEnabled(false);
        jPanel5.add(mgp1);
        mgp1.setBounds(250, 410, 70, 30);

        agp1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        agp1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        agp1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        agp1.setEnabled(false);
        jPanel5.add(agp1);
        agp1.setBounds(250, 450, 70, 30);

        ag1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        ag1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ag1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ag1.setEnabled(false);
        jPanel5.add(ag1);
        ag1.setBounds(150, 450, 70, 30);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Annealed Glass");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(20, 440, 110, 30);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Ceramic Glass");
        jPanel5.add(jLabel17);
        jLabel17.setBounds(430, 70, 110, 30);

        cg2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cg2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cg2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cg2.setEnabled(false);
        jPanel5.add(cg2);
        cg2.setBounds(610, 70, 70, 30);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Quantity");
        jPanel5.add(jLabel30);
        jLabel30.setBounds(610, 40, 60, 15);

        tg2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tg2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tg2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tg2.setEnabled(false);
        jPanel5.add(tg2);
        tg2.setBounds(610, 110, 70, 30);

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Tempered Glass");
        jPanel5.add(jLabel31);
        jLabel31.setBounds(430, 110, 120, 30);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Mirror Glass");
        jPanel5.add(jLabel32);
        jLabel32.setBounds(430, 150, 100, 30);

        mg2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mg2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mg2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mg2.setEnabled(false);
        jPanel5.add(mg2);
        mg2.setBounds(610, 150, 70, 30);

        ag2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        ag2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ag2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ag2.setEnabled(false);
        jPanel5.add(ag2);
        ag2.setBounds(610, 190, 70, 30);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Annealed Glass");
        jPanel5.add(jLabel33);
        jLabel33.setBounds(430, 190, 110, 30);

        agp2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        agp2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        agp2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        agp2.setEnabled(false);
        jPanel5.add(agp2);
        agp2.setBounds(710, 190, 70, 30);

        mgp2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mgp2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mgp2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mgp2.setEnabled(false);
        jPanel5.add(mgp2);
        mgp2.setBounds(710, 150, 70, 30);

        tgp2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tgp2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tgp2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tgp2.setEnabled(false);
        jPanel5.add(tgp2);
        tgp2.setBounds(710, 110, 70, 30);

        cgp2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cgp2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cgp2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cgp2.setEnabled(false);
        cgp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cgp2ActionPerformed(evt);
            }
        });
        jPanel5.add(cgp2);
        cgp2.setBounds(710, 70, 70, 30);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Price");
        jPanel5.add(jLabel34);
        jLabel34.setBounds(720, 40, 40, 15);

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Quantity");
        jPanel5.add(jLabel35);
        jLabel35.setBounds(150, 300, 60, 15);

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Price");
        jPanel5.add(jLabel36);
        jLabel36.setBounds(260, 300, 40, 15);

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Purchasing Date: ");
        jPanel5.add(jLabel37);
        jLabel37.setBounds(20, 180, 120, 30);
        jPanel5.add(date_manufacture);
        date_manufacture.setBounds(170, 180, 180, 32);

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Shape");
        jPanel5.add(jLabel38);
        jLabel38.setBounds(50, 30, 110, 30);

        glassShape_jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Circle", "Square" }));
        jPanel5.add(glassShape_jComboBox);
        glassShape_jComboBox.setBounds(170, 30, 180, 30);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, glassManufacturedOrderList, jTable5);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${gmId}"));
        columnBinding.setColumnName("Gm Id");
        columnBinding.setColumnClass(java.math.BigDecimal.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${glassMaterial}"));
        columnBinding.setColumnName("Glass Material");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${shape}"));
        columnBinding.setColumnName("Shape");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${quantity}"));
        columnBinding.setColumnName("Quantity");
        columnBinding.setColumnClass(java.math.BigInteger.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${height}"));
        columnBinding.setColumnName("Height");
        columnBinding.setColumnClass(java.math.BigInteger.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${width}"));
        columnBinding.setColumnName("Width");
        columnBinding.setColumnClass(java.math.BigInteger.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${length}"));
        columnBinding.setColumnName("Length");
        columnBinding.setColumnClass(java.math.BigInteger.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${amountPerGlass}"));
        columnBinding.setColumnName("Amount Per Glass");
        columnBinding.setColumnClass(java.math.BigInteger.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${gmDate}"));
        columnBinding.setColumnName("Gm Date");
        columnBinding.setColumnClass(java.util.Date.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jPanel5.add(jScrollPane5);
        jScrollPane5.setBounds(410, 260, 730, 190);

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Circle");
        jPanel5.add(jLabel39);
        jLabel39.setBounds(530, 70, 110, 30);

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Circle");
        jPanel5.add(jLabel40);
        jLabel40.setBounds(530, 110, 120, 30);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Square");
        jPanel5.add(jLabel41);
        jLabel41.setBounds(920, 70, 100, 30);

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Square");
        jPanel5.add(jLabel42);
        jLabel42.setBounds(920, 110, 110, 30);

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Circle");
        jPanel5.add(jLabel43);
        jLabel43.setBounds(530, 150, 110, 30);

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Circle");
        jPanel5.add(jLabel44);
        jLabel44.setBounds(530, 190, 120, 30);

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Square");
        jPanel5.add(jLabel45);
        jLabel45.setBounds(920, 150, 100, 30);

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Square");
        jPanel5.add(jLabel46);
        jLabel46.setBounds(920, 190, 110, 30);

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Ceramic Glass");
        jPanel5.add(jLabel47);
        jLabel47.setBounds(810, 70, 110, 30);

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Tempered Glass");
        jPanel5.add(jLabel48);
        jLabel48.setBounds(810, 110, 120, 30);

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Mirror Glass");
        jPanel5.add(jLabel49);
        jLabel49.setBounds(810, 150, 100, 30);

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Annealed Glass");
        jPanel5.add(jLabel50);
        jLabel50.setBounds(810, 190, 110, 30);

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Price");
        jPanel5.add(jLabel51);
        jLabel51.setBounds(1090, 40, 40, 15);

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Quantity");
        jPanel5.add(jLabel52);
        jLabel52.setBounds(980, 40, 60, 15);

        cg3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cg3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cg3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cg3.setEnabled(false);
        jPanel5.add(cg3);
        cg3.setBounds(980, 70, 70, 30);

        cgp3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cgp3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cgp3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cgp3.setEnabled(false);
        cgp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cgp3ActionPerformed(evt);
            }
        });
        jPanel5.add(cgp3);
        cgp3.setBounds(1080, 70, 70, 30);

        tgp3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tgp3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tgp3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tgp3.setEnabled(false);
        jPanel5.add(tgp3);
        tgp3.setBounds(1080, 110, 70, 30);

        tg3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tg3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tg3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tg3.setEnabled(false);
        jPanel5.add(tg3);
        tg3.setBounds(980, 110, 70, 30);

        mg3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mg3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mg3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mg3.setEnabled(false);
        jPanel5.add(mg3);
        mg3.setBounds(980, 150, 70, 30);

        mgp3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mgp3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        mgp3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mgp3.setEnabled(false);
        jPanel5.add(mgp3);
        mgp3.setBounds(1080, 150, 70, 30);

        agp3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        agp3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        agp3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        agp3.setEnabled(false);
        jPanel5.add(agp3);
        agp3.setBounds(1080, 190, 70, 30);

        ag3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        ag3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ag3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ag3.setEnabled(false);
        jPanel5.add(ag3);
        ag3.setBounds(980, 190, 70, 30);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel5.add(jLabel28);
        jLabel28.setBounds(-350, -120, 1510, 620);

        jTabbedPane3.addTab("Manufacture Glass", jPanel5);

        jPanel6.setLayout(null);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Godown ID:");
        jPanel6.add(jLabel19);
        jLabel19.setBounds(10, 20, 74, 20);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Godown Name:");
        jPanel6.add(jLabel20);
        jLabel20.setBounds(10, 60, 110, 30);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Purchasing Date: ");
        jPanel6.add(jLabel21);
        jLabel21.setBounds(20, 410, 120, 30);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Glass Material:");
        jPanel6.add(jLabel22);
        jLabel22.setBounds(20, 290, 110, 30);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Quantity");
        jPanel6.add(jLabel23);
        jLabel23.setBounds(20, 330, 80, 30);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Shape:");
        jPanel6.add(jLabel26);
        jLabel26.setBounds(20, 370, 110, 30);

        AddRecordBtn3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AddRecordBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        AddRecordBtn3.setText("Send Godown");
        AddRecordBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRecordBtn3ActionPerformed(evt);
            }
        });
        jPanel6.add(AddRecordBtn3);
        AddRecordBtn3.setBounds(170, 460, 160, 30);

        Godown_Quantity.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Godown_Quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Godown_QuantityActionPerformed(evt);
            }
        });
        jPanel6.add(Godown_Quantity);
        Godown_Quantity.setBounds(170, 330, 160, 30);

        GName3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel6.add(GName3);
        GName3.setBounds(160, 60, 160, 30);

        Id3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Id3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Id3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Id3.setEnabled(false);
        jPanel6.add(Id3);
        Id3.setBounds(160, 20, 160, 30);
        jPanel6.add(Godown_date);
        Godown_date.setBounds(170, 410, 160, 32);

        Clear_Btn3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Clear_Btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_edit-clear_118917.png"))); // NOI18N
        Clear_Btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Clear_Btn3ActionPerformed(evt);
            }
        });
        jPanel6.add(Clear_Btn3);
        Clear_Btn3.setBounds(940, 450, 210, 30);

        Godown_Glassmaterialcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tempered", "Mirror", "Annealed", "Ceramic" }));
        jPanel6.add(Godown_Glassmaterialcombo);
        Godown_Glassmaterialcombo.setBounds(170, 290, 160, 30);

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("City:");
        jPanel6.add(jLabel53);
        jLabel53.setBounds(10, 100, 110, 30);

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Godown Placement");
        jPanel6.add(jLabel54);
        jLabel54.setBounds(70, 200, 180, 30);

        godown_id.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        godown_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        godown_id.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanel6.add(godown_id);
        godown_id.setBounds(170, 250, 160, 30);

        Gcity1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel6.add(Gcity1);
        Gcity1.setBounds(160, 100, 160, 30);

        addGodown_btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addGodown_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        addGodown_btn.setText("Add Godown");
        addGodown_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGodown_btnActionPerformed(evt);
            }
        });
        jPanel6.add(addGodown_btn);
        addGodown_btn.setBounds(160, 140, 160, 30);

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Godown ID:");
        jPanel6.add(jLabel55);
        jLabel55.setBounds(20, 260, 74, 20);

        Godown_shape.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Circle", "Square" }));
        jPanel6.add(Godown_shape);
        Godown_shape.setBounds(170, 370, 160, 30);

        Godown_table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Godown_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "GODOWN ID", "NAME", "CITY", "MATERIAL", "SHAPE", "QUANTITY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Godown_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Godown_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Godown_table);

        jPanel6.add(jScrollPane1);
        jScrollPane1.setBounds(360, 10, 781, 230);

        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, godownPlacement1List, jTable1);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(java.math.BigDecimal.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${godownId}"));
        columnBinding.setColumnName("Godown Id");
        columnBinding.setColumnClass(java.math.BigInteger.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${glassMaterial}"));
        columnBinding.setColumnName("Glass Material");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${shape}"));
        columnBinding.setColumnName("Shape");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${quantity}"));
        columnBinding.setColumnName("Quantity");
        columnBinding.setColumnClass(java.math.BigInteger.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${gpDate}"));
        columnBinding.setColumnName("Gp Date");
        columnBinding.setColumnClass(java.util.Date.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        jScrollPane3.setViewportView(jTable1);

        jPanel6.add(jScrollPane3);
        jScrollPane3.setBounds(360, 260, 780, 180);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel6.add(jLabel29);
        jLabel29.setBounds(-10, 0, 1190, 500);

        jTabbedPane3.addTab("Manufacturer Orders", jPanel6);

        jPanel3.setLayout(null);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Shop ID:");
        jPanel3.add(jLabel24);
        jLabel24.setBounds(10, 20, 74, 20);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Shop Name:");
        jPanel3.add(jLabel25);
        jLabel25.setBounds(10, 60, 110, 30);

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("City:");
        jPanel3.add(jLabel56);
        jLabel56.setBounds(10, 100, 110, 30);

        addshop_btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addshop_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        addshop_btn.setText("Add Shop");
        addshop_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addshop_btnActionPerformed(evt);
            }
        });
        jPanel3.add(addshop_btn);
        addshop_btn.setBounds(160, 140, 160, 30);

        shop_table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        shop_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "GODOWN ID", "NAME", "CITY", "MATERIAL", "SHAPE", "QUANTITY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        shop_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shop_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(shop_table);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(360, 10, 781, 230);

        shop_city.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(shop_city);
        shop_city.setBounds(160, 100, 160, 30);

        shop_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(shop_name);
        shop_name.setBounds(160, 60, 160, 30);

        shop_id.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shop_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        shop_id.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        shop_id.setEnabled(false);
        jPanel3.add(shop_id);
        shop_id.setBounds(160, 20, 160, 30);

        SHOP_ID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SHOP_ID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SHOP_ID.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanel3.add(SHOP_ID);
        SHOP_ID.setBounds(170, 260, 160, 30);

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Shop Placement");
        jPanel3.add(jLabel57);
        jLabel57.setBounds(70, 180, 180, 40);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Glass Material:");
        jPanel3.add(jLabel27);
        jLabel27.setBounds(20, 300, 110, 30);

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Shop ID:");
        jPanel3.add(jLabel58);
        jLabel58.setBounds(20, 270, 80, 15);

        Shop_Glassmaterialcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tempered", "Mirror", "Annealed", "Ceramic" }));
        jPanel3.add(Shop_Glassmaterialcombo);
        Shop_Glassmaterialcombo.setBounds(170, 300, 160, 30);

        Shop_Quantity.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Shop_Quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Shop_QuantityActionPerformed(evt);
            }
        });
        jPanel3.add(Shop_Quantity);
        Shop_Quantity.setBounds(170, 340, 160, 30);

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Quantity");
        jPanel3.add(jLabel59);
        jLabel59.setBounds(20, 340, 80, 30);

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Shape:");
        jPanel3.add(jLabel60);
        jLabel60.setBounds(20, 380, 110, 30);

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Purchasing Date: ");
        jPanel3.add(jLabel61);
        jLabel61.setBounds(20, 420, 120, 30);
        jPanel3.add(Shop_date);
        Shop_date.setBounds(170, 420, 160, 30);

        Shop_shape.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Circle", "Square" }));
        jPanel3.add(Shop_shape);
        Shop_shape.setBounds(170, 380, 160, 30);

        shop_send_btn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shop_send_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        shop_send_btn.setText("Send Shop");
        shop_send_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shop_send_btnActionPerformed(evt);
            }
        });
        jPanel3.add(shop_send_btn);
        shop_send_btn.setBounds(170, 460, 160, 30);

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Godown ID:");
        jPanel3.add(jLabel63);
        jLabel63.setBounds(20, 230, 80, 15);

        shop_godown_id.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        shop_godown_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        shop_godown_id.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jPanel3.add(shop_godown_id);
        shop_godown_id.setBounds(170, 220, 160, 30);

        shop_stock_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(shop_stock_table);

        jPanel3.add(jScrollPane4);
        jScrollPane4.setBounds(360, 260, 780, 220);

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel3.add(jLabel62);
        jLabel62.setBounds(0, 0, 1180, 500);

        jTabbedPane3.addTab("Shop", jPanel3);

        jPanel1.setLayout(null);

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Consumer Order");
        jPanel1.add(jLabel67);
        jLabel67.setBounds(70, 10, 180, 40);

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Shop ID:");
        jPanel1.add(jLabel69);
        jLabel69.setBounds(20, 70, 80, 15);

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Glass Material:");
        jPanel1.add(jLabel70);
        jLabel70.setBounds(20, 100, 110, 30);

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Quantity");
        jPanel1.add(jLabel71);
        jLabel71.setBounds(20, 180, 80, 30);

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Total Amt.");
        jPanel1.add(jLabel72);
        jLabel72.setBounds(20, 300, 110, 30);

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Purchasing Date: ");
        jPanel1.add(jLabel73);
        jLabel73.setBounds(20, 220, 120, 30);

        shop_send_btn1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shop_send_btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_list-add_118777.png"))); // NOI18N
        shop_send_btn1.setText("Place Order");
        shop_send_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shop_send_btn1ActionPerformed(evt);
            }
        });
        jPanel1.add(shop_send_btn1);
        shop_send_btn1.setBounds(170, 350, 160, 30);
        jPanel1.add(consumer_date);
        consumer_date.setBounds(170, 220, 160, 30);

        consumer_shape_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Circle", "Square" }));
        jPanel1.add(consumer_shape_combobox);
        consumer_shape_combobox.setBounds(170, 140, 160, 30);

        consumer_Glassmaterialcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tempered", "Mirror", "Annealed", "Ceramic" }));
        jPanel1.add(consumer_Glassmaterialcombo);
        consumer_Glassmaterialcombo.setBounds(170, 100, 160, 30);

        consumer_quantity_combo_box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
        consumer_quantity_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumer_quantity_combo_boxActionPerformed(evt);
            }
        });
        jPanel1.add(consumer_quantity_combo_box);
        consumer_quantity_combo_box.setBounds(170, 180, 160, 30);

        consumer_shopID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        jPanel1.add(consumer_shopID);
        consumer_shopID.setBounds(170, 60, 160, 30);

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Shape:");
        jPanel1.add(jLabel75);
        jLabel75.setBounds(20, 140, 110, 30);

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Amount Per Glass:");
        jPanel1.add(jLabel76);
        jLabel76.setBounds(20, 260, 120, 30);

        consumer_apg.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        consumer_apg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        consumer_apg.setEnabled(false);
        jPanel1.add(consumer_apg);
        consumer_apg.setBounds(170, 260, 160, 30);

        consumer_total_amount.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        consumer_total_amount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        consumer_total_amount.setEnabled(false);
        jPanel1.add(consumer_total_amount);
        consumer_total_amount.setBounds(170, 300, 160, 30);

        consumer_order_details.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(consumer_order_details);

        jPanel1.add(jScrollPane6);
        jScrollPane6.setBounds(390, 60, 750, 250);

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel1.add(jLabel74);
        jLabel74.setBounds(0, 0, 1180, 500);

        jTabbedPane3.addTab("Comsumer", jPanel1);

        getContentPane().add(jTabbedPane3);
        jTabbedPane3.setBounds(30, 110, 1160, 520);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("MANUFACTURER");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel15);
        jLabel15.setBounds(100, 30, 420, 58);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
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
        getContentPane().add(jPanel7);
        jPanel7.setBounds(230, 300, 380, 180);

        bindingGroup.bind();

        setSize(new java.awt.Dimension(1226, 694));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
//        Vendor vv = new Vendor();
//        vv.setMyUser(user);
//        vv.setMyPass(pass);
//        vv.FetchVendorOnly(jTable3);
//        vv.FetchVendor(jTable4);
//        vv.ShowGlassQuantity(tg, ag, mg, cg, tgp, agp, mgp, cgp);

        Vendor V = new Vendor();
        V.setMyUser(user);
        V.setMyPass(pass);
        V.ShowGodownStockDetails(Godown_table);
        V.ShowConsumerOrderDetails(consumer_order_details);
        V.ShowShopPlacementDetails(shop_table);
        V.ShowShopStockDetails(shop_stock_table);
        V.ShowGlassQuantity(tg1, ag1, mg1, cg1, tgp1, agp1, mgp1, cgp1);
        V.ShowAfterManufactureGlassQuantityCircle(tg2, ag2, mg2, cg2, tgp2, agp2, mgp2, cgp2);
        V.ShowAfterManufactureGlassQuantitySquare(tg3, ag3, mg3, cg3, tgp3, agp3, mgp3, cgp3);
    }//GEN-LAST:event_formWindowOpened

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        new AdminPanel(user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void Godown_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Godown_tableMouseClicked
        // TODO add your handling code here:
        //        Order o= new Order();
        //        o.setMyUser(user);
        //        o.setMyPass(pass);
        //        o.viewData(jTable1);
        //
        //        int oidd = Order.getO_ID();
        //        oid.setText(String.valueOf(oidd));
        //
        //        int uidd = Order.getU_ID();
        //        uid.setText(String.valueOf(uidd));
        //
        //        datec.setText(o.getDate());
        //        type.setText(o.getGlassType());
        //        shape.setText(o.getGlassShape());
        //        thickness.setText(o.getGlassThickness());
        //        dimension.setText(o.getGlassDimension());
        //        quantity.setText(String.valueOf(o.getQuantity()));
        //        totalamount.setText(String.valueOf(o.getTotal_Order_Price()));
    }//GEN-LAST:event_Godown_tableMouseClicked

    private void addGodown_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGodown_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addGodown_btnActionPerformed

    private void Clear_Btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Clear_Btn3ActionPerformed
        // TODO add your handling code here:
        //        del_txt_field3.setText("");
        //        o_no1.setText("");
        //        Id3.setText("");
        //        GName3.setText("");
        //        cno3.setText("");
        //        Godown_date.setDate(null);
        //        Godown_Glassmaterialcombo.setSelectedItem("");
        //        Godown_Quantity.setText("");
        //        APG3.setText("");
        //        TM3.setText("");
    }//GEN-LAST:event_Clear_Btn3ActionPerformed

    private void Godown_QuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Godown_QuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Godown_QuantityActionPerformed

    private void AddRecordBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRecordBtn3ActionPerformed
        // TODO add your handling code here:
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Vendor V = new Vendor();
        V.setMyUser(user);
        V.setMyPass(pass);
        int id = Integer.parseInt(godown_id.getText());
        String datee = df.format(Godown_date.getDate());
        String glass_material = String.valueOf(Godown_Glassmaterialcombo.getSelectedItem());
        String shape = String.valueOf(Godown_shape.getSelectedItem());
        int qty = Integer.parseInt(Godown_Quantity.getText());

        if (!(String.valueOf(id).isEmpty()) && !(String.valueOf(qty).isEmpty()) && !(glass_material.isEmpty()) && !(datee.isEmpty())
            && !(shape.isEmpty())) {

            V.Godown_Stock_Placement(id, glass_material, shape, qty, datee);
            V.ShowGodownStockDetails(Godown_table);
            V.ShowAfterManufactureGlassQuantityCircle(tg2, ag2, mg2, cg2, tgp2, agp2, mgp2, cgp2);
            V.ShowAfterManufactureGlassQuantitySquare(tg3, ag3, mg3, cg3, tgp3, agp3, mgp3, cgp3);

        } else {
            JOptionPane.showMessageDialog(null, "Fill out all the fields. ");
        }
    }//GEN-LAST:event_AddRecordBtn3ActionPerformed

    private void cgp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cgp3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cgp3ActionPerformed

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5MouseClicked

    private void cgp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cgp2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cgp2ActionPerformed

    private void cgp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cgp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cgp1ActionPerformed

    private void Clear_Btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Clear_Btn2ActionPerformed
        //        // TODO add your handling code here:
        //        del_txt_field2.setText("");
        //        VName2.setText("");
        //        quantity_txt.setText("");
        //        Id2.setText("");
    }//GEN-LAST:event_Clear_Btn2ActionPerformed

    private void AddRecordBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRecordBtn2ActionPerformed
        // TODO add your handling code here:
        Vendor vv = new Vendor();
        vv.setMyUser(user);
        vv.setMyPass(pass);

        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(date_manufacture.getDate());
        String glass_material = String.valueOf(glassMaterial_jComboBox.getSelectedItem());
        String glass_shape = String.valueOf(glassShape_jComboBox.getSelectedItem());
        int qty = Integer.parseInt(quantity_txt.getText());
        int length = 0;
        int width = 0;
        int height = 0;

        if(glass_shape.equals("Circle") && glass_material.equals("Ceramic")){
            length = 50;
            width = 60;
            height = 45;
        }
        else if(glass_shape.equals("Square") && glass_material.equals("Ceramic")){
            length = 35;
            width = 40;
            height = 45;
        }
        else if(glass_shape.equals("Square") && glass_material.equals("Annealed")){
            length = 35;
            width = 40;
            height = 45;
        }
        else if(glass_shape.equals("Square") && glass_material.equals("Annealed")){
            length = 35;
            width = 40;
            height = 45;
        }
        else if(glass_shape.equals("Square") && glass_material.equals("Mirror")){
            length = 35;
            width = 40;
            height = 45;
        }
        else if(glass_shape.equals("Square") && glass_material.equals("Mirror")){
            length = 35;
            width = 40;
            height = 45;
        }
        else if(glass_shape.equals("Square") && glass_material.equals("Tempered")){
            length = 35;
            width = 40;
            height = 45;
        }
        else if(glass_shape.equals("Square") && glass_material.equals("Tempered")){
            length = 60;
            width = 70;
            height = 75;
        }

        try{
            if(!(String.valueOf(qty).isEmpty())){
                vv.ManufactureGlass(glass_material, glass_shape, qty, date, length, width, height);
                vv.ShowGlassQuantity(tg1, ag1, mg1, cg1, tgp1, agp1, mgp1, cgp1);
                vv.ShowAfterManufactureGlassQuantityCircle(tg2, ag2, mg2, cg2, tgp2, agp2, mgp2, cgp2);
                vv.ShowAfterManufactureGlassQuantitySquare(tg3, ag3, mg3, cg3, tgp3, agp3, mgp3, cgp3);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Fill out all the fields.");
        }
    }//GEN-LAST:event_AddRecordBtn2ActionPerformed

    private void addshop_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addshop_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addshop_btnActionPerformed

    private void shop_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shop_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_shop_tableMouseClicked

    private void Shop_QuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Shop_QuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Shop_QuantityActionPerformed

    private void shop_send_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shop_send_btnActionPerformed
        // TODO add your handling code here:
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Vendor V = new Vendor();
        V.setMyUser(user);
        V.setMyPass(pass);
        int godown_id = Integer.parseInt(shop_godown_id.getText());
        int shop_ID = Integer.parseInt(SHOP_ID.getText());
        String datee = df.format(Shop_date.getDate());
        String glass_material = String.valueOf(Shop_Glassmaterialcombo.getSelectedItem());
        String shape = String.valueOf(Shop_shape.getSelectedItem());
        int qty = Integer.parseInt(Shop_Quantity.getText());

        if (!(String.valueOf(shop_ID).isEmpty()) && !(String.valueOf(godown_id).isEmpty()) && !(String.valueOf(qty).isEmpty()) && !(glass_material.isEmpty()) && !(datee.isEmpty())
            && !(shape.isEmpty())) {

            V.Shop_Stock_Placement(godown_id, shop_ID, glass_material, shape, qty, datee);
            V.ShowGodownStockDetails(Godown_table);
            V.ShowShopStockDetails(shop_stock_table);
            V.ShowShopPlacementDetails(shop_table);
        } else {
            JOptionPane.showMessageDialog(null, "Fill out all the fields. ");
        }
    }//GEN-LAST:event_shop_send_btnActionPerformed

    private void shop_send_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shop_send_btn1ActionPerformed
        // TODO add your handling code here:
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Vendor V = new Vendor();
        V.setMyUser(user);
        V.setMyPass(pass);
        int shop_ID = Integer.parseInt(consumer_shopID.getSelectedItem().toString());
        String datee = df.format(consumer_date.getDate());
        String glass_material = String.valueOf(consumer_Glassmaterialcombo.getSelectedItem());
        String shape = String.valueOf(consumer_shape_combobox.getSelectedItem());
        int qty = Integer.parseInt(consumer_quantity_combo_box.getSelectedItem().toString());
        int apg = Integer.parseInt(consumer_apg.getText());
        double total_amount = Double.parseDouble(consumer_total_amount.getText());

        if (!(String.valueOf(shop_ID).isEmpty()) && !(String.valueOf(qty).isEmpty()) && !(glass_material.isEmpty()) && !(datee.isEmpty())
            && !(shape.isEmpty()) && !(String.valueOf(apg).isEmpty()) && !(String.valueOf(total_amount).isEmpty())) {

            V.Consumer_Order(shop_ID, glass_material, shape, qty, datee, apg, total_amount);
            V.ShowShopPlacementDetails(shop_table);
            V.ShowShopStockDetails(shop_stock_table);
            V.ShowConsumerOrderDetails(consumer_order_details);
        } else {
            JOptionPane.showMessageDialog(null, "Fill out all the fields. ");
        }
    }//GEN-LAST:event_shop_send_btn1ActionPerformed

    private void consumer_quantity_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumer_quantity_combo_boxActionPerformed
        // TODO add your handling code here:
        Vendor V = new Vendor();
        V.setMyUser(user);
        V.setMyPass(pass);
        
        int shop_ID = Integer.parseInt(consumer_shopID.getSelectedItem().toString());
        String glass_material = String.valueOf(consumer_Glassmaterialcombo.getSelectedItem());
        String shape = String.valueOf(consumer_shape_combobox.getSelectedItem());
        int qty = Integer.parseInt(consumer_quantity_combo_box.getSelectedItem().toString());
        
        V.show_APG(shop_ID, glass_material, shape, qty, consumer_apg, consumer_total_amount);
        
    }//GEN-LAST:event_consumer_quantity_combo_boxActionPerformed

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
            java.util.logging.Logger.getLogger(ManufactureForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManufactureForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManufactureForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManufactureForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManufactureForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddRecordBtn2;
    private javax.swing.JButton AddRecordBtn3;
    private javax.swing.JButton Clear_Btn2;
    private javax.swing.JButton Clear_Btn3;
    private javax.swing.JTextField GName3;
    private javax.swing.JTextField Gcity1;
    private javax.swing.JComboBox<String> Godown_Glassmaterialcombo;
    private javax.swing.JTextField Godown_Quantity;
    private com.toedter.calendar.JDateChooser Godown_date;
    private javax.swing.JComboBox<String> Godown_shape;
    private javax.swing.JTable Godown_table;
    private javax.swing.JTextField Id3;
    private javax.swing.JTextField SHOP_ID;
    private javax.swing.JComboBox<String> Shop_Glassmaterialcombo;
    private javax.swing.JTextField Shop_Quantity;
    private com.toedter.calendar.JDateChooser Shop_date;
    private javax.swing.JComboBox<String> Shop_shape;
    private javax.swing.JButton addGodown_btn;
    private javax.swing.JButton addshop_btn;
    private javax.swing.JTextField ag1;
    private javax.swing.JTextField ag2;
    private javax.swing.JTextField ag3;
    private javax.swing.JTextField agp1;
    private javax.swing.JTextField agp2;
    private javax.swing.JTextField agp3;
    private javax.swing.JTextField cg1;
    private javax.swing.JTextField cg2;
    private javax.swing.JTextField cg3;
    private javax.swing.JTextField cgp1;
    private javax.swing.JTextField cgp2;
    private javax.swing.JTextField cgp3;
    private java.util.List<GUI.Consumer1> consumer1List;
    private java.util.List<GUI.Consumer1> consumer1List1;
    private java.util.List<GUI.Consumer1> consumer1List2;
    private java.util.List<GUI.Consumer1> consumer1List3;
    private java.util.List<GUI.Consumer1> consumer1List4;
    private javax.persistence.Query consumer1Query;
    private javax.persistence.Query consumer1Query1;
    private javax.persistence.Query consumer1Query2;
    private javax.persistence.Query consumer1Query3;
    private javax.persistence.Query consumer1Query4;
    private javax.swing.JComboBox<String> consumer_Glassmaterialcombo;
    private javax.swing.JTextField consumer_apg;
    private com.toedter.calendar.JDateChooser consumer_date;
    private javax.swing.JTable consumer_order_details;
    private javax.swing.JComboBox<String> consumer_quantity_combo_box;
    private javax.swing.JComboBox<String> consumer_shape_combobox;
    private javax.swing.JComboBox<String> consumer_shopID;
    private javax.swing.JTextField consumer_total_amount;
    private com.toedter.calendar.JDateChooser date_manufacture;
    private javax.persistence.EntityManager entityManager;
    private java.util.List<GUI.GlassManufacturedOrder> glassManufacturedOrderList;
    private javax.persistence.Query glassManufacturedOrderQuery;
    private javax.swing.JComboBox<String> glassMaterial_jComboBox;
    private javax.swing.JComboBox<String> glassShape_jComboBox;
    private java.util.List<GUI.GodownPlacement1> godownPlacement1List;
    private javax.persistence.Query godownPlacement1Query;
    private javax.swing.JTextField godown_id;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField mg1;
    private javax.swing.JTextField mg2;
    private javax.swing.JTextField mg3;
    private javax.swing.JTextField mgp1;
    private javax.swing.JTextField mgp2;
    private javax.swing.JTextField mgp3;
    private javax.swing.JTextField quantity_txt;
    private javax.swing.JTextField shop_city;
    private javax.swing.JTextField shop_godown_id;
    private javax.swing.JTextField shop_id;
    private javax.swing.JTextField shop_name;
    private javax.swing.JButton shop_send_btn;
    private javax.swing.JButton shop_send_btn1;
    private javax.swing.JTable shop_stock_table;
    private javax.swing.JTable shop_table;
    private javax.swing.JTextField tg1;
    private javax.swing.JTextField tg2;
    private javax.swing.JTextField tg3;
    private javax.swing.JTextField tgp1;
    private javax.swing.JTextField tgp2;
    private javax.swing.JTextField tgp3;
    private java.util.List<GUI.VendorMain1> vendorMain1List;
    private java.util.List<GUI.VendorMain1> vendorMain1List1;
    private javax.persistence.Query vendorMain1Query;
    private javax.persistence.Query vendorMain1Query1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
