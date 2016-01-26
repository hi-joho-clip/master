package daodto;

import java.util.Date;

public class UserDTO {


	private int user_id;
	private String user_name;
	private String nickname;
	private String mailaddress;
	private String password;
	private int friend_flag;
	private Date created;
	private Date modified;
	private String birth;


	public UserDTO() {
		this.user_id = 0;
		this.nickname = null;
		this.mailaddress = null;
		this.password = null;
		this.friend_flag = 0;
		this.created=null;
		this.modified=null;

	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getMailaddress() {
		return mailaddress;
	}


	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getFriend_flag() {
		return friend_flag;
	}


	public void setFriend_flag(int friend_flag) {
		this.friend_flag = friend_flag;
	}


	public java.util.Date getCreated() {
		return created;
	}


	public void setCreated(java.util.Date created) {
		this.created = created;
	}


	public java.util.Date getModified() {
		return modified;
	}


	public void setModified(java.util.Date modified) {
		this.modified = modified;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getBirth() {
		return birth;
	}


	public void setBirth(String birth) {
		this.birth = birth;
	}



}
