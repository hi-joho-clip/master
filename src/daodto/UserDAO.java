package daodto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection con;

	public UserDAO() throws Exception {
		DBManagerAdmin dbManagerAdmin = DBManagerAdmin.getDBManagerAdmin();
		this.con = dbManagerAdmin.getConnection();
	}

	/**
	 * ユーザ情報を登録（修正必要）
	 * マイリスト作成、お気に入りタグ作成
	 * @param userDTO
	 * @throws Exception
	 */
	public int add(UserDTO userDTO) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insert_user_id = 0;
		String sql = "INSERT INTO users values(0,?,?,?,?,?,now(),now(),?);";
		String mylist_sql ="INSERT into mylists values(0,?,false)";
		String tag_sql = "insert into tags values(0,?,now(),now(),null,?)";
		String last_insert_sql = "SELECT LAST_INSERT_ID() AS LAST;";

		try {
			con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userDTO.getUser_name());
				pstmt.setString(2, userDTO.getNickname());
				pstmt.setString(3, ToSHA2.getDigest(userDTO.getUser_name() + userDTO.getPassword()));
				pstmt.setString(4, userDTO.getMailaddress());
				pstmt.setInt(5, userDTO.getFriend_flag());
				pstmt.setString(6, userDTO.getBirth());
				pstmt.executeUpdate();

				// 自動採番されたIDを取得
				pstmt = con.prepareStatement(last_insert_sql);
				rs = pstmt.executeQuery();
				if (rs != null && rs.next()){
	                insert_user_id = rs.getInt("LAST");
	            }

				pstmt = con.prepareStatement(mylist_sql);
				pstmt.setInt(1, insert_user_id);
				pstmt.executeUpdate();

				pstmt = con.prepareStatement(tag_sql);
				pstmt.setString(1, "お気に入り");
				pstmt.setInt(2, insert_user_id);
				pstmt.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
			pstmt.close();
			con.setAutoCommit(true);
			throw new Exception();
		}finally {
			con.setAutoCommit(true);
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return insert_user_id;
	}

	/**
	 * メールアドレスとパスワードからユーザ名検索
	 * @param userDTO
	 * @return
	 */
	public UserDTO searchUserName(String mailaddress,String user_name, String password) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = new UserDTO();
		String sql = "select * from users where mailaddress = ? and  password = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mailaddress);
			pstmt.setString(2, ToSHA2.getDigest(user_name + password));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDTO.setUser_id(rs.getInt("user_id"));
				userDTO.setUser_name(rs.getString("user_name"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setMailaddress(rs.getString("mailaddress"));
				userDTO.setFriend_flag(rs.getInt("friend_flag"));
				userDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				userDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				userDTO.setBirth(rs.getString("birth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(userDTO.getUser_id());
		return userDTO;

	}

	/**
	 * メールアドレスとユーザ名からパスワード検索
	 * @param userDTO
	 * @return 有効：true 無効：false
	 */
	public UserDTO searchPassword(String user_name, String mailaddress) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = new UserDTO();
		String sql = "select * from users where mailaddress = ? and user_name = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mailaddress);
			pstmt.setString(2, user_name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDTO.setUser_id(rs.getInt("user_id"));
				userDTO.setUser_name(rs.getString("user_name"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setMailaddress(rs.getString("mailaddress"));
				userDTO.setFriend_flag(rs.getInt("friend_flag"));
				userDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				userDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				userDTO.setBirth(rs.getString("birth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userDTO;

	}

	/**
	 * ユーザ名の重複チェック
	 * @param user_name
	 * @return 有効：true 無効：false
	 * @throws Exception
	 */
	public boolean checkUserName(String user_name) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from users where user_name = ? ";

		boolean flag = true;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 存在する
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * メールアドレスの重複チェック
	 * @param mailaddress
	 * @return 有効：true 無効：false
	 */
	public boolean checkMailAddress(String mailaddress) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from users where mailaddress = ? ";

		boolean flag = true;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mailaddress);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 存在する
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * ユーザ情報の削除（修正必要）
	 * ユーザの削除の前にシェアマイリストの削除が必要
	 * @param user_id
	 * @throws Exception
	 */
	public boolean delete(int user_id) throws Exception {
		PreparedStatement pstmt = null;
		String mylists_sql = "delete from mylists where user_id = ?";
		String users_sql = "delete from users where user_id = ?";

		boolean flag =false;

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(mylists_sql);
			pstmt.setInt(1, user_id);
			System.out.println(pstmt);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement(users_sql);
			pstmt.setInt(1, user_id);
			System.out.println(pstmt);
			pstmt.executeUpdate();

			con.commit();
			flag = true;


		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
			throw new Exception();
		} finally {
			con.setAutoCommit(true);
			try {
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;

	}

	/**
	 * ユーザ情報を更新
	 * @param userDTO
	 * @throws Exception
	 */
	public boolean update(UserDTO userDTO) throws Exception {
		PreparedStatement pstmt = null;
		String sql = "update users set user_name = ?, nickname = ?," +
				" password = ?, mailaddress = ?, " +
				" friend_flag = ?, modified = now(), birth = ?" +
				" where user_id = ?";

		int state = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userDTO.getUser_name());
			pstmt.setString(2, userDTO.getNickname());
			// パスワードはユーザ名＋パスワードでハッシュ化
			pstmt.setString(3, ToSHA2.getDigest(userDTO.getUser_name() + userDTO.getPassword()));
			pstmt.setString(4, userDTO.getMailaddress());
			pstmt.setInt(5, userDTO.getFriend_flag());
			pstmt.setString(6,userDTO.getBirth());
			// ユーザID
			pstmt.setInt(7, userDTO.getUser_id());
			state = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (state == 1) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * ユーザIDから1件のユーザ情報を取得
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public UserDTO view(int user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = new UserDTO();
		String sql = "select * from users where user_id  = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDTO.setUser_id(rs.getInt("user_id"));
				userDTO.setUser_name(rs.getString("user_name"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setMailaddress(rs.getString("mailaddress"));
				userDTO.setFriend_flag(rs.getInt("friend_flag"));
				userDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				userDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				userDTO.setBirth(rs.getString("birth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userDTO;

	}


	/**
	 * メールアドレスから1件のユーザ情報を取得
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public UserDTO viewMail(String mailaddress) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = new UserDTO();
		String sql = "select * from users where mailaddress  = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mailaddress);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDTO.setUser_id(rs.getInt("user_id"));
				userDTO.setUser_name(rs.getString("user_name"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setMailaddress(rs.getString("mailaddress"));
				userDTO.setFriend_flag(rs.getInt("friend_flag"));
				userDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				userDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				userDTO.setBirth(rs.getString("birth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userDTO;

	}

	/**
	 * メールアドレスとパスワードから検索
	 * @param user_name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public UserDTO loginMail(String mailaddress, String password) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = new UserDTO();
		String sql = "select * from users where mailaddress = ? and password = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mailaddress);
			pstmt.setString(2, password);
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDTO.setUser_id(rs.getInt("user_id"));
				userDTO.setUser_name(rs.getString("user_name"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setMailaddress(rs.getString("mailaddress"));
				userDTO.setFriend_flag(rs.getInt("friend_flag"));
				userDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				userDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				userDTO.setBirth(rs.getString("birth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userDTO;
	}

	/**
	 * ユーザ名とパスワードから検索
	 * @param user_name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public UserDTO login(String user_name, String password) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = new UserDTO();
		String sql = "select * from users where user_name  = ? and password = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDTO.setUser_id(rs.getInt("user_id"));
				userDTO.setUser_name(rs.getString("user_name"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setMailaddress(rs.getString("mailaddress"));
				userDTO.setFriend_flag(rs.getInt("friend_flag"));
				userDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				userDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				userDTO.setBirth(rs.getString("birth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userDTO;
	}



	/**
	 * ユーザ名とメールアドレスと生年月日から(パスワード再発行で利用)
	 * @param user_name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public UserDTO PasswordSearch(String mailaddress, String username, String birth) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = new UserDTO();
		String sql = "select * from users where mailaddress = ? and user_name = ? and birth = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mailaddress);
			pstmt.setString(2, username);
			pstmt.setString(3, birth);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userDTO.setUser_id(rs.getInt("user_id"));
				userDTO.setUser_name(rs.getString("user_name"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setMailaddress(rs.getString("mailaddress"));
				userDTO.setFriend_flag(rs.getInt("friend_flag"));
				userDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				userDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				userDTO.setBirth(rs.getString("birth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userDTO;
	}

	/**
	 *
	 * フレンドフラグを許可(0)に設定
	 * @param user_id
	 * @return flag true:SQLが正常に実行
	 * @throws Exception
	 */
	public boolean friend_accept(int user_id) throws Exception {
		PreparedStatement pstmt = null;
		String sql = "update users set friend_flag = 0 where user_id = ?";

		int state = 0;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			state = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (state == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * フレンドフラグを拒否(1)に設定
	 * @param user_id
	 * @throws Exception
	 */
	public boolean friend_deny(int user_id) throws Exception {
		PreparedStatement pstmt = null;
		String sql = "update users set friend_flag = 1 where user_id = ?";

		int state = 0;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			state = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (state == 0) {
			return false;
		} else {
			return true;
		}
	}



	/**
	 * パスワード再発行
	 * @param User_id
	 * @param Password
	 * @return
	 * @throws Exception
	 */
	public boolean updatePassword(int User_id,String User_name, String Password) throws Exception {
		PreparedStatement pstmt = null;
		String sql = "update users set password = ?, modified = now()" +
				" where user_id = ?";

		int state = 0;
		try {
			pstmt = con.prepareStatement(sql);
			// パスワードはユーザ名＋パスワードでハッシュ化
			pstmt.setString(1, ToSHA2.getDigest(User_name + Password));
			// ユーザID
			pstmt.setInt(2, User_id);
			state = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (state == 1) {
			return true;
		} else {
			return false;
		}

	}

}