package Bean;

import java.sql.Date;

public class ExamBean {
  private int exam_id;
  private Date exam_time;
  private String exam_name;
  private String course_id;
  private String teacher_id;
  private String table_name;
  private int isfinish;
  private String subjectlist;
  public ExamBean() {
	super();
  }
  
  public int getExam_id() {
	return exam_id;
  }
  public void setExam_id(int exam_id) {
	this.exam_id = exam_id;
  }
  public Date getExam_time() {
    return exam_time;
  }
  public void setExam_time(Date exam_time) {
	this.exam_time = exam_time;
  }
  public String getExam_name() {
	return exam_name;
  }
  public void setExam_name(String exam_name) {
    this.exam_name = exam_name;
  }
  public String getCourse_id() {
	return course_id;
  }
  public void setCourse_id(String course_id) {
	this.course_id = course_id;
  }
  public String getTeacher_id() {
	return teacher_id;
  }
  public void setTeacher_id(String teacher_id) {
	this.teacher_id = teacher_id;
  }
  public String getTable_name() {
	return table_name;
  }
  public void setTable_name(String table_name) {
	this.table_name = table_name;
  }
  
  public int getIsfinish() {
	return isfinish;
}

public void setIsfinish(int isfinish) {
	this.isfinish = isfinish;
}

public String getSubjectlist() {
	return subjectlist;
}

public void setSubjectlist(String subjectlist) {
	this.subjectlist = subjectlist;
}

@Override
  public boolean equals(Object obj) {
	ExamBean tmp = (ExamBean) obj;
	if (this.exam_id==tmp.exam_id) return true;
	else return false;
  }	  

}
