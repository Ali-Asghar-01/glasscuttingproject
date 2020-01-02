
package GUI;

import Classes.Sales;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SalesFdba extends javax.swing.JFrame {

    public SalesFdba() {
        this.setUndecorated(true);
        initComponents();
    }
    String user,pass;
    public SalesFdba(String user, String pass) {
        this.setUndecorated(true);
        initComponents();
        this.user=user;
        this.pass=pass;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        yearly = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Sale_txt = new javax.swing.JTextField();
        Profit_txt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        OneDaySaleBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        onedaytable = new javax.swing.JTable();
        Tax_txt = new javax.swing.JLabel();
        Tax_Field = new javax.swing.JTextField();
        date_txt_field = new com.toedter.calendar.JDateChooser();
        clearoneday = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        monthlySale = new javax.swing.JTextField();
        Tax = new javax.swing.JTextField();
        profit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        MonthlySaleBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        monthlytable = new javax.swing.JTable();
        Month_Combo_Txt = new javax.swing.JComboBox<>();
        clearbtnmonthly = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        yealytable = new javax.swing.JTable();
        yearlytaxtf = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        yearlysaletf = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        year_combo = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        yearlyprofittf = new javax.swing.JTextField();
        YearlySaleBtn = new javax.swing.JButton();
        clearbtnyealy = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        jLabel9.setText("jLabel9");

        jLabel11.setText("jLabel11");

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SALES");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(60, 10, 210, 100);

        yearly.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, null, java.awt.Color.darkGray, java.awt.Color.lightGray));
        yearly.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Date");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 40, 83, 20);

        Sale_txt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Sale_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Sale_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sale_txtActionPerformed(evt);
            }
        });
        jPanel1.add(Sale_txt);
        Sale_txt.setBounds(110, 120, 161, 32);

        Profit_txt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Profit_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(Profit_txt);
        Profit_txt.setBounds(110, 220, 161, 32);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Sale");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 120, 57, 40);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tax");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 170, 57, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Profit");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 220, 65, 30);

        OneDaySaleBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        OneDaySaleBtn.setText("Check Sale ");
        OneDaySaleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OneDaySaleBtnActionPerformed(evt);
            }
        });
        jPanel1.add(OneDaySaleBtn);
        OneDaySaleBtn.setBounds(128, 318, 144, 31);

        onedaytable.setModel(new javax.swing.table.DefaultTableModel(
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
        onedaytable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onedaytableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(onedaytable);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(288, 37, 880, 310);

        Tax_txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Tax_txt.setForeground(new java.awt.Color(255, 255, 255));
        Tax_txt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Tax_txt.setText("17%");
        jPanel1.add(Tax_txt);
        Tax_txt.setBounds(230, 177, 44, 20);

        Tax_Field.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Tax_Field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Tax_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tax_FieldActionPerformed(evt);
            }
        });
        jPanel1.add(Tax_Field);
        Tax_Field.setBounds(110, 171, 113, 30);

        date_txt_field.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        date_txt_field.setDateFormatString("dd-MMM-yyyy");
        date_txt_field.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(date_txt_field);
        date_txt_field.setBounds(110, 40, 150, 30);

        clearoneday.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clearoneday.setText("Clear");
        clearoneday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearonedayActionPerformed(evt);
            }
        });
        jPanel1.add(clearoneday);
        clearoneday.setBounds(10, 318, 112, 31);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel1.add(jLabel16);
        jLabel16.setBounds(-20, -40, 1230, 490);

        yearly.addTab("One day Sales Report", jPanel1);

        jPanel2.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Month");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(20, 30, 90, 33);

        monthlySale.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        monthlySale.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(monthlySale);
        monthlySale.setBounds(130, 120, 153, 30);

        Tax.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Tax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(Tax);
        Tax.setBounds(130, 170, 120, 30);

        profit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        profit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(profit);
        profit.setBounds(130, 220, 153, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Monthly Sale");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(20, 120, 108, 30);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tax");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 170, 108, 30);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Monthly Profit");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(20, 220, 108, 30);

        MonthlySaleBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        MonthlySaleBtn.setText("Check Monthly Sale");
        MonthlySaleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonthlySaleBtnActionPerformed(evt);
            }
        });
        jPanel2.add(MonthlySaleBtn);
        MonthlySaleBtn.setBounds(120, 320, 170, 33);

        monthlytable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        monthlytable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        monthlytable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        monthlytable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                monthlytableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(monthlytable);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(301, 27, 880, 320);

        Month_Combo_Txt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        Month_Combo_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Month_Combo_TxtActionPerformed(evt);
            }
        });
        jPanel2.add(Month_Combo_Txt);
        Month_Combo_Txt.setBounds(124, 29, 153, 33);

        clearbtnmonthly.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clearbtnmonthly.setText("Clear");
        clearbtnmonthly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnmonthlyActionPerformed(evt);
            }
        });
        jPanel2.add(clearbtnmonthly);
        clearbtnmonthly.setBounds(10, 320, 100, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("17%");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(260, 170, 30, 30);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel2.add(jLabel15);
        jLabel15.setBounds(0, -20, 1210, 480);

        yearly.addTab("Dynamic Sales Report", jPanel2);

        jPanel3.setLayout(null);

        yealytable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        yealytable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        yealytable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        yealytable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                yealytableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(yealytable);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(301, 27, 880, 320);

        yearlytaxtf.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        yearlytaxtf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(yearlytaxtf);
        yearlytaxtf.setBounds(130, 170, 120, 30);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("17%");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(250, 170, 30, 30);

        yearlysaletf.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        yearlysaletf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(yearlysaletf);
        yearlysaletf.setBounds(130, 120, 153, 30);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Yearly Sale");
        jPanel3.add(jLabel17);
        jLabel17.setBounds(10, 120, 108, 30);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Year");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(10, 30, 108, 33);

        year_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2018", "2017", "2016", "2015" }));
        year_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                year_comboActionPerformed(evt);
            }
        });
        jPanel3.add(year_combo);
        year_combo.setBounds(124, 29, 153, 33);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Tax");
        jPanel3.add(jLabel20);
        jLabel20.setBounds(10, 170, 108, 30);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Yearly Profit");
        jPanel3.add(jLabel21);
        jLabel21.setBounds(10, 220, 108, 30);

        yearlyprofittf.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        yearlyprofittf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(yearlyprofittf);
        yearlyprofittf.setBounds(130, 220, 153, 30);

        YearlySaleBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        YearlySaleBtn.setText("Check Yearly Sale");
        YearlySaleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearlySaleBtnActionPerformed(evt);
            }
        });
        jPanel3.add(YearlySaleBtn);
        YearlySaleBtn.setBounds(120, 320, 170, 33);

        clearbtnyealy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clearbtnyealy.setText("Clear");
        clearbtnyealy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnyealyActionPerformed(evt);
            }
        });
        jPanel3.add(clearbtnyealy);
        clearbtnyealy.setBounds(10, 320, 100, 30);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        jPanel3.add(jLabel22);
        jLabel22.setBounds(0, -20, 1200, 460);

        yearly.addTab("Yearly Sales Report", jPanel3);

        getContentPane().add(yearly);
        yearly.setBounds(30, 110, 1200, 470);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/iconfinder_back_1.png"))); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel14);
        jLabel14.setBounds(30, 40, 50, 40);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/if_save_accept_44368 (1).png"))); // NOI18N
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel23);
        jLabel23.setBounds(1170, 40, 60, 40);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Background/123.jpg"))); // NOI18N
        getContentPane().add(jLabel18);
        jLabel18.setBounds(-200, -50, 1584, 990);

        setSize(new java.awt.Dimension(1300, 662));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    String date;    
    private void clearbtnmonthlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnmonthlyActionPerformed
        // TODO add your handling code here:
        monthlySale.setText("");
        Tax.setText("");
        profit.setText("");
    }//GEN-LAST:event_clearbtnmonthlyActionPerformed

    private void Month_Combo_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Month_Combo_TxtActionPerformed

    }//GEN-LAST:event_Month_Combo_TxtActionPerformed

    private void monthlytableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_monthlytableMouseClicked
        // TODO add your handling code here:
        //Sales S = new Sales();
        //S.viewinFields(jTable1);

        //String add1 = String.valueOf(S.getID());
        //ID_table2_txt.setText(add1);
    }//GEN-LAST:event_monthlytableMouseClicked

    private void MonthlySaleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonthlySaleBtnActionPerformed

        String datestart, dateend;
        String month = String.valueOf(Month_Combo_Txt.getSelectedItem());
        Sales o = new Sales();
        o.setMyUser(user);
        o.setMyPass(pass);
        if("January".equals(month))
        {
            datestart="01-JAN-2018";
            dateend="31-JAN-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("February".equals(month)){
            datestart="01-Feb-2018";
            dateend="28-Feb-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("March".equals(month)){
            datestart="01-MAR-2018";
            dateend="31-MAR-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("April".equals(month)){
            datestart="01-APR-2018";
            dateend="30-APR-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("May".equals(month)){
            datestart="01-MAY-2018";
            dateend="31-MAY-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("June".equals(month)){
            datestart="01-JUN-2018";
            dateend="30-JUN-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("July".equals(month)){
            datestart="01-JUL-2018";
            dateend="31-JUL-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("August".equals(month)){
            datestart="01-AUG-2018";
            dateend="31-AUG-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("September".equals(month)){
            datestart="01-SEP-2018";
            dateend="30-SEP-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("October".equals(month)){
            datestart="01-OCT-2018";
            dateend="31-OCT-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("November".equals(month)){
            datestart="01-NOV-2018";
            dateend="30-NOV-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
        else if("December".equals(month)){
            datestart="01-DEC-2018";
            dateend="31-DEC-2018";
            o.FetchSaleByMonth(monthlySale,Tax,datestart,dateend);
            o.FetchMonthlyProfit(profit, datestart, dateend);
        }
    }//GEN-LAST:event_MonthlySaleBtnActionPerformed

    private void clearonedayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearonedayActionPerformed
        // TODO add your handling code here:
        date_txt_field.setDate(null);
        Tax_txt.setText("");
        Profit_txt.setText("");
        Sale_txt.setText("");
    }//GEN-LAST:event_clearonedayActionPerformed

    private void Tax_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tax_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tax_FieldActionPerformed

    private void onedaytableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onedaytableMouseClicked
        // TODO add your handling code here:
        //Sales S = new Sales();
        //S.viewinFields(jTable2);

        //String add = S.getDate();
        //dateoneday.setText(add);

        //String add1 = String.valueOf(S.getID());
        //dateoneday.setText(add1);
    }//GEN-LAST:event_onedaytableMouseClicked

    private void OneDaySaleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OneDaySaleBtnActionPerformed

        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        Sales S = new Sales();
        S.setMyUser(user);
        S.setMyPass(pass);
        S.OneDaySale(Sale_txt, Tax_Field, df.format(date_txt_field.getDate()));
        S.OneDayProfit(df.format(date_txt_field.getDate()), Profit_txt);
    }//GEN-LAST:event_OneDaySaleBtnActionPerformed

    private void Sale_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sale_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sale_txtActionPerformed

    private void yealytableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yealytableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_yealytableMouseClicked

    private void year_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_year_comboActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_year_comboActionPerformed

    private void YearlySaleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearlySaleBtnActionPerformed
        // TODO add your handling code here:
        String datestart, dateend;
        String year = String.valueOf(year_combo.getSelectedItem());
        Sales o = new Sales();
            o.setMyUser(user);
            o.setMyPass(pass);
        if(null != year)
        switch (year) {
            case "2018":
                datestart="01-JAN-2018";
                dateend="31-Dec-2018";
                o.FetchSaleByMonth(yearlysaletf,yearlytaxtf,datestart,dateend);
                o.FetchMonthlyProfit(yearlyprofittf, datestart, dateend);
                break;
            case "2017":
                datestart="01-Jan-2017";
                dateend="31-Dec-2017";
                o.FetchSaleByMonth(yearlysaletf,yearlytaxtf,datestart,dateend);
                o.FetchMonthlyProfit(yearlyprofittf, datestart, dateend);
                break;
            case "2016":
                datestart="01-Jan-2016";
                dateend="31-Dec-2016";
                o.FetchSaleByMonth(yearlysaletf,yearlytaxtf,datestart,dateend);
                o.FetchMonthlyProfit(yearlyprofittf, datestart, dateend);
                break;
            case "2015":
                datestart="01-Jan-2015";
                dateend="31-Dec-2015";
                o.FetchSaleByMonth(yearlysaletf,yearlytaxtf,datestart,dateend);
                o.FetchMonthlyProfit(yearlyprofittf, datestart, dateend);
                break;
            case "2019":
                datestart="01-Jan-2019";
                dateend="31-Dec-2019";
                o.FetchSaleByMonth(yearlysaletf,yearlytaxtf,datestart,dateend);
                o.FetchMonthlyProfit(yearlyprofittf, datestart, dateend);
                break;
            default:
                break;
        }

    }//GEN-LAST:event_YearlySaleBtnActionPerformed

    private void clearbtnyealyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnyealyActionPerformed
        // TODO add your handling code here:
        yearlyprofittf.setText("");
        yearlysaletf.setText("");
        yearlytaxtf.setText("");
    }//GEN-LAST:event_clearbtnyealyActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Sales S = new Sales();
        S.setMyUser(user);
        S.setMyPass(pass);
        S.fetchQuery(monthlytable);
        S.fetchQuery(onedaytable);
        S.fetchQuery(yealytable);

    }//GEN-LAST:event_formWindowOpened

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        new AdminPaneldba(user, pass).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
        Sales S = new Sales();
        S.fetchQuery(monthlytable);
        S.fetchQuery(onedaytable);
        S.fetchQuery(yealytable);
    }//GEN-LAST:event_jLabel23MouseClicked

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
            java.util.logging.Logger.getLogger(SalesFdba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesFdba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesFdba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesFdba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesFdba().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Month_Combo_Txt;
    private javax.swing.JButton MonthlySaleBtn;
    private javax.swing.JButton OneDaySaleBtn;
    private javax.swing.JTextField Profit_txt;
    private javax.swing.JTextField Sale_txt;
    private javax.swing.JTextField Tax;
    private javax.swing.JTextField Tax_Field;
    private javax.swing.JLabel Tax_txt;
    private javax.swing.JButton YearlySaleBtn;
    private javax.swing.JButton clearbtnmonthly;
    private javax.swing.JButton clearbtnyealy;
    private javax.swing.JButton clearoneday;
    private com.toedter.calendar.JDateChooser date_txt_field;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextField monthlySale;
    private javax.swing.JTable monthlytable;
    private javax.swing.JTable onedaytable;
    private javax.swing.JTextField profit;
    private javax.swing.JTable yealytable;
    private javax.swing.JComboBox<String> year_combo;
    private javax.swing.JTabbedPane yearly;
    private javax.swing.JTextField yearlyprofittf;
    private javax.swing.JTextField yearlysaletf;
    private javax.swing.JTextField yearlytaxtf;
    // End of variables declaration//GEN-END:variables
}
