package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

public class Order extends User {
    private String myUser;
    private String myPass;
    private static int O_ID;
    private static int U_ID;
    private String glassType;
    private int glassTypePrice;
    private String glassShape;
    private int glassShapePrice;
    private String glassThickness;
    private int glassThicknessPrice;
    private String glassDimension;
    private int glassDimensionsLength;
    private int lengthPrice;
    private int glassDimensionsWidth;
    private int widthPrice;
    private int glassDimensionsHeight;
    private int heightPrice;
    private int quantity;
    private int total_Order_Price;
    private String date;
    private String month;
    
    public Order() {
    }

    @Override
    public String getMyUser() {
        return myUser;
    }

    @Override
    public void setMyUser(String myUser) {
        this.myUser = myUser;
    }

    @Override
    public String getMyPass() {
        return myPass;
    }

    @Override
    public void setMyPass(String myPass) {
        this.myPass = myPass;
    }
    
    public static int getO_ID() {
        return O_ID;
    }

    public static void setO_ID(int O_ID) {
        Order.O_ID = O_ID;
    }

    public String getGlassDimension() {
        return glassDimension;
    }

    public void setGlassDimension(String glassDimension) {
        this.glassDimension = glassDimension;
    }

    public int getTotal_Order_Price() {
        return total_Order_Price;
    }

    public void setTotal_Order_Price(int total_Order_Price) {
        this.total_Order_Price = total_Order_Price;
    }

    public int getGlassThicknessPrice() {
        return glassThicknessPrice;
    }

    public void setGlassThicknessPrice(int glassThicknessPrice) {
        this.glassThicknessPrice = glassThicknessPrice;
    }
    
    public String getGlassType() {
        return glassType;
    }

    public void setGlassType(String glassType) {
        this.glassType = glassType;
    }

    public String getGlassShape() {
        return glassShape;
    }

    public void setGlassShape(String glassShape) {
        this.glassShape = glassShape;
    }

    public String getGlassThickness() {
        return glassThickness;
    }

    public void setGlassThickness(String glassThickness) {
        this.glassThickness = glassThickness;
    }

    public int getGlassDimensionsLength() {
        return glassDimensionsLength;
    }

    public void setGlassDimensionsLength(int glassDimensionsLength) {
        this.glassDimensionsLength = glassDimensionsLength;
    }

    public int getGlassDimensionsWidth() {
        return glassDimensionsWidth;
    }

    public void setGlassDimensionsWidth(int glassDimensionsWidth) {
        this.glassDimensionsWidth = glassDimensionsWidth;
    }

    public int getGlassDimensionsHeight() {
        return glassDimensionsHeight;
    }

    public void setGlassDimensionsHeight(int glassDimensionsHeight) {
        this.glassDimensionsHeight = glassDimensionsHeight;
    }

    public static int getU_ID() {
        return U_ID;
    }

