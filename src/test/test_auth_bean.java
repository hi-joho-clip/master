package test;

import beansdomain.UserAuth;

public class test_auth_bean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		test_login();
	}


	static public void test_login() {
		try {
			UserAuth authuserBean = new UserAuth();

			System.out.println(authuserBean.loginMailaddress("2133@gmail.com", "password"));



		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
