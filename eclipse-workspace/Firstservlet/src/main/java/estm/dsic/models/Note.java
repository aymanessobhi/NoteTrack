package estm.dsic.models;

import java.util.Date;

public class Note {
		private int id;
	    private String title;
	    private String content;
	    private Date createdAt;
	    private Date updatedAt;
	    private User user;
	public Note() {
		// TODO Auto-generated constructor stub
	}
	public Note(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
