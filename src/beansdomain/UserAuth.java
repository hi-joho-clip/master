package beansdomain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import daodto.ToSHA2;
import daodto.UserDAO;
import daodto.UserDTO;

public class UserAuth {

	private int user_id;
	private String user_name;
	private String nickname;
	private String password;
	private String mailaddress;
	private int friend_flag;
	private Date created;
	private Date modified;

	private HashMap<String, String> ErrorMessages = new HashMap<String, String>();

	private UserDAO userDAO;
	private UserDTO userDTO;

	private ArrayList<UserDTO> userlists = new ArrayList<UserDTO>();

	public void setUserDTO() {
		this.userDTO = new UserDTO();
		this.userDTO.setUser_id(this.user_id);
		this.userDTO.setUser_name(this.user_name);
		this.userDTO.setNickname(this.nickname);
		this.userDTO.setPassword(this.password);
		this.userDTO.setMailaddress(this.mailaddress);
		this.userDTO.setFriend_flag(this.friend_flag);
		this.userDTO.setCreated(this.created);
		this.userDTO.setModified(this.modified);
	}

/**
	 * ユーザのログイン。Set:user_name, password
	 *
	 * @throws Exception
	 */
	public boolean loginUserName(String user_name, String password) throws Exception {

		boolean login_flag = false;
		String hash_pass = null;
		this.userDAO = new UserDAO();
		hash_pass = ToSHA2.getDigest(user_name + password);

		setUserDTO();

		// 戻り値のユーザDTOのニックネームがNullであればログイン失敗
		if (userDAO.login(user_name, hash_pass)
				.getNickname() == null) {

		} else {
			login_flag = true;
		}

		return login_flag;

	}

	/**
	 * ユーザのログイン。Set:user_name, password
	 *
	 * @throws Exception
	 */
	public boolean loginMailaddress(String mailaddress, String password)
			throws Exception {

		boolean login_flag = false;
		this.userDAO = new UserDAO();
		this.userDTO = userDAO.viewMail(mailaddress);
		String user_name = this.userDTO.getUser_name();
		String hash_pass = null;

		if (user_name == null) {
			return login_flag;
		}

		this.user_name = user_name;
		hash_pass = (ToSHA2.getDigest(user_name
				+ password));
		this.mailaddress = mailaddress;
		System.out.println("1:" + this.userDTO.getPassword());
		setUserDTO();


		// 戻り値のユーザDTOのニックネームがNullであればログイン失敗
		if (userDAO.loginMail(mailaddress, hash_pass).getNickname() == null) {

		} else {
			login_flag = true;
		}

		return login_flag;

	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMailaddress() {
		return mailaddress;
	}

	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}

	public int getFriend_flag() {
		return friend_flag;
	}

	public void setFriend_flag(int friend_flag) {
		this.friend_flag = friend_flag;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public HashMap<String, String> getErrorMessages() {
		return ErrorMessages;
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		ErrorMessages = errorMessages;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

}
