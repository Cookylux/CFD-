/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author rieje
 */
public class javaconnect {
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static Connection connectdb() {
        try {
            // Load MySQL JDBC Driver (optional in newer versions, but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/room_management", "root", "Hatdog08_");
            stmt = con.createStatement();
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(javaconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Statement getStatement() {
        return stmt;
    }

    public static ResultSet getResultSet() {
        return rs;
    }

    public static ResultSet executeQuery(String query) {
        try {
            if (stmt == null) {
                connectdb();  // ensure connection and statement are initialized
            }
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(javaconnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

