package Bean;

import java.util.List;

public class DetailRecordByTeaBean {
    private Long goClassLateCount;
    private List<String> goClassLateName;
    private Long backRoomLateCount;
    private List<String> backRoomLateName;
    private Long notRoomCount;
    private List<String> notRoomName;
    private Long askLeaveCount;
    private List<String> askLeaveCountName;
    private Long truantCount;
    private List<String> truantName;

    public Long getGoClassLateCount() {
        return goClassLateCount;
    }

    public void setGoClassLateCount(Long goClassLateCount) {
        this.goClassLateCount = goClassLateCount;
    }

    public List<String> getGoClassLateName() {
        return goClassLateName;
    }

    public void setGoClassLateName(List<String> goClassLateName) {
        this.goClassLateName = goClassLateName;
    }

    public Long getBackRoomLateCount() {
        return backRoomLateCount;
    }

    public void setBackRoomLateCount(Long backRoomLateCount) {
        this.backRoomLateCount = backRoomLateCount;
    }

    public List<String> getBackRoomLateName() {
        return backRoomLateName;
    }

    public void setBackRoomLateName(List<String> backRoomLateName) {
        this.backRoomLateName = backRoomLateName;
    }

    public Long getNotRoomCount() {
        return notRoomCount;
    }

    public void setNotRoomCount(Long notRoomCount) {
        this.notRoomCount = notRoomCount;
    }

    public List<String> getNotRoomName() {
        return notRoomName;
    }

    public void setNotRoomName(List<String> notRoomName) {
        this.notRoomName = notRoomName;
    }

    public Long getAskLeaveCount() {
        return askLeaveCount;
    }

    public void setAskLeaveCount(Long askLeaveCount) {
        this.askLeaveCount = askLeaveCount;
    }

    public List<String> getAskLeaveCountName() {
        return askLeaveCountName;
    }

    public void setAskLeaveCountName(List<String> askLeaveCountName) {
        this.askLeaveCountName = askLeaveCountName;
    }

    public Long getTruantCount() {
        return truantCount;
    }

    public void setTruantCount(Long truantCount) {
        this.truantCount = truantCount;
    }

    public List<String> getTruantName() {
        return truantName;
    }

    public void setTruantName(List<String> truantName) {
        this.truantName = truantName;
    }

    @Override
    public String toString() {
        return "DetailRecordByTeaBean{" +
                "goClassLateCount=" + goClassLateCount +
                ", goClassLateName=" + goClassLateName +
                ", backRoomLateCount=" + backRoomLateCount +
                ", backRoomLateName=" + backRoomLateName +
                ", notRoomCount=" + notRoomCount +
                ", notRoomName=" + notRoomName +
                ", askLeaveCount=" + askLeaveCount +
                ", askLeaveCountName=" + askLeaveCountName +
                ", truantCount=" + truantCount +
                ", truantName=" + truantName +
                '}';
    }
}
