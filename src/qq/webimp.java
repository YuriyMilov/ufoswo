package qq;

import java.io.IOException;
import java.io.PrintWriter;
import javax.jdo.PersistenceManager;
import javax.persistence.Id;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;

import java.util.Date;
import java.util.List;

public class webimp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		String s = "no orders";
		try {
			s = "select from " + Weborder3.class.getName() + " where impex == 'q' && b == '"+req.getParameter("b")+"'";
			PersistenceManager pm = PMF.get().getPersistenceManager();
			List<Weborder3> r = (List<Weborder3>) pm.newQuery(s).execute();
			if(!r.isEmpty())
			{
				Weborder3 ord=r.get(0);
				s = ord.get_txt();
				ord.set_impex(new Date().toString());
			}
			else
				s = "no orders";
			
			pm.close();
			
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