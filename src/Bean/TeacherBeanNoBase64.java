package Bean;

import java.util.Date;

public class TeacherBeanNoBase64 {
	private String teacher_id;
	private String teacher_openid;
	private String teacher_head;
	private String teacher_nickname;
	private String course_group;
	private String teacher_identityid;
	private String teacher_name;
	private Date teacher_birthday;
	private String teacher_phone;
	private String teacher_password;
	private int admin_duty_id;
	private int course_duty_id;
	private String course_id;
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getTeacher_openid() {
		return teacher_openid;
	}
	public void setTeacher_openid(String teacher_openid) {
		this.teacher_openid = teacher_openid;
	}
	public String getTeacher_head() {
		return teacher_head;
	}
	public void setTeacher_head(String teacher_head) {
		this.teacher_head = teacher_head;
	}
	public String getTeacher_nickname() {
		return teacher_nickname;
	}
	public void setTeacher_nickname(String teacher_nickname) {
		this.teacher_nickname = teacher_nickname;
	}
	public String getCourse_group() {
		return course_group;
	}
	public void setCourse_group(String course_group) {
		this.course_group = course_group;
	}
	public String getTeacher_identityid() {
		return teacher_identityid;
	}
	public void setTeacher_identityid(String teacher_identityid) {
		this.teacher_identityid = teacher_identityid;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public Date getTeacher_birthday() {
		return teacher_birthday;
	}
	public void setTeacher_birthday(Date teacher_birthday) {
		this.teacher_birthday = teacher_birthday;
	}
	public String getTeacher_phone() {
		return teacher_phone;
	}
	public void setTeacher_phone(String teacher_phone) {
		this.teacher_phone = teacher_phone;
	}
	public String getTeacher_password() {
		return teacher_password;
	}
	public void setTeacher_password(String teacher_password) {
		this.teacher_password = teacher_password;
	}
	public int getAdmin_duty_id() {
		return admin_duty_id;
	}
	public void setAdmin_duty_id(int admin_duty_id) {
		this.admin_duty_id = admin_duty_id;
	}
	public int getCourse_duty_id() {
		return course_duty_id;
	}
	public void setCourse_duty_id(int course_duty_id) {
		this.course_duty_id = course_duty_id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	@Override
	public String toString() {
		return "TeacherBeanNoBase64 [teacher_id=" + teacher_id + ", teacher_openid=" + teacher_openid
				+ ", teacher_head=" + teacher_head + ", teacher_nickname=" + teacher_nickname + ", course_group="
				+ course_group + ", teacher_identityid=" + teacher_identityid + ", teacher_name=" + teacher_name
				+ ", teacher_birthday=" + teacher_birthday + ", teacher_phone=" + teacher_phone + ", teacher_password="
				+ teacher_password + ", admin_duty_id=" + admin_duty_id + ", course_duty_id=" + course_duty_id
				+ ", course_id=" + course_id + "]";
	}
	
	
}
