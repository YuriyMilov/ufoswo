package qq.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import qq.Consignee;
import qq.PMF;
import qq.Shipper;
import qq.Weborder;
import qq.shta;
import qq.client.Service;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
// @SuppressWarnings("serial")
public class ServiceImpl extends RemoteServiceServlet implements Service {

	public static HttpSession sess = null;
	public String login() {
		HttpServletResponse resp = getThreadLocalResponse();
		UserService userService = UserServiceFactory.getUserService();
			try {
				resp.sendRedirect(userService.createLoginURL("/"));
			} catch (IOException e) {
				return e.toString();
			}
			return "";
	}
	 
	
	public String get_user(String input) {
		HttpServletRequest req = this.getThreadLocalRequest();
		String s = "";
		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			if (user != null) {
				s = user.getEmail();
			} else {
				s = "<!--ymqq--><table width='100%' height='100%'><tr><td align=center>" +
						"<a href=\""+ userService.createLoginURL("/")+ "\">Enter</a>" +
						"</td></tr></table>";
				//<tr><td align=center>Test account: <br/><i>Email:</i>&nbsp;&nbsp;<b>ufos@quicklydone.com</b><br/><i>Password:</i>&nbsp;&nbsp;<b>weborder</b></td></tr>
			}
		} catch (Exception e) {
			s = e.toString();
		}

