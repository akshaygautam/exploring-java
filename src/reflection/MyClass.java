package reflection;

import annotations.MyAnno1;

@MyAnno1(manyThings = { "thing1", "thing2" }, name = "MyAnno1", somthing = "On MyClass")
public class MyClass {
	private String name;
	private String email;
	public String address;

	
	public MyClass(String name, String email, String address) {
		super();
		this.name = name;
		this.email = email;
		this.address = address;
	}
	public MyClass() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return getToStringPrivate();
	}

	private String getToStringPrivate() {
		return "MyClass [name=" + name + ", email=" + email + ", address=" + address + "]";
	}
	
	
}
