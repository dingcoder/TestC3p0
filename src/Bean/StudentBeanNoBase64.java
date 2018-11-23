package Bean;


/*
 * 只读取关键信息的学生Bean
 * @author:Karoy
 */

public class StudentBeanNoBase64 {
	private int id ;
	private String course_id;
	private String student_name;
	private String parent1_id;
	private String parent2_id;
	
	public StudentBeanNoBase64() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "StutentyBeanNoBase64 [id=" + id + ", course_id=" + course_id + ", student_name=" + student_name
				+ ", parent1_id=" + parent1_id + ", parent2_id=" + parent2_id + "]";
	}
	
}
