package Bean;

public class MessageBean {
	//message_information.*
	private int message_id;
	private int target;
	private int duty_id;
	private String course_id;
	private int method;
	private String message_content;
	private String teacher_id;
	private String template_id;
	private String message_time;
	//teacher_information
	private String teacher_name;
	//message_template
	private String template_name;
	public int getMessage_id() {
		return message_id;
	}
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getDuty_id() {
		return duty_id;
	}
	public void setDuty_id(int duty_id) {
		this.duty_id = duty_id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public int getMethod() {
		return method;
	}
	public void setMethod(int method) {
		this.method = method;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getMessage_time() {
		return message_time;
	}
	public void setMessage_time(String message_time) {
		this.message_time = message_time;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	@Override
	public String toString() {
		return "MessageBean [message_id=" + message_id + ", target=" + target + ", duty_id=" + duty_id + ", course_id="
				+ course_id + ", method=" + method + ", message_content=" + message_content + ", teacher_id="
				+ teacher_id + ", template_id=" + template_id + ", message_time=" + message_time + ", teacher_name="
				+ teacher_name + ", template_name=" + template_name + "]";
	}
	
}
