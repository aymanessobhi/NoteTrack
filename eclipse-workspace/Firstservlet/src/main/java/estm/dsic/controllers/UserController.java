package estm.dsic.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import estm.dsic.models.Note;
import estm.dsic.models.User;
import estm.dsic.services.NoteService;
import estm.dsic.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserController  {
	private UserService userService;
	private NoteService noteservice;
	HttpSession session;
	public UserController() {
		// TODO Auto-generated constructor stub
		 	userService=new UserService();
	        noteservice=new NoteService();
	}
	  public void signup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    	String name=request.getParameter("name");
			String email = request.getParameter("email");
			String password=request.getParameter("password");
	    	try { 
	        	User user =new User();
	            user.setName(name);
	            user.setEmail(email);
	            user.setIsAdmin(false);
	            user.setPassword(password);
	            userService.addUser(user);
	            HttpSession session=request.getSession();
	            request.getSession();
	            session.setAttribute("user", user);
	            response.sendRedirect("auth.jsp");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            request.setAttribute("errorMessage", "Database error occurred");
	            request.getRequestDispatcher("signup.jsp").forward(request, response);
	        }
	    }

	    public void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	String name=request.getParameter("name");
			String email = request.getParameter("email");
			String password=request.getParameter("password");
	    	User user =new User();
	        int id = Integer.parseInt(request.getParameter("id"));
	        boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
	        user.setId(id);
	        user.setName(name);
            user.setEmail(email);
            user.setIsAdmin(isAdmin);
            user.setPassword(password);            
	        try {
				userService.updateUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				List<User> list = userService.getAllUsers(null);
				request.setAttribute("list", list);
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        request.getRequestDispatcher("/appAdmin.jsp").forward(request, response);
	    }

	    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	int userId = Integer.parseInt(request.getParameter("id"));
	    	try {
				userService.deleteUser(userId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
				List<User> list = userService.getAllUsers(null);
				request.setAttribute("list", list);
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	request.getRequestDispatcher("/appAdmin.jsp").forward(request, response);
	    }

	    public void searchUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	// search for users
	    	try {
	    			String query = request.getParameter("q");
	    			System.out.println(query);
	    			List<User> userList = userService.getAllUsers(query);
	    			request.setAttribute("list", userList);
	    			request.getRequestDispatcher("appAdmin.jsp").forward(request, response);
	    			} catch (SQLException | NamingException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}															
	    }
	    
		public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			session=request.getSession();
			User user=new User();
			String name=request.getParameter("name");
			String email = request.getParameter("email");
			String password=request.getParameter("password");
			user.setName(name);
			user.setPassword(password);
			if (userService.checkUser(user) != null){
				session.setAttribute("user", user);
				try {
					User currentUser = (User) request.getSession().getAttribute("user");
					String username = currentUser.getName();
					try {
		    		    user = userService.getIdByName(username);
		    		} catch (SQLException e1) {
		    		    e1.printStackTrace();
		    		}
					currentUser.setId(user.getId());
					List<Note> noteList = noteservice.getAllNotes(null,currentUser.getId());
					System.out.println(currentUser.getId()+" we hwre aga");
					request.setAttribute("notes", noteList);
				} catch (SQLException | NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getRequestDispatcher("app.jsp").forward(request, response);
			}else if(userService.checkAdmin(user) != null) {
				
				try {
					List<User> list = userService.getAllUsers(null);
					request.setAttribute("list", list);
				} catch (SQLException | NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				session.setAttribute("user", user);
				request.getRequestDispatcher("appAdmin.jsp").forward(request, response);
			}
			else {
				response.sendRedirect("error.jsp");
			}
		}
		
		public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			session = request.getSession(false);
	        if (session != null) {
	            session.invalidate();
	        }
	        request.getRequestDispatcher("auth.jsp").forward(request, response);
		}
		
}
