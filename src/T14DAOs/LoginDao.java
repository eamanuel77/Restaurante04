package T14DAOs;
/*
@author: Divyang Soni
@date : 10/18/2017
@ This class is having database related methods for login application
*/
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginDao extends HttpServlet{

	public static boolean validate(HttpServletRequest request, String name, String pass) {
		boolean validLogin = false;
		try {
			//defining database driver to use
			Class.forName("com.mysql.jdbc.Driver");
			
			//getting connection from the mysql database
			//jdbc:mysql://localhost:3306 is database url
			//login is database name
			//root : username
			//root: password
			//syntex : databaseurl/databasename, username , password
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/team14?zeroDateTimeBehavior=convertToNull", "root", "team14");
			
			

			//prepared statement is used for secure access
			// ? used for data to put in query
			// actual query to execute is
			// select * from users where username = name and password = pass
			PreparedStatement oPrStmt = con
					.prepareStatement("select * from users where UserID=? and UserPW=?");// ? represents some parameter to include
																							
			oPrStmt.setString(1, name);// parameter index start from 1
			oPrStmt.setString(2, pass);
			ResultSet rs = oPrStmt.executeQuery(); // executing the query and getting the resultset from databse
			
			//rs.next() shows that the resultset contains nect value or not
			// for retriving multiple results, you can use while(rs.next)
			
			//after valid login
			if (rs.next()) { //checking if the resultset has any value?   
				int adminStatus = rs.getInt("UserAdminStatus");
				int userNum = rs.getInt("UserNum");
	            HttpSession session = request.getSession();
	            session.setAttribute("user", name);
	            session.setAttribute("adminstatus", adminStatus);
	            session.setAttribute("usernum", userNum);
				validLogin = true;
				return validLogin;
			}
		
		} catch (Exception e) {
			System.out.println(e);
		}
		return validLogin;
	}
}
