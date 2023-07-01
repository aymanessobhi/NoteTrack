package estm.dsic.controllers;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = {"/AuthServlet"})
/**
 * Servlet implementation class AuthServlet
 */
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserController userController;
	NoteController noteController;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthServlet() {
        super();
        // TODO Auto-generated constructor stub
        userController = new UserController();
        noteController = new NoteController();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		String action = request.getParameter("action");
		
	    if ( action != null && action.equals("search")) {
	    	
	        userController.searchUser(request, response);
	        
	    }else if ( action != null && action.equals("update")) {
	    	
	    	userController.editUser(request, response);
	    	
	    }else if ( action != null && action.equals("delete")) {
	    	
	    	userController.deleteUser(request, response);
	    	
	    }
	    //For notes
	    
	    else if(action.equals("createNote")) {
	    	
	    	noteController.createNote(request, response);
	    	
	    }else if (action.equals("saveNotes")) {
	    	
	    	noteController.saveNotes(request, response);
	    	
	    }else if (action != null && action.equals("searchNote")) {
	    	
	    	noteController.searchNotes(request, response);
	    	
	    }else if (action != null && action.equals("editNote")) {
	    	
	    	noteController.editNote(request, response);
	    	
	    }else if (action != null && action.equals("deleteNote")) {
	    	
	    	noteController.deleteNote(request, response);
	    	
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if(action != null &&  action.equals("login")) {
			
            userController.login(request, response);
            
		}else if (action != null && action.equals("logout")) {
			
	        userController.logout(request, response);
	        
	    }else if(action != null && action.equals("signup")) {
	    	
	    	userController.signup(request, response);
	    	
	    }else if(action != null && action.equals("logoutNotes")) {
	    	
	    	noteController.logoutNotes(request, response);
	    	
	    }
	}
}
