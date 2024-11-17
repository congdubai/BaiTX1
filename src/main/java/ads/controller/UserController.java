package ads.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ads.objects.UserObject;
import ads.objects.UserResponse;
import ads.user.User;
import ads.user.UserImpl;

@WebServlet("/loadUser")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private User user;

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	// Hàm lấy user
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("user-action");
		if (action != null) {
			switch (action) {
			case "add":
				add(request, response);
				break;
			case "delete":
				delete(request, response);
				break;
			case "deleteform":
				deleteform(request, response);
				break;
			case "editform":
				editfrom(request, response);
				break;
			case "edit":
				edit(request, response);
				break;
			case "detailform":
				detailform(request, response);
				break;
			case "search":
				search(request, response);
				break;
			default:
				load(request, response);
			}
		} else {
			load(request, response);
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		user = new UserImpl();
		int totalPage = 0;
		int page = 1;
		String optPage = request.getParameter("page");
		if (optPage != null) {
			page = Integer.parseInt(optPage);
		}
		try {
			request.setCharacterEncoding("UTF-8");
			String txt = request.getParameter("txt");
			ArrayList<UserObject> Lists = new ArrayList<UserObject>();		
			UserResponse userResponse = user.getUserByName(txt.trim(), 5, page);
			totalPage = userResponse.getTotalUsers();
			Lists = userResponse.getUsers();
			
			request.setAttribute("txtS", txt);
			
				request.setAttribute("currentPage", page);
				request.setAttribute("Lists", Lists);
				request.setAttribute("totalPage", (int) Math.ceil((double) totalPage / 5));			
				try {
					request.getRequestDispatcher("/view/admin/user/show.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void detailform(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		user = new UserImpl();
		String sid = request.getParameter("sId");
		request.setAttribute("userIdDetail", sid);
		int id = Integer.parseInt(sid);
		ResultSet res = user.getUser(id);
		UserObject userObj = new UserObject();
		try {
			// Set giá trị
			while (res.next()) {
				userObj.setUser_id(res.getInt("user_id"));
				userObj.setUser_email(res.getString("user_email"));
				userObj.setUser_pass(res.getString("user_pass"));
				userObj.setUser_fullname(res.getString("user_fullname"));
				userObj.setUser_address(res.getString("user_address"));
				userObj.setUser_phone(res.getString("user_phone"));
				userObj.setUser_role_id(res.getString("user_role_id"));
				userObj.setUser_gender(res.getString("user_gender"));
				userObj.setUser_birthday(res.getString("user_birthday"));
				userObj.setUser_avatar(res.getString("user_avatar"));
				userObj.setUser_create_at(res.getString("user_create_at"));
			}

			// Gửi giá trị lên form
			request.setAttribute("user_id", userObj.getUser_id());
			request.setAttribute("user_email", userObj.getUser_email());
			request.setAttribute("user_pass", userObj.getUser_pass());
			request.setAttribute("user_fullname", userObj.getUser_fullname());
			request.setAttribute("user_address", userObj.getUser_address());
			request.setAttribute("user_phone", userObj.getUser_phone());
			request.setAttribute("user_role_id", userObj.getUser_role_id());
			request.setAttribute("user_gender", userObj.getUser_gender());
			request.setAttribute("user_birthday", userObj.getUser_birthday());
			request.setAttribute("user_avatar", userObj.getUser_avatar());
			request.setAttribute("user_create_at", userObj.getUser_create_at());

			try {
				load(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		user = new UserImpl();
		HttpSession session = request.getSession();
		Object objId = session.getAttribute("secIdEdit");

		if (objId != null) {
			int id = (int) objId;
			UserObject userObj = new UserObject();
			// Lấy giá trị
			String email = request.getParameter("user_email");
			String avatar = request.getParameter("user_avatar");
			String role = request.getParameter("user_role_id");
			String address = request.getParameter("user_address");
			String phone = request.getParameter("user_phone");
			String gender = request.getParameter("user_gender");
			String fullname = request.getParameter("user_fullname");
			String birthday = request.getParameter("user_birthday");

			userObj.setUser_id(id);
			userObj.setUser_email(email);
			userObj.setUser_fullname(fullname);
			userObj.setUser_address(address);
			userObj.setUser_phone(phone);
			userObj.setUser_role_id(role);
			userObj.setUser_gender(gender);
			userObj.setUser_birthday(birthday);
			userObj.setUser_avatar(avatar);

			user.editUser(userObj);
			try {
				load(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void editfrom(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		user = new UserImpl();
		String sid = request.getParameter("sId");
		request.setAttribute("userIdEdit", sid);
		try {
			int id = Integer.parseInt(sid);
			HttpSession session = request.getSession();
			ResultSet res = user.getUser(id);
			UserObject userObj = new UserObject();

			try {
				// Set giá trị
				while (res.next()) {
					userObj.setUser_id(res.getInt("user_id"));
					userObj.setUser_email(res.getString("user_email"));
					userObj.setUser_fullname(res.getString("user_fullname"));
					userObj.setUser_address(res.getString("user_address"));
					userObj.setUser_phone(res.getString("user_phone"));
					userObj.setUser_role_id(res.getString("user_role_id"));
					userObj.setUser_gender(res.getString("user_gender"));
					userObj.setUser_birthday(res.getString("user_birthday"));
					userObj.setUser_avatar(res.getString("user_avatar"));
				}

				// Gửi giá trị lên form
				request.setAttribute("user_id", userObj.getUser_id());
				request.setAttribute("user_email", userObj.getUser_email());
				request.setAttribute("user_fullname", userObj.getUser_fullname());
				request.setAttribute("user_address", userObj.getUser_address());
				request.setAttribute("user_phone", userObj.getUser_phone());
				request.setAttribute("user_role_id", userObj.getUser_role_id());
				request.setAttribute("user_gender", userObj.getUser_gender());
				request.setAttribute("user_birthday", userObj.getUser_birthday());
				request.setAttribute("user_avatar", userObj.getUser_avatar());

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("secIdEdit", id);
			load(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Hiển thị form delete
	private void deleteform(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String sid = request.getParameter("sId");
		request.setAttribute("userIdToDelete", sid);
		try {
			int id = Integer.parseInt(sid);
			HttpSession session = request.getSession();
			session.setAttribute("secId", id);
			load(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Hàm xóa user
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		user = new UserImpl();

		HttpSession session = request.getSession();
		Object objId = session.getAttribute("secId");

		if (objId != null) {
			int id = (int) objId;
			user.delUser(id);
			try {
				load(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Hàm thêm user
	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		user = new UserImpl();
		UserObject userOject = new UserObject();

		// Lấy giá trị
		String email = request.getParameter("user_email");
		String pass = request.getParameter("user_pass");
		String avatar = request.getParameter("user_avatar");
		String role = request.getParameter("user_role_id");
		String address = request.getParameter("user_address");
		String phone = request.getParameter("user_phone");
		String gender = request.getParameter("user_gender");
		String fullname = request.getParameter("user_fullname");
		String birthday = request.getParameter("user_birthday");

		// set giá trị
		userOject.setUser_email(email);
		userOject.setUser_address(address);
		userOject.setUser_avatar(avatar);
		userOject.setUser_pass(pass);
		userOject.setUser_gender(gender);
		userOject.setUser_birthday(birthday);
		userOject.setUser_fullname(fullname);
		userOject.setUser_phone(phone);
		userOject.setUser_role_id(role);

		user.addUser(userOject);

		try {
			load(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Hàm hiển thị
	protected void load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.user = new UserImpl();
		int totalPage = 0;
		int page = 1;
		String optPage = request.getParameter("page");
		if (optPage != null) {
			page = Integer.parseInt(optPage);
		} else {

		}

		ArrayList<ResultSet> res = user.getUsers(null, 5, page);
		ResultSet rs = res.get(0);

		// Danh sach user
		ArrayList<UserObject> listUser = new ArrayList<UserObject>();
		try {
			while (rs.next()) {
				UserObject user = new UserObject();
				user.setUser_id(rs.getInt("user_id"));
				user.setUser_avatar(rs.getString("user_avatar"));
				user.setUser_email(rs.getString("user_email"));
				user.setUser_fullname(rs.getString("user_fullname"));
				user.setUser_gender(rs.getString("user_gender"));
				user.setUser_birthday(rs.getString("user_birthday"));
				user.setUser_phone(rs.getString("user_phone"));
				user.setUser_address(rs.getString("user_address"));
				user.setUser_role_id(rs.getString("user_role_id"));
				user.setUser_create_at(rs.getString("user_create_at"));
				listUser.add(user);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = res.get(1);
		if (rs != null) {
			try {
				if (rs.next()) {
					totalPage = rs.getInt("total");
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		request.setAttribute("currentPage", page);
		request.setAttribute("Lists", listUser);
		request.setAttribute("totalPage", (int) Math.ceil((double) totalPage / 5));

		// response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("/view/admin/user/show.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		doGet(request, response);
	}

}