    public static void setU_ID(int U_ID) {
        Order.U_ID = U_ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getGlassTypePrice() {
        return glassTypePrice;
    }

    public void setGlassTypePrice(int glassTypePrice) {
        this.glassTypePrice = glassTypePrice;
    }

    public int getGlassShapePrice() {
        return glassShapePrice;
    }

    public void setGlassShapePrice(int glassShapePrice) {
        this.glassShapePrice = glassShapePrice;
    }

    public int getLengthPrice() {
        return lengthPrice;
    }

    public void setLengthPrice(int lengthPrice) {
        this.lengthPrice = lengthPrice;
    }

    public int getWidthPrice() {
        return widthPrice;
    }

    public void setWidthPrice(int widthPrice) {
        this.widthPrice = widthPrice;
    }

    public int getHeightPrice() {
        return heightPrice;
    }

    public void setHeightPrice(int heightPrice) {
        this.heightPrice = heightPrice;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    
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
    
    public void SaveOrderData(Order o){
        System.out.println("In order class "+getMyUser()+getMyPass());
        try{
            try (Connection con = ConnectDB(getMyUser(), getMyPass())) {
                String sql = "INSERT into maaz.glass_user_order(order_id, glass_id, price_id, user_id, order_date)"
                        + "VALUES(maaz.order_id_seq.NEXTVAL, maaz.glassid_seq.CURRVAL, maaz.price_id_seq.CURRVAL,"
                        + "'"+getU_ID()+"','"+getDate()+"')";     
                
                String sql1 = "INSERT into maaz.glass_details(glass_id, glasstype, glassshape, glassthickness,glassdimension)"
                        + "VALUES(maaz.glassid_seq.NEXTVAL,'"+getGlassType()+"','"+getGlassShape()+"','"+getGlassThickness()+"',"
                        + "'"+getGlassDimensionsLength()+"x"+getGlassDimensionsWidth()+"')";
                
                String sql2 = "INSERT into maaz.glass_price(price_id, quantity, amount)"
                        + "VALUES(maaz.price_id_seq.NEXTVAL, '"+getQuantity()+"', '"+getTotal_Order_Price()+"')";

                pst = con.prepareStatement(sql1);
                pst.execute();
                pst = con.prepareStatement(sql2);
                pst.execute();
                pst = con.prepareStatement(sql);
                pst.execute();

                JOptionPane.showMessageDialog(null, "Record Added");
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void viewData(JTable jTable1) {
         
        try 
        {
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            int row =jTable1.getSelectedRow();
            String clicked = (jTable1.getModel().getValueAt(row, 0).toString());
            String sql = "SELECT go.order_id, ud.user_id, gd.glasstype, gd.glassshape, gd.glassthickness, "
                    + "gd.glassdimension, gp.quantity, gp.amount, go.order_date\n" +
                "from maaz.glass_user_order go INNER JOIN maaz.user_details ud\n" +
                "ON go.USER_ID=ud.USER_ID INNER JOIN maaz.glass_details gd\n" +
                "ON go.GLASS_ID=gd.GLASS_ID INNER JOIN maaz.glass_price gp\n" +
                "ON go.PRICE_ID=gp.PRICE_ID where go.ORDER_ID = '"+clicked+"'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next())
            {
                setO_ID(rs.getInt("ORDER_ID"));
                setU_ID(rs.getInt("USER_ID"));
                setGlassType(rs.getString("GLASSTYPE"));
                setGlassShape(rs.getString("GLASSSHAPE"));
                setGlassThickness(rs.getString("GLASSTHICKNESS"));
                setGlassDimension(rs.getString("GLASSDIMENSION"));
                setQuantity(rs.getInt("QUANTITY"));
                setTotal_Order_Price(rs.getInt("AMOUNT"));
                setDate(rs.getString("ORDER_DATE"));
            }
            
        }
        catch(SQLException e)
            {
                JOptionPane.showConfirmDialog(null, e);
            }   
    }
    
    public boolean deleteorder(int id) {
         //To change body of generated methods, choose Tools | Templates.
         boolean success = false;
         int priceid=0, glassid=0;
         try {
              
            Connection con = ConnectDB(getMyUser(), getMyPass()); 
            Statement st = con.createStatement();
            String sq = "SELECT * FROM maaz.Glass_User_Order where Order_ID = '"+id+"'";
            rs = st.executeQuery(sq);
                
            if(rs.next()){
                priceid = rs.getInt("PRICE_ID");
                glassid = rs.getInt("GLASS_ID");
            }
            
                String sql= "Delete from maaz.Glass_User_Order where Order_ID = '"+id+"'";
                String sql2= "Delete from maaz.Glass_Details where Glass_ID = '"+glassid+"'";
                String sql3= "Delete from maaz.Glass_Price where Price_ID = '"+priceid+"'";
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
                JOptionPane.showMessageDialog(null, "Record Deleted");
          }
         
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
         }
           return success;
    }
    
    /**
     *
     * @param table
     * @param datestart
     * @param dateend
     */
    public void FetchOrderByMonth(JTable table, String datestart, String dateend){
        
        try {   
            Connection con = ConnectDB(getMyUser(), getMyPass());
            String sql = "SELECT go.order_id, ud.user_id, gd.glasstype, gd.glassshape,\n" +
"                gd.glassthickness, gd.glassdimension, gp.quantity, gp.amount, go.order_date\n" +
"                from maaz.glass_user_order go INNER JOIN maaz.user_details ud\n" +
"                ON go.USER_ID=ud.USER_ID INNER JOIN maaz.glass_details gd\n" +
"                ON go.GLASS_ID=gd.GLASS_ID INNER JOIN maaz.glass_price gp\n" +
"                ON go.PRICE_ID=gp.PRICE_ID AND go.order_date between '"+datestart+"' AND '"+dateend+"'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void searchorder(JTable table, int id) {
        
        try {
            Connection con = ConnectDB(getMyUser(), getMyPass()); 
            Statement st = con.createStatement();
            String sql = "SELECT go.order_id, ud.user_id, gd.glasstype, gd.glassshape, gd.glassthickness, "
                    + "gd.glassdimension, gp.quantity, gp.amount, go.order_date\n" +
                "from maaz.glass_user_order go INNER JOIN maaz.user_details ud\n" +
                "ON go.USER_ID=ud.USER_ID INNER JOIN maaz.glass_details gd\n" +
                "ON go.GLASS_ID=gd.GLASS_ID INNER JOIN maaz.glass_price gp\n" +
                "ON go.PRICE_ID=gp.PRICE_ID where go.ORDER_ID = '"+id+"'";
            rs = st.executeQuery(sql);
            //table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
            if(rs.next()){
                rs = st.executeQuery(sql);
                table.setModel(net.proteanit.sql.DbUtils.resultSetToTableModel(rs));
            }
            else
                JOptionPane.showMessageDialog(null, "No Record Found");
        }
         catch(SQLException e){
             JOptionPane.showMessageDialog(null, e);       
        }
    }
    
    public void fetchorder(JTable table){
        
        try {   
            Connection con = ConnectDB(getMyUser(), getMyPass());
            String sql = "SELECT go.order_id, ud.user_id, gd.glasstype, gd.glassshape,"
                    + " gd.glassthickness, gd.glassdimension, gp.quantity, gp.amount, go.order_date\n" +
                "from maaz.glass_user_order go INNER JOIN maaz.user_details ud\n" +
                "ON go.USER_ID=ud.USER_ID INNER JOIN maaz.glass_details gd\n" +
                "ON go.GLASS_ID=gd.GLASS_ID INNER JOIN maaz.glass_price gp\n" +
                "ON go.PRICE_ID=gp.PRICE_ID";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "You don't have certain priviliges");
        }
    }   
    
    public int fetchglasstypeprice(String glassname){
        int gprice = 0;
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT glassprice from maaz.glass_type_price where glasstypename='"+glassname+"'";
            rs = st.executeQuery(sql);
        
            if(rs.next()){
                gprice=rs.getInt("glassprice");
            }
            return gprice;
            //JOptionPane.showMessageDialog(null, "Update");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
}
