package daodto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TagDAO {
	private Connection con;

	public TagDAO() throws Exception {
		DBManagerAdmin dbManagerAdmin = DBManagerAdmin.getDBManagerAdmin();
		this.con = dbManagerAdmin.getConnection();
	}

	/**
	 * タグの追加
	 * @param tag
	 * @return
	 * @throws Exception
	 */
	public boolean add(TagDTO tag) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		String hantei_sql = "SELECT * FROM tags WHERE user_id = ? AND tag_body = ?";
		String sql = "INSERT INTO tags(tag_body,created,modified,lastest,user_id) VALUES(?,now(),now(),now(),?)";

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(hantei_sql);
			pstmt.setInt(1, tag.getUser_id());
			pstmt.setString(2, tag.getTag_body());
			rs = pstmt.executeQuery();
			// 検索結果がない場合インサート可能
			if (!rs.next()) {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, tag.getTag_body());
				pstmt.setInt(2, tag.getUser_id());
				pstmt.executeUpdate();

				con.commit();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
			throw new Exception();
		}finally{
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
	 * タグの更新
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean update(TagDTO tag) throws Exception {
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = "UPDATE tags SET tag_body = ?,modified = now() WHERE tag_id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tag.getTag_body());
			pstmt.setInt(2, tag.getTag_id());
			pstmt.executeUpdate();
			flag = true;
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
		return flag;
	}

	/**
	 * タグの削除
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean delete(int tag_id,int user_id) throws Exception {
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = "DELETE FROM tags WHERE tag_id =? AND user_id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, tag_id);
			pstmt.setInt(2, user_id);
			pstmt.executeUpdate();
			flag = true;
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
		return flag;
	}

	/**
	 * タグの一覧表示
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<TagDTO> lists(int user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<TagDTO> tagList = new ArrayList<TagDTO>();
		String sql = "SELECT * FROM tags WHERE tag_body <> 'お気に入り' AND user_id = ? order by lastest desc";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TagDTO tag = new TagDTO();
				tag.setTag_id(rs.getInt("tag_id"));
				tag.setTag_body(rs.getString("tag_body"));
				tag.setLastest(rs.getDate("lastest"));
				tagList.add(tag);
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
		return tagList;
	}

	/**
	 * 記事に付与されたタグを取得
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<TagDTO> getTagLists(int user_id, int article_id)
			throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<TagDTO> tagList = new ArrayList<TagDTO>();
		String sql = "SELECT tag_id,tag_body FROM tags WHERE tags.tag_body <> 'お気に入り' AND tags.user_id = ? AND tag_id IN "
				+ "(SELECT tag_id FROM articles A,article_tag T WHERE A.article_id = ? AND T.article_id = ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, article_id);
			pstmt.setInt(3, article_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TagDTO tag = new TagDTO();
				tag.setTag_id(rs.getInt("tag_id"));
				tag.setTag_body(rs.getString("tag_body"));
				tagList.add(tag);
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
		return tagList;
	}

	/**
	 * 更新日時が新しいタグの20件を取得
	 *
	 * @return
	 * @throws Exception
	 */
	public ArrayList<TagDTO> usingTags(int user_id) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<TagDTO> tagList = new ArrayList<TagDTO>();
		String sql = "SELECT * FROM tags WHERE tag_body <> 'お気に入り' AND user_id = ? ORDER BY lastest DESC LIMIT 20";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TagDTO tag = new TagDTO();
				tag.setTag_id(rs.getInt("tag_id"));
				tag.setTag_body(rs.getString("tag_body"));
				tag.setLastest(rs.getDate("lastest"));
				tagList.add(tag);
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
		return tagList;
	}

}
