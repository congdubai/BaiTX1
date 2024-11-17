package ads.basic;

import java.sql.*;
import java.util.*;

public interface Basic {
	boolean add(PreparedStatement pre);

	boolean edit(PreparedStatement pre);

	boolean del(PreparedStatement pre);

	ArrayList<ResultSet> gets(String mutilSelect);
	ResultSet get(String sql, int value);
	ResultSet get(String sql, String name, String pass);
	
	void releaseConnection();
}
