package Service;

import Bean.DetailRecordByTeaBean;
import Bean.RecordByLifeteaBean;
import Bean.student_informationBean;
import Dao.kaoqinDao;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class kaoqinService {

    /**
     * 前端传过来的值
     * 值1：楼栋号 :例如：SS1_
     * 值2:时间类别 :例如：Morning，Noon，Night
     */

    /**
     * 早上出门迟到：1，中午入门迟到：2，中午出门迟到：3，晚上入门迟到：4 ，晚上正常是5
     */
    kaoqinDao dao = new kaoqinDao();

    //得到家长
    public List<student_informationBean> getAllRecordByPar(String parentId, String curTime) throws SQLException {
        List<student_informationBean> allList = dao.getAllRecordByPar(parentId, curTime);
        return allList;
    }

    //生活老师得到：
    //早上 ：出门迟到；中午 ：入门迟到 ，出门迟到； 晚上：入门迟到，未归寝

    /**
     * @param buildid 前端传来的楼栋号
     * @param timeid  前端传来的时间段 1(早)，2(中)，3(晚)
     * @return 返回给前端JSON格式的字符串
     * @throws SQLException
     */
    public JSONArray recordByLifetea(String buildid, String timeid) throws SQLException, ParseException {
        JSONArray reJsonArray = new JSONArray();
        if (timeid.equals("1")) {
//            早上 ：出门迟到
            List<RecordByLifeteaBean> reList = dao.morningLifetea(buildid);
            for (RecordByLifeteaBean record : reList) {
                JSONObject reJsonObject = new JSONObject();
                reJsonObject.put("dormitoryid", record.getDormitory());
                reJsonObject.put("courseid", record.getCourse_id());
                reJsonObject.put("studentname", record.getStudent_name());
                reJsonObject.put("type", record.getType());
                reJsonObject.put("recordtime", record.getRecord_time());
                reJsonArray.add(reJsonObject);
            }
        }
        if (timeid.equals("2")) {
//      中午 ：入门迟到 ，出门迟到
            List<RecordByLifeteaBean> reList = dao.noonLifetea(buildid);
            for (RecordByLifeteaBean record : reList) {
                JSONObject reJsonObject = new JSONObject();
                reJsonObject.put("dormitoryid", record.getDormitory());
                reJsonObject.put("courseid", record.getCourse_id());
                reJsonObject.put("studentname", record.getStudent_name());
                reJsonObject.put("type", record.getType());
                reJsonObject.put("recordtime", record.getRecord_time());
                reJsonArray.add(reJsonObject);
            }
        }
        if (timeid.equals("3")) {
//            晚上：入门迟到，未归寝
            List<RecordByLifeteaBean> reList = dao.nightLifetea(buildid);
            for (RecordByLifeteaBean record : reList) {
                JSONObject reJsonObject = new JSONObject();
                reJsonObject.put("dormitoryid", record.getDormitory());
                reJsonObject.put("courseid", record.getCourse_id());
                reJsonObject.put("studentname", record.getStudent_name());
                reJsonObject.put("type", record.getType());
                reJsonObject.put("recordtime", record.getRecord_time());
                reJsonArray.add(reJsonObject);
            }
        }
        return reJsonArray;
    }

//    老师得到每个班级的详细考勤信息

    /**
     * @param classid 前端传来的班级信息
     * @return 返回给前端JSON格式的字符串
     */

    public JSONArray detailRecordByTea(String classid) throws SQLException {
        JSONArray reJsonArray = new JSONArray();
//        list中 list[0]:上课迟到, list[1]：归寝迟到 ;list[2]:未归寝, list[3]：请假, list[4]：旷课,
//      1、第一步上午信息
        List<List<String>> DetailRecordMorByTeaBean = dao.getDetailRecordMorByTea(classid);

        JSONObject reJsonObjectMor = new JSONObject();
        reJsonObjectMor.put("上课迟到人数", DetailRecordMorByTeaBean.get(0).size());
        String goClassLateNameStr = "";
        for (String s : DetailRecordMorByTeaBean.get(0)) {
            goClassLateNameStr = goClassLateNameStr + s;
        }
        reJsonObjectMor.put("上课迟到姓名", goClassLateNameStr);
        reJsonObjectMor.put("归寝迟到人数", DetailRecordMorByTeaBean.get(1).size());
        String backRoomLateNameStr = "";
        for (String s : DetailRecordMorByTeaBean.get(0)) {
            backRoomLateNameStr = backRoomLateNameStr + s;
        }
        reJsonObjectMor.put("归寝迟到姓名", backRoomLateNameStr);
        reJsonObjectMor.put("未归寝人数", DetailRecordMorByTeaBean.get(2).size());
        reJsonObjectMor.put("未归寝姓名", DetailRecordMorByTeaBean.get(2));
        reJsonObjectMor.put("请假人数", DetailRecordMorByTeaBean.get(3).size());
        reJsonObjectMor.put("请假姓名", DetailRecordMorByTeaBean.get(3));
        reJsonObjectMor.put("旷课人数", DetailRecordMorByTeaBean.get(4).size());
        reJsonObjectMor.put("旷课姓名", DetailRecordMorByTeaBean.get(4));
        reJsonArray.add(reJsonObjectMor);

//      2、第二步中午信息
        List<List<String>> DetailRecordNoonByTeaBean = dao.getDetailRecordNoonByTea(classid);
        JSONObject reJsonObjectNoon = new JSONObject();
        reJsonObjectNoon.put("上课迟到人数", DetailRecordNoonByTeaBean.get(0).size());
        reJsonObjectNoon.put("上课迟到姓名", DetailRecordNoonByTeaBean.get(0));
        reJsonObjectNoon.put("归寝迟到人数", DetailRecordNoonByTeaBean.get(1).size());
        reJsonObjectNoon.put("归寝迟到姓名", DetailRecordNoonByTeaBean.get(1));
        reJsonObjectNoon.put("未归寝人数", DetailRecordNoonByTeaBean.get(2).size());
        reJsonObjectNoon.put("未归寝姓名", DetailRecordNoonByTeaBean.get(2));
        reJsonObjectNoon.put("请假人数", DetailRecordNoonByTeaBean.get(3).size());
        reJsonObjectNoon.put("请假姓名", DetailRecordNoonByTeaBean.get(3));
        reJsonObjectNoon.put("旷课人数", DetailRecordNoonByTeaBean.get(4).size());
        reJsonObjectNoon.put("旷课姓名", DetailRecordNoonByTeaBean.get(4));
        reJsonArray.add(reJsonObjectMor);
//      3、第三步晚上信息
        List<List<String>> DetailRecordNightByTeaBean = dao.getDetailRecordNightByTea(classid);

        JSONObject reJsonObjectNight = new JSONObject();
        reJsonObjectNight.put("上课迟到人数", DetailRecordNightByTeaBean.get(0).size());
        reJsonObjectNight.put("上课迟到姓名", DetailRecordNightByTeaBean.get(0));
        reJsonObjectNight.put("归寝迟到人数", DetailRecordNightByTeaBean.get(1).size());
        reJsonObjectNight.put("归寝迟到姓名", DetailRecordNightByTeaBean.get(1));
        reJsonObjectNight.put("未归寝人数", DetailRecordNightByTeaBean.get(2).size());
        reJsonObjectNight.put("未归寝姓名", DetailRecordNightByTeaBean.get(2));
        reJsonObjectNight.put("请假人数", DetailRecordNightByTeaBean.get(3).size());
        reJsonObjectNight.put("请假姓名", DetailRecordNightByTeaBean.get(3));
        reJsonObjectNight.put("旷课人数", DetailRecordNightByTeaBean.get(4).size());
        reJsonObjectNight.put("旷课姓名", DetailRecordNightByTeaBean.get(4));
        reJsonArray.add(reJsonObjectMor);

        return reJsonArray;
    }

}
