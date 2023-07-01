package estm.dsic.services;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import estm.dsic.dal.NoteDao;
import estm.dsic.models.Note;
import estm.dsic.models.User;
import jakarta.servlet.http.HttpSession;


public class NoteService {
	private NoteDao notedao=new NoteDao();
	public NoteService() {
		// TODO Auto-generated constructor stub
	}
	public boolean addNote(Note n) throws SQLException{
			
			return notedao.createNote(n);
			
	}
	public List<Note> getAllNotes(String q, int id) throws SQLException, NamingException{
		
		return notedao.getAllNotes(q, id);
		
	}
	public void incrementNoteCount(HttpSession session) {
		Integer noteCount = (Integer) session.getAttribute("noteCount");
		if (noteCount == null) {
		    noteCount = 0;
		}
		noteCount++;
		session.setAttribute("noteCount", noteCount);
	}
	public boolean deleteNoteById(int id) throws SQLException{
		
		return notedao.deleteNote(id);
		
	}
	public List<Note> getNotesByPage(int page, int pageSize) throws SQLException, NamingException {
	    int offset = (page - 1) * pageSize;
	    return notedao.getNotes(offset, pageSize);
	}
	public int getTotalPages(int totalNotes, int pageSize) {
	    return (int) Math.ceil((double) totalNotes / pageSize);
	}
	public boolean updateNote(Note n) throws SQLException, NamingException{
		
		return notedao.updateNote(n);
		
	}
}