		return s;
	}

	public String get_table(String table) {

		String s = "", sa = "";
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:odbc:dbFox");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM \"" + table
					+ ".dbf\"");
			while (rs.next())
				s = s + rs.getObject(2).toString().trim() + "  "
						+ rs.getObject(3).toString().trim() + "<br>";
			sa = s;

		} catch (Exception e) {
			sa = e.toString();
		}

		return s;
	}

	public String get_new(

	String purchase_order_id, String ship_id, String cons_id,
			Date shipping_date_time, Date delivery_date_time,
			String description, String equipment_type, String shipment_type,
			String quantity, String weight_value, String weight_unit,
			String container_id, String bill_of_lading,
			String special_instructions

	) {
		String s = "", sm = "";
		try {
			// HttpServletRequest req = getThreadLocalRequest();

			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			Date date = new Date();
			String customer = user.getNickname();
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Long l_ship;
			try {
				l_ship = Long.parseLong(ship_id.trim());

			} catch (NumberFormatException nfe) {
				l_ship = null;
			}
			Long l_cons;
			try {
				l_cons = Long.parseLong(cons_id.trim());

			} catch (NumberFormatException nfe) {
				l_cons = null;
			}

			Weborder ns = new Weborder(user, date, customer, purchase_order_id,
					l_ship, l_cons, shipping_date_time, delivery_date_time,
					description, equipment_type, shipment_type, quantity,
					weight_value, weight_unit, container_id, bill_of_lading,
					special_instructions);
			pm.makePersistent(ns);
			s = "<br>_____________<br>" + date.toString() + "<br>Customer: "
					+ user.getNickname() + "<br>Purchase Order ID: "
					+ purchase_order_id + " <br>Shipper: "

					+ det_ship(l_ship)

					+ " <br>Consignee: "

					+ det_cons(l_cons)

					+ " <br>Shipping Date/Time: " + shipping_date_time
					+ " <br>Delivery Date/Time: " + delivery_date_time
					+ " <br>Order Description: " + description
					+ " <br>Equipment Type: " + equipment_type
					+ " <br>Shipment Type: " + shipment_type
					+ " <br>Quantity: " + quantity + " <br>Weight: "
					+ weight_value + " " + weight_unit + " <br>Container ID: "
					+ container_id + " <br>Bill of Lading: " + bill_of_lading
					+ " <br>Special Instructions: " + special_instructions;

			sm = shta.send_mail("weborder@quicklydone.com", "new web order", s
					.replace("<br>", "\r\n"));
			// sm=rfu("http://wo.quicklydone.com/servlet/sq.run");
			// sm=rfu("http://wo.quicklydone.com:9090/q/q");
		} catch (Exception e) {
			sm = e.toString();
		}
		if (sm.indexOf("OK")>-1)
			sm="<font color=red>WO to be imported</font>";
		return sm; 
	}

	public String det_ship(Long lid) {
		String s = "";
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String query = "select from " + Shipper.class.getName()
					+ " where id == " + lid;
			List<Shipper> results = (List<Shipper>) pm.newQuery(query)
					.execute();

			Shipper sh = results.get(0);
			s = sh.get_company_name() + " " + sh.get_address1() + " "
					+ sh.get_city() + " " + sh.get_prov_state() + " "
					+ sh.get_country() + " " + sh.get_postal_code()
					+ "<br>Conact: " + sh.get_contact() + " " + sh.get_phone();

		} catch (Exception e) {
			s = e.toString();
		}

		return s.replaceAll("\\b\\s{2,}\\b", " ");
	}

	public String det_cons(Long lid) {
		String s = "";
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String query = "select from " + Consignee.class.getName()
					+ " where id == " + lid;
			List<Consignee> results = (List<Consignee>) pm.newQuery(query)
					.execute();

			Consignee sh = results.get(0);
			s = sh.get_company_name() + " " + sh.get_address1() + " "
					+ sh.get_city() + " " + sh.get_prov_state() + " "
					+ sh.get_country() + " " + sh.get_postal_code()
					+ "<br>Conact: " + sh.get_contact() + " " + sh.get_phone();

		} catch (Exception e) {
			s = e.toString();
		}

		return s.replaceAll("\\b\\s{2,}\\b", " ");
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

	public String get(String s) {
		HttpServletRequest req = getThreadLocalRequest();
		String scn = "";
		try {

			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			if (user != null) {
				scn = req.getUserPrincipal().getName();

				// s = shta.rff("new_shipper_templ.htm");
				s = shta.rff(s);
				s = s.replaceAll("<!- customer_name ->", scn);
			} else {
				s = "<a href=\""
						+ userService.createLoginURL("/")
						+ "\">Login</a> <br/>   Test account: \"guest\", password: \"123456\"";
			}
		} catch (Exception e) {
			s = e.toString();
		}

		return s;
	}

	public String[] get_list(String s) {
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			if (s.indexOf("Shipper") > -1) {
				String query = "select from " + Shipper.class.getName()
						+ " order by date";
				List<Shipper> results = (List<Shipper>) pm.newQuery(query)
						.execute();
				String ss[] = new String[2 * results.size()];
				for (int i = 0; i < results.size(); i++) {
					ss[2 * i] = results.get(i).get_company_name().toString();
					ss[2 * i + 1] = results.get(i).getId().toString();

				}
				return ss;
			}
			if (s.indexOf("Consignee") > -1) {
				String query = "select from " + Consignee.class.getName()
						+ " order by date";
				List<Consignee> results = (List<Consignee>) pm.newQuery(query)
						.execute();
				String ss[] = new String[2 * results.size()];
				for (int i = 0; i < results.size(); i++) {
					ss[2 * i] = results.get(i).get_company_name().toString();
					ss[2 * i + 1] = results.get(i).getId().toString();

				}
				return ss;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public String[] get_array(String s) {
		String equipment_type[] = { "VAN", "40' HIGH CUBE", "48' Reefer",
				"48' Trailer", "5 Ton Reefer", "5 Ton Truck", "53' Dry Van",
				"53' Reefer", "Flat Bed 2 Axle", "Flat Bed 3 Axle",
				"Flat Bed 5 Axle" };

		String shipment_type[] = { "Skids          ", "1/2 TL         ",
				"1/4 TL         ", "20' Container  ", "20' FR         ",
				"20' HC OT      ", "20' HS         ", "20' High Cube  ",
				"20' OT         ", "20' REF        ", "3/4 TL         ",
				"40' Container  ", "40' FR         ", "40' High Cube  ",
				"40' OT         ", "40' REF        ", "45' CONTAINER  ",
				"45' OT         ", "45' REF        ", "53' REF        ",
				"53' TRAILER    ", "B TRAIN        ", "BAGS           ",
				"Boxes          ", "CBM            ", "CONTAINER      ",
				"Cartons        ", "Crate          ", "Crates         ",
				"Expedite       ", "FEET           ", "FTL            ",
				"Gaylord        ", "Kilogram       ", "LBS            ",
				"MILES          ", "Piece          ", "Pieces         ",
				"Pounds         ", "Skid           " };

		String ss[] = { "ss", "ss" };
		if (s.equals("et"))
			return equipment_type;
		else if (s.equals("st"))
			return shipment_type;
		else
			return ss;
	}


	public String get_file(String s) {
		return shta.rff(s);
	}
}
