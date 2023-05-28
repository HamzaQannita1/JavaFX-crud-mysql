/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hamza qannita
 */
public class Db {

    public static Connection ConnectioDB() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "");
            System.out.println(" Connection DataBase ^_^");
            return conn;

        } catch (ClassNotFoundException ex) {
            System.out.println("Error Connection DataBase ??");
            return null;
        }
    }

}
