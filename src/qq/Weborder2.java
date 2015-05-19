package qq;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Weborder2 {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	// @Persistent
	// private User user;
	
	 @Persistent
	 private Text txt;

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

	private String ShipperCompanyName;
	private String ShipperCompanyAddress1;
	private String ShipperCompanyCity;
	private String ShipperCompanyStateProv;
	private String ShipperCompanyZipPc;
	private String ShipperCompanyCountry;
	private String ShipperContactName;
	private String ShipperContactPhone;

	@Persistent
	private Long consignee_id;

	private String ConsigneeCompanyName;
	private String ConsigneeCompanyAddress1;
	private String ConsigneeCompanyCity;
	private String ConsigneeCompanyStateProv;
	private String ConsigneeCompanyZipPc;
	private String ConsigneeCompanyCountry;
	private String ConsigneeContactName;
	private String ConsigneeContactPhone;

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

	public Weborder2(Date date, String customer, String purchase_order_id,
			Long shipper_id, Long consignee_id, Date shippingDateTime,
			Date deliveryDateTime, String description, String equipment_type,
			String shipment_type, String quantity, String weight_value,
			String weight_unit, String container_id, String bill_of_lading,
			String special_instructions, String ShipperCompanyName,
			String ShipperCompanyAddress1, String ShipperCompanyCity,
			String ShipperCompanyStateProv, String ShipperCompanyZipPc,
			String ShipperCompanyCountry, String ShipperContactName,
			String ShipperContactPhone, String ConsigneeCompanyName,
			String ConsigneeCompanyAddress1, String ConsigneeCompanyCity,
			String ConsigneeCompanyStateProv, String ConsigneeCompanyZipPc,
			String ConsigneeCompanyCountry, String ConsigneeContactName,
			String ConsigneeContactPhone

	) {
		set_impex("q");
		// this.user = user;
		this.date = date;
		this.customer = customer;
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

		this.ShipperCompanyName = ShipperCompanyName;
		this.ShipperCompanyAddress1 = ShipperCompanyAddress1;
		this.ShipperCompanyCity = ShipperCompanyCity;
		this.ShipperCompanyStateProv = ShipperCompanyStateProv;
		this.ShipperCompanyZipPc = ShipperCompanyZipPc;
		this.ShipperCompanyCountry = ShipperCompanyCountry;
		this.ShipperContactName = ShipperContactName;
		this.ShipperContactPhone = ShipperContactPhone;
		this.ConsigneeCompanyName = ConsigneeCompanyName;
		this.ConsigneeCompanyAddress1 = ConsigneeCompanyAddress1;
		this.ConsigneeCompanyCity = ConsigneeCompanyCity;
		this.ConsigneeCompanyStateProv = ConsigneeCompanyStateProv;
		this.ConsigneeCompanyZipPc = ConsigneeCompanyZipPc;
		this.ConsigneeCompanyCountry = ConsigneeCompanyCountry;
		this.ConsigneeContactName = ConsigneeContactName;
		this.ConsigneeContactPhone = ConsigneeContactPhone;

	}

	// Accessors for the fields. JDO doesn't use these, but your application
	// does.

	public Long getId() {
		return id;
	}

	// public User getUser() {
	// return user;
	// }

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
		this.impex = impex;
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

	public String get_ShipperCompanyName() {
		return ShipperCompanyName;
	}

	public String get_ShipperCompanyAddress1() {
		return ShipperCompanyAddress1;
	}

	public String get_ShipperCompanyCity() {
		return ShipperCompanyCity;
	}

	public String get_ShipperCompanyStateProv() {
		return ShipperCompanyStateProv;
	}

	public String get_ShipperCompanyZipPc() {
		return ShipperCompanyZipPc;
	}

	public String get_ShipperCompanyCountry() {
		return ShipperCompanyCountry;
	}

	public String get_ShipperContactName() {
		return ShipperContactName;
	}

	public String get_ShipperContactPhone() {
		return ShipperContactPhone;
	}

	public String get_ConsigneeCompanyName() {
		return ConsigneeCompanyName;
	}

	public String get_ConsigneeCompanyAddress1() {
		return ConsigneeCompanyAddress1;
	}

	public String get_ConsigneeCompanyCity() {
		return ConsigneeCompanyCity;
	}

	public String get_ConsigneeCompanyStateProv() {
		return ConsigneeCompanyStateProv;
	}

	public String get_ConsigneeCompanyZipPc() {
		return ConsigneeCompanyZipPc;
	}

	public String get_ConsigneeCompanyCountry() {
		return ConsigneeCompanyCountry;
	}

	public String get_ConsigneeContactName() {
		return ConsigneeContactName;
	}

	public String get_ConsigneeContactPhone() {
		return ConsigneeContactPhone;
	}

}