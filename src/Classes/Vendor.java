
package Classes;

import static Classes.Order.ConnectDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;
import oracle.jdbc.OraclePreparedStatement;

public class Vendor {
    private String myUser;
    private String myPass;
    private static int V_ID, Order_No;
    private int quantity;
    private String vendorName, purchaseDate, glassMaterial, contactNo, is_type="vendor";

    public String getIs_type() {
        return is_type;
    }

    public void setIs_type(String is_type) {
        this.is_type = is_type;
    }
    
    private int amountPerGlass, totalAmount;

    public Vendor() {
    }

    public Vendor(int quantity, String vendorName, String purchaseDate, String glassMaterial, String contactNo, 
        int amountPerGlass, int totalAmount) {
        this.quantity = quantity;
        this.vendorName = vendorName;
        this.purchaseDate = purchaseDate;
        this.glassMaterial = glassMaterial;
        this.contactNo = contactNo;
        this.amountPerGlass = amountPerGlass;
        this.totalAmount = totalAmount;
    }

    public String getMyUser() {
        return myUser;
    }

    public void setMyUser(String myUser) {
        this.myUser = myUser;
    }

    public String getMyPass() {
        return myPass;
    }

    public void setMyPass(String myPass) {
        this.myPass = myPass;
    }
    
    public static int getV_ID() {
        return V_ID;
    }

    public static int getOrder_No() {
        return Order_No;
    }

    public static void setOrder_No(int Order_No) {
        Vendor.Order_No = Order_No;
    }

    public static void setV_ID(int V_ID) {
        Vendor.V_ID = V_ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getGlassMaterial() {
        return glassMaterial;
    }

    public void setGlassMaterial(String glassMaterial) {
        this.glassMaterial = glassMaterial;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getAmountPerGlass() {
        return amountPerGlass;
    }

    public void setAmountPerGlass(int amountPerGlass) {
        this.amountPerGlass = amountPerGlass;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public static Connection ConnectDB(){
        try{
            
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orc", 
                    "maaz", "poppin");
            System.out.println("Done with Connection");
            return con;
            
        }
        catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    public static Connection ConnectDB(String user, String pass){
        try{
            String drivername = "oracle.jdbc.OracleDriver";
            Class.forName(drivername);
            String serverName = "localhost";
            String serverPort = "1521";
            String sid = "orcl";
            String url = "jdbc:oracle:thin:@"+serverName+":"+serverPort+":"+sid+"";
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Done with Connection");
            //JOptionPane.showMessageDialog(null,"Connect");
            return con;
            
        }
        catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    public void Godown_Stock_Placement(int godown_id, String glass_material, String shape, int quantity, String date){
        
        int am_quantity = 0;
        int godown_stock_quantity = 0;
        
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT * from maaz.am_glass_details1\n" +
                        "where glass_material='"+glass_material+"' and shape='"+shape+"'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                am_quantity=rs.getInt("quantity");
            }
            
            if(!(am_quantity>=quantity)){
                JOptionPane.showMessageDialog(null, "Glass out of range.");
                return ;
            }
            
            //Inserting into godown placement
            String sql_godown_placement = "INSERT into maaz.godown_placement1(ID, GODOWN_ID, GP_DATE, QUANTITY, GLASS_MATERIAL, SHAPE)"
                    + "VALUES(godown_placement1_seq.NEXTVAL, '"+godown_id+"', '"+date+"', '"+quantity+"', '"+glass_material+"', '"+shape+"')";
            pst = con.prepareStatement(sql_godown_placement);
            pst.execute();
            
            am_quantity -= quantity;      
            // Glass after manufacture updation
            String sql_bm = "UPDATE maaz.am_glass_details1 set quantity=? where glass_material='"+glass_material+"' and shape='"+shape+"'";
            pst = (OraclePreparedStatement) con.prepareStatement(sql_bm);
            pst.setInt(1, am_quantity);
//            pst.setInt(2, getAmountPerGlass());
            pst.executeUpdate();
            
            //Checking am_glass_quantity first
            String sql_godown_stock_details = "SELECT * from maaz.godown_stock_details1 "
                    + "where glass_material = '"+glass_material+"' and shape='"+shape+"'";
            rs = st.executeQuery(sql_godown_stock_details);
        
            if(rs.next()){
                godown_stock_quantity=rs.getInt("quantity");
            }
            godown_stock_quantity += quantity;
            
            // Now add glass in godown stock details
            String sql_am_glass = "UPDATE maaz.godown_stock_details1 set quantity=?"
                    + "where glass_material='"+glass_material+"' and shape='"+shape+"'";
            pst = (OraclePreparedStatement) con.prepareStatement(sql_am_glass);
            pst.setInt(1, godown_stock_quantity);
            pst.executeUpdate();
            
            
            JOptionPane.showMessageDialog(null, "Glass Successfully Placed.");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void show_APG(int shop_id, String glass_material, String shape, int quantity, JTextField amount_per_glass, JTextField total_amount){
        
        int db_amount_per_glass = 0;
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT * from maaz.shop_stock_details1\n" +
                        "where shop_id='"+shop_id+"' and glass_material='"+glass_material+"' and shape='"+shape+"'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                db_amount_per_glass=rs.getInt("amount_per_glass");
            }
            db_amount_per_glass = (int) (db_amount_per_glass*1.5);
            amount_per_glass.setText(String.valueOf(db_amount_per_glass));
            total_amount.setText(String.valueOf(db_amount_per_glass*quantity));
            
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void Shop_Stock_Placement(int godown_id, int shop_id, String glass_material, String shape, int quantity, String date){
        
        int godown_glass_quantity = 0;
        int shop_glass_quantity = 0;
        int am_glass_price = 0;
        
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT * from maaz.godown_stock_details1\n" +
                        "where glass_material='"+glass_material+"' and shape='"+shape+"'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                godown_glass_quantity=rs.getInt("quantity");
            }
            
            if(!(godown_glass_quantity>=quantity)){
                JOptionPane.showMessageDialog(null, "Glass out of range.");
                return ;
            }
            
            //Inserting into godown placement
            String sql_godown_placement = "INSERT into maaz.shop_arrival_stock1(SAS_ID, SHOP_ID, GODOWN_ID, GLASS_MATERIAL, SHAPE, QUANTITY, SAS_DATE)"
                    + "VALUES(shop_arrival_stock1_seq.NEXTVAL, '"+shop_id+"', '"+godown_id+"', '"+glass_material+"', '"+shape+"', '"+quantity+"',"
                    + "'"+date+"')";
            pst = con.prepareStatement(sql_godown_placement);
            pst.execute();
            
            godown_glass_quantity -= quantity;      
            // Glass after manufacture updation
            String sql_bm = "UPDATE maaz.godown_stock_details1 set quantity=? where glass_material='"+glass_material+"' and shape='"+shape+"'";
            pst = (OraclePreparedStatement) con.prepareStatement(sql_bm);
            pst.setInt(1, godown_glass_quantity);
//            pst.setInt(2, getAmountPerGlass());
            pst.executeUpdate();
            
            //Checking am_glass_quantity first
            String sql_shop_stock_details = "SELECT * from maaz.shop_stock_details1 "
                    + "where glass_material = '"+glass_material+"' and shape='"+shape+"'";
            rs = st.executeQuery(sql_shop_stock_details);
        
            if(rs.next()){
                shop_glass_quantity=rs.getInt("quantity");
            }
            shop_glass_quantity += quantity;
            
            String sql_am_price = "SELECT * from maaz.am_glass_details1 "
                    + "where glass_material = '"+glass_material+"' and shape='"+shape+"'";
            rs = st.executeQuery(sql_am_price);
        
            if(rs.next()){
                am_glass_price=rs.getInt("amount_per_glass");
            }
            
            // Now add glass in godown stock details
            String sql_am_glass = "UPDATE maaz.shop_stock_details1 set quantity=?, amount_per_glass=?"
                    + "where glass_material='"+glass_material+"' and shape='"+shape+"' and SHOP_ID='"+shop_id+"'";
            pst = (OraclePreparedStatement) con.prepareStatement(sql_am_glass);
            pst.setInt(1, shop_glass_quantity);
            pst.setInt(2, am_glass_price);
            pst.executeUpdate();
            
            
            JOptionPane.showMessageDialog(null, "Glass Successfully Placed.");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void ManufactureGlass(String glass_material, String glass_shape, int quantity, String date, int length, int width, int height){
        int bm_quantity = 0; float bm_glass_material_price = (float) 0.0;
        int am_quantity = 0; float am_glass_material_price = (float) 0.0;
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT * from maaz.bm_glass_details where glass_material = '"+glass_material+"'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                bm_quantity=rs.getInt("quantity");
                bm_glass_material_price=(float) rs.getInt("AMOUNT_PER_GLASS");
            }
            
            if(!(bm_quantity>=quantity)){
                JOptionPane.showMessageDialog(null, "Glass out of range.");
                return ;
            }
            
            am_glass_material_price = (float) (bm_glass_material_price * 1.2);
            //Glass after manufacture
            String sql_am = "INSERT into maaz.glass_manufactured_order(gm_id, glass_material, shape,"
                    + "quantity, gm_date, length, width, height, amount_per_glass)"
                    + "VALUES(glass_manufactured_order_seq.NEXTVAL, '"+glass_material+"', '"+glass_shape+"', '"+quantity+"', '"+date+"',"
                    + "'"+length+"', '"+width+"', '"+height+"', '"+bm_glass_material_price+"')";
            pst = con.prepareStatement(sql_am);
            pst.execute();
            
            bm_quantity -= quantity;      
            // Glass before manufacture updation
            String sql_bm = "UPDATE maaz.bm_glass_details set quantity=? where glass_material='"+glass_material+"'";
            pst = (OraclePreparedStatement) con.prepareStatement(sql_bm);
            pst.setInt(1, bm_quantity);
//            pst.setInt(2, getAmountPerGlass());
            pst.executeUpdate();
            
            //Checking am_glass_quantity first
            String sql1_am = "SELECT * from maaz.am_glass_details1 "
                    + "where glass_material = '"+glass_material+"' and shape='"+glass_shape+"'";
            rs = st.executeQuery(sql1_am);
        
            if(rs.next()){
                am_quantity=rs.getInt("quantity");
            }
            am_quantity += quantity;
            
            // Now add glass in after manufacture details
            String sql_am_glass = "UPDATE maaz.am_glass_details1 set quantity=?, amount_per_glass=? "
                    + "where glass_material='"+glass_material+"' and shape='"+glass_shape+"'";
            pst = (OraclePreparedStatement) con.prepareStatement(sql_am_glass);
            pst.setInt(1, am_quantity);
            pst.setInt(2, (int) am_glass_material_price);
            pst.executeUpdate();
            
            
            JOptionPane.showMessageDialog(null, "Glass Successfully Maufactured");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void AddGlassQuantity(String glassname, int qty, int apg){
        int dbquantity = 0, dbid =0;
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT * from maaz.bm_glass_details where glass_material = '"+glassname+"'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                dbquantity=rs.getInt("quantity");
//                dbid = rs.getInt("glasstypeid");
            }
                dbquantity += qty;
        
            String sq = "UPDATE maaz.bm_glass_details set quantity=?, amount_per_glass=? where glass_material='"+glassname+"'";
            pst = (OraclePreparedStatement) con.prepareStatement(sq);
            pst.setInt(1, dbquantity);
            pst.setInt(2, apg);
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Update");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public boolean SubtractGlassQuantity(String glassname, int qty){
        boolean found=false;
        int dbquantity = 0, dbid =0;
        try{
            Connection con = ConnectDB();
            Statement st = con.createStatement();
            String sql = "SELECT * from maaz.glass_type_price where glasstypename = '"+glassname+"'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                dbquantity=rs.getInt("glassquantity");
                dbid = rs.getInt("glasstypeid");
            }
                dbquantity -= qty;
                
            String sq = "UPDATE maaz.glass_type_price set glassquantity=? where glasstypeid='"+dbid+"'";
            pst = (OraclePreparedStatement) con.prepareStatement(sq);
            pst.setInt(1, dbquantity);
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Update");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }
    
    public void ShowGlassQuantity(JTextField t, JTextField a, JTextField m,
            JTextField c, JTextField tp, JTextField ap, JTextField mp, JTextField cp){
        int temperedq = 0, ceramicq = 0, mirrorq = 0, annealedq = 0;
        int temperedp = 0, ceramicp = 0, mirrorp = 0, annealedp = 0;
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT quantity from maaz.bm_glass_details where glass_material='Ceramic'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                ceramicq=rs.getInt("quantity");
                c.setText(String.valueOf(ceramicq));
            }
            
            String sql1 = "SELECT quantity from maaz.bm_glass_details where glass_material='Annealed'";
            rs = st.executeQuery(sql1);
        
            if(rs.next()){
                annealedq=rs.getInt("quantity");
                a.setText(String.valueOf(annealedq));
            }
            
            String sql2 = "SELECT quantity from maaz.bm_glass_details where glass_material='Mirror'";
            rs = st.executeQuery(sql2);
        
            if(rs.next()){
                mirrorq=rs.getInt("quantity");
                m.setText(String.valueOf(mirrorq));
            }
            
            String sql3 = "SELECT quantity from maaz.bm_glass_details where glass_material='Tempered'";
            rs = st.executeQuery(sql3);
        
            if(rs.next()){
                temperedq=rs.getInt("quantity");
                t.setText(String.valueOf(temperedq));
            }
            
            String sql4 = "SELECT amount_per_glass from maaz.bm_glass_details where glass_material='Tempered'";
            rs = st.executeQuery(sql4);
        
            if(rs.next()){
                temperedp=rs.getInt("amount_per_glass");
                tp.setText(String.valueOf(temperedp));
            }
            
            String sql5 = "SELECT amount_per_glass from maaz.bm_glass_details where glass_material='Ceramic'";
            rs = st.executeQuery(sql5);
        
            if(rs.next()){
                ceramicp=rs.getInt("amount_per_glass");
                cp.setText(String.valueOf(ceramicp));
            }
            
            String sql6 = "SELECT amount_per_glass from maaz.bm_glass_details where glass_material='Annealed'";
            rs = st.executeQuery(sql6);
        
            if(rs.next()){
                annealedp=rs.getInt("amount_per_glass");
                ap.setText(String.valueOf(annealedp));
            }
            
            String sql7 = "SELECT amount_per_glass from maaz.bm_glass_details where glass_material='Mirror'";
            rs = st.executeQuery(sql7);
        
            if(rs.next()){
                mirrorp=rs.getInt("amount_per_glass");
                mp.setText(String.valueOf(mirrorp));
            }
            
            //JOptionPane.showMessageDialog(null, "Update");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void ShowAfterManufactureGlassQuantityCircle(JTextField t, JTextField a, JTextField m,
            JTextField c, JTextField tp, JTextField ap, JTextField mp, JTextField cp){
        int temperedq = 0, ceramicq = 0, mirrorq = 0, annealedq = 0;
        int temperedp = 0, ceramicp = 0, mirrorp = 0, annealedp = 0;
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT quantity from maaz.am_glass_details1 where glass_material='Ceramic' and shape='Circle'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                ceramicq=rs.getInt("quantity");
                c.setText(String.valueOf(ceramicq));
            }
            
            String sql1 = "SELECT quantity from maaz.am_glass_details1 where glass_material='Annealed' and shape='Circle'";
            rs = st.executeQuery(sql1);
        
            if(rs.next()){
                annealedq=rs.getInt("quantity");
                a.setText(String.valueOf(annealedq));
            }
            
            String sql2 = "SELECT quantity from maaz.am_glass_details1 where glass_material='Mirror' and shape='Circle'";
            rs = st.executeQuery(sql2);
        
            if(rs.next()){
                mirrorq=rs.getInt("quantity");
                m.setText(String.valueOf(mirrorq));
            }
            
            String sql3 = "SELECT quantity from maaz.am_glass_details1 where glass_material='Tempered' and shape='Circle'";
            rs = st.executeQuery(sql3);
        
            if(rs.next()){
                temperedq=rs.getInt("quantity");
                t.setText(String.valueOf(temperedq));
            }
            
            String sql4 = "SELECT amount_per_glass from maaz.am_glass_details1 where glass_material='Tempered' and shape='Circle'";
            rs = st.executeQuery(sql4);
        
            if(rs.next()){
                temperedp=rs.getInt("amount_per_glass");
                tp.setText(String.valueOf(temperedp));
            }
            
            String sql5 = "SELECT amount_per_glass from maaz.am_glass_details1 where glass_material='Ceramic' and shape='Circle'";
            rs = st.executeQuery(sql5);
        
            if(rs.next()){
                ceramicp=rs.getInt("amount_per_glass");
                cp.setText(String.valueOf(ceramicp));
            }
            
            String sql6 = "SELECT amount_per_glass from maaz.am_glass_details1 where glass_material='Annealed' and shape='Circle'";
            rs = st.executeQuery(sql6);
        
            if(rs.next()){
                annealedp=rs.getInt("amount_per_glass");
                ap.setText(String.valueOf(annealedp));
            }
            
            String sql7 = "SELECT amount_per_glass from maaz.am_glass_details1 where glass_material='Mirror' and shape='Circle'";
            rs = st.executeQuery(sql7);
        
            if(rs.next()){
                mirrorp=rs.getInt("amount_per_glass");
                mp.setText(String.valueOf(mirrorp));
            }
            
            //JOptionPane.showMessageDialog(null, "Update");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
        public void ShowAfterManufactureGlassQuantitySquare(JTextField t, JTextField a, JTextField m,
            JTextField c, JTextField tp, JTextField ap, JTextField mp, JTextField cp){
        int temperedq = 0, ceramicq = 0, mirrorq = 0, annealedq = 0;
        int temperedp = 0, ceramicp = 0, mirrorp = 0, annealedp = 0;
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT quantity from maaz.am_glass_details1 where glass_material='Ceramic' and shape='Square'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                ceramicq=rs.getInt("quantity");
                c.setText(String.valueOf(ceramicq));
            }
            
            String sql1 = "SELECT quantity from maaz.am_glass_details1 where glass_material='Annealed' and shape='Square'";
            rs = st.executeQuery(sql1);
        
            if(rs.next()){
                annealedq=rs.getInt("quantity");
                a.setText(String.valueOf(annealedq));
            }
            
            String sql2 = "SELECT quantity from maaz.am_glass_details1 where glass_material='Mirror' and shape='Square'";
            rs = st.executeQuery(sql2);
        
            if(rs.next()){
                mirrorq=rs.getInt("quantity");
                m.setText(String.valueOf(mirrorq));
            }
            
            String sql3 = "SELECT quantity from maaz.am_glass_details1 where glass_material='Tempered' and shape='Square'";
            rs = st.executeQuery(sql3);
        
            if(rs.next()){
                temperedq=rs.getInt("quantity");
                t.setText(String.valueOf(temperedq));
            }
            
            String sql4 = "SELECT amount_per_glass from maaz.am_glass_details1 where glass_material='Tempered' and shape='Square'";
            rs = st.executeQuery(sql4);
        
            if(rs.next()){
                temperedp=rs.getInt("amount_per_glass");
                tp.setText(String.valueOf(temperedp));
            }
            
            String sql5 = "SELECT amount_per_glass from maaz.am_glass_details1 where glass_material='Ceramic' and shape='Square'";
            rs = st.executeQuery(sql5);
        
            if(rs.next()){
                ceramicp=rs.getInt("amount_per_glass");
                cp.setText(String.valueOf(ceramicp));
            }
            
            String sql6 = "SELECT amount_per_glass from maaz.am_glass_details1 where glass_material='Annealed' and shape='Square'";
            rs = st.executeQuery(sql6);
        
            if(rs.next()){
                annealedp=rs.getInt("amount_per_glass");
                ap.setText(String.valueOf(annealedp));
            }
            
            String sql7 = "SELECT amount_per_glass from maaz.am_glass_details1 where glass_material='Mirror' and shape='Square'";
            rs = st.executeQuery(sql7);
        
            if(rs.next()){
                mirrorp=rs.getInt("amount_per_glass");
                mp.setText(String.valueOf(mirrorp));
            }
            
            //JOptionPane.showMessageDialog(null, "Update");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void SaveVendorData(Vendor V){
   
        int found=0, id=0;
        try {    
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sq = "SELECT * FROM maaz.user_info";
            rs = st.executeQuery(sq);
            
            while(rs.next()){
                String a = rs.getString("CONTACT_NO");
                id = rs.getInt("USER_ID");
                //System.out.println("Vendor Name "+a);
                //System.out.println("Vendor ID "+id);
                
                if(getContactNo().equals(a)){
                    found=1;
                    break;
                }
                else
                    found=0;
            }
        
            //System.out.println(id);
            if(found==1){
//                String sql1 = "INSERT INTO maaz.vendor_price(Glass_ID, Glass_Material, Quantity, Amount_Per_Glass, Total_Amount)"
//                        + "VALUES(vglass_id_seq.NEXTVAL,'"+getGlassMaterial()+"','"+getQuantity()+"','"+getAmountPerGlass()+"','"+getTotalAmount()+"')";
//
//                String sql3 = "INSERT INTO maaz.Vendor_Date(Date_ID, purchasing_date)"
//                        + "VALUES(date_seq.NEXTVAL,'"+getPurchaseDate()+"')";
//
//                //Order No PK hay or iskey elawa idhr sb foreign key hay
//                String sql4 = "INSERT INTO maaz.Vendor_Main(Order_No, V_ID, Glass_ID, Date_ID)"
//                        + "VALUES(Order_No_Seq.NEXTVAL,'"+id+"',vglass_id_seq.CURRVAL,date_seq.CURRVAL)";
                
                String vendor_record_sql = "INSERT INTO maaz.vendor_main1(order_id, vendor_id, purchase_date, glass_material, quantity,"
                        + "amount_per_glass, total_amount) VALUES(vendor_main1_seq.NEXTVAL, '"+id+"', '"+getPurchaseDate()+"', '"+getGlassMaterial()+"',"
                        + "'"+getQuantity()+"', '"+getAmountPerGlass()+"', '"+getTotalAmount()+"')";
                
//                pst = con.prepareStatement(sql1);
//                pst.execute();
//                pst = con.prepareStatement(sql3);
//                pst.execute();
//                pst = con.prepareStatement(sql4);
//                pst.execute();         
                  pst = con.prepareStatement(vendor_record_sql);
                  pst.execute();         

                

                JOptionPane.showMessageDialog(null, "Record Added if");
                con.close();
                
            }
            else{
                 //VENDOR ID idhr primary key hay..
//            String sql = "INSERT INTO maaz.Vendor_Details(V_ID, V_Name, V_CNo)"
//                    + "VALUES(VID_seq.NEXTVAL,'"+getVendorName()+"', '"+getContactNo()+"')";
// 
//            String sql1 = "INSERT INTO maaz.vendor_price(Glass_ID, Glass_Material, Quantity, Amount_Per_Glass, Total_Amount)"
//                    + "VALUES(vglass_id_seq.NEXTVAL,'"+getGlassMaterial()+"','"+getQuantity()+"','"+getAmountPerGlass()+"','"+getTotalAmount()+"')";
//            
//            String sql3 = "INSERT INTO maaz.Vendor_Date(Date_ID, purchasing_date)"
//                    + "VALUES(date_seq.NEXTVAL,'"+getPurchaseDate()+"')";
//            
//            //Order No PK hay or iskey elawa idhr sb foreign key hay
//            String sql4 = "INSERT INTO maaz.Vendor_Main(Order_No, V_ID, Glass_ID, Date_ID)"
//                    + "VALUES(Order_No_Seq.NEXTVAL,VID_seq.CURRVAL,vglass_id_seq.CURRVAL,date_seq.CURRVAL)";
              
                String sql = "INSERT INTO maaz.user_info(USER_ID, NAME, CONTACT_NO, IS_TYPE)"
                        + "VALUES(user_info_seq.NEXTVAL, '"+getVendorName()+"', '"+getContactNo()+"', '"+getIs_type()+"')";
                
                pst = con.prepareStatement(sql);
                pst.execute();
                
                String vendor_record_sql = "INSERT INTO maaz.vendor_main1(order_id, vendor_id, purchase_date, glass_material, quantity,"
                        + "amount_per_glass, total_amount) VALUES(vendor_main1_seq.NEXTVAL, user_info_seq.CURRVAL, '"+getPurchaseDate()+"', '"+getGlassMaterial()+"',"
                        + "'"+getQuantity()+"', '"+getTotalAmount()+"')";
                
                pst = con.prepareStatement(vendor_record_sql);
                pst.execute();
//            pst = con.prepareStatement(sql3);
//            pst.execute();
//            pst = con.prepareStatement(sql4);
//            pst.execute();         
                    
            JOptionPane.showMessageDialog(null, "Record Added else");
            con.close();
            
            }
                
        }
        catch(SQLException sqlEx){
            JOptionPane.showMessageDialog(null, sqlEx);
        }     
    }
    
    public void Consumer_Order(int shop_ID, String glass_material, String shape, int qty, String datee, int apg, double total_amount){
   
        try {
            int shop_glass_quantity = 0;
            try (Connection con = ConnectDB(getMyUser(), getMyPass())) {
                
                Statement st = con.createStatement();
                String sql = "SELECT * from maaz.shop_stock_details1\n" +
                            "where shop_id = '"+shop_ID+"' and glass_material='"+glass_material+"' and shape='"+shape+"'";
                rs = st.executeQuery(sql);

                if(rs.next()){
                    shop_glass_quantity=rs.getInt("quantity");
                }

                if(!(shop_glass_quantity>=qty)){
                    JOptionPane.showMessageDialog(null, "Glass out of range.");
                    return ;
                }
                
                String sql_insert = "INSERT INTO maaz.consumer1(ORDER_ID, SHOP_ID, GLASS_MATERIAL, SHAPE, QUANTITY, "
                        + "AMOUNT_PER_GLASS, TOTAL_AMOUNT, PURCHASE_DATE)"
                        + "VALUES(consumer1_seq.NEXTVAL,'"+shop_ID+"','"+glass_material+"', '"+shape+"', '"+qty+"', "
                        + "'"+apg+"', '"+total_amount+"', '"+datee+"')";
                
                pst = con.prepareStatement(sql_insert);
                pst.execute();
                
                shop_glass_quantity -= qty;      
                // Glass after manufacture updation
                String sql_bm = "UPDATE maaz.shop_stock_details1 set quantity=? "
                        + "where SHOP_ID='"+shop_ID+"' and glass_material='"+glass_material+"' and shape='"+shape+"'";
                pst = (OraclePreparedStatement) con.prepareStatement(sql_bm);
                pst.setInt(1, shop_glass_quantity);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Record Added.");
            }
                
        }
        catch(SQLException sqlEx){
            JOptionPane.showMessageDialog(null, sqlEx);
        }
    }
    
    public void InsertVendor(Vendor V){
   
        try {    
            try (Connection con = ConnectDB(getMyUser(), getMyPass())) {
                String sql = "INSERT INTO maaz.user_info(USER_ID, NAME, CONTACT_NO, IS_TYPE)"
                        + "VALUES(user_info_seq.NEXTVAL,'"+getVendorName()+"','"+getContactNo()+"', '"+getIs_type()+"')";
                
                pst = con.prepareStatement(sql);
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "Record Added.");
            }
                
        }
        catch(SQLException sqlEx){
            JOptionPane.showMessageDialog(null, sqlEx);
        }
    }
    
    public void InsertGodownDetails(){
        
        try {    
            try (Connection con = ConnectDB(getMyUser(), getMyPass())) {
                String sql = "INSERT INTO maaz.user_info(USER_ID, NAME, CONTACT_NO, IS_TYPE)"
                        + "VALUES(user_info_seq.NEXTVAL,'"+getVendorName()+"','"+getContactNo()+"', '"+getIs_type()+"')";
                
                pst = con.prepareStatement(sql);
                pst.execute();
                
                JOptionPane.showMessageDialog(null, "Record Added.");
            }
                
        }
        catch(SQLException sqlEx){
            JOptionPane.showMessageDialog(null, sqlEx);
        }
        
    }
    
    public void ShowGodownStockDetails(JTable jTable1){
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            String sql = "select gsd.id, gsd.godown_id, gd.godown_name, gd.godown_city, gsd.glass_material, gsd.shape, gsd.quantity \n" +
                            "from godown_stock_details1 gsd INNER JOIN godown_details gd\n" +
                            "ON gsd.godown_id=gd.godown_id";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
//            if(con!=null){
//                int i=0;
//                Statement state = con.createStatement();
//                String sql = "select gsd.id, gsd.godown_id, gd.godown_name, gd.godown_city, gsd.glass_material, gsd.shape, gsd.quantity \n" +
//                            "from godown_stock_details1 gsd INNER JOIN godown_details gd\n" +
//                            "ON gsd.godown_id=gd.godown_id";
//  
//                    rs = state.executeQuery(sql);
//                
//                while(rs.next()){
//                    jTable1.getModel().setValueAt(rs.getInt("ID"),i,0);
//                    jTable1.getModel().setValueAt(rs.getInt("GODOWN_ID"),i,1);
//                    jTable1.getModel().setValueAt(rs.getString("GODOWN_NAME"),i,2);
//                    jTable1.getModel().setValueAt(rs.getString("GODOWN_CITY"),i,3);
//                    jTable1.getModel().setValueAt(rs.getString("Glass_Material"),i,4);
//                    jTable1.getModel().setValueAt(rs.getString("SHAPE"),i,5);
//                    jTable1.getModel().setValueAt(rs.getInt("QUANTITY"),i,6);
////                    jTable1.getModel().setValueAt(rs.getInt("Total_Amount"),i,7);
////                    jTable1.getModel().setValueAt(rs.getString("Purchase_Date"),i,8);
//                    i++;
//                }
//            } 
        }
  
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void ShowConsumerOrderDetails(JTable jTable1){
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            String sql = "SELECT * from maaz.consumer1";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
  
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void ShowShopPlacementDetails(JTable jTable1){
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            String sql = "SELECT sas.SAS_Id, sas.SHOP_ID, sd.SHOP_NAME, sas.GODOWN_ID, gd.GODOWN_NAME, sas.GLASS_MATERIAL, sas.SHAPE, sas.QUANTITY, sas.SAS_DATE\n" +
                        "FROM shop_arrival_stock1 sas INNER JOIN shop_details sd \n" +
                        "ON sas.SHOP_Id=sd.SHOP_ID INNER JOIN godown_details gd\n" +
                        "ON sas.GODOWN_ID=gd.GODOWN_ID";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
  
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void ShowShopStockDetails(JTable jTable1){
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            String sql = "SELECT * from maaz.shop_stock_details1";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
  
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void viewinFields(JTable table) {
        try 
        {
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st;
            st = con.createStatement();
            int row =table.getSelectedRow();
            String clicked = (table.getModel().getValueAt(row, 0).toString());
            String sql = "SELECT vd.V_ID, vd.V_Name, vd.V_CNo, vm.Order_No, vp.Glass_Material, vp.Quantity, vp.Amount_Per_Glass, vp.Total_Amount, vdd.purchasing_date\n" +
                    "from maaz.vendor_details vd INNER JOIN maaz.vendor_main vm\n" +
                    "ON vd.V_ID=vm.V_ID INNER JOIN maaz.vendor_price vp\n" +
                    "ON vm.glass_id=vp.glass_id INNER JOIN maaz.vendor_date vdd\n" +
                    "ON vm.Date_ID = vdd.Date_ID\n" +
                    "where vm.Order_No = '"+clicked+"'";
            
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next())
            {
                setV_ID(rs.getInt("V_ID"));
                setVendorName(rs.getString("V_Name"));
                setContactNo(rs.getString("V_CNo"));
                setPurchaseDate(rs.getString("Purchasing_Date"));
                setGlassMaterial(rs.getString("Glass_Material"));
                setQuantity(rs.getInt("Quantity"));
                setAmountPerGlass(rs.getInt("Amount_Per_Glass"));
                setTotalAmount(rs.getInt("Total_Amount"));
                setOrder_No(rs.getInt("Order_No"));
            }
        }
        catch(SQLException e)
        {
                JOptionPane.showConfirmDialog(null, e);
        }   
    }
    
    public void viewinFields2(JTable table) {
        try 
        {
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st;
            st = con.createStatement();
            int row =table.getSelectedRow();
            String clicked = (table.getModel().getValueAt(row, 0).toString());
            String sql = "SELECT * from maaz.user_info where USER_ID = '"+clicked+"'";
            
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next())
            {
                setV_ID(rs.getInt("USER_ID"));
                setVendorName(rs.getString("NAME"));
                setContactNo(rs.getString("CONTACT_NO"));
            }
        }
        catch(SQLException e)
        {
                JOptionPane.showConfirmDialog(null, e);
        }   
    }
    
    public void FetchVendor(JTable jTable1){
        
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            
            if(con!=null){
                int i=0;
                Statement state = con.createStatement();
//                String sql = "SELECT vd.V_ID, vd.V_Name, vd.V_CNo, vm.Order_No, vp.Glass_Material, vp.Quantity, vp.Amount_Per_Glass, vp.Total_Amount, vdd.purchasing_date\n" +
//                    "from maaz.vendor_details vd INNER JOIN maaz.vendor_main vm\n" +
//                    "ON vd.V_ID=vm.V_ID INNER JOIN maaz.vendor_price vp\n" +
//                    "ON vm.glass_id=vp.glass_id INNER JOIN maaz.vendor_date vdd\n" +
//                    "ON vm.Date_ID = vdd.Date_ID\n";
                  String sql = "SELECT vm.order_id, vm.vendor_id, ui.contact_no, ui.name, vm.purchase_date, vm.glass_material, vm.quantity, vm.amount_per_glass, vm.total_amount\n" +
                    "from maaz.vendor_main1 vm INNER JOIN maaz.user_info ui \n" +
                    "ON vm.vendor_id=ui.user_id";
  
                    rs = state.executeQuery(sql);
                
                while(rs.next()){
                    jTable1.getModel().setValueAt(rs.getInt("ORDER_ID"),i,0);
                    jTable1.getModel().setValueAt(rs.getInt("VENDOR_ID"),i,1);
                    jTable1.getModel().setValueAt(rs.getString("NAME"),i,2);
                    jTable1.getModel().setValueAt(rs.getString("CONTACT_NO"),i,3);
                    jTable1.getModel().setValueAt(rs.getString("Glass_Material"),i,4);
                    jTable1.getModel().setValueAt(rs.getInt("Quantity"),i,5);
                    jTable1.getModel().setValueAt(rs.getInt("Amount_Per_Glass"),i,6);
                    jTable1.getModel().setValueAt(rs.getInt("Total_Amount"),i,7);
                    jTable1.getModel().setValueAt(rs.getString("Purchase_Date"),i,8);
                    i++;
                }
            }            
        }
  
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
    public void FetchVendorOnly(JTable jTable1){
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
        
            if(con!=null){
                int i=0;
                Statement state = con.createStatement();
                String sql = "SELECT * from maaz.user_info where IS_TYPE='"+getIs_type()+"'";
                rs = state.executeQuery(sql);
                
                while(rs.next()){
                    jTable1.getModel().setValueAt(rs.getInt("USER_ID"),i,0);
                    jTable1.getModel().setValueAt(rs.getString("NAME"),i,1);
                    jTable1.getModel().setValueAt(rs.getString("CONTACT_NO"),i,2);
                    i++;
                }
            }            
        }
  
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public boolean delete(int id) {
        
        boolean success = false;
        int vid=0, dateid=0, glassid=0;
        try {
              
            try (Connection con = ConnectDB(getMyUser(), getMyPass())) {
                 
                Statement st = con.createStatement();
                String sq = "SELECT * FROM maaz.Vendor_main where Order_No = '"+id+"'";
                rs = st.executeQuery(sq);
                
                if(rs.next()){
                    //vid = rs.getInt("V_ID");
                    dateid = rs.getInt("DATE_ID");
                    glassid = rs.getInt("GLASS_ID");
                }
                
                String sql= "Delete from maaz.Vendor_main where Order_No = '"+id+"'";
                //Never Delete Vendor ID.
                //String sql1= "Delete from Vendor_details where V_ID = '"+vid+"'";
                String sql2= "Delete from maaz.Vendor_price where Glass_ID = '"+glassid+"'";
                String sql3= "Delete from maaz.Vendor_date where Date_ID = '"+dateid+"'";
                String sql4="Commit";
                
                pst=con.prepareStatement(sql);
                pst.executeUpdate();
                pst=con.prepareStatement(sql2);
                pst.executeUpdate();
                pst=con.prepareStatement(sql3);
                pst.executeUpdate();
                pst = con.prepareStatement(sql4);
                pst.execute();
                
                success=true;
            }
                JOptionPane.showMessageDialog(null, "Record Deleted");
        }
         
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
           return success;
    }
    
    public void deleteVendorOnly(int id){
        
        try{
            try (Connection con = ConnectDB(getMyUser(), getMyPass())) {
                 
                Statement st = con.createStatement();
                String sql = "DELETE FROM maaz.user_info where USER_ID = '"+id+"'";
                String sql1 = "Commit";
                
                pst=con.prepareStatement(sql);
                pst.executeUpdate();
                pst=con.prepareStatement(sql1);
                pst.executeUpdate();
            }
                JOptionPane.showMessageDialog(null, "Record Deleted");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
             
    }
}
