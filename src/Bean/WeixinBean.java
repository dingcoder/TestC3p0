package Bean;

public class WeixinBean {
	private int id;
	private String openid;
	private String phone;
	private String uid;
	private String nickname;
	private String headurl;
	
	public WeixinBean() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	@Override
	public String toString() {
		return "WeixinBean [id=" + id + ", openid=" + openid + ", phone=" + phone + ", uid=" + uid + ", nickname="
				+ nickname + ", headurl=" + headurl + "]";
	}
	
}
