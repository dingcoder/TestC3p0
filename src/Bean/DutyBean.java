package Bean;

public class DutyBean {
	private int duty_id;
	private String duty_name;
	private String message_send_teacher;
	private int message_send_parent;
	private int grade_import;
	private int grade_look;
	private int record_look;
	private int information_update;
	private int leave_check;
	private int visit_check;

	public String getSimpDutyName() {
		String dutyName = getDuty_name();
		return dutyName.split(",")[0];
	}

	public String getRightFullDutyName() {
		String dutyName = getDuty_name();
		String[] sArr = dutyName.split(",");
		String rightFullDutyName = "";
		for (String s : sArr) {
			rightFullDutyName += s;
		}
		return rightFullDutyName;
	}

	public int getDuty_id() {
		return duty_id;
	}

	public void setDuty_id(int duty_id) {
		this.duty_id = duty_id;
	}

	public String getDuty_name() {
		return duty_name;
	}

	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}

	public String getMessage_send_teacher() {
		return message_send_teacher;
	}

	public void setMessage_send_teacher(String message_send_teacher) {
		this.message_send_teacher = message_send_teacher;
	}

	public int getMessage_send_parent() {
		return message_send_parent;
	}

	public void setMessage_send_parent(int message_send_parent) {
		this.message_send_parent = message_send_parent;
	}

	public int getGrade_import() {
		return grade_import;
	}

	public void setGrade_import(int grade_import) {
		this.grade_import = grade_import;
	}

	public int getGrade_look() {
		return grade_look;
	}

	public void setGrade_look(int grade_look) {
		this.grade_look = grade_look;
	}

	public int getRecord_look() {
		return record_look;
	}

	public void setRecord_look(int record_look) {
		this.record_look = record_look;
	}

	public int getInformation_update() {
		return information_update;
	}

	public void setInformation_update(int information_update) {
		this.information_update = information_update;
	}

	public int getLeave_check() {
		return leave_check;
	}

	public void setLeave_check(int leave_check) {
		this.leave_check = leave_check;
	}

	public int getVisit_check() {
		return visit_check;
	}

	public void setVisit_check(int visit_check) {
		this.visit_check = visit_check;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duty_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DutyBean other = (DutyBean) obj;
		if (duty_id != other.duty_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DutyBean [duty_id=" + duty_id + ", duty_name=" + duty_name + ", message_send_teacher="
				+ message_send_teacher + ", message_send_parent=" + message_send_parent + ", grade_import="
				+ grade_import + ", grade_look=" + grade_look + ", record_look=" + record_look + ", information_update="
				+ information_update + ", leave_check=" + leave_check + ", visit_check=" + visit_check + "]";
	}

}
