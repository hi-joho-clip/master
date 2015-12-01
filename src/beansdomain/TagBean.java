package beansdomain;

import java.util.ArrayList;
import java.util.Date;

import daodto.TagDAO;
import daodto.TagDTO;

public class TagBean {
	private int    tag_id;
	private String tag_body;
	private Date   created;
	private Date   modified;
	private Date   lastest;
	private int    user_id;
	private TagDTO tagDTO;
	private TagDAO tagDAO;
	private ArrayList<TagDTO> taglists;
	/**
	 * タグの追加
	 * @return
	 * @throws Exception
	 */
	public boolean addTag() throws Exception{
		this.tagDAO = new TagDAO();
		setTagDTO();
		return this.tagDAO.add(this.tagDTO);
	}
	/**
	 * タグの削除
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTag(int tag_id) throws Exception{
		this.tagDAO = new TagDAO();
		return this.tagDAO.delete(tag_id);

	}
	/**
	 * タグの更新
	 * @return
	 * @throws Exception
	 */
	public boolean updateTag() throws Exception{
		this.tagDAO = new TagDAO();
		setTagDTO();
		return this.tagDAO.update(this.tagDTO);
	}
	/**
	 * タグの一覧表示
	 * @return
	 * @throws Exception
	 */
	public ArrayList<TagBean> viewTagList(int user_id) throws Exception{
		ArrayList<TagBean> tagList = new ArrayList<TagBean>();
		this.tagDAO = new TagDAO();
		taglists = tagDAO.lists(user_id);
		for(int i=0; i< taglists.size(); i++){
			TagBean tagBean = new TagBean();
			tagBean.setTag_id(taglists.get(i).getTag_id());
			tagBean.setTag_body(taglists.get(i).getTag_body());
			tagBean.setLastest(taglists.get(i).getLastest());
			tagList.add(tagBean);
		}
		return tagList;
	}
	/**
	 * 記事に付与されたタグを取得
	 * @return
	 * @throws Exception
	 */
	public ArrayList<TagBean> viewExistingTag(int user_id , int article_id) throws Exception{
		ArrayList<TagBean> tagList = new ArrayList<TagBean>();
		this.tagDAO = new TagDAO();
		taglists = tagDAO.getTagLists(user_id,article_id);
		for(int i=0; i< taglists.size(); i++){
			TagBean tagBean = new TagBean();
			tagBean.setTag_id(taglists.get(i).getTag_id());
			tagBean.setTag_body(taglists.get(i).getTag_body());
			tagList.add(tagBean);
		}
		return tagList;
	}

	public void setTagDTO(){
		this.tagDTO = new TagDTO();
		this.tagDTO.setTag_id(this.tag_id);
		this.tagDTO.setTag_body(this.tag_body);
		this.tagDTO.setCreated(this.created);
		this.tagDTO.setModified(this.modified);
		this.tagDTO.setLastest(this.lastest);
		this.tagDTO.setUser_id(this.user_id);
	}

	public int getTag_id() {
		return tag_id;
	}

	public String getTag_body() {
		return tag_body;
	}

	public Date getCreated() {
		return created;
	}

	public Date getModified() {
		return modified;
	}

	public Date getLastest() {
		return lastest;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public void setTag_body(String tag_body) {
		this.tag_body = tag_body;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public void setLastest(Date lastest) {
		this.lastest = lastest;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
