package model;

public class User {
	final private String user_id;
	private String first_name;
	private String last_name;
	private String address;
	
	private User (String user_id, String firstName, String lastName, String address){
		this.user_id=user_id;
		this.first_name=firstName;
		this.last_name=lastName;
		this.address=address;
	}
	
	
	public static class UserBuilder{
		private final String user_id;
		private String first_name;
		private String last_name = "";
		private String address = "";
		public UserBuilder(String user_id, String first_name){
			this.user_id=user_id;
			this.first_name=first_name;
		}
		public UserBuilder lastName (String last_name){
			this.last_name=last_name;
			return this;
		}
		public UserBuilder address(String address){
			this.address=address;
			return this;
		}
		public User build(){
			return new User(user_id, first_name, last_name, address);
		}
		
	}

	public String getUserID() {
		return user_id;
	}

	public String getFirstName() {
		return first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public String getAddress() {
		return address;
	}
	
	
}
