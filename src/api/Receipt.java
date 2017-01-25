package api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MySQLDBConnection;


/**
 * Servlet implementation class Receipt
 */
@WebServlet("/receipt")
public class Receipt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Receipt() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			final DBConnection connection = new MySQLDBConnection();
			if (request.getParameterMap().containsKey("user") && 
					request.getParameterMap().containsKey("image_url")) {
				String user = request.getParameter("user");
				String imageURL = (String) request.getParameter("image_url");
				JSONObject receipt = connection.getReceipt(user, imageURL);
				RpcParser.writeOutput(response, receipt);
			}else{
				RpcParser.writeOutput(response, new JSONObject().put("status", "Invalid Parameter"));
			}
		}catch (JSONException e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			final DBConnection connection = new MySQLDBConnection();
			JSONObject input = RpcParser.parseInput(request);
			if (request.getParameterMap().containsKey("user") && 
					request.getParameterMap().containsKey("image_url") && 
					request.getParameterMap().containsKey("merchant_name") && 
					request.getParameterMap().containsKey("category") &&
					request.getParameterMap().containsKey("grand_total") &&
					request.getParameterMap().containsKey("date")) {
					String user = request.getParameter("user");
				boolean userExist = connection.getUser(user);
				if(userExist){
					
					System.out.println(userExist);
					HashMap<String, String> map = getParams(request);
					
					boolean result = connection.addReceipt(user, map);
					if(result){
						RpcParser.writeOutput(response,
								new JSONObject().put("status", "OK"));
					}else{
						RpcParser.writeOutput(response,
								new JSONObject().put("status", "Unsuccessful"));
					}
				}else {
					System.out.println(userExist);
					RpcParser.writeOutput(response,
							new JSONObject().put("status", "User Does Not Exist"));
				}
			} else {
				RpcParser.writeOutput(response,
						new JSONObject().put("status", "Invalid Parameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			final DBConnection connection = new MySQLDBConnection();
			if (request.getParameterMap().containsKey("user") && 
					request.getParameterMap().containsKey("image_url")) {
				String imageURL = request.getParameter("image_url");
				boolean receiptExist = connection.receiptExist(imageURL);
				if(receiptExist){
					String user = (String) request.getParameter("user");

					HashMap<String, String> map=getParams(request);

					boolean result = connection.updateReceipt(user, map);
					if(result){
						RpcParser.writeOutput(response,
								new JSONObject().put("status", "OK"));
					}else{
						RpcParser.writeOutput(response,
								new JSONObject().put("status", "Update Unsucessful"));
					}
				}else{
					RpcParser.writeOutput(response, new JSONObject().put("status", "Receipt Does Not exist"));
				}
			} else {
				RpcParser.writeOutput(response,
						new JSONObject().put("status", "Invalid Parameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			final DBConnection connection = new MySQLDBConnection();
			JSONObject input = RpcParser.parseInput(request);
			if (request.getParameterMap().containsKey("user") && 
					request.getParameterMap().containsKey("image_url")) {
				String user = request.getParameter("user");
				String imageURL = request.getParameter("image_url");

				boolean result = connection.deleteReceipt(user, imageURL);
				if(result){
					RpcParser.writeOutput(response,
							new JSONObject().put("status", "OK"));
				}else{
					RpcParser.writeOutput(response,
							new JSONObject().put("status", "Delete Unsucessful"));
				}
			} else {
				RpcParser.writeOutput(response,
						new JSONObject().put("status", "Invalid Parameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, String> getParams (HttpServletRequest request) throws JSONException{
		HashMap<String, String> map = new HashMap<>();

		if(request.getParameterMap().containsKey("user")){
			String user = (String) request.getParameter("user");
			map.put("user", user);
		}
		
		if(request.getParameterMap().containsKey("image_url")){
			String imageURL = (String) request.getParameter("image_url");
			map.put("image_url", imageURL);
		}

		if(request.getParameterMap().containsKey("category")){
			String category = (String) request.getParameter("category");
			map.put("category", category);
		}

		if(request.getParameterMap().containsKey("merchant_name")){
			String merchantName = (String) request.getParameter("merchant_name");
			map.put("merchant_name", merchantName);
		}

		if(request.getParameterMap().containsKey("grand_total")){
			String grandTotal = (String) request.getParameter("grand_total");
			map.put("grand_total", grandTotal);
		}

		if(request.getParameterMap().containsKey("merchant_address")){
			String merchantAddress = (String) request.getParameter("merchant_address");
			map.put("merchant_address", merchantAddress);
		}

		if(request.getParameterMap().containsKey("subtotal")){
			String subtotal = (String) request.getParameter("subtotal");
			map.put("subtotal", subtotal);
		}

		if(request.getParameterMap().containsKey("taxes")){
			String tax = (String) request.getParameter("taxes");
			map.put("taxes", tax);
		}

		if(request.getParameterMap().containsKey("discount")){
			String discount = (String) request.getParameter("discount");
			map.put("discount", discount);
		}

		if(request.getParameterMap().containsKey("tips")){
			String tips = (String) request.getParameter("tips");
			map.put("tips", tips);
		}

		if(request.getParameterMap().containsKey("date")){
			String date = (String) request.getParameter("date");
			map.put("date", date);
		}
		return map;
	}

}
