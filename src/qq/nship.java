package qq;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.jdo.PersistenceManager;

import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.List;

public class nship extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {

		PrintWriter out = resp.getWriter();		
		String s = "",scn="";

		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
				String thisURL = req.getRequestURI();
				if ( user != null) {
					scn=req.getUserPrincipal().getName();
					
					s=shta.rff("new_shipper_templ.htm");
					
					s=s.replaceAll("<!- customer_name ->", scn );
					
				} else {
					s="<a href=\""
									+ userService.createLoginURL(thisURL)
									+ "\">Log in</a> <br/>You have to login. Or try a guest account(\"guest\", \"123456\")"
									+ shta.rff("1.txt");
				}
				out.println(s);
	}
	
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		PrintWriter out = resp.getWriter();		
		String s = "", ss="", sq = "";
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		String customer_name=user.getNickname();
		String company_name=req.getParameter("TextBox1");
		String address1=req.getParameter("TextBox2");
		String address2=req.getParameter("TextBox3");
		String city=req.getParameter("TextBox4");
		String prov_state=req.getParameter("DropDownList2");
		String postal_code=req.getParameter("TextBox6");
		String country=req.getParameter("DropDownList1");
		String contact=req.getParameter("TextBox8");
		String phone=req.getParameter("TextBox9");
		

		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Shipper ns = new Shipper(user, new Date(),
				customer_name, company_name, 
				address1, address2, 
				city, prov_state, 
				postal_code, country, 
				contact, phone);
		
		pm.makePersistent(ns);

/*		//sq = "select from " + Shipper.class.getName() + " where lastName == 'Smith'";
		sq = "select from " + Shipper.class.getName();
		
		List<Shipper> slist = (List<Shipper>) pm.newQuery(sq).execute();
		
		// s=shta.rff("1.txt");
		int i=0;
		if(!slist.isEmpty())
		i=slist.size();
		while(i-- >0)
			ss = ss+slist.get(i).get_country()+"<br>";
		
		*/

		
	      s = "Customer: " + customer_name + 
	      "\r\nCompany name: " +  company_name + 
	      "\r\nAddress: " + address1 + " " + address2 + 
	      "\r\nCity: " + city + 
	      "\r\nProv/State: " + 	prov_state + 
	      "\r\nPostal code: " + postal_code + 
	      "\r\nCountry: " + country + 
	      "\r\nContact: " + contact + 
	      "\r\nPhone: " + phone;
	      	
	      
	     s=shta.send_mail("weborder@quicklydone.com","new shipper",s);
//	 	out.println(s+ shta.rff("1.txt"));
	     //out.println(s+ shta.rff("2.txt"));
resp.sendRedirect("/");
	    
	}

}