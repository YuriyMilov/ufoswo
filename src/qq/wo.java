package qq;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class wo extends HttpServlet {

	private static final long serialVersionUID = 1L;
	String s = "", ss = "", sa = "";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
PrintWriter out=resp.getWriter();
		UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();
		String thisURL = req.getRequestURI();
		if ( user != null) {
			s=req.getUserPrincipal().getName()
							+ " | <a href=\""
							+ userService.createLogoutURL(thisURL)
							+ "\">Log out</a><br/>";
		} else {
			s="<a href=\""
							+ userService.createLoginURL(thisURL)
							+ "\">Log in</a> <br/>   Test account: \"guest\", password: \"123456\"";
		}
		s=s+shta.rff("1.txt");
		out.println(s);
	}

}