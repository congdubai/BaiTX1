package ads.user;

import java.sql.ResultSet;
import java.util.ArrayList;

import ads.*;
import ads.objects.UserObject;
import ads.objects.UserResponse;
public interface User {
	boolean addUser(UserObject item);
	boolean editUser(UserObject item);
	boolean delUser(int id);
	ArrayList<ResultSet> getUsers(String multiSelect, int size, int page);
	UserResponse  getUserByName(String txt, int size, int page);
	ResultSet getUser(int id);
	ResultSet getUser(String name, String pass);
}
