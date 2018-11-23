package Bean;

import java.util.Date;

public class student_informationBean {
    private int id;
    private String student_id;
    private String student_xueji;
    private String student_kaoji;
    private String course_id;
    private String student_identityid;
    private String student_name;
    private String student_gender;
    private Date student_birthday;
    private String student_base64;
    private String dormitory;
    private String parent1_id;
    private String parent2_id;
    private int isliveinschool;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_xueji() {
        return student_xueji;
    }

    public void setStudent_xueji(String student_xueji) {
        this.student_xueji = student_xueji;
    }

    public String getStudent_kaoji() {
        return student_kaoji;
    }

    public void setStudent_kaoji(String student_kaoji) {
        this.student_kaoji = student_kaoji;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getStudent_identityid() {
        return student_identityid;
    }

    public void setStudent_identityid(String student_identityid) {
        this.student_identityid = student_identityid;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_gender() {
        return student_gender;
    }

    public void setStudent_gender(String student_gender) {
        this.student_gender = student_gender;
    }

    public Date getStudent_birthday() {
        return student_birthday;
    }

    public void setStudent_birthday(Date student_birthday) {
        this.student_birthday = student_birthday;
    }

    public String getStudent_base64() {
        return student_base64;
    }

    public void setStudent_base64(String student_base64) {
        this.student_base64 = student_base64;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public String getParent1_id() {
        return parent1_id;
    }

    public void setParent1_id(String parent1_id) {
        this.parent1_id = parent1_id;
    }

    public String getParent2_id() {
        return parent2_id;
    }

    public void setParent2_id(String parent2_id) {
        this.parent2_id = parent2_id;
    }

    public int getIsliveinschool() {
        return isliveinschool;
    }

    public void setIsliveinschool(int isliveinschool) {
        this.isliveinschool = isliveinschool;
    }

    @Override
    public String toString() {
        return "student_informationBean{" +
                "id=" + id +
                ", student_id='" + student_id + '\'' +
                ", student_xueji='" + student_xueji + '\'' +
                ", student_kaoji='" + student_kaoji + '\'' +
                ", course_id='" + course_id + '\'' +
                ", student_identityid='" + student_identityid + '\'' +
                ", student_name='" + student_name + '\'' +
                ", student_gender='" + student_gender + '\'' +
                ", student_birthday=" + student_birthday +
                ", student_base64='" + student_base64 + '\'' +
                ", dormitory='" + dormitory + '\'' +
                ", parent1_id='" + parent1_id + '\'' +
                ", parent2_id='" + parent2_id + '\'' +
                ", isliveinschool=" + isliveinschool +
                '}';
    }

	public boolean isPictureEmpty() {
		if(student_base64 == null) return true;
		else return false;
	}
}
