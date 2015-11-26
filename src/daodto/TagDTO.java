package daodto;

import java.util.Date;

public class TagDTO {
	private int    tag_id;
	private String tag_body;
	private Date   created;
	private Date   modified;
	private Date   lastest;
	private int    user_id;


public TagDTO(){
	this.tag_id=0;
	this.tag_body=null;
	this.created=null;
	this.modified=null;
	this.lastest=null;
	this.user_id=0;

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