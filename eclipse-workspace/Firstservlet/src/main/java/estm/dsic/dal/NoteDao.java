package estm.dsic.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import estm.dsic.models.Note;
import estm.dsic.models.User;
import estm.dsic.services.UserService;

public class NoteDao {
	UserService userservice;
	public NoteDao() {
		// TODO Auto-generated constructor stub
		 userservice = new UserService();
	}
	public boolean createNote(Note note) throws SQLException {
	    PreparedStatement statement;
		try {
	        String query = "INSERT INTO notes (user_id,title, content, created_at, updated_at) VALUES (?,?, ?, ?, ?)";
	        statement = Database.getConnection().prepareStatement(query);
	        statement.setInt(1, note.getUser().getId());
	        statement.setString(2, note.getTitle());
	        statement.setString(3, note.getContent());
	        statement.setTimestamp(4, new Timestamp(note.getCreatedAt().getTime()));
	        statement.setTimestamp(5, new Timestamp(note.getUpdatedAt().getTime()));
	        int affectedRows = statement.executeUpdate();
	        if (affectedRows != 0) {
	            System.out.println("cree");
	        }else {
	        	System.out.println("not cree");
	        }
	    }catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}

	public List<Note> getAllNotes(String query,int id) throws SQLException, NamingException {
	    List<Note> notes = new ArrayList<>();
	    String sql = "SELECT * FROM notes WHERE user_id = '"+id+"'";
	    if (query != null && !query.isEmpty()) {
	        sql += " And title LIKE '%" + query + "%' OR content LIKE '%" + query + "%' ";
	    }
	    sql += " order by created_at desc";
	    try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Note note = new Note();
	                note.setTitle(resultSet.getString("title"));
	                note.setContent(resultSet.getString("content"));
	                note.setCreatedAt(resultSet.getTimestamp("created_at"));
	                note.setUpdatedAt(resultSet.getTimestamp("updated_at"));
//	                User user = userservice.getUserById(resultSet.getInt("user_id"));
//	                note.setUser(user);
	                note.setId(resultSet.getInt("id"));
	                notes.add(note);
	            }
	        }
	        
	        return notes;
	    }
	}
	public boolean deleteNote(int id) {
	 	boolean success = false;
	    try (PreparedStatement stmt = Database.getConnection().prepareStatement("DELETE FROM notes WHERE id = ?")) {
	        stmt.setInt(1, id);
	        int rowsAffected = stmt.executeUpdate();
	        success = rowsAffected > 0;
	    } catch (SQLException ex) {
	    	System.out.println("Error deleting note: " + ex.getMessage());
	    }
	    return success;
	}
	public List<Note> getNotes(int offset, int pageSize) throws SQLException {
	    List<Note> notes = new ArrayList<>();
	    ResultSet resultSet = null;
	    ResultSet rs;
		try {PreparedStatement stmt = Database.getConnection().prepareStatement("SELECT * FROM notes LIMIT ?, ?");
	        stmt.setInt(1, offset);
	        stmt.setInt(2, pageSize);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            Note note = new Note();
	            note.setId(rs.getInt("id"));
	            note.setTitle(rs.getString("title"));
	            note.setContent(rs.getString("content"));
				User user = userservice.getUserById(resultSet.getInt("user_id"));
                note.setUser(user);
	            note.setCreatedAt(rs.getTimestamp("created_at"));
	            note.setUpdatedAt(rs.getTimestamp("updated_at"));
	            notes.add(note);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    return notes;
	}
	public boolean updateNote(Note note) throws SQLException, NamingException {
	    boolean success = false;
	    try {
	        PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE notes SET title = ?, content = ?, updated_at = ? WHERE id = ?");
	        statement.setString(1, note.getTitle());
	        statement.setString(2, note.getContent());
	        statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
	        statement.setInt(4, note.getId());
	        int rowsUpdated = statement.executeUpdate();
	        if (rowsUpdated > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return success;
	}

}
