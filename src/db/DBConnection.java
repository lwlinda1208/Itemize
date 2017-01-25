package db;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

public interface DBConnection {

	public JSONObject getReceipt (String user, String url);
	
	public boolean addReceipt (String user, Map<String, String> map);
	
	public boolean updateReceipt (String user, Map<String, String> map);
	
	public boolean deleteReceipt (String user, String url);
	
	public boolean getUser (String user);
	
	public boolean receiptExist (String imageURL);
	
	public void close();
	
}
