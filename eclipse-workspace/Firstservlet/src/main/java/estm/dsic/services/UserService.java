package estm.dsic.services;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import estm.dsic.dal.UserDao;
import estm.dsic.models.User;

public class UserService {
	
	private UserDao userdao=new UserDao();
	
	public User checkUser(User u){
		
		return userdao.exit(u);
		
	}
	public User checkAdmin(User u){
		
		return userdao.exitAdmin(u);
		
	}
	public boolean addUser(User u) throws SQLException{
		
		return userdao.createUser(u);
		
	}
	public List<User> getAllUsers(String q) throws SQLException, NamingException{
		
		return userdao.getAllUsers(q);
		
	}
	public boolean deleteUser(int id) throws SQLException{
		
		return userdao.deleteUser(id);
		
	}
	public boolean updateUser(User u) throws SQLException{
		
		return userdao.updateUser(u);
		
	}
	public User getIdByName(String name) throws SQLException{
		
		return userdao.getIdByName(name);
		
	}
	public User getUserById(int id) throws SQLException{
		
		return userdao.getUserById(id);
		
	}

}
