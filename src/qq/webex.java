package qq;

import java.io.IOException;
import java.io.PrintWriter;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Text;


public class webex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		String s = "ok";
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Weborder3 ns = new Weborder3(req.getParameter("a"),req.getParameter("b"),new Text(req.getParameter("c")));
			ns.set_impex("q");
			pm.makePersistent(ns);
		} catch (Exception ee) {
			s = ee.toString();
		}
		out.println(s);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
doPost(req,resp);
	}


}