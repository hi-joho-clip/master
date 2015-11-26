package test;

import java.util.Random;

import daodto.UserDAO;
import daodto.UserDTO;

public class test_dao {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO 自動生成されたメソッド・スタブ


		//view_test(9);

		//friend_accept_test(1);

		//friend_deny_test(1);

		//delete_test(15);


		for (int i = 0; i < 100 ; i++) {
		add_test(randam());
		}

		//update_test();

		//checkUserName("testku");
		//checkMailAddress("testa@gmail.com");

		//searchUserName("test@gmail.com", "password");

		//searchPassword("707", "mail@gmail.com");

		//login("707", "password");



	}


	public static void login(String user_name, String password) {

		try {
			UserDAO userDAO = new UserDAO();
			UserDTO userDTO = userDAO.login(user_name, password);

			System.out.println(userDTO.getNickname());

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public static void searchPassword(String user_name, String mailaddress) {


		try {
			UserDAO userDAO = new UserDAO();
			UserDTO userDTO = userDAO.searchPassword(user_name, mailaddress);

			System.out.println(userDTO.getPassword());

		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	public static void searchUserName(String mailaddress, String password) {


		try {
			UserDAO userDAO = new UserDAO();
			UserDTO userDTO = userDAO.searchUserName(mailaddress, password);

			System.out.println(userDTO.getUser_name());

		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	public static void checkUserName(String user_name) throws Exception{

		UserDAO userDAO = new UserDAO();
		System.out.println(userDAO.checkUserName(user_name));

	}

	public static void checkMailAddress(String mailaddress) throws Exception{
		UserDAO userDAO = new UserDAO();
		System.out.println(userDAO.checkMailAddress(mailaddress));
	}


	public static void update_test() throws Exception{

		UserDAO userDAO = new UserDAO();
		UserDTO userDTO = new UserDTO();

		userDTO = userDAO.view(17);
		userDTO.setNickname("gorigori27");
		userDTO.setUser_name("0");
		System.out.println(userDAO.update(userDTO));

	}

	private static String randam() {
	  Random rnd = new Random();
      int ran = rnd.nextInt(10000);
      return Integer.toString(ran);

	}

	static public void delete_test(int user_id) throws Exception {
		UserDAO userDAO = new UserDAO();

		userDAO.delete(user_id);
	}

	static public void friend_accept_test(int user_id) throws Exception{

		UserDAO userDAO = new UserDAO();

		System.out.println(userDAO.friend_accept(user_id));
	}

	static public void friend_deny_test(int user_id) throws Exception{
		UserDAO userDAO = new UserDAO();

		System.out.println(userDAO.friend_deny(user_id));
	}


	static public void view_test(int n) throws Exception{

		UserDTO userDTO = new UserDTO();
		UserDAO userDAO = new UserDAO();

		userDTO = userDAO.view(n);

		System.out.println(userDTO.getUser_name());
		System.out.println(userDTO.getNickname());
		System.out.println(userDTO.getMailaddress());
		System.out.println(userDTO.getCreated());

	}

	static public void add_test(String user_name) throws Exception{
		UserDTO userDTO = new UserDTO();

		userDTO.setUser_name(user_name);
		userDTO.setNickname("dummy" + user_name);
		userDTO.setPassword("password");
		userDTO.setMailaddress(user_name + "@gmail.com");
		userDTO.setFriend_flag(0);


		UserDAO userDAO = new UserDAO();

		System.out.println(userDAO.add(userDTO));
	}

//	public void test() throws Exception{
//
//
//		sample t_dao = new sample();
//
//		System.out.println(t_dao.testSelect());
//
//
//
//		Calendar cal = Calendar.getInstance();
//		java.util.Date util_date = cal.getTime();
//		System.out.println(util_date);
//
//
//
//		DateEncode de = new DateEncode();
//		java.sql.Date sql_date = de.DateToSQL(util_date);
//
//
//		System.out.println(de.DateToSQL(util_date).toString());
//		System.out.println(sql_date);
//
//		try{
//			  //1000ミリ秒Sleepする
//			  Thread.sleep(2000);
//			}catch(InterruptedException e){}
//
//		java.util.Date util2 = de.DateToUTIL(sql_date);
//		Calendar cal2 = Calendar.getInstance();
//		cal2.setTime(util2);
//		System.out.println(cal.getTime());
//	}

}
