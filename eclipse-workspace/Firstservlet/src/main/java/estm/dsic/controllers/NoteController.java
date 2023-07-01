package estm.dsic.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

public class NoteController {
	private UserService userService = new UserService();
	private NoteService noteservice = new NoteService();
	public NoteController() {
		// TODO Auto-generated constructor stub
	}
	 public void listNotes(HttpServletRequest request, HttpServletResponse response) {
	        // Logic for displaying a list of notes
	    }

	    public void createNote(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	HttpSession session=request.getSession();
	    	User user = (User) session.getAttribute("user");
	    	String title = request.getParameter("title");
	        String content = request.getParameter("content");
	        Note note = new Note();
	        note.setTitle(title);
	        note.setContent(content);
	        List<Note> notes = (List<Note>) session.getAttribute("notes");
	        if (notes == null) {
	            notes = new ArrayList<>();
	        }
	        notes.add(note);
	        session.setAttribute("notes", notes);
	        response.sendRedirect("app.jsp");
	    }

	    public void editNote(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	int noteId = Integer.parseInt(request.getParameter("id"));
	        String title = request.getParameter("title");
	        String content = request.getParameter("content");
	        Note note = new Note();
	        note.setId(noteId);
	        note.setTitle(title);
	        note.setContent(content);
	        System.out.println(note.getTitle() + " aa " +title);
	        try {
	            noteservice.updateNote(note);
	        } catch (SQLException | NamingException e) {
	            e.printStackTrace();
	        }
	        response.sendRedirect("app.jsp");
	    }

	    public void deleteNote(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	 int noteId = Integer.parseInt(request.getParameter("id"));
		        try {
		             noteservice.deleteNoteById(noteId);
		            System.out.println("Deleted note with ID: " + noteId);
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        response.sendRedirect("app.jsp");
	    }

	    public void saveNotes(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	HttpSession session=request.getSession();
	    	List<Note> notes = (List<Note>) session.getAttribute("notes");
	    	if(notes!=null){
	    		User currentUser = (User) request.getSession().getAttribute("user");
		        String username = currentUser.getName();
		        for (Note note : notes) {
		            try {
		            	User userId = userService.getIdByName(username);
		            	currentUser.setId(userId.getId());
		        		note.setUser(currentUser);
		        		note.setTitle(note.getTitle());
		        		note.setCreatedAt(new Date());
		        		note.setUpdatedAt(new Date());
		        		note.setTitle(note.getTitle());
		        		
						noteservice.addNote(note);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        session.setAttribute("notes", null);
	     		
	    	}
	    	response.sendRedirect("app.jsp");
	    }
	    public void searchNotes(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	    	User user = null;
	    	User currentUser = (User) request.getSession().getAttribute("user");
	    	String username = currentUser.getName();
    		try {
    		    user = userService.getIdByName(username);
    		} catch (SQLException e1) {
    		    e1.printStackTrace();
    		}
	    	String searchQuery = request.getParameter("search");
	    	List<Note> notes = null;
			try {
				notes = noteservice.getAllNotes(searchQuery, user.getId());
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  // set the notes attribute and forward to the JSP
	    	  request.setAttribute("notes", notes);
	    	  request.getRequestDispatcher("app.jsp").forward(request, response);
	    }
	    public void logoutNotes(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	    	HttpSession session=request.getSession();
	    	session = request.getSession(false);
	        if (session != null) {
	            session.invalidate();
	        }
	        request.getRequestDispatcher("auth.jsp").forward(request, response);
	    }

}
