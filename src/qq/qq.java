package qq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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

public class qq extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// UserService userService = UserServiceFactory.getUserService();
		// User user = userService.getCurrentUser();

		PrintWriter out = resp.getWriter();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String s = req.getHeader("Authorization");

		// System.out.println(ss+" : "+s);
		if (s != null) {
			//if (s.equals("Basic eG1sYXhzdW46YXhzdW4zMjE="))  
				
				{
					//s = "no orders";

				String q = "select from qq.Weborder" // +
				// Weborder.class.getName()
					//+ " where customer == 'test' && impex==null  order by date";
					//
					+ " where impex=='q' && customer == '"+s+"'";//"+s+"
							
				
				
				// String q = "select from " + Weborder.class.getName() +
				// " order by date";

				List<Weborder> r = (List<Weborder>) pm.newQuery(q).execute();
				// for (int i = 0; i < r.size(); i++)
				// s = s + r.get(i).getUser().getNickname() + " <br>\r\n";

				if (r.size() > 0) {

					s = shta.rff("template.xml");
					s = s.replace("_DateTime_", new Date().toString());
					s = s.replace("_UniqueRefNb_", String.valueOf((new Date()
							.getTime())));
					s = s.replace("_CustomerDescr_", "Coffee Inc.");
					//
					s = s.replace("_PurchaseOrderNb_", r.get(0)
							.get_purchase_order_id());
					if (r.get(0).get_shipping_date_time() != null)
						s = s.replace("_RequestedShippingDate_",
								new SimpleDateFormat("yyyy-MM-dd").format(r
										.get(0).get_shipping_date_time()));
					else
						s = s.replace("_RequestedShippingDate_", "");
					if (r.get(0).get_shipping_date_time() != null)
						s = s.replace("_RequestedShippingTime_",
								new SimpleDateFormat("hh:mm").format(r.get(0)
										.get_shipping_date_time()));
					else
						s = s.replace("_RequestedShippingTime_", "");
					if (r.get(0).get_delivery_date_time() != null)
						s = s.replace("_RequestedDeliveryDate_",
								new SimpleDateFormat("yyyy-MM-dd").format(r
										.get(0).get_delivery_date_time()));
					else
						s = s.replace("_RequestedDeliveryDate_", "");
					if (r.get(0).get_delivery_date_time() != null)
						s = s.replace("_RequestedDeliveryTime_",
								new SimpleDateFormat("hh:mm").format(r.get(0)
										.get_delivery_date_time()));
					else
						s = s.replace("_RequestedDeliveryTime_", "");

					s = s.replace("_Equipment_", r.get(0).get_equipment_type());
					s = s.replace("_BOL_", r.get(0).get_bill_of_lading());
					s = s
							.replace("_HeaderComment_", r.get(0)
									.get_description());
					s = s.replace("_Product Description_", r.get(0)
							.get_special_instructions());
					s = s.replace("_TotalQtyValue_", r.get(0).get_quantity());
					s = s.replace("_TotalQtyUom_", r.get(0).get_shipment_type());

					s = s.replace("_TotalWeight_", r.get(0).get_weight_value());
					s = s
							.replace("_ItemWeightUom_", r.get(0)
									.get_weight_unit());
					s = s.replace("_Special_instructions_", r.get(0)
							.get_special_instructions());
					s = s.replace("_ContainerId_", r.get(0).get_container_id());

					// /////////// Shipper //////////////////

					// /////////// Shipper Consignee //////////////////

					q = "select from qq.Consignee"
							+ " where "//customer_name == 'test' && "
							+ "id=="
							+ r.get(0).get_consignee();

					List<Consignee> recons = (List<Consignee>) pm.newQuery(q).execute();
					if (recons.size() > 0) {
						s = s.replace("_cName_", recons.get(0).get_company_name());
						s = s.replace("_cAddress1_", recons.get(0).get_address1());
						s = s.replace("_cAddress2_", recons.get(0).get_address2());
						s = s.replace("_cStateProv_", recons.get(0).get_prov_state());
						s = s.replace("_cZipPc_", recons.get(0).get_postal_code());
						s = s.replace("_cCountry_", recons.get(0).get_country());
						s = s.replace("_cPhone_", recons.get(0).get_phone());
						s = s.replace("_cContactName_", recons.get(0).get_contact());
					}

					q = "select from qq.Shipper"
							+ " where "//customer_name == 'test' && " 
							+ "id=="
							+ r.get(0).get_shipper();

					List<Shipper> reship = (List<Shipper>) pm.newQuery(q).execute();
					if (reship.size() > 0) {
						s = s.replace("_sName_", reship.get(0).get_company_name());
						s = s.replace("_sAddress1_", reship.get(0).get_address1());
						s = s.replace("_sAddress2_", reship.get(0).get_address2());
						s = s.replace("_sStateProv_", reship.get(0).get_prov_state());
						s = s.replace("_sZipPc_", reship.get(0).get_postal_code());
						s = s.replace("_sCountry_", reship.get(0).get_country());
						s = s.replace("_sPhone_", reship.get(0).get_phone());
						s = s.replace("_sContactName_", reship.get(0).get_contact());
					}
					r.get(0).set_impex(new Date().toString());
				}
				
			}
		} //else
		//	s = "not authorized";
		if(s.indexOf("<")< 0)
			s = "no orders";
		pm.close();
		out.println(s);

	}

	public void set_impex(User user, String impex) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Weborder w = pm.getObjectById(Weborder.class, user.getEmail());
		w.set_impex(impex);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter out = resp.getWriter();
		String s = "?";
		
		

/*
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String q = "select from wwo.Weborder" + " where customer == 'test'"
		// + " && impex==null "
				+ " order by date";

		List<Weborder> r = (List<Weborder>) pm.newQuery(q).execute();

		if (r.size() > 0) {

			s = shta.rff("template.xml");
			s = s.replace("_DateTime_", new Date().toString());
			s = s.replace("_UniqueRefNb_", String
					.valueOf((new Date().getTime())));
			s = s.replace("_CustomerDescr_", "Coffee Inc.");
			//
			s = s
					.replace("_PurchaseOrderNb_", r.get(0)
							.get_purchase_order_id());
			if (r.get(0).get_shipping_date_time() != null)
				s = s
						.replace("_RequestedShippingDate_",
								new SimpleDateFormat("yyyy-MM-dd").format(r
										.get(0).get_shipping_date_time()));
			else
				s = s.replace("_RequestedShippingDate_", "");
			if (r.get(0).get_shipping_date_time() != null)
				s = s.replace("_RequestedShippingTime_", new SimpleDateFormat(
						"hh:mm").format(r.get(0).get_shipping_date_time()));
			else
				s = s.replace("_RequestedShippingTime_", "");
			if (r.get(0).get_delivery_date_time() != null)
				s = s
						.replace("_RequestedDeliveryDate_",
								new SimpleDateFormat("yyyy-MM-dd").format(r
										.get(0).get_delivery_date_time()));
			else
				s = s.replace("_RequestedDeliveryDate_", "");
			if (r.get(0).get_delivery_date_time() != null)
				s = s.replace("_RequestedDeliveryTime_", new SimpleDateFormat(
						"hh:mm").format(r.get(0).get_delivery_date_time()));
			else
				s = s.replace("_RequestedDeliveryTime_", "");

			s = s.replace("_Equipment_", r.get(0).get_equipment_type());
			s = s.replace("_BOL_", r.get(0).get_bill_of_lading());
			s = s.replace("_HeaderComment_", r.get(0).get_description());
			s = s.replace("_Product Description_", r.get(0)
					.get_special_instructions());
			s = s.replace("_TotalQtyValue_", r.get(0).get_quantity());

			s = s.replace("_TotalWeight_", r.get(0).get_weight_value());
			s = s.replace("_ItemWeightUom_", r.get(0).get_weight_unit());
			s = s.replace("_Special_instructions_", r.get(0)
					.get_special_instructions());
			s = s.replace("_ContainerId_", r.get(0).get_container_id());

			// /////////// Shipper Consignee //////////////////

			q = "select from wwo.Consignee"
					+ " where customer_name == 'test' "
					+ "&& id=="
					+ r.get(0).get_consignee() + "";

			List<Consignee> recons = (List<Consignee>) pm.newQuery(q).execute();
			if (recons.size() > 0) {
				s = s.replace("_cName_", recons.get(0).get_company_name());
				s = s.replace("_cAddress1_", recons.get(0).get_address1());
				s = s.replace("_cAddress2_", recons.get(0).get_address2());
				s = s.replace("_cStateProv_", recons.get(0).get_prov_state());
				s = s.replace("_cZipPc_", recons.get(0).get_postal_code());
				s = s.replace("_cCountry_", recons.get(0).get_country());
				s = s.replace("_cPhone_", recons.get(0).get_phone());
				s = s.replace("_cContactName_", recons.get(0).get_contact());
			}

			q = "select from wwo.Shipper"
					+ " where customer_name == 'test' && id=="
					+ r.get(0).get_shipper() + "";

			List<Shipper> reship = (List<Shipper>) pm.newQuery(q).execute();
			if (reship.size() > 0) {
				s = s.replace("_sName_", reship.get(0).get_company_name());
				s = s.replace("_sAddress1_", reship.get(0).get_address1());
				s = s.replace("_sAddress2_", reship.get(0).get_address2());
				s = s.replace("_sStateProv_", reship.get(0).get_prov_state());
				s = s.replace("_sZipPc_", reship.get(0).get_postal_code());
				s = s.replace("_sCountry_", reship.get(0).get_country());
				s = s.replace("_sPhone_", reship.get(0).get_phone());
				s = s.replace("_sContactName_", reship.get(0).get_contact());
			}
		}
		pm.close();
*/		
		
		out.println(s);

	}

}