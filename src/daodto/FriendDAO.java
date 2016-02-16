package daodto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FriendDAO {

	private Connection con;

	public FriendDAO() throws Exception {
		DBManagerAdmin dbManagerAdmin = DBManagerAdmin.getDBManagerAdmin();
		this.con = dbManagerAdmin.getConnection();
	}

	/*
	 * フレンドテーブルの状態コード：1＝申請中、2＝フレンド済み、3＝拒否済み
	 */

	/**
	 * ユーザ名からユーザのリスト検索
	 * フレンド申請を行っている場合、フレンド登録済みを除く
	 * @param user_name
	 * @return
	 */
	public ArrayList<UserDTO> search(int user_id, String nickname) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<UserDTO> userList = new ArrayList<UserDTO>();
		String sql = "select * from users left join ( " +
				"select friends.friend_user_id as f_user_id " +
				"from friends where friends.own_user_id = ? " +
				"and  friends.status in (1,2,3) " +
				") friend_temp " +
				"on f_user_id = users.user_id " +
				"where users.friend_flag = 0 " +
				"and users.nickname like ? " +
				"and f_user_id is null " +
				"and users.user_id != ? " +
				"limit 50";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setString(2, "%" + nickname + "%");
			pstmt.setInt(3, user_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUser_id(rs.getInt("user_id"));
				userDTO.setUser_name(rs.getString("user_name"));
				userDTO.setNickname(rs.getString("nickname"));
				userDTO.setPassword(rs.getString("password"));
				userDTO.setMailaddress(rs.getString("mailaddress"));
				userDTO.setFriend_flag(rs.getInt("friend_flag"));
				userDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				userDTO.setModified(DateEncode.toDate(rs.getString("modified")));
				userList.add(userDTO);
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
		System.out.println(userList.size());
		return userList;

	}

	/**
	 * フレンド申請 status = 2, 申請先 status = 1	(OK)
	 * @return
	 */
	public boolean addRequest(int own_user_id, int friend_user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String hantei_sql = "select * from friends where own_user_id = ? and friend_user_id = ? " +
				"or friend_user_id = ? and friend_user_id = ?";
		String sql = "INSERT INTO friends values(0,?,?,null,now(),null,2)";
		String sql2 = "INSERT INTO friends values(0,?,?,null,now(),null,1)";

		boolean flag = false;

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(hantei_sql);
			pstmt.setInt(1, own_user_id);
			pstmt.setInt(2, friend_user_id);
			pstmt.setInt(3, own_user_id);
			pstmt.setInt(4, friend_user_id);
			rs = pstmt.executeQuery();
			// 検索結果がない場合インサート可能
			if (!rs.next()) {
				// こっちが申請先のインサート
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, own_user_id);
				pstmt.setInt(2, friend_user_id);
				pstmt.executeUpdate();

				// こっちが申請元のインサート
				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, friend_user_id);
				pstmt.setInt(2, own_user_id);
				pstmt.executeUpdate();

				con.commit();
				flag = true;
			}

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
	 * フレンド申請の削除
	 * @param own_user_id
	 * @param friend_user_id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteRequest(int own_user_id, int friend_user_id) throws Exception {

		boolean flag = denyRequest(own_user_id, friend_user_id);
		return flag;
	}

	/**
	 * フレンド許可 status = 3 (完成）
	 * ここではマイリストの作成（ShareIDの発行も行う）
	 * @return
	 */
	public boolean acceptRequest(int own_user_id, int friend_user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insert_share_id = -1;

		// すでにフレンド登録済みかを判定
		String hantei_sql = "select * from friends where own_user_id = ? and friend_user_id = ? " +
				"and status = 3 " +
				"or friend_user_id = ? and own_user_id = ? " +
				"and status = 3";

		// マイリストを作成する（シェアフラッグ有効）
		String sql = "insert into mylists values(0, ?, true)";
		String last_insert_sql = "SELECT LAST_INSERT_ID() AS LAST;";

		// マイリストを作成に代わる
		String sql2 = "update friends set id = ? , acceptdate = now(), status = 3 " +
				"where own_user_id = ? and friend_user_id = ? " +
				"or friend_user_id = ? and own_user_id = ? ";

		boolean flag = false;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(hantei_sql);
			pstmt.setInt(1, own_user_id);
			pstmt.setInt(2, friend_user_id);
			pstmt.setInt(3, own_user_id);
			pstmt.setInt(4, friend_user_id);
			//System.out.println(pstmt);
			rs = pstmt.executeQuery();

			// 検索結果がない場合インサート可能
			if (!rs.next()) {

				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, own_user_id);
				pstmt.executeUpdate();

				// 自動採番されたIDを取得
				pstmt = con.prepareStatement(last_insert_sql);
				rs = pstmt.executeQuery();
				if (rs != null && rs.next()) {
					insert_share_id = rs.getInt("LAST");
				}

				pstmt = con.prepareStatement(sql2);
				pstmt.setInt(1, insert_share_id);
				pstmt.setInt(2, own_user_id);
				pstmt.setInt(3, friend_user_id);
				pstmt.setInt(4, own_user_id);
				pstmt.setInt(5, friend_user_id);
				pstmt.executeUpdate();

				con.commit();
				flag = true;
			}

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
	 * フレンド拒否 レコード削除（3は廃止）
	 * @return
	 */
	public boolean denyRequest(int own_user_id, int friend_user_id) throws Exception {
		PreparedStatement pstmt = null;

		String sql = "delete from friends where own_user_id = ? and friend_user_id = ? " +
				"or friend_user_id = ? and own_user_id = ?";

		boolean flag = false;
		int count = 0;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, own_user_id);
			pstmt.setInt(2, friend_user_id);
			pstmt.setInt(3, own_user_id);
			pstmt.setInt(4, friend_user_id);
			count = pstmt.executeUpdate();

			if (count != 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.getStackTrace();
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

		return flag;
	}

	/**
	 * フレンドの削除（修正必要）
	 * シェアしているマイリストIDを取得して、マイリストを削除→記事、画像、Article_tagのレコードが削除される
	 * 最終的にフレンドレコードを削除する
	 *
	 * @param own_user_id
	 * @param friend_user_id
	 * @throws Exception
	 */
	public boolean deleteFriend(int own_user_id, int friend_user_id) throws Exception {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int share_id = -1;
		boolean flag = false;

		String getid_sql = "select id from friends where own_user_id = ? and " +
				"friend_user_id = ?";

		String mylist_sql = "delete from mylists where id = ? and share_flag = true";

		String tag_sql = "delete from article_tag where id in (select article_id from articles where articles.id = ? )";

		//

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(getid_sql);
			pstmt.setInt(1, own_user_id);
			pstmt.setInt(2, friend_user_id);
			rs = pstmt.executeQuery();
			rs.next();
			share_id = rs.getInt("id");

			if (share_id >= 1) {
				// シェアIDが正常に取得できた場合
				// まずはタグIDを削除
				pstmt = con.prepareStatement(tag_sql);
				pstmt.setInt(1, share_id);
				pstmt.executeUpdate();

				// シェアIDが正常に取得できた場合
				// 次はマイリスト削除（記事削除され、画像も削除される
				pstmt = con.prepareStatement(mylist_sql);
				pstmt.setInt(1, share_id);
				pstmt.executeUpdate();
				con.commit();
				flag = true;
			}

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
	 *  フレンドとの一件のデータ取得
	 * @param own_user_id
	 * @param friend_user_id
	 * @return
	 */
	public FriendDTO view(int own_user_id, int friend_user_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FriendDTO friendDTO = new FriendDTO();

		return friendDTO;
	}

	/**
	 *
	 * 申請しているユーザとフレンドになったユーザの一覧を取得
	 * @param own_user_id
	 */
	public ArrayList<FriendDTO> friendList(int own_user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<FriendDTO> friendList = new ArrayList<FriendDTO>();
		// フレンドに申請済み、
		String friend_sql = "select * from friends where own_user_id = ? " +
				"order by status asc, created desc, acceptdate desc";

		try {
			pstmt = con.prepareStatement(friend_sql);
			pstmt.setInt(1, own_user_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FriendDTO friendDTO = new FriendDTO();
				friendDTO.setFriend_id(rs.getInt("friend_id"));
				friendDTO.setOwn_user_id(rs.getInt("own_user_id"));
				friendDTO.setFriend_user_id(rs.getInt("friend_user_id"));
				friendDTO.setShare_id(rs.getInt("id"));
				friendDTO.setStatus(rs.getInt("status"));
				friendDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				friendList.add(friendDTO);
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
		return friendList;
	}

	/**
	 *
	 * フレンドになったユーザの一覧を取得
	 * @param own_user_id
	 */
	public ArrayList<FriendDTO> friend(int own_user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<FriendDTO> friendList = new ArrayList<FriendDTO>();
		// フレンドに申請済み、
		String friend_sql = "select * from friends where own_user_id = ? AND status = 3 " +
				"order by status asc, created desc, acceptdate desc";

		try {
			pstmt = con.prepareStatement(friend_sql);
			pstmt.setInt(1, own_user_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FriendDTO friendDTO = new FriendDTO();
				friendDTO.setFriend_id(rs.getInt("friend_id"));
				friendDTO.setOwn_user_id(rs.getInt("own_user_id"));
				friendDTO.setFriend_user_id(rs.getInt("friend_user_id"));
				friendDTO.setShare_id(rs.getInt("id"));
				friendDTO.setStatus(rs.getInt("status"));
				friendDTO.setCreated(DateEncode.toDate(rs.getString("created")));
				friendList.add(friendDTO);
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
		return friendList;
	}
}
