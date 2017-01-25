package db;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import model.Receipt;

public class MySQLDBConnection implements DBConnection {

	private Connection conn = null;

	public MySQLDBConnection() {
		this(DBUtil.URL);
	}


	public MySQLDBConnection(String url) {
		try {
			// Forcing the class representing the MySQL driver to load and
			// initialize.
			// The newInstance() call is a work around for some broken Java
			// implementations
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = (Connection) DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) { /* ignored */
			}
		}
	}

	@Override
	public JSONObject getReceipt(String user, String url) {
		// TODO Auto-generated method stub
		try{
			String sql = "SELECT * from receipts where user_id= ? and image_url= ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, user);
			statement.setString(2, url);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				Receipt newReceipt = new Receipt.ReceiptBuilder(rs.getString("merchant_name"), 
						rs.getString("image_url"), rs.getString("category"), 
						rs.getFloat("grand_total"), rs.getDate("date"))
						.merchantAddress(rs.getString("merchant_address"))
						.subtotal(rs.getFloat("subtotal"))
						.tips(rs.getFloat("tips"))
						.discount(rs.getFloat("discount"))
						.taxes(rs.getFloat("discount"))
						.build();
				JSONObject obj = newReceipt.toJSONObject();
				System.out.println(obj);
				return obj;
			}
		}catch (Exception e) { /* report an error */
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean getUser(String user){
		try{
			String sql = "SELECT * from users where user_id = ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, user);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean addReceipt(String user, Map<String, String> map) {
		// TODO Auto-generated method stub
		try{
			String sql = "INSERT IGNORE INTO receipts VALUE (?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			Receipt r = Receipt.buildReceipt(map);
			if(r!=null){
				statement.setString(1, user);
				statement.setString(2, r.getMerchantName());
				statement.setString(3, r.getMerchantAddress());
				statement.setString(4, r.getExpenseCategory());
				statement.setString(5, r.getImageURL());
				statement.setFloat(6, (float) r.getSubtotal());
				statement.setFloat(7, (float) r.getTaxes());
				statement.setFloat(8, (float) r.getDiscount());
				statement.setFloat(9, (float) r.getTips());
				statement.setFloat(10, (float) r.getGrandTotal());
				statement.setDate(11, (Date) r.getDate());
				statement.execute();
				return true;
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean receiptExist(String imageURL){
		try{
			String sql = "SELECT * from receipts where image_url = ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, imageURL);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean updateReceipt(String user, Map<String, String> map) {
		// TODO Auto-generated method stub
		try{
			String image_url = map.get("image_url");
			String sql = "UPDATE receipts SET ";
			ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
			int count=0;
			for(Map.Entry<String, String> entry: map.entrySet()){
				if(entry.getKey()=="user" || entry.getKey()=="image_url")continue;
				else {
					if(count!=0) sql = sql+", ";
					list.add(entry);
					sql=sql+entry.getKey()+" = ?";
					count++;
				}
			}
			sql = sql+" WHERE user_id = ? AND image_url = ?";
			
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			for(int i=0; i<list.size(); i++){
				Map.Entry<String, String> entry = list.get(i);
				if(entry.getKey()=="merchant_address"|| 
						entry.getKey()=="merchant_name" || entry.getKey()=="category"){
					statement.setString(i+1, entry.getValue());
				}else if(entry.getKey()=="subtotal"|| entry.getKey()=="tips" || entry.getKey()=="taxes" 
						|| entry.getKey()=="discount"|| entry.getKey()=="grand_total"){
					statement.setFloat(i+1, Float.parseFloat(entry.getValue()));
				}else if(entry.getKey()=="date"){
					DateFormat df = new SimpleDateFormat("MMddyyyy");
					java.util.Date date1 = df.parse(entry.getValue());
					java.sql.Date date = new Date(date1.getTime());
					statement.setDate(i+1, date);
				}
			}
			statement.setString(list.size()+1, user);
			statement.setString(list.size()+2, image_url);
			statement.execute();
			//System.out.println(statement.toString());
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteReceipt(String user, String url) {
		// TODO Auto-generated method stub
		try{
			String sql = "DELETE FROM receipts WHERE user_id = ? and image_url = ?";
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			statement.setString(1, user);;
			statement.setString(2, url);
			statement.execute();
			return true;
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}

	public void testing(){
		String query = "SHOW TABLES";
		try {
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
