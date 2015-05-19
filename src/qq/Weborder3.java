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
public class Weborder3 {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	// @Persistent
	// private User user;
	
	 @Persistent
	 private Text txt;

		@Persistent
		private String impex;

		@Persistent
		private String a;

		@Persistent
		private String b;


	public Weborder3(String a, String b, Text txt
	) {
		this.a=a;
		this.b=b;
		this.txt=txt;
	}
	public Long getId() {
		return id;
	}
	public String get_impex() {
		return impex;
	}
	public String get_a() {
		return a;
	}
	public String get_b() {
		return b;
	}
	public String get_txt() {
		return txt.getValue().toString();
	}
	public void set_impex(String impex) {
		this.impex = impex;
	}


}