package contacts;
/*********************************************
 * This class serves as an object which will be the most basic unit of
 * a Set of Contacts. 
 * A contact entry serves as collection of name, address, phone number and email.
 * @author JSGREWAL
 * @version 1.00 2018-11-14 The Base Object
 */
public class ContactEntry {
	// Attributes
	String name =" ";
	String address =" ";
	String phone = " ";
	String email =" ";
	/****
	 * ContactEntry Constructor
	 * @param n Name
	 * @param a Address
	 * @param p Phone
	 * @param e E-Mail
	 */
	public ContactEntry(String n, String a, String p, String e) {
		name = n;
		address=a;
		phone = p;
		email =e;
	}
	// Getter and Setters for the ContactEntry Attributes
	public String getName() {
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public String getEmail() {
		return this.email;
	}
	public String getPhone() {
		return phone;
	}
}
