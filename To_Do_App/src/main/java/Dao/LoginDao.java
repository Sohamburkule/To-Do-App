package Dao;
import Pojo.LoginPojo;
import JDBC.Utils;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
	
    public static boolean validate(LoginPojo login) {
    	boolean status = false;
    	Connection con = Utils.getConnection();
    	String sql="select * from users where username = ? and password = ?";
    	PreparedStatement ps;
    	try{
    		ps=con.prepareStatement(sql);
    		ps.setString(1, login.getUsername());
    		ps.setString(2, login.getPassword());
    		
    		System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return status;
    }
}
