package db;

import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

public class DBTesting {
	public static void main(String[] args) {
		MySQLDBConnection connection = new MySQLDBConnection();
		HashMap<String, String> map = new HashMap<>();
		map.put("user", "1");
		map.put("merchant_name", "testing");
		map.put("category", "just testing");
		map.put("image_url", "http://pleaseworks.com");
		map.put("subtotal", "1");
		map.put("taxes", "1");
		map.put("grand_total", "2");
		map.put("date", "01012017");
		//boolean r = connection.addReceipt("1", map);
		
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("subtotal", "0.7");
		map2.put("tips", "0.3");
		map2.put("image_url", "http://pleaseworks.com");
		map2.put("user", "1");
		//connection.updateReceipt("1", map2);
		connection.getReceipt("2", "http://pleaseworks.com");
	}
}
