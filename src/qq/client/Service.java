package qq.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("srv")
public interface Service extends RemoteService {
	String login();
	String[] get_array(String name);

	String[] get_list(String name);

	String get_file(String name);
	String get(String name);

	String get_user(String name);

	String get_new(String purchase_order_id, String ship_id, String cons_id,
			Date shipping_date_time, Date delivery_date_time,
			String description, String equipment_type, String shipment_type,
			String quantity, String weight_value, String weight_unit,
			String container_id, String bill_of_lading,
			String special_instructions);
}
