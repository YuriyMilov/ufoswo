package qq.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * The async counterpart of <code>Service</code>.
 */
public interface ServiceAsync {
	void login(AsyncCallback<String> callback);
	void get_array(String input, AsyncCallback<String[]> callback);
	void get_list(String input, AsyncCallback<String[]> callback);
	void get_file(String input, AsyncCallback<String> callback);
	void get(String input, AsyncCallback<String> callback);
	void get_user(String input, AsyncCallback<String> callback);
	void get_new(
			String purchase_order_id,
			String ship_id,
			String cons_id,
			Date shipping_date_time,
			Date delivery_date_time,
			String description,
			String equipment_type,
			String shipment_type,
			String quantity,
			String weight_value,
			String weight_unit,
			String container_id,
			String bill_of_lading,
			String special_instructions	
			
			, AsyncCallback<String> callback);
}
