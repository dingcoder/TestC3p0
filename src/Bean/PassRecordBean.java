package Bean;

import java.util.Date;

public class PassRecordBean {
    private int record_id;
    private String person_id;
    private String machine_name;
    private Date record_time;
    private int flag;

    public PassRecordBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PassRecordBean{" +
                "record_id=" + record_id +
                ", person_id='" + person_id + '\'' +
                ", machine_name='" + machine_name + '\'' +
                ", record_time=" + record_time +
                ", flag=" + flag +
                '}';
    }
}
