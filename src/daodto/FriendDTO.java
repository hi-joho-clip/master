package daodto;

import java.util.Date;

public class FriendDTO {

	private int friend_id;
	private int own_user_id;
	private int friend_user_id;
	private int share_id;
	private int status;
	private Date acceptdate;
	private Date created;
	private Date modified;


//	//private FriendDAO friendDAO = new FriendDAO();
//
//	ArrayList<FriendDTO> friendlists = new ArrayList<FriendDTO>();

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

//	public FriendDTO getFriendDTO() {
//		return friendDTO;
//	}
//
//	public void setFriendDTO(FriendDTO friendDTO) {
//		this.friendDTO = friendDTO;
//	}

//	public FriendDAO getFriendDAO() {
//		return friendDAO;
//	}
//
//	public void setFriendDAO(FriendDAO friendDAO) {
//		this.friendDAO = friendDAO;
//	}

//	public ArrayList<FriendDTO> getFriendlists() {
//		return friendlists;
//	}
//
//	public void setFriendlists(ArrayList<FriendDTO> friendlists) {
//		this.friendlists = friendlists;
//	}



}
