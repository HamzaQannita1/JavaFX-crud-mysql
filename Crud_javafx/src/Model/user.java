/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hamza qannita
 */
public class user {
    
    private String name;
    private String salary;
    private String email;
    private String password;
    private int id;
    
    public user() {
    }
    
    public user(String name, String salary, String email, String password, int id) {
        this.name = name;
        this.salary = salary;
        this.email = email;
        this.password = password;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSalary() {
        return salary;
    }
    
    public void setSalary(String salary) {
        this.salary = salary;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
   
    PreparedStatement pst;
    Connection conn;
    Db db;
    int recordCounter = 0;
    
    public int save() throws SQLException, ClassNotFoundException {
        conn = Db.ConnectioDB();
        pst = null;
        
        String sql;
        sql = "INSERT INTO user(id,name, email, password, salary) VALUES (?,?,?,?,?)";
        pst = conn.prepareStatement(sql);
        pst.setInt(1, getId());
        pst.setString(2, this.getName());
        pst.setString(3, this.getEmail());
        pst.setString(4, this.getPassword());
        pst.setString(5, this.getSalary());
        
        recordCounter = pst.executeUpdate();
        if (recordCounter > 0) {
            System.out.println(this.getName() + " User was added successfully!");
        }
        if (pst != null) {
            pst.close();
        }
        conn.close();
        return recordCounter;
    }
    
    public static ArrayList<user> getAlluser() throws SQLException, ClassNotFoundException {
        ArrayList<user> users;
        try (Connection c = Db.ConnectioDB()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            users = new ArrayList<>();
            String sql = "SELECT * FROM user ";
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("name");
                String salary = rs.getString("salary");
                String email = rs.getString("email");
                String password = rs.getString("password");
                user user = new user(username, salary, email, password, id);
                user.setId(rs.getInt(1));
                users.add(user);
                
            }
            if (ps != null) {
                ps.close();
            }
        }
        return users;
    }
    
}
