package estm.dsic.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import estm.dsic.models.User;
import org.mindrot.jbcrypt.BCrypt;
public class UserDao {

	public User exit(User u) {
		try {
			Statement stm=Database.getConnection().createStatement();
			ResultSet rs=stm.executeQuery("SELECT * FROM users WHERE name='"+u.getName()+"' AND password='"+
			u.getPassword()+"' AND isAdmin =false ;");
			if(rs.next())
				return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public User exitAdmin(User u) {
		try {
			Statement stm=Database.getConnection().createStatement();
			ResultSet rs=stm.executeQuery("SELECT * FROM users WHERE name='"+u.getName()+"' AND password='"+
			u.getPassword()+"' AND isAdmin = true ;");
			if(rs.next())
				return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	 public boolean createUser(User user) throws SQLException{
		      String sql = "INSERT INTO users (name,email,isAdmin,password) VALUES (?, ?, ?,?)";
		      try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
		        statement.setString(1, user.getName());
		        statement.setString(2, user.getEmail());
		        statement.setBoolean(3, user.getIsAdmin());
		        statement.setString(4, user.getPassword());
		        statement.executeUpdate();
		      }
			return true;
	}
	 public List<User> getAllUsers(String query) throws SQLException, NamingException {
			    List<User> users = new ArrayList<>();
			    String sql = "SELECT * FROM users";
		        if (query != null && !query.isEmpty()) {
		            sql += " WHERE name LIKE '%" + query + "%' OR email LIKE '%" + query + "%'";
		        }
		      try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
		        try (ResultSet resultSet = statement.executeQuery()) {
		          while (resultSet.next()) {
		        	User user = new User();
		            user.setName(resultSet.getString("name"));
		            user.setEmail(resultSet.getString("email"));
		            //hash Password
		            String TextPassword = resultSet.getString("password");
		            String salt = BCrypt.gensalt();
		            String hashedPassword = BCrypt.hashpw(TextPassword, salt);
		            user.setPassword(hashedPassword);
		            //
		            user.setIsAdmin(resultSet.getBoolean("isAdmin"));
		            user.setId(resultSet.getInt("id"));
		            users.add(user);
		          }
		        }
		        for(User user : users) {
		        	System.out.println(user.toString());
		        }
		        return users;
		  }
	 }
	 public boolean deleteUser(int id) {
		 	boolean success = false;
		    try (PreparedStatement stmt = Database.getConnection().prepareStatement("DELETE FROM users WHERE id = ?")) {
		        stmt.setInt(1, id);
		        int rowsAffected = stmt.executeUpdate();
		        success = rowsAffected > 0;
		    } catch (SQLException ex) {
		    	System.out.println("Error deleting user: " + ex.getMessage());
		    }
		    return success;
		}
	 public boolean updateUser(User user) {
		    boolean success = false;
		    try {
		    	PreparedStatement stmt = Database.getConnection().prepareStatement("UPDATE users SET name = ?, email = ?, password = ?, isAdmin = ? WHERE id = ?");
		        stmt.setString(1, user.getName());
		        stmt.setString(2, user.getEmail());
		        stmt.setString(3, user.getPassword());
		        stmt.setBoolean(4, user.getIsAdmin());
		        stmt.setInt(5, user.getId());
		        int rowsUpdated = stmt.executeUpdate();
		        if (rowsUpdated > 0) {
		            success = true;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return success;
		}
	 public User getIdByName(String name) throws SQLException {
		    String sql = "SELECT id,name,email FROM users WHERE name = ?";
		    ResultSet resultSet = null;
		    User user = null;
		    try {
		    	PreparedStatement stmt =  Database.getConnection().prepareStatement(sql);
		        stmt.setString(1, name);
		        resultSet = stmt.executeQuery();
		        
		        if (resultSet.next()) {
		            user = new User();
		            user.setId(resultSet.getInt("id"));
		            user.setName(resultSet.getString("name"));
		            user.setEmail(resultSet.getString("email"));
		        }
		    }catch (SQLException e) {
	            // Log the exception or throw it further
	        }
			return user;
	 		}
		 public User getUserById(int id) throws SQLException {
			    String sql = "SELECT * FROM users WHERE id = ?";
			    try (PreparedStatement statement = Database.getConnection().prepareStatement(sql)) {
			        statement.setInt(1, id);
			        try (ResultSet resultSet = statement.executeQuery()) {
			            if (resultSet.next()) {
			                User user = new User();
			                user.setId(resultSet.getInt("id"));
			                user.setName(resultSet.getString("name"));
			                user.setEmail(resultSet.getString("email"));
			                user.setPassword(resultSet.getString("password"));
			                user.setIsAdmin(resultSet.getBoolean("isAdmin"));
			                return user;
			            }
			        }
			    } catch (SQLException e) {
			        // Log the exception or throw it further
			    }
			    return null;
			}
	 }

