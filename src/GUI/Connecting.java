
package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Connecting {
    
    private static String user;
    private static String pass;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Connecting.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        Connecting.pass = pass;
    }
    
    public static Connection ConnectDB(){
        try{
            String drivername = "oracle.jdbc.OracleDriver";
            Class.forName(drivername);
            String serverName = "localhost";
            String serverPort = "1521";
            String sid = "orcl";
            String url = "jdbc:oracle:thin:@"+serverName+":"+serverPort+":"+sid+"";
            Connection con = DriverManager.getConnection(url, getUser(), getPass());
            System.out.println("Done with Connection");
            //JOptionPane.showMessageDialog(null,"Connect");
            return con;
            
        }
        catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    public boolean matchPassword(String user, String pass){
         
        Connection con;
        boolean found =false;
        try { 
            Connecting.setUser(user);
            Connecting.setPass(pass);
            con = Connecting.ConnectDB();    
            Statement st = con.createStatement();
            found=true;
            con.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }     
     return found; 
    }
}