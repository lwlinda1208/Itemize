package db;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

public interface DBConnection {
	
	//This method accepts a user id (String) and the image url of the receipt
	//It returns the receipt in JSONObject format
	public JSONObject getReceipt (String user, String url);
	
	//This method accepts a user id (String) and a map containing all parameters
	//It returns the status of the post action (success/fail)
	public boolean addReceipt (String user, Map<String, String> map);
	
	//This method accepts a user id (String) and a map containing all parameters
	//It returns the status of the update action (success/fail)
	public boolean updateReceipt (String user, Map<String, String> map);
	
	//This method accepts a user id (String) and the image url of the receipt
	//It returns the status of the delete action
	public boolean deleteReceipt (String user, String url);
	
	//This method accepts a user id (String) and returns if the user exists in the database
	public boolean getUser (String user);
	
	//This method accepts an image url (String) and returns if the receipt exists in the database
	public boolean receiptExist (String imageURL);
	
	public void close();
	
}
