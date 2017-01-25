package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Receipt {
	private String merchantName;
	private String merchantAddress;
	private String expenseCategory;
	private final String imageURL;
	private double subtotal;
	private double taxes;
	private double discount;
	private double tips;
	private double grandTotal;
	private Date date;
	
	public Receipt(String merchantName, String merchantAddress, String expenseCategory, String imageURL,
			double subtotal, double taxes, double discount, double tips, double grandTotal, Date date) {
		this.merchantName = merchantName;
		this.merchantAddress = merchantAddress;
		this.expenseCategory = expenseCategory;
		this.imageURL = imageURL;
		this.subtotal = subtotal;
		this.taxes = taxes;
		this.discount = discount;
		this.tips = tips;
		this.grandTotal = grandTotal;
		this.date = date;
	}
	
	
	public static class ReceiptBuilder{
		private String merchantName;
		private String merchantAddress = "";
		private String expenseCategory;
		private final String imageURL;
		private double subtotal = 0;
		private double taxes = 0;
		private double discount = 0;
		private double tips = 0;
		private double grandTotal;
		private Date date;
		
		public ReceiptBuilder(String merchantName, String imageURL, String expenseCategory, double grandTotal, Date date){
			this.merchantName=merchantName;
			this.imageURL=imageURL;
			this.expenseCategory = expenseCategory;
			this.grandTotal=grandTotal;
			this.date=date;
		}
		
		public ReceiptBuilder merchantAddress(String merchantAddress){
			this.merchantAddress=merchantAddress;
			return this;
		}
		
		public ReceiptBuilder subtotal (double subtotal){
			this.subtotal=subtotal;
			return this;
		}
		
		public ReceiptBuilder taxes (double taxes){
			this.taxes=taxes;
			return this;
		}
		
		public ReceiptBuilder discount (double discount){
			this.discount=discount;
			return this;
		}
		
		public ReceiptBuilder tips (double tips){
			this.tips=tips;
			return this;
		}
		
		public Receipt build(){
			return new Receipt(merchantName, merchantAddress, expenseCategory, imageURL,
			subtotal, taxes, discount, tips, grandTotal, date);
		}
	}
	
	public static Receipt buildReceipt(Map<String, String> map){
		String merchant_name = map.get("merchant_name");
		String category = map.get("category");
		String image_url = map.get("image_url");
		double grand_total = Double.parseDouble(map.get("grand_total"));
		DateFormat df = new SimpleDateFormat("MMddyyyy");
		try{
			java.util.Date date1 = df.parse(map.get("date"));
			java.sql.Date date = new Date(date1.getTime());
			ReceiptBuilder rb = new ReceiptBuilder(merchant_name, image_url, category, grand_total, date);
			
			String merchant_address = map.get("merchant_address");
			if(merchant_address!=null) rb.merchantAddress(merchant_address);
			
			String subtotal = map.get("subtotal");
			if(subtotal!=null) rb.subtotal(Double.parseDouble(subtotal));
			
			String tips = map.get("tips");
			if(tips!=null) rb.tips(Double.parseDouble(tips));
			
			String taxes = map.get("taxes");
			if(taxes!=null) rb.taxes(Double.parseDouble(taxes));
			
			String discount = map.get("discount");
			if(discount!=null) rb.discount(Double.parseDouble(discount));
			
			return rb.build();
		}catch (ParseException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject toJSONObject(){
		try {
			JSONObject obj = new JSONObject();
			obj.put("merchant name", this.merchantName);
			obj.put("merchant address", this.merchantAddress);
			obj.put("image url", this.imageURL);
			obj.put("expense category", this.expenseCategory);
			obj.put("grand total", (double)Math.round(this.grandTotal*100d)/100);
			obj.put("subtotal", (double)Math.round(this.subtotal*100d)/100);
			obj.put("tips", (double)Math.round(this.tips*100d)/100);
			obj.put("taxes", (double)Math.round(this.taxes*100d)/100);
			obj.put("discount", (double)Math.round(this.discount*100d)/100);
			obj.put("purchase date", date);
			
			return obj;
		}catch (JSONException e){
			e.printStackTrace();
		}
		return null;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public String getExpenseCategory() {
		return expenseCategory;
	}

	public String getImageURL() {
		return imageURL;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getTaxes() {
		return taxes;
	}

	public double getDiscount() {
		return discount;
	}

	public double getTips() {
		return tips;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public Date getDate() {
		return date;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public void setExpenseCategory(String expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setTips(double tips) {
		this.tips = tips;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public void setPurchaseDate(Date date) {
		this.date = date;
	}
	
}
