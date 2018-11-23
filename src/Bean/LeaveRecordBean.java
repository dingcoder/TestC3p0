package Bean;


public class LeaveRecordBean {
	private int leave_id;
	private String parent_id;
	private String student_id;
	private String teacher_id;
	private String leave_time;
	private String start_time;
	private String end_time;
	private String leave_content;
	private int isauthority;

	public int getLeave_id() {
		return leave_id;
	}

	public void setLeave_id(int leave_id) {
		this.leave_id = leave_id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getLeave_time() {
		return leave_time;
	}

	public void setLeave_time(String leave_time) {
		this.leave_time = leave_time;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getLeave_content() {
		return leave_content;
	}

	public void setLeave_content(String leave_content) {
		this.leave_content = leave_content;
	}

	public int getIsauthority() {
		return isauthority;
	}

	public void setIsauthority(int isauthority) {
		this.isauthority = isauthority;
	}

	@Override
	public String toString() {
		return "LeaveRecordBean{" +
				"leave_id=" + leave_id +
				", parent_id='" + parent_id + '\'' +
				", student_id='" + student_id + '\'' +
				", teacher_id='" + teacher_id + '\'' +
				", leave_time='" + leave_time + '\'' +
				", start_time='" + start_time + '\'' +
				", end_time='" + end_time + '\'' +
				", leave_content='" + leave_content + '\'' +
				", isauthority=" + isauthority +
				'}';
	}
}
