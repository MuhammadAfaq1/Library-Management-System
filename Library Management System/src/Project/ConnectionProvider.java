package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionProvider {
	public static Connection con = null;
	public static Statement st;
	public static boolean connectionMade;

	public static Connection getCon() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "admin");
			st = con.createStatement();
			System.out.println("checkConnection");
			return con;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = st.executeQuery(s);
				rs.updateRow();
				return rs;
			} else {
				st.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			destroyConnection();
			getCon();
			e.printStackTrace();
			System.out.println("flash");
		}
		return null;
	}

	public static void destroyConnection() {
		try {
			st.close();
			con.close();
			connectionMade = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
