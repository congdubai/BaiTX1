package ads;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPoolImpl implements ConnectionPool {
	private String driver;
	private String username;
	private String password;
	private String url;
	private Stack<Connection> pool;

	// singleton design
	private static ConnectionPool cp = null;

	private ConnectionPoolImpl() {
		// Xác đinh trình điều khiển
		this.driver = "com.mysql.cj.jdbc.Driver";

		// Xác định đường dẫn chạy MySQL
		this.url = "jdbc:mysql://localhost:3306/clothes_data?allowMultiQueries=true";

		// Xác định tài khoản làm việc
		this.username = "Cong";
		this.password = "Cong@27032003";

		// Nạp trình điều khiển
		try {
			Class.forName(this.driver);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// Khởi tạo bộ lưu trữ đối tượng kết nối
		this.pool = new Stack<>();
	}

	@Override
	public Connection getConnection(String objectName) throws SQLException {
		// TODO Auto-generated method stub
		if (this.pool.isEmpty()) {
			// Khởi tạo kết nối mới
			System.out.println(objectName + "have created a new Connection");
			return DriverManager.getConnection(this.url, this.username, this.password);
		}
		// Lấy kết nối được lưu trữ
		System.out.println(objectName + "have popped the Connection");
		return this.pool.pop();
	}

	@Override
	public void releaseConnection(Connection con, String objectName) throws SQLException {
		// TODO Auto-generated method stub
		// Thu hồi lại kết nối
		System.out.println(objectName + "have pushed the Connection");
		this.pool.push(con);
	}

	public static ConnectionPool getInstance() {
		if (cp == null) {
			synchronized (ConnectionPoolImpl.class) {
				if (cp == null) {
					cp = new ConnectionPoolImpl();
				}
			}
		}

		return cp;
	}

}