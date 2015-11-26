package test;

import beansdomain.User;

public class test_beans {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		test_addUser();

		//test_updateUser_username(25, "nagao");

		//test_updateUser_mailaddress();

		//test_updateUserPassword();

		//test_updateUser_nickname();

		//test_friend_accept(25);

		//test_friend_deny();

	}

	static public void test_friend_accept(int user_id) {

		try {
			User userBean = new User(user_id);
			userBean.friend_accept();

			if (userBean.getErrorMessages().containsKey("friend_accept")) {
				System.out.println(userBean.getErrorMessages().get("friend_accept"));
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	static public void test_friend_deny() {
		try {
			User userBean = new User(22);
			userBean.friend_deny();

			if (userBean.getErrorMessages().containsKey("friend_deny")) {
				System.out.println(userBean.getErrorMessages().get("friend_deny"));
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	static public void test_updateUserPassword() {
		try {
			User userBean = new User(1);

			userBean.setPassword("password");
			userBean.updatePassword();
			if (userBean.getErrorMessages().containsKey("password")) {
				System.out.println(userBean.getErrorMessages().get("password"));
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	static public void test_updateUser_mailaddress() {
		try {
			User userBean = new User(22);
			userBean.setMailaddress("gori2gori222@gmail.com");
			userBean.updateMailaddress();

			if (userBean.getErrorMessages().containsKey("mailaddress")) {
				System.out.println(userBean.getErrorMessages().get("mailaddress"));
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	static public void test_updateUser_nickname() {

		try {
			User userBean = new User(24);
			userBean.setNickname("YAJUSENPAI");
			userBean.updateNickname();

			if (userBean.getErrorMessages().containsKey("nickname")) {
				System.out.println(userBean.getErrorMessages().get("nickname"));
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	static public void test_updateUser_username(int user_id, String user_name) {

		try {
			User userBean = new User(user_id);

			userBean.setUser_name(user_name);
			userBean.updateUserName();

			if (userBean.getErrorMessages().containsKey("user_name")) {
				System.out.println(userBean.getErrorMessages().get("user_name"));
			}
			if (userBean.getErrorMessages().containsKey("mailaddress")) {
				System.out.println(userBean.getErrorMessages().get("mailaddress"));
			}
			if (userBean.getErrorMessages().containsKey("nickname")) {
				System.out.println(userBean.getErrorMessages().get("nickname"));
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	static public void test_addUser() {

		try {
			User userBean = new User();

			userBean.setUser_name("gorigor i27");
			userBean.setPassword("password");
			userBean.setMailaddress("gorigoaaaaaari@gori.com");
			userBean.setNickname("YAJISENPaaAI");
			userBean.setFriend_flag(0);
			userBean.addUser();

			if (userBean.getErrorMessages().containsKey("user_name")) {
				System.out.println(userBean.getErrorMessages().get("user_name"));
			}
			if (userBean.getErrorMessages().containsKey("mailaddress")) {
				System.out.println(userBean.getErrorMessages().get("mailaddress"));
			}
			if (userBean.getErrorMessages().containsKey("nickname")) {
				System.out.println(userBean.getErrorMessages().get("nickname"));
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
