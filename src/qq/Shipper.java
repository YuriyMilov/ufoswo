package qq;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Shipper {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private User user;

	@Persistent
	private Date date;

	@Persistent
	private String customer_name;
	@Persistent
	private String company_name;
	@Persistent
	private String address1;
	@Persistent
	private String address2;
	@Persistent
	private String city;
	@Persistent
	private String prov_state;
	@Persistent
	private String postal_code;
	@Persistent
	private String country;
	@Persistent
	private String contact;
	@Persistent
	private String phone;

	public Shipper(User user, Date date,
			String customer_name, String company_name, String address1,
			String address2, String city, String prov_state,
			String postal_code, String country, String contact, String phone) {
		this.user = user;
		this.date = date;
		this.customer_name = customer_name;
		this.company_name = company_name;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.prov_state = prov_state;
		this.postal_code = postal_code;
		this.country = country;
		this.contact = contact;
		this.phone = phone;

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


	public String get_customer_name(){return customer_name;}
	public String get_company_name(){return company_name;}
	public String get_address1(){return address1;}
	public String get_address2(){return address2;}
	public String get_city(){return city;}
	public String get_prov_state(){return prov_state;}
	public String get_postal_code(){return postal_code;}
	public String get_country(){return country;}
	public String get_contact(){return contact;}
	public String get_phone(){return phone;}

		
}