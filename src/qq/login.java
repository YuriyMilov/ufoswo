package qq;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class login extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PrintWriter out=resp.getWriter();		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user==null)
		resp.sendRedirect(userService.createLoginURL("/"));
		else		
		out.println(user.getNickname());
	}
}