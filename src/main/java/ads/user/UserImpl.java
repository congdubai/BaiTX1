package ads.user;

import java.sql.*;
import java.time.Instant;
import java.util.*;

import org.apache.catalina.startup.UserConfig;

import ads.objects.*;
import ads.basic.*; 

public class UserImpl extends BasicImpl implements User {
		
	public UserImpl() {
		super("User");
	}
	
	//Kiểm tra user tồn tại 
	private boolean isExistting(UserObject item) {
		boolean flag = false;
		String sql = "SELECT user_id FROM tbluser WHERE user_email=?";
		try (PreparedStatement pre = this.con.prepareStatement(sql)) {
			pre.setString(1, item.getUser_email());

			try (ResultSet rs = pre.executeQuery()) {
				if (rs.next()) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Xử lý lỗi tại đây nếu cần
		}
		return flag;
	}
	
	@Override
	public boolean addUser(UserObject item) {
		// TODO Auto-generated method stub
		if (this.isExistting(item)) {
			return false;
		}
		Instant curDate = Instant.now();
		String date = curDate.toString();
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbluser(");
		sql.append("user_email, user_pass, user_avatar, user_fullname, user_address, user_birthday, ");
		sql.append("user_phone, user_role_id, user_gender, user_create_at) ");
		sql.append("VALUES(?, md5(?), ?, ?, ?, ?, ?, ?, ?, ?)");
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getUser_email());
			pre.setString(2, item.getUser_pass());
			pre.setString(3, item.getUser_avatar());
			pre.setString(4, item.getUser_fullname());
			pre.setString(5, item.getUser_address());
			pre.setString(6, item.getUser_birthday());
			pre.setString(7, item.getUser_phone());
			pre.setString(8, item.getUser_role_id());
			pre.setString(9, item.getUser_gender());
			pre.setString(10, date);
			
			return this.exe(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	//check email tồn tại chưa
	public boolean isUserEmail(UserObject item) {
		String sql = "SELECT COUNT(*) FROM tbluser WHERE user_email = ? AND user_id != ?";
		try {
			PreparedStatement pre = this.con.prepareStatement(sql);
			pre.setString(1, item.getUser_email());
			pre.setInt(2, item.getUser_id());

			ResultSet rs = pre.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	
	@Override
	public boolean editUser(UserObject item) {
		// TODO Auto-generated method stub
		if (isUserEmail(item)) {
			return false;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbluser SET ");
		sql.append("user_fullname= ?, user_avatar = ?, user_address= ?, user_birthday = ?, ");
		sql.append("user_phone = ?, user_role_id = ?, user_gender=? ");
		sql.append("WHERE user_id = ?;");

		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, item.getUser_fullname());
			pre.setString(2, item.getUser_avatar());
			pre.setString(3, item.getUser_address());
			pre.setString(4, item.getUser_birthday());
			pre.setString(5, item.getUser_phone());
			pre.setString(6, item.getUser_role_id());
			pre.setString(7, item.getUser_gender());
			pre.setInt(8, item.getUser_id());
			return this.exe(pre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delUser(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM tbluser WHERE  user_id = ?";
		PreparedStatement pre;
		try {
			pre = this.con.prepareStatement(sql);
			pre.setInt(1, id);
			return this.exe(pre);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public UserResponse getUserByName(String txt, int size, int page) {
	    ArrayList<UserObject> list = new ArrayList<>();
	    StringBuilder sql = new StringBuilder();
	    String countSql = "SELECT COUNT(user_id) AS total FROM tbluser;";
	    int totalUsers = 0;
	    if (txt != null) {
	        sql.append("SELECT * FROM tbluser ");
	        sql.append("WHERE user_fullname LIKE ? ");
	        sql.append("LIMIT ? OFFSET ?;");
	        
	        try (PreparedStatement pre = this.con.prepareStatement(sql.toString())) {
	            pre.setString(1, "%" + txt + "%");
	            pre.setInt(2, size);
	            pre.setInt(3, (page - 1) * size);
	            
	            try (ResultSet rs = pre.executeQuery()) {
	                while (rs.next()) {
	                    UserObject user = new UserObject();
	                	user.setUser_id(rs.getInt("user_id"));
	    				user.setUser_avatar(rs.getString("user_avatar"));
	    				user.setUser_email(rs.getString("user_email"));
	    				user.setUser_pass(rs.getString("user_pass"));
	    				user.setUser_fullname(rs.getString("user_fullname"));
	    				user.setUser_gender(rs.getString("user_gender"));
	    				user.setUser_birthday(rs.getString("user_birthday"));
	    				user.setUser_phone(rs.getString("user_phone"));
	    				user.setUser_address(rs.getString("user_address"));
	    				user.setUser_role_id(rs.getString("user_role_id"));
	    				user.setUser_create_at(rs.getString("user_create_at"));
	                    list.add(user);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        try (PreparedStatement preCount = this.con.prepareStatement(countSql);
	                ResultSet rsCount = preCount.executeQuery()) {
	               if (rsCount.next()) {
	                   totalUsers = rsCount.getInt("total");
	               }
	           } catch (SQLException e) {
	               e.printStackTrace();
	           }
	       }

	       return new UserResponse(list, totalUsers);
	}


	@Override
	public ResultSet getUser(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM tbluser WHERE user_id=?";
		return this.get(sql, id);
	}

	@Override
	public ResultSet getUser(String name, String pass) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM tbluser WHERE (user_name=?) AND (user_pass=?)";
		return this.get(sql, name, pass);
	}
	
	@Override
	public ArrayList<ResultSet> getUsers(String multiSelect, int size, int page) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tbluser ");
		sql.append(" ");
		sql.append("ORDER BY user_id DESC ");
		sql.append("LIMIT ").append(size).append(" OFFSET ").append((page - 1) * size).append(";");
		
		//Dem so luong nguoi su dung
		sql.append("SELECT COUNT(user_id) AS total FROM tbluser;");
		
		return this.gets(sql.toString());
	}
	

}
