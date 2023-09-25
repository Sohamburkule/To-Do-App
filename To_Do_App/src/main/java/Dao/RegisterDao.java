package Dao;
import JDBC.Utils;
import Pojo.RegisterPojo;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDao {
	    
    public String insert(RegisterPojo register) {
    	
    	Connection con = Utils.getConnection();
    	String result="You Have Successfully Registered";
    	String sql="insert into users (name,username,password) values(?,?,?)";
    	PreparedStatement ps;
    	try{
    		ps=con.prepareStatement(sql);
    		ps.setString(1,register.getName());
    		ps.setString(2, register.getUsername());
    		ps.setString(3, register.getPassword());
    		ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="Data Not Entered";
		}
    	return result;
    }
}
