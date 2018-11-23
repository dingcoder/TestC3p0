package Bean;

import java.util.Date;

public class PassRecordInformationBean {
    private String pass_record_time;
    private String pass_record_table;
    private Date create_time;

    public String getPass_record_time() {
        return pass_record_time;
    }

    public void setPass_record_time(String pass_record_time) {
        this.pass_record_time = pass_record_time;
    }

    public String getPass_record_table() {
        return pass_record_table;
    }

    public void setPass_record_table(String pass_record_table) {
        this.pass_record_table = pass_record_table;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "PassRecordInformationBean{" +
                "pass_record_time='" + pass_record_time + '\'' +
                ", pass_record_table='" + pass_record_table + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
