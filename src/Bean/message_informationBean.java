package Bean;

import java.util.Date;

public class message_informationBean {
    private int message_id;
    private String message_content;
    private int method;
    private String teacher_id;
    private String sender_identity;
    private String course_id;
    private int target;
    private Date message_time;

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getSender_identity() {
        return sender_identity;
    }

    public void setSender_identity(String sender_identity) {
        this.sender_identity = sender_identity;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Date getMessage_time() {
        return message_time;
    }

    public void setMessage_time(Date message_time) {
        this.message_time = message_time;
    }

    @Override
    public String toString() {
        return "message_informationBean{" +
                "message_id=" + message_id +
                ", message_content='" + message_content + '\'' +
                ", method=" + method +
                ", teacher_id='" + teacher_id + '\'' +
                ", sender_identity='" + sender_identity + '\'' +
                ", course_id='" + course_id + '\'' +
                ", target=" + target +
                ", message_time=" + message_time +
                '}';
    }
}
