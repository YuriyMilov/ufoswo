package qq;

import java.io.*;
import java.net.URL;

import javax.servlet.*;
import javax.servlet.http.*;



public class get_rate extends HttpServlet { 

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String s = rfu("http://www.bankofcanada.ca/en/markets/csv/exchange_eng.csv");
		s=s.substring(s.indexOf("U.S. Dollar"));
		s=s.substring(0,s.indexOf("\r\n"));
		s=s.substring(s.lastIndexOf(",")+1);
		out.println(s);
		//out.println("1.2323");
		 System.out.println(s + "  U.S. Dollar _ @ _  http://www.bankofcanada.ca/en/markets/csv/exchange_eng.csv");
		out.close();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
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
}
