package beansdomain;

import java.util.ArrayList;
import java.util.Date;

import daodto.FriendDAO;
import daodto.FriendDTO;
import daodto.UserDAO;
import daodto.UserDTO;

public class Friend {

	private int friend_id;
	private int own_user_id;
	private int friend_user_id;
	private int share_id;
	private int status;
	private Date acceptdate;
	private Date created;
	private Date modified;

	// これはフレンドのユーザ名
	private String user_name;
	private String nickname;

	private FriendDTO friendDTO;
	private FriendDAO friendDAO;



	private ArrayList<FriendDTO> friendlists = new ArrayList<FriendDTO>();

	private ArrayList<Friend> friendList = new ArrayList<Friend>();

	private ArrayList<User> userList = new ArrayList<User>();

	public Friend() {

	}

	/**
	 * ここはコンストラクタとして自身のIDを渡すとフレンド一覧を取得
	 * @param own_user_id
	 * @param frien_user_id
	 * @throws Exception
	 */
//	public FriendBean(int own_user_id) throws Exception{
//
//		this.friendDAO = new FriendDAO();
//		// DAOで検索しその結果をDTOで受け取る
//		this.friendlists = friendDAO.friendList(own_user_id);
//		// フレンドリストからフレンドのユーザDTOをひも付
//		for (FriendDTO f_DTO : friendlists) {
//			f_DTO.getFriend_user_id();
//		}
//	}

	public Friend viewFriend(int own_user_id ,int friend_user_id)  throws Exception {

			this.friendDAO = new FriendDAO();
			FriendDTO friendDTO = friendDAO.view(own_user_id , friend_user_id);
			this.friend_id = friendDTO.getFriend_id();
			this.own_user_id = friendDTO.getOwn_user_id();
			this.friend_user_id = friendDTO.getFriend_user_id();
			this.share_id = friendDTO.getShare_id();
			this.created = friendDTO.getCreated();
			this.acceptdate = friendDTO.getAcceptdate();
			this.status = friendDTO.getStatus();



		return this;

	}
	/**
	 *
	 * 申請しているユーザとフレンドになったユーザの一覧を取得
	 * @param own_user_id
	 */
	public ArrayList<Friend> getFriendList(int own_user_id) throws Exception{
		friendDAO = new FriendDAO();
		UserDAO userDAO = new UserDAO();
		ArrayList<FriendDTO> friendArrayDTO = new ArrayList<FriendDTO>();

		friendArrayDTO = friendDAO.friendList(own_user_id);

		for (int i = 0; i< friendArrayDTO.size(); i++) {
			Friend friendbean = new Friend();
			friendbean.setFriend_id(friendArrayDTO.get(i).getFriend_id());
			friendbean.setOwn_user_id(friendArrayDTO.get(i).getOwn_user_id());
			friendbean.setFriend_user_id(friendArrayDTO.get(i).getFriend_user_id());
			friendbean.setShare_id(friendArrayDTO.get(i).getShare_id());
			friendbean.setStatus(friendArrayDTO.get(i).getStatus());
			friendbean.setAcceptdate(friendArrayDTO.get(i).getAcceptdate());
			friendbean.setCreated(friendArrayDTO.get(i).getCreated());
			friendbean.setModified(friendArrayDTO.get(i).getModified());
			friendbean.setUser_name(userDAO.view(friendArrayDTO.get(i).getFriend_user_id()).getUser_name());
			friendbean.setNickname(userDAO.view(friendArrayDTO.get(i).getFriend_user_id()).getNickname());
			this.friendList.add(friendbean);
		}
		return this.friendList;

	}
	/**
	 *
	 * フレンドになったユーザの一覧を取得
	 * @param own_user_id
	 */
	public ArrayList<Friend> getFriend(int own_user_id) throws Exception{
		friendDAO = new FriendDAO();
		UserDAO userDAO = new UserDAO();
		ArrayList<FriendDTO> friendArrayDTO = new ArrayList<FriendDTO>();

		friendArrayDTO = friendDAO.friend(own_user_id);

		for (int i = 0; i< friendArrayDTO.size(); i++) {
			Friend friendbean = new Friend();
			friendbean.setFriend_id(friendArrayDTO.get(i).getFriend_id());
			friendbean.setOwn_user_id(friendArrayDTO.get(i).getOwn_user_id());
			friendbean.setFriend_user_id(friendArrayDTO.get(i).getFriend_user_id());
			friendbean.setShare_id(friendArrayDTO.get(i).getShare_id());
			friendbean.setStatus(friendArrayDTO.get(i).getStatus());
			friendbean.setAcceptdate(friendArrayDTO.get(i).getAcceptdate());
			friendbean.setCreated(friendArrayDTO.get(i).getCreated());
			friendbean.setModified(friendArrayDTO.get(i).getModified());
			friendbean.setUser_name(userDAO.view(friendArrayDTO.get(i).getFriend_user_id()).getUser_name());
			friendbean.setNickname(userDAO.view(friendArrayDTO.get(i).getFriend_user_id()).getNickname());
			this.friendList.add(friendbean);
		}
		return this.friendList;

	}
	/**
	 *
	 * 取得するのはユーザID、ニックネームのみ
	 * @param own_user_id
	 */
	public ArrayList<User> searchFriend(int own_user_id, String str) throws Exception{
		friendDAO = new FriendDAO();
		ArrayList<UserDTO> userListDTO = friendDAO.search(own_user_id, str);
		for (UserDTO userDTO : userListDTO) {
			User userbean = new User();

			userbean.setUser_id(userDTO.getUser_id());
			userbean.setNickname(userDTO.getNickname());
			this.userList.add(userbean);
		}

		return this.userList;

	}

	public boolean addRequest(int own_user_id , int friend_user_id) throws Exception {

		boolean flag = false;

		this.friendDAO = new FriendDAO();
		flag = this.friendDAO.addRequest(own_user_id, friend_user_id);

		return flag;
	}


	public boolean acceptRequest(int own_user_id , int friend_user_id) throws Exception {

		boolean flag = false;

		this.friendDAO = new FriendDAO();
		flag = this.friendDAO.acceptRequest(own_user_id, friend_user_id);

		return flag;
	}

	public boolean denyRequest(int own_user_id , int friend_user_id) throws Exception {

		boolean flag = false;

		this.friendDAO = new FriendDAO();
		flag = this.friendDAO.denyRequest(own_user_id, friend_user_id);

		return flag;
	}

	public boolean deleteRequest(int own_user_id , int friend_user_id) throws Exception {

		boolean flag = false;

		this.friendDAO = new FriendDAO();
		flag = this.friendDAO.deleteRequest(own_user_id, friend_user_id);

		return flag;
	}

	public boolean deleteFriend(int own_user_id , int friend_user_id) throws Exception {

		boolean flag = false;

		this.friendDAO = new FriendDAO();
		flag=this.friendDAO.deleteFriend(own_user_id, friend_user_id);

		return flag;
	}



	public int getFriend_id() {
		return friend_id;
	}

	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
	}

	public int getOwn_user_id() {
		return own_user_id;
	}

	public void setOwn_user_id(int own_user_id) {
		this.own_user_id = own_user_id;
	}

	public int getFriend_user_id() {
		return friend_user_id;
	}

	public void setFriend_user_id(int friend_user_id) {
		this.friend_user_id = friend_user_id;
	}

	public int getShare_id() {
		return share_id;
	}

	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAcceptdate() {
		return acceptdate;
	}

	public void setAcceptdate(Date acceptdate) {
		this.acceptdate = acceptdate;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date create) {
		this.created = create;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
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

	public ArrayList<FriendDTO> getFriendlists() {
		return friendlists;
	}

	public void setFriendlists(ArrayList<FriendDTO> friendlists) {
		this.friendlists = friendlists;
	}

	public ArrayList<Friend> getFriendList() {
		return friendList;
	}

	public void setFriendList(ArrayList<Friend> friendList) {
		this.friendList = friendList;
	}

}
