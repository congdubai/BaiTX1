package ads;

import java.sql.*;

public interface ConnectionPool {
	Connection getConnection(String objectName) throws SQLException;
	void releaseConnection(Connection con, String objectName) throws SQLException;
}
