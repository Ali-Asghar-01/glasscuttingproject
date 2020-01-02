
package Classes;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

public class User {
    private String myUser;
    private String myPass;
    private static int U_ID;
    private String name;
    private String CNIC;
    private String contactNo;
    private String date;
    private String city;
    private String is_type;
    
    public User() {
    }

    public User(String name, String CNIC, String contactNo, String date, String city) {
        this.name = name;
        this.CNIC = CNIC;
        this.contactNo = contactNo;
        this.date = date;
        this.city = city;
    }

    public String getIs_type() {
        return is_type;
    }

    public void setIs_type(String is_type) {
        this.is_type = is_type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static int getU_ID() {
        return U_ID;
    }

    public static void setU_ID(int U_ID) {
        User.U_ID = U_ID;
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
//            JOptionPane.showMessageDialog(null,"Connect");
            return con;
            
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println("Error " + String.valueOf(e));
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    public void InsertUser(User u){
        
        try{    
                Connection con = ConnectDB(getMyUser(), getMyPass());
                String sql = "INSERT into maaz.user_info(USER_ID,NAME,CONTACT_NO,IS_TYPE)"
                    + "VALUES(maaz.user_info_seq.NEXTVAL,'"+getName()+"','"+getContactNo()+"','"+getIs_type()+"')";
     
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Record Added");
            
        }
        catch(HeadlessException | SQLException sqlEx){
            JOptionPane.showMessageDialog(null, "You don't have certain priviliges");
        }     
    }
    
    public boolean UserCheck(int id, String name){
        
        boolean found = false;
        //Connection con = null;
        try{
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT * from maaz.user_info where user_id='"+id+"'";
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                //int userid = rs.getInt("USER_ID");
                String username = rs.getString("NAME");
                
                if(name.equals(username))
                    found = true;
                else
                    found = false;
            }
            con.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return found;
    }
    
    public boolean UserCreateCheck(String name){
        
        boolean found = false;
        //Connection con = null;
        try{
            try (Connection con = ConnectDB(getMyUser(), getMyPass())) {
                Statement st = con.createStatement();
                String sql = "SELECT * from maaz.user_info where name='"+name+"'";
                rs = st.executeQuery(sql);
                
                while(rs.next()){
                    //int userid = rs.getInt("USER_ID");
                    String username = rs.getString("NAME");
                    
                    if(name.equals(username))
                        found = true;
                    else
                        found = false;
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return found;
    }
    
    public void provideUserID(String name){
        Connection con;
        try{
            con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            String sql = "SELECT * from maaz.user_info where name='"+name+"'";
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt("USER_ID");
                //System.out.println("User ID: "+id);
                JOptionPane.showMessageDialog(null, "User ID: "+id+" \nUsername " +name+ "\nCreated");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public boolean delete(int id) {
         //To change body of generated methods, choose Tools | Templates.
         boolean success = false;
         try {
              
            Connection con = ConnectDB(getMyUser(), getMyPass()); 
            
            String sql= "Delete from maaz.user_info where USER_ID = '"+id+"'";
            pst=con.prepareStatement(sql);
            pst.executeUpdate();
            success=true;
            con.close();
            JOptionPane.showMessageDialog(null, "Record Deleted");
          }
         
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
         }
           return success;
    }
    
    public void search(JTable table, int id) {
        
        try {
            Connection con = ConnectDB(getMyUser(), getMyPass()); 
            Statement st = con.createStatement();
            String sql = "SELECT * FROM maaz.user_info where USER_ID = '"+id+"'";
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
    
    public void viewinFields(JTable jTable1) {
         
        try 
        {
            Connection con = ConnectDB(getMyUser(), getMyPass());
            Statement st = con.createStatement();
            int row =jTable1.getSelectedRow();
            String clicked = (jTable1.getModel().getValueAt(row, 0).toString());
            String sql = "select * from maaz.user_info where USER_ID = '"+clicked+"'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next())
            {
                setName(rs.getString("Name"));
//                setCNIC(rs.getString("CNIC"));
                setContactNo(rs.getString("CONTACT_NO"));
//                setCity(rs.getString("CITY"));
                setIs_type(rs.getString("IS_TYPE"));
                setU_ID(rs.getInt("USER_ID"));
//                setDate(rs.getString("CREATED_DATE"));  
            }
            
        }
        catch(SQLException e)
            {
                JOptionPane.showConfirmDialog(null, e);
            }   
    }
    
    public void fetchuser(JTable jTable1){
        try {   
            Connection con = ConnectDB(getMyUser(), getMyPass());    
            String sql = "Select * From maaz.user_info ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "You don't have certain priviliges");
        }
    }
    
    public int count(JTable jTable1){
        
        try {   
            Connection con = ConnectDB(getMyUser(), getMyPass());    
            String sql = "Select COUNT(*) from maaz.user_info ";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
           
            rs.next();
            int rowCount = rs.getInt(1);
            
            return rowCount;
            
            //RecordLabel.setText("Total Records Found = " + Integer.toString(rowCount));
         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "You don't have certain priviliges");
        }
        return 0;
    }
}
