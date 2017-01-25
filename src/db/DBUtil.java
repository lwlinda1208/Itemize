package db;

public class DBUtil {
	private static final String HOSTNAME = "db4free.net";
    private static final String PORT_NUM = "3306";// change it to your mysql port number
    public static final String DB_NAME = "receiptcrud";
    private static final String USERNAME = "lwlinda1208";
    private static final String PASSWORD = "itemize";
    public static final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
   			 + "?user=" + USERNAME + "&password=" + PASSWORD + "&autoreconnect=true";
}