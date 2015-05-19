package qq;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class qq73 {

	public qq73() throws Exception {
		Runtime.getRuntime().exec("cmd /c start C:/qq.bat");
	}

	public static void main(String[] args) throws Exception {
		// Runtime.getRuntime().exec("cmd /c start c:/qq.bat");
		new qq73();
		String s = "", q = "";
		int i = 0;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:dbFox");
			Statement stmt = conn.createStatement();

			/*
			 * q = "select * from sysfile"; ResultSet rs = stmt.executeQuery(q);
			 * while (rs.next()) s = s +
			 * rs.getObject(rs.findColumn("last_wrk_no")).toString() .trim();
			 */

			q = "select * from xmlimp";
			ResultSet rs = stmt.executeQuery(q);
			while (rs.next())
				i = rs.getRow();

			// q="insert into xmlimp () values ('123','','',,.T.,.T.,'','','','')";
			// stmt.execute(q);

			conn.close();

		} catch (Exception e) {
			s = e.toString();
		}
		System.out.println(i);
	}
}