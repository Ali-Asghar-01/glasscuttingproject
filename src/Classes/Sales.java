
package Classes;

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

public class Sales extends AbsSale{
    private String myUser;
    private String myPass;

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
    
    private String date;
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public Sales() {
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
            String sid = "orc";
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
    
    @Override
    public void OneDaySale(JTextField sale,JTextField tax, String date){
        double amount=0;
        try {
              Connection con = ConnectDB(getMyUser(), getMyPass());    
              Statement st = con.createStatement();
              String sql = "Select sum(b.amount) as TOTAL\n" +
                "FROM maaz.glass_user_order a INNER JOIN maaz.glass_price b\n" +
                "ON a.price_ID=b.price_ID AND a.order_date='"+date+"'";
              rs = st.executeQuery(sql);
              
              if(rs.next()){
                  amount = rs.getInt("TOTAL");
              }
              sale.setText(String.valueOf(amount));
              tax.setText(String.valueOf(Math.round(amount*0.17)));
                          
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
            
    @Override
    public void OneDayProfit(String date, JTextField Profit){
        String glasstype,glassshape;
        double total=0;
        try {
            Connection con = ConnectDB(getMyUser(), getMyPass());    
            Statement st = con.createStatement();
            String sql = "SELECT g.glasstype AS GlassType, g.glassshape AS GlassShape , o.order_date FROM maaz.glass_details g\n" +
                "INNER JOIN maaz.glass_user_order o ON o.glass_id=g.glass_ID AND o.order_Date='"+date+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){
                 
                glasstype=rs.getString("GlassType");
                glassshape=rs.getString("GlassShape");
               
                if(date.equals(rs.getString("Order_DATE"))){
                    
                }
                if(glasstype.equals("Annealed Glass")){
                    total+=500;
                }
                if(glassshape.equals("Circle")){
                    total+=700;
                }
                if(glassshape.equals("Square")){
                    total+=400;
                }
                if(glassshape.equals("Pentagon")){
                    total+=800;
                }
                if(glassshape.equals("Diamond")){
                    total+=1100;
                }
                if(glassshape.equals("Triangle")){
                    total+=400;
                }
                if(glassshape.equals("Rounded Corners")){
                    total+=900;
                }
                if(glasstype.equals("Ceremic Glass")){
                    total+=700;
                }
                if(glasstype.equals("Mirror Glass")){
                    total+=400;
                }
                if(glasstype.equals("Tempered Glass")){
                    total+=800;
                }
            }
            
            }catch(SQLException sqlEx){
                    JOptionPane.showMessageDialog(null, sqlEx);
            }finally{
                if(total==0){
                    Profit.setText(String.valueOf(total));
                    JOptionPane.showMessageDialog(null, "No sale for this date");
                }
                else
                    Profit.setText(String.valueOf(total));
            }
    }
    
    @Override
    public void FetchSaleByMonth(JTextField sale, JTextField tax, String datestart, String dateend){
        double amount=0, total=0;
        String glasstype, glassshape;
        try {   
            Connection con = ConnectDB(getMyUser(), getMyPass());
            String sql = "Select sum(b.amount) AS AMOUNT\n" +
                "FROM maaz.glass_user_order a INNER JOIN maaz.glass_price b\n" +
                "ON a.price_ID=b.price_ID AND a.ORDER_DATE between '"+datestart+"' AND '"+dateend+"'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            if(rs.next()){
                amount = rs.getInt("AMOUNT");
            }
            
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally{
            sale.setText(String.valueOf(amount));
            tax.setText(String.valueOf(amount*0.17));
        }
    }
    
    @Override
    public void FetchMonthlyProfit(JTextField profit, String datestart, String dateend){
        String glasstype,glassshape;
        double total=0;
        try {
            Connection con = ConnectDB(getMyUser(), getMyPass());    
            Statement st = con.createStatement();
            String sql = "SELECT g.glasstype AS GlassType, g.glassshape AS GlassShape , o.order_date FROM maaz.glass_details g\n" +
            "INNER JOIN maaz.glass_user_order o ON o.glass_id=g.glass_ID AND o.order_Date between '"+datestart+"' and '"+dateend+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){
                 
                glasstype=rs.getString("GlassType");
                glassshape=rs.getString("GlassShape");
                //System.out.println(glasstype);
                //System.out.println(glassshape);
                
                if(glasstype.equals("Annealed Glass")){
                    total+=500;
                }
                if(glassshape.equals("Circle")){
                    total+=700;
                }
                if(glassshape.equals("Square")){
                    total+=400;
                }
                if(glassshape.equals("Pentagon")){
                    total+=800;
                }
                if(glassshape.equals("Diamond")){
                    total+=1100;
                }
                if(glassshape.equals("Triangle")){
                    total+=400;
                }
                if(glassshape.equals("Rounded Corners")){
                    total+=900;
                }
                if(glasstype.equals("Ceremic Glass")){
                    total+=700;
                }
                if(glasstype.equals("Mirror Glass")){
                    total+=400;
                }
                if(glasstype.equals("Tempered Glass")){
                    total+=800;
                }
            }
            
            }catch(SQLException sqlEx){
                    JOptionPane.showMessageDialog(null, sqlEx);
            }finally{
                if(total==0){
                    profit.setText(String.valueOf(total));
                    JOptionPane.showMessageDialog(null, "No sale for this month/year");
                }
                else
                    profit.setText(String.valueOf(total));
            }
    }
    
    public void fetchQuery(JTable table){
        
        try {   
            Connection con = ConnectDB(getMyUser(), getMyPass());
            String sql = "Select a.order_ID, b.quantity, b.amount, c.glasstype, a.order_date\n" +
                "FROM maaz.glass_user_order a INNER JOIN maaz.glass_price b\n" +
                "ON a.price_ID=b.price_ID INNER JOIN maaz.glass_details c\n" +
                "ON a.glass_ID=c.glass_ID";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
}

