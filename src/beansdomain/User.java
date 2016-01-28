package beansdomain;

import java.util.Date;
import java.util.HashMap;

import daodto.UserDAO;
import daodto.UserDTO;

public class User {

	private int user_id;
	private String user_name;
	private String nickname;
	private String password;
	private String mailaddress;
	private int friend_flag;
	private Date created;
	private Date modified;
	private String birth;

	private HashMap<String, String> ErrorMessages = new HashMap<String, String>();

	private UserDAO userDAO;


	private UserDTO userDTO;

	//private ArrayList<UserDTO> userlists = new ArrayList<UserDTO>();

	public User() {
	}

	public User(int user_id) {
		try {
			this.userDAO = new UserDAO();
			//DAOで検索し、その結果をDTOで受け取る
			UserDTO userDTO = userDAO.view(user_id); //受け取ったデータをBeans(ドメイン（このオブジェクト））に入れなおす
			//一見無駄とも思える行為だが、内輪ルールとして行う
			this.user_id = userDTO.getUser_id();
			this.user_name = userDTO.getUser_name();
			this.nickname = userDTO.getNickname();
			this.password = userDTO.getPassword();
			this.mailaddress = userDTO.getMailaddress();
			this.friend_flag = userDTO.getFriend_flag();
			this.created = userDTO.getCreated();
			this.modified = userDTO.getModified();
			this.birth = userDTO.getBirth();
		} catch (Exception e) {
			ErrorMessages.put("Error", "constract error");
		}
	}

	/**
	 * ユーザーの追加登録
	 * @return
	 * @throws Exception
	 */
	public void addUser() throws Exception {
		this.userDAO = new UserDAO();
		setUserDTO();
		/*
		 * ユーザIDとパスワードの重複判定
		 * マイリストの作成
		 * お気に入りタグ生成
		 */
		if (checkUserNameValidation() && checkUserMailaddressValidation()) {
			userDAO.add(userDTO);
		} else {
			return;
		}

		/*
		 * 空値の判定はブラウザ側のバリデーションで行う
		 */
	}

	/**
	 * データ挿入前のバリデーションチェック
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserNameValidation() throws Exception {
		boolean validate_flag = true;
		// trueであれば有効
		if (!this.userDAO.checkUserName(this.user_name) || this.user_name == null) {
			//すでに同名ユーザが存在する
			validate_flag = false;
			ErrorMessages.put("user_name", "すでに登録されているユーザ名です。");
		}
		return validate_flag;
	}

	/**
	 * メールアドレスのバリデーションチェック
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserMailaddressValidation() throws Exception {
		boolean validate_flag = true;
		// trueであれば有効
		if (!this.userDAO.checkMailAddress(this.mailaddress) || this.mailaddress == null) {
			// 同一のメールアドレスが登録されているか確認する
			validate_flag = false;
			ErrorMessages.put("mailaddress", "すでに登録されているメールアドレスです。");
		}
		return validate_flag;

	}

	/**
	 * パスワード検索
	 */
	public void searchPassword() throws Exception{
		this.userDAO = new UserDAO();
		setUserDTO();
		userDTO = userDAO.searchPassword(this.user_name , this.mailaddress);


	}



	/**
	 * フレンド申請の許可設定
	 * @throws Exception
	 */
	public void friend_accept() throws Exception {

		this.userDAO = new UserDAO();
		setUserDTO();

		if (this.userDTO.getUser_id() != 0) {
			userDAO.friend_accept(this.userDTO.getUser_id());
		} else {
			ErrorMessages.put("friend_accept", "invalid user_id");
		}

	}

	/**
	 * フレンド申請の拒否設定
	 * @throws Exception
	 */
	public void friend_deny() throws Exception {

		this.userDAO = new UserDAO();
		setUserDTO();

		if (this.userDTO.getUser_id() != 0) {
			userDAO.friend_deny(this.userDTO.getUser_id());
		} else {
			ErrorMessages.put("friend_deny", "invalid user_id");
		}

	}

	/**
	 * ユーザーの更新(入力値される値はNullなし）
	 * @return
	 * @throws Exception
	 */
	public void updateUserName() throws Exception {
		this.userDAO = new UserDAO();
		setUserDTO();

		if (checkUserNameValidation()) {
			this.userDAO.update(this.userDTO);
		} else {
			return;
		}
	}

	/**
	 * メールアドレスのアップデート
	 * @throws Exception
	 */
	public void updateMailaddress() throws Exception {
		this.userDAO = new UserDAO();
		setUserDTO();

		if (checkUserMailaddressValidation()) {
			userDAO.update(this.userDTO);
		} else {
			return;
		}
	}

	/**
	 * ニックネームのアップデートS
	 * @throws Exception
	 */
	public void updateNickname() throws Exception {
		this.userDAO = new UserDAO();
		setUserDTO();

		if (this.userDTO.getNickname() != null && !this.userDTO.getNickname().equals("")) {
			userDAO.update(this.userDTO);
		} else {
			this.ErrorMessages.put("nickname", "invalid nickname");
			return;
		}
	}

	public void updatePassword() throws Exception {
		this.userDAO = new UserDAO();
		setUserDTO();

		System.out.println("userpass:" + userDTO.getPassword());
		if (userDTO.getPassword() != null && !userDTO.getPassword().equals("")) {
			userDAO.update(userDTO);
		} else {
			//System.out.println("aaa");
			this.ErrorMessages.put("password", "invalid password");
			return;
		}
	}

	/**
	 * パスワード再発行(ユーザ名、メールアドレス、生年月日で検索)
	 */
	public boolean searchPass() throws Exception{
		boolean flag = false;
		this.userDAO = new UserDAO();
		setUserDTO();
		System.out.println("user_idを取得する前："+ userDTO.getUser_id());
		userDTO = userDAO.PasswordSearch(this.userDTO.getMailaddress(), this.userDTO.getUser_name(), this.userDTO.getBirth());
		System.out.println("user_idを取得した後："+ userDTO.getUser_id());
		 if(userDTO.getUser_id() != 0){
			 flag = true;
			 this.user_id = userDTO.getUser_id();
		 }
		 return flag;
	}


	/**
	 * パスワード再発行(パスワードを更新)
	 */
	public void reissuePass() throws Exception{
		this.userDAO = new UserDAO();
		setUserDTO();

		userDAO.updatePassword(this.userDTO.getUser_id(), this.userDTO.getUser_name(), this.userDTO.getPassword());
	}


	/**
	 * ユーザID確認
	 * @throws Exception
	 */
	public boolean UserID() throws Exception{
		boolean flag = false;
		this.userDAO = new UserDAO();
		setUserDTO();

		userDAO.searchUserName(this.userDTO.getMailaddress(), this.userDTO.getPassword());

		if(userDTO != null){
			flag = true;
			 this.user_id = userDAO.searchUserName(this.userDTO.getMailaddress(), this.userDTO.getPassword()).getUser_id();
		}
		return flag;
	}


	/**
	 * ユーザーの削除（未完成）
	 * @return
	 * @throws Exception
	 */
	public void deleteUser() throws Exception {
		this.userDAO = new UserDAO();
		setUserDTO();
		userDAO.delete(this.userDTO.getUser_id());
	}

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
		this.userDTO.setBirth(this.birth);
	}

	public void setErrorMessages(HashMap<String, String> errorMessages) {
		ErrorMessages = errorMessages;
	}

	public HashMap<String, String> getErrorMessages() {
		return ErrorMessages;
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

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}



}
