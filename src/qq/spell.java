package qq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.jdo.PersistenceManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.List;

public class spell extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		String s=req.getQueryString();
		s="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><spellrequest textalreadyclipped=\"0\" ignoredigits=\"1\" ignoreallcaps=\"1\" ignoredups=\"1\"><text>"+s+"</text></spellrequest>";
		//s = URLEncoder.encode("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><spellrequest textalreadyclipped=\"0\" ignoredigits=\"1\" ignoreallcaps=\"1\" ignoredups=\"1\"><text>"+s+"</text></spellrequest>", "UTF-8");

	        try {
	        	//URL url = new URL("http://localhost/tbproxy/spell?lang=en&hl=en");
	        	URL url = new URL("https://www.google.com/tbproxy/spell?lang=en&hl=en");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setRequestMethod("POST");

	            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	            writer.write(s);
	            writer.close();
	    
	            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                // OK
	            	
	        		DataInputStream inStream = new DataInputStream(connection.getInputStream());
	        		StringBuffer stb = new StringBuffer();

	        		int ch = ' ';
	        		for (ch = inStream.read(); ch > 0; ch = inStream.read()) {
	        			stb.append((char) ch);
	        			 System.out.print((char) ch);
	        			if (stb.indexOf("\r\n0\r\n\r\n") > -1)
	        				break;
	        		}
	        		inStream.close();
	        		s=stb.toString();
	        		
	            } else {
	                // Server returned HTTP error code.
	            }
	        } catch (MalformedURLException e) {
	            // ...
	        } catch (IOException e) {
	            // ...
	        }
	        out.println(s);
	}
	
	public static String rfu(String url) {
		StringBuffer s = new StringBuffer();
		try {
			URL u = new URL(url);
			
			InputStream in = u.openConnection().getInputStream();
			for (int ch = in.read(); ch > 0; ch = in.read()) {
				s.append((char) ch);
			}
			in.close();
		} catch (IOException e) {
			return e.toString();
		}
		return s.toString();
	}
	
	public static String rfup(String protocol, String host, int port, String file) {
		StringBuffer s = new StringBuffer();
		try {
			URL u = new URL(protocol,host,port, file);
			
			InputStream in = u.openConnection().getInputStream();
			for (int ch = in.read(); ch > 0; ch = in.read()) {
				s.append((char) ch);
			}
			in.close();
		} catch (IOException e) {
			return e.toString();
		}
		return s.toString();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		String s = "", ss = "", sq = "";
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String customer_name = user.getNickname();
		String company_name = req.getParameter("TextBox1");
		String address1 = req.getParameter("TextBox2");
		String address2 = req.getParameter("TextBox3");
		String city = req.getParameter("TextBox4");
		String prov_state = req.getParameter("DropDownList2");
		String postal_code = req.getParameter("TextBox6");
		String country = req.getParameter("DropDownList1");
		String contact = req.getParameter("TextBox8");
		String phone = req.getParameter("TextBox9");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Shipper ns = new Shipper(user, new Date(), customer_name, company_name,
				address1, address2, city, prov_state, postal_code, country,
				contact, phone);

		pm.makePersistent(ns);

		/*
		 * //sq = "select from " + Shipper.class.getName() +
		 * " where lastName == 'Smith'"; sq = "select from " +
		 * Shipper.class.getName();
		 * 
		 * List<Shipper> slist = (List<Shipper>) pm.newQuery(sq).execute();
		 * 
		 * // s=shta.rff("1.txt"); int i=0; if(!slist.isEmpty()) i=slist.size();
		 * while(i-- >0) ss = ss+slist.get(i).get_country()+"<br>";
		 */

		s = "Customer: " + customer_name + "\r\nCompany name: " + company_name
				+ "\r\nAddress: " + address1 + " " + address2 + "\r\nCity: "
				+ city + "\r\nProv/State: " + prov_state + "\r\nPostal code: "
				+ postal_code + "\r\nCountry: " + country + "\r\nContact: "
				+ contact + "\r\nPhone: " + phone;

		s = shta.send_mail("weborder@quicklydone.com", "new shipper", s);
		out.println(s + shta.rff("1.txt"));

	}

	public String get_table(String table) {

		String s = "";
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:dbFox");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM \"" + table
					+ ".dbf\"");
			while (rs.next())
				s = s + rs.getObject(2).toString().trim() + "  "
						+ rs.getObject(3).toString().trim() + "<br>";
		} catch (Exception e) {
			s = e.toString();
		}

		return s;
	}

	public static String won() {
		String s = "",q="";
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:dbFox");
			Statement stmt = conn.createStatement();
			//q = "select * from sysfile";
			//ResultSet rs = stmt.executeQuery(q);
			//while (rs.next())
			//	s = s
			//			+ rs.getObject(rs.findColumn("last_wrk_no")).toString()
			//					.trim();
		q="select * from xmlimp";	
		ResultSet rs = stmt.executeQuery(q);
		while (rs.next())
			s=rs.getNString(1);
			
			
			
			//q="insert into xmlimp () values ('123','','',,.T.,.T.,'','','','')";
			//stmt.execute(q);
			
			
			conn.close();
			
		} catch (Exception e) {
			s = e.toString();
		}

		return s;
	}
}