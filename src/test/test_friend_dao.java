package test;

import java.util.ArrayList;

import daodto.FriendDAO;
import daodto.UserDTO;

public class test_friend_dao {

	public static void main(String[] args) {


		//test_serch(25, "");
		//test_serch(2, "9");
		//test_serch(24,"");

		//test_addRequest(2, 14);
		//test_addRequest(2, 30);


		//test_acceptRequest(2, 9);
//		test_acceptRequest(2,30);

		//test_deleteFriend(2,18);

		//test_deleteRequest(2,40);
	}

	static public void test_deleteFriend(int own_user_id, int friend_user_id) {

		try {
			FriendDAO friendDAO = new FriendDAO();
			System.out.println(friendDAO.deleteFriend(own_user_id, friend_user_id));

		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	static public void test_serch(int own_user_id, String user_name) {

		try {
			FriendDAO friendDAO = new FriendDAO();

			ArrayList<UserDTO> userlist = new ArrayList<UserDTO>();
			userlist = friendDAO.search(own_user_id, user_name);

			for (UserDTO userDTO : userlist) {
				System.out.println(userDTO.getUser_id() + "," + userDTO.getUser_name());
			}
		} catch (Exception e) {
			e.getStackTrace();

		}

	}
	static public void test_addRequest(int own_user_id, int friend_user_id) {
		try {
			FriendDAO friendDAO = new FriendDAO();

			System.out.println(friendDAO.addRequest(own_user_id, friend_user_id));
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	static public void test_acceptRequest(int own_user_id, int friend_user_id) {

		try {
			FriendDAO friendDAO = new FriendDAO();

			System.out.println(friendDAO.acceptRequest(own_user_id, friend_user_id));

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	static public void test_deleteRequest(int own_user_id, int friend_user_id) {

		try {
			FriendDAO friendDAO = new FriendDAO();

			System.out.println(friendDAO.deleteRequest(own_user_id, friend_user_id));
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
