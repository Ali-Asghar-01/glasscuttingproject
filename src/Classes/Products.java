
package Classes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Products {
    
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
    
    public void TopGlass(JTextField tglass){
        String topglass=""; 
        try {
              Connection con = ConnectDB(getMyUser(), getMyPass());    
              Statement st = con.createStatement();
              String sql = "SELECT * from maaz.topproduct";
                ResultSet rs = st.executeQuery(sql);
              
              if(rs.next()){
                  topglass = rs.getString("glasstype");
              }
              tglass.setText(topglass);
                          
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void MostProfitableGlass(JTextField profitglass, String datestart, String dateend){
        String glasstype, topglassname = null;
        double annealedg=0, ceramicg=0, mirrorg=0, temperedg=0;
        double annealedp=0, ceramicp=0, mirrorp=0, temperedp=0;
        double annealedgprofit=0, ceramicgprofit=0, mirrorgprofit=0, temperedgprofit=0;
        try {
            Connection con = ConnectDB(getMyUser(), getMyPass());    
            Statement st = con.createStatement();
            String sql = "SELECT glassprice from maaz.glass_type_price where glasstypename='Ceramic Glass'";
            ResultSet rs = st.executeQuery(sql);
        
            if(rs.next()){
                ceramicg=rs.getInt("glassprice");
                ceramicgprofit=rs.getInt("glassprice")*0.2;
            }
            
            String sql1 = "SELECT glassprice from maaz.glass_type_price where glasstypename='Annealed Glass'";
            rs = st.executeQuery(sql1);
        
            if(rs.next()){
                annealedg=rs.getInt("glassprice");
                annealedgprofit=rs.getInt("glassprice")*0.2;
            }
            
            String sql2 = "SELECT glassprice from maaz.glass_type_price where glasstypename='Mirror Glass'";
            rs = st.executeQuery(sql2);
        
            if(rs.next()){
                mirrorg=rs.getInt("glassprice");
                mirrorgprofit=rs.getInt("glassprice")*0.2;
            }
            
            String sql3 = "SELECT glassprice from maaz.glass_type_price where glasstypename='Tempered Glass'";
            rs = st.executeQuery(sql3);
        
            if(rs.next()){
                temperedg=rs.getInt("glassprice");
                temperedgprofit=rs.getInt("glassprice")*0.2;
            }
            
            String sql5 = "SELECT g.glasstype AS GlassType, g.glassshape AS GlassShape , o.order_date FROM maaz.glass_details g\n" +
            "INNER JOIN maaz.glass_user_order o ON o.glass_id=g.glass_ID AND o.order_Date between '"+datestart+"' and '"+dateend+"'";
            rs = st.executeQuery(sql5);
            
            while(rs.next()){
                glasstype=rs.getString("GlassType");
                
                if(glasstype.equals("Annealed Glass")){
                    annealedp+=annealedgprofit;
                }
                if(glasstype.equals("Ceramic Glass")){
                    ceramicp+=ceramicgprofit;
                }
                if(glasstype.equals("Mirror Glass")){
                    mirrorp+=mirrorgprofit;
                }
                if(glasstype.equals("Tempered Glass")){
                    temperedp+=temperedgprofit;
                }
            }
            
            if(annealedp>ceramicp && annealedp>mirrorp && annealedp>temperedp)
                topglassname="Annealed Glass";
            else if(ceramicp>annealedp && ceramicp>mirrorp && ceramicp>temperedp)
                topglassname="Ceramic Glass";
            else if(mirrorp>annealedp && mirrorp>ceramicp && mirrorp>temperedp)
                topglassname="Mirror Glass";
            else
                topglassname="Tempered Glass";
            
            System.out.println(annealedp);
            System.out.println(mirrorp);
            System.out.println(ceramicp);
            System.out.println(temperedp);
            
            }catch(SQLException sqlEx){
                    JOptionPane.showMessageDialog(null, sqlEx);
            }finally{
                if("".equals(topglassname)){
                    profitglass.setText(String.valueOf(topglassname));
                    JOptionPane.showMessageDialog(null, "No sale for this month/year");
                }
                else
                    profitglass.setText(String.valueOf(topglassname));
            }
    }
    
    public void TopCustomer(JTextField tcustomer){
        String topcustomer=""; 
        try {
              Connection con = ConnectDB(getMyUser(), getMyPass());    
              Statement st = con.createStatement();
              String sql = "Select * from maaz.topcustomer";
                ResultSet rs = st.executeQuery(sql);
              
              if(rs.next()){
                  topcustomer = rs.getString("name");
              }
              tcustomer.setText(topcustomer);
                          
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void TopVendor(JTextField tvendor){
        String topcustomer=""; 
        try {
              Connection con = ConnectDB(getMyUser(), getMyPass());    
              Statement st = con.createStatement();
              String sql = "Select * from maaz.topvendor";
              ResultSet rs = st.executeQuery(sql);
              
              if(rs.next()){
                  topcustomer = rs.getString("v_name");
              }
              tvendor.setText(topcustomer);
                          
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void MostProfitableCustomer(JTextField profitcustomer){
        String glasstype,glassshape,uname = null, pcustomer = null;
        double total=0, uid=0, amount=0;
        try {
            Connection con = ConnectDB(getMyUser(), getMyPass());    
            Statement st = con.createStatement();
            String sql = "SELECT USER_ID from maaz.user_details";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                
                uid=rs.getInt("USER_ID");
                System.out.println(rs.getInt("USER_ID"));
                
                String sqlin = "SELECT g.glasstype, g.glassshape, u.name FROM maaz.glass_details g\n" +
                "INNER JOIN maaz.glass_user_order o ON o.glass_id=g.glass_ID INNER JOIN maaz.user_details u "
                        + "ON u.USER_ID=o.USER_ID AND u.USER_ID='"+uid+"'";
                rs = st.executeQuery(sqlin);
            
                while(rs.next()){
                    glasstype=rs.getString("GlassType");
                    glassshape=rs.getString("GlassShape");
                
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
                        uname=rs.getString("NAME");
                }
                    amount=total;
                    if(amount<total){
                        amount=total;
                        pcustomer=uname;
                    }
                    else if(amount==total){
                        amount=total;
                        pcustomer=uname;
                    }
                    System.out.println(amount);
                    System.out.println(uname);
            }
            
            }catch(SQLException sqlEx){
                    JOptionPane.showMessageDialog(null, sqlEx);
            }finally{
                if(total==0){
                    profitcustomer.setText(pcustomer);
                    JOptionPane.showMessageDialog(null, "No sale for this month/year");
                }
                else
                    profitcustomer.setText(pcustomer);
            }
    }
    
    public void userappears(int id, JTextField user, String dateS, String dateE){
        System.out.println(dateS+dateE);
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql=" SELECT userappear('"+id+"','"+dateS+"','"+dateE+"')a from maaz.glass_user_order ";
            ResultSet rs = st.executeQuery(sql);
            
            if(rs.next()){
                user.setText(rs.getString("a"));
           }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
}
