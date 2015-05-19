package qq;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Weborder {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private User user;

	@Persistent
	private Date date;

	@Persistent
	private String customer;
	@Persistent
	private String impex;
	@Persistent
	private String purchase_order_id;
	@Persistent
	private Long shipper_id;
	@Persistent
	private Long consignee_id;
	@Persistent
	private Date shipping_date_time;
	@Persistent
	private Date delivery_date_time;
	@Persistent
	private String description;
	@Persistent
	private String equipment_type;
	@Persistent
	private String shipment_type;
	@Persistent
	private String quantity;
	@Persistent
	private String weight_value;
	@Persistent
	private String weight_unit;
	@Persistent
	private String container_id;
	@Persistent
	private String bill_of_lading;
	@Persistent
	private String special_instructions;

	

	public Weborder(User user, Date date, String customer,
			String purchase_order_id, Long shipper_id, Long consignee_id,
			Date shippingDateTime, Date deliveryDateTime, String description, String equipment_type,
			String shipment_type, String quantity, String weight_value,
			String weight_unit, String container_id, String bill_of_lading,
			String special_instructions) {
		set_impex("q");
		this.user = user;
		this.date = date;
		this.customer=customer;
		this.purchase_order_id = purchase_order_id;
		this.shipper_id = shipper_id;
		this.consignee_id = consignee_id;
		this.shipping_date_time = shippingDateTime;
		this.delivery_date_time = deliveryDateTime;
		this.description = description;
		this.equipment_type = equipment_type;
		this.shipment_type = shipment_type;
		this.quantity = quantity;
		this.weight_value = weight_value;
		this.weight_unit = weight_unit;
		this.container_id = container_id;
		this.bill_of_lading = bill_of_lading;
		this.special_instructions = special_instructions;

	}

	// Accessors for the fields. JDO doesn't use these, but your application
	// does.

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}

	// ... other accessors...

	public String get_customer() {
		return customer;
	}
	
	public String get_impex() {
		return impex;
	}
	
	public void set_impex(String impex) {
		this.impex=impex;
	}

	public String get_purchase_order_id() {
		return purchase_order_id;
	}

	public Long get_shipper() {
		return shipper_id;
	}

	public Long get_consignee() {
		return consignee_id;
	}

	public Date get_shipping_date_time() {
		return shipping_date_time;
	}

	public Date get_delivery_date_time() {
		return delivery_date_time;
	}

	public String get_description() {
		return description;
	}

	public String get_equipment_type() {
		return equipment_type;
	}

	public String get_shipment_type() {
		return shipment_type;
	}

	public String get_quantity() {
		return quantity;
	}

	public String get_weight_value() {
		return weight_value;
	}

	public String get_weight_unit() {
		return weight_unit;
	}

	public String get_container_id() {
		return container_id;
	}

	public String get_bill_of_lading() {
		return bill_of_lading;
	}

	public String get_special_instructions() {
		return special_instructions;
	}

}