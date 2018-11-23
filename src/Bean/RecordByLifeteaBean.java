package Bean;

import java.util.Date;

public class RecordByLifeteaBean {
    private String dormitory;
    private String course_id;
    private String student_name;
    private String type;
    private Date record_time;

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    @Override
    public String toString() {
        return "RecordByLifeteaBean{" +
                "dormitory='" + dormitory + '\'' +
                ", course_id='" + course_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", type='" + type + '\'' +
                ", record_time=" + record_time +
                '}';
    }
}
