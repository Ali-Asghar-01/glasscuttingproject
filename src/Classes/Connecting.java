
package Classes;

import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;

public class Connecting {

    public Connecting() {
    }
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public Connection connect(){
        String dataSourceName="database/GCP.accdb"; //where is database
        String dir = System.getProperty("user.dir"); // java builtin pw (pick everything)
        String url = "jdbc:ucanaccess://"+dir+"/" + dataSourceName; //url is of database
        Connection conn = null;
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conn = DriverManager.getConnection(url);
        }
        catch(HeadlessException | ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return conn;
    }
}
