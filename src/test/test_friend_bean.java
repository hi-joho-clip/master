package test;

import java.util.ArrayList;

import beansdomain.Friend;
import beansdomain.User;

public class test_friend_bean {


	public static void main(String args[]) {


		/*
		 * :search
		 * :friend_list
		 * acceptfriend
		 * denyfriend
		 * deleterequest
		 * deletefriend
		 */

		test_friend_search(2, "");
		//test_friend_list(2);

	}

	public static void test_friend_list(int own_user_id) {

		try {
			ArrayList<Friend> friendList = new ArrayList<Friend>();
			Friend friBean = new Friend();

			friendList = friBean.getFriendList(own_user_id);

			for (Friend fb : friendList) {
				System.out.print("user_name:" + fb.getUser_name());
				System.out.print(",nickname:" + fb.getNickname());
				if (fb.getStatus() == 3) {

					System.out.print(",status:" + "friend");
				} else {
					System.out.print(",status:" + fb.getStatus());
				}
				System.out.println(",share_id:" + fb.getShare_id());
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	public static void test_friend_search(int own_user_id, String str) {

		try {
			ArrayList<User> userList = new ArrayList<User>();
			Friend friBean = new Friend();

			userList = friBean.searchFriend(own_user_id, str);

			for (User fb : userList) {
				System.out.print("user_name:" + fb.getUser_name());
				System.out.println(",nickname:" + fb.getNickname());
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

	}
}
