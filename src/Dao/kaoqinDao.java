package Dao;

import Bean.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DruidUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class kaoqinDao {


    // 获取当天流水记录的表名
    private String getTableName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return "pass_record" + dateFormat.format(date);
    }


    public List<student_informationBean> getAllRecordByPar(String parentId, String curTime) throws SQLException {
        List<PassRecordBean> reList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());
        //先查索引中当前日期所对应的表
        String queryTable = "select pass_record_information where pass_record_time = ?";
        PassRecordInformationBean passRecordInformationBean = queryRunner.query(queryTable, new BeanHandler<>(PassRecordInformationBean.class), curTime);
        String tableName = passRecordInformationBean.getPass_record_table();

        String sql = "select * from student_information where parent1_id = ? or parent2_id = ?";
        List<student_informationBean> studentInformationBeans = queryRunner.query(sql, new BeanListHandler<>(student_informationBean.class), parentId, parentId);
        if (studentInformationBeans.size() != 0) {
            for (student_informationBean studentInformationBean : studentInformationBeans) {
                String studentId = studentInformationBean.getStudent_id();
                String queryStu = "select * from " + tableName;
                List<PassRecordBean> passRecordBeans = queryRunner.query(queryStu, new BeanListHandler<>(PassRecordBean.class));
                for (PassRecordBean passRecordBean : passRecordBeans) {
                    reList.add(passRecordBean);
                }
            }
        }
        return null;
    }

    /**
     * 早上出门迟到：1，
     */
    public List<RecordByLifeteaBean> morningLifetea(String buildId) throws SQLException, ParseException {
        List<RecordByLifeteaBean> reList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());
//       先查索引中当前日期所对应的表
//       获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String curTime = df.format(new Date());// new Date()为获取当前系统时间
//        string 转 Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//
//        String queryTable = "select pass_record_information where pass_record_time like " + curTime + "%";
//        PassRecordInformationBean passRecordInformationBean = queryRunner.query(queryTable, new BeanHandler<>(PassRecordInformationBean.class));
//        String tableName = passRecordInformationBean.getPass_record_table();
//        得到表名
        String tableName = getTableName();


//        分割宿舍楼栋号
        String building = buildId.substring(2, 3);
//        如果是1,2栋的话
        String sql = "";
        if (building.equals("1") || building.equals("2")) {
//            早上园区 1 ，2 出门迟到
            sql = "select * from " + tableName + " where flag = 1 and machine_name = 'SS1' ";
        } else {
//            早上园区 3, 4 出门迟到
            sql = "select * from " + tableName + " where flag = 1 and machine_name = 'SS3' ";
        }
        List<PassRecordBean> passRecordBeans = queryRunner.query(sql, new BeanListHandler<>(PassRecordBean.class));
        for (PassRecordBean passRecordBean : passRecordBeans) {
            String queryStu = "select * from student_information where student_id = ? and dormitory like ?";
            String dormitoryFirst = building + "%";
            student_informationBean bean = queryRunner.query(queryStu, new BeanHandler<>(student_informationBean.class), passRecordBean.getPerson_id(), dormitoryFirst);
            if (bean != null) {
                RecordByLifeteaBean record = new RecordByLifeteaBean();
                record.setDormitory(bean.getDormitory());
                record.setCourse_id(bean.getCourse_id());
                record.setRecord_time(passRecordBean.getRecord_time());
                record.setStudent_name(bean.getStudent_name());
                record.setType("迟到");
                reList.add(record);
            }
        }
//        查询请假的
        String askLeaveSql = "select * from student_information where dormitory like ? ";
        List<student_informationBean> studentInformationBeans = queryRunner.query(askLeaveSql, new BeanListHandler<>(student_informationBean.class), building + "%");
        for (student_informationBean studentInformationBean : studentInformationBeans) {
            String student_id = studentInformationBean.getStudent_id();
            String leaveSql = "select * from leave_record where student_id = ? and start_time >= ? and end_time <= ? and isauthroity = 1";
            LeaveRecordBean leaveRecordBean = queryRunner.query(leaveSql, new BeanHandler<>(LeaveRecordBean.class), studentInformationBean.getId(), curTime, curTime);
            if (leaveRecordBean != null) {
                System.out.println("===========" + leaveRecordBean);
                RecordByLifeteaBean record = new RecordByLifeteaBean();
                record.setDormitory(studentInformationBean.getDormitory());
                record.setCourse_id(studentInformationBean.getCourse_id());
                record.setStudent_name(studentInformationBean.getStudent_name());
                record.setType("请假");
                record.setRecord_time(sdf.parse(curTime));
                reList.add(record);
            }
        }

        return reList;
    }

    /**
     * 中午入门迟到：2，中午出门迟到：3
     *
     * @return
     */
    public List<RecordByLifeteaBean> noonLifetea(String buildId) throws SQLException, ParseException {
        List<RecordByLifeteaBean> reList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());
//       先查索引中当前日期所对应的表
//       获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String curTime = df.format(new Date());// new Date()为获取当前系统时间
//        string 转 Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String queryTable = "select pass_record_information where pass_record_time like " + curTime + "%";
//        PassRecordInformationBean passRecordInformationBean = queryRunner.query(queryTable, new BeanHandler<>(PassRecordInformationBean.class));
//        String tableName = passRecordInformationBean.getPass_record_table();
        String tableName = getTableName();

//        分割宿舍楼栋号
        String building = buildId.substring(2, 3);
//        1、先查询中午入门迟到
//        如果是1,2栋的话
        String inDoorsql = "";
        String outDoorsql = "";

        if (building.equals("1") || building.equals("2")) {
//            中午园区 1 ，2 入门迟到
            inDoorsql = "select * from " + tableName + " where flag = 2 and machine_name = 'SS1'";
//            中午园区 1 ，2 出门迟到
            outDoorsql = "select * from " + tableName + " where flag = 3 and machine_name = 'SS1'";
        } else {
//            中午园区 3, 4 入门迟到
            inDoorsql = "select * from " + tableName + " where flag = 2 and machine_name = 'SS3'";
//            中午园区 3, 4 出门迟到
            outDoorsql = "select * from " + tableName + " where flag = 3 and machine_name = 'SS3'";
        }
        List<PassRecordBean> passRecordInDoorBeans = queryRunner.query(inDoorsql, new BeanListHandler<>(PassRecordBean.class));
        for (PassRecordBean passRecordBean : passRecordInDoorBeans) {
            String queryStu = "select * from student_information where student_id = ? and dormitory like ?";
            String dormitoryFirst = building + "%";
            student_informationBean bean = queryRunner.query(queryStu, new BeanHandler<>(student_informationBean.class), passRecordBean.getPerson_id(), dormitoryFirst);
            RecordByLifeteaBean record = new RecordByLifeteaBean();
            record.setDormitory(bean.getDormitory());
            record.setCourse_id(bean.getCourse_id());
            record.setRecord_time(passRecordBean.getRecord_time());
            record.setStudent_name(bean.getStudent_name());
            record.setType("入门迟到");
            reList.add(record);
        }
//        2、查询中午出门迟到
        List<PassRecordBean> passRecordOutDoorBeans = queryRunner.query(outDoorsql, new BeanListHandler<>(PassRecordBean.class));
        for (PassRecordBean passRecordBean : passRecordOutDoorBeans) {
            String queryStu = "select * from student_information where student_id = ?  and dormitory like ?";
            String dormitoryFirst = building + "%";
            student_informationBean bean = queryRunner.query(queryStu, new BeanHandler<>(student_informationBean.class), passRecordBean.getPerson_id(), dormitoryFirst);
            RecordByLifeteaBean record = new RecordByLifeteaBean();
            record.setDormitory(bean.getDormitory());
            record.setCourse_id(bean.getCourse_id());
            record.setRecord_time(passRecordBean.getRecord_time());
            record.setStudent_name(bean.getStudent_name());
            record.setType("出门迟到");
            reList.add(record);
        }


        //        查询请假的
        String askLeaveSql = "select * from student_information where dormitory like ? ";
        List<student_informationBean> studentInformationBeans = queryRunner.query(askLeaveSql, new BeanListHandler<>(student_informationBean.class), building + "%");
        for (student_informationBean studentInformationBean : studentInformationBeans) {
            String student_id = studentInformationBean.getStudent_id();
            String leaveSql = "select * from leave_record where student_id = ? and start_time >= ? and end_time <= ? and isauthroity = 1";
            LeaveRecordBean leaveRecordBean = queryRunner.query(leaveSql, new BeanHandler<>(LeaveRecordBean.class), studentInformationBean.getId(), curTime, curTime);
            if (leaveRecordBean != null) {
                RecordByLifeteaBean record = new RecordByLifeteaBean();
                record.setDormitory(studentInformationBean.getDormitory());
                record.setCourse_id(studentInformationBean.getCourse_id());
                record.setStudent_name(studentInformationBean.getStudent_name());
                record.setType("请假");
                record.setRecord_time(sdf.parse(curTime));
                reList.add(record);
            }
        }
        return reList;

    }

    /**
     * 晚上入门迟到：4 ，晚上正常是5
     *
     * @return
     */
    public List<RecordByLifeteaBean> nightLifetea(String buildId) throws SQLException, ParseException {
        List<RecordByLifeteaBean> reList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());
//       先查索引中当前日期所对应的表
//       获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String curTime = df.format(new Date());// new Date()为获取当前系统时间
//        string 转 Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String queryTable = "select pass_record_information where pass_record_time like " + curTime + "%";
//        PassRecordInformationBean passRecordInformationBean = queryRunner.query(queryTable, new BeanHandler<>(PassRecordInformationBean.class));
//        String tableName = passRecordInformationBean.getPass_record_table();
        String tableName = getTableName();

        List<String> tempStu = new ArrayList<>();
        //        分割宿舍楼栋号
        String building = buildId.substring(2, 3);
        //        如果是1,2栋的话
        String inDoorsql = "";
        if (building.equals("1") || building.equals("2")) {
//            晚上园区 1 ，2 入门迟到
            inDoorsql = "select * from " + tableName + " where flag = 4 and machine_name = 'SS1'";
        } else {
//            晚上园区 3, 4 入门迟到
            inDoorsql = "select * from " + tableName + " where flag = 4 and machine_name = 'SS3'";
        }
        //1、先查询晚上入门迟到
        List<PassRecordBean> passRecordInDoorBeans = queryRunner.query(inDoorsql, new BeanListHandler<>(PassRecordBean.class));
        for (PassRecordBean passRecordBean : passRecordInDoorBeans) {
            String queryStu = "select * from student_information where student_id = ?  and dormitory like ?";
            String dormitoryFirst = building + "%";
            student_informationBean bean = queryRunner.query(queryStu, new BeanHandler<>(student_informationBean.class), passRecordBean.getPerson_id(), dormitoryFirst);
            if (bean != null) {
                tempStu.add(bean.getStudent_id());
                RecordByLifeteaBean record = new RecordByLifeteaBean();
                record.setDormitory(bean.getDormitory());
                record.setCourse_id(bean.getCourse_id());
                record.setRecord_time(passRecordBean.getRecord_time());
                record.setStudent_name(bean.getStudent_name());
                record.setType("入门迟到");
                reList.add(record);
            }
        }

        //        2、查询请假的
        String askLeaveSql = "select * from student_information where dormitory like ? ";
        List<student_informationBean> allStudentsInBuild = queryRunner.query(askLeaveSql, new BeanListHandler<>(student_informationBean.class), building + "%");
        for (student_informationBean studentInformationBean : allStudentsInBuild) {
            String student_id = studentInformationBean.getStudent_id();
            String leaveSql = "select * from leave_record where student_id = ? and start_time >= ? and end_time <= ? and isauthroity = 1";
            LeaveRecordBean leaveRecordBean = queryRunner.query(leaveSql, new BeanHandler<>(LeaveRecordBean.class), studentInformationBean.getId(), curTime, curTime);
            if (leaveRecordBean != null) {
                tempStu.add(leaveRecordBean.getStudent_id());
                RecordByLifeteaBean record = new RecordByLifeteaBean();
                record.setDormitory(studentInformationBean.getDormitory());
                record.setCourse_id(studentInformationBean.getCourse_id());
                record.setStudent_name(studentInformationBean.getStudent_name());
                record.setType("请假");
                record.setRecord_time(sdf.parse(curTime));
                reList.add(record);
            }
        }

        //3、查询晚上正常归寝的人
        String nightBuilding = buildId.substring(0, 4) + "%";
        String normalSql = "select * from " + tableName + " where flag = 0 and machine_name like ? ";
        List<PassRecordBean> passRecordNotBeans = queryRunner.query(normalSql, new BeanListHandler<>(PassRecordBean.class), nightBuilding);
        for (PassRecordBean passRecordBean : passRecordNotBeans) {
            String queryStu = "select * from student_information where student_id = ?";
            student_informationBean bean = queryRunner.query(queryStu, new BeanHandler<>(student_informationBean.class), passRecordBean.getPerson_id());
            if (bean != null) {
                tempStu.add(bean.getStudent_name());
            }
        }

//        4、这栋宿舍的人-晚上迟到的人-晚上正常归寝的人-请假的人=未归寝的人
        for (student_informationBean informationBean : allStudentsInBuild) {
            if (!tempStu.contains(informationBean.getStudent_name())) {
                RecordByLifeteaBean record = new RecordByLifeteaBean();
                record.setDormitory(informationBean.getDormitory());
                record.setCourse_id(informationBean.getCourse_id());
                record.setStudent_name(informationBean.getStudent_name());
                record.setType("未归寝");
                record.setRecord_time(sdf.parse(curTime));
                reList.add(record);
            }
        }
        return reList;

    }

    //   早上的详细情况Dao
    public List<List<String>> getDetailRecordMorByTea(String classId) throws SQLException {
        List<List<String>> reList = new ArrayList<>();
        //       获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String curTime = df.format(new Date());// new Date()为获取当前系统时间
        SimpleDateFormat askdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String askcurTime = askdf.format(new Date());// new Date()为获取当前系统时间
        List<String> goClassLate = new ArrayList<>();
        List<String> backRoomLate = new ArrayList<>();
        List<String> notRoom = new ArrayList<>();
        List<String> askLeave = new ArrayList<>();
        List<String> truant = new ArrayList<>();
//        得到当前记录表名
        String tableName = getTableName();
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());


//        查找这个班级有哪些人
        String classAllSql = "select * from student_information where course_id =?";
        List<student_informationBean> studentInformationBeans = queryRunner.query(classAllSql, new BeanListHandler<>(student_informationBean.class), classId);
//        这个班级的人 有些在passrecord表里有些没有在
        for (student_informationBean studentInformationBean : studentInformationBeans) {
//                查询当天请假的
            String askLeaveSql = "select * from leave_record where student_id = ? and start_time <= ? and end_time >= ? and isauthroity = 1";
            LeaveRecordBean leaveRecordBean = queryRunner.query(askLeaveSql, new BeanHandler<>(LeaveRecordBean.class), studentInformationBean.getId(), askcurTime, askcurTime);
            if (leaveRecordBean != null) {
                askLeave.add(studentInformationBean.getStudent_name());
            }
            String student_id = studentInformationBean.getStudent_id();
            String insql = "select * from " + tableName + " where person_id = ?";
            PassRecordBean passRecordBean = queryRunner.query(insql, new BeanHandler<>(PassRecordBean.class), student_id);
            if (passRecordBean != null) {
//                在考勤表里
                if (passRecordBean.getFlag() == 1 || passRecordBean.getFlag() == 7) {
                    goClassLate.add(studentInformationBean.getStudent_name());
                }
            }
        }


        reList.add(goClassLate);
        reList.add(backRoomLate);
        reList.add(notRoom);
        reList.add(askLeave);
        reList.add(truant);

        return reList;
    }

    //   中午的详细情况Dao
    public List<List<String>> getDetailRecordNoonByTea(String classId) throws SQLException {
        List<List<String>> reList = new ArrayList<>();
        //       获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String curTime = df.format(new Date());// new Date()为获取当前系统时间
        SimpleDateFormat askdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String askcurTime = askdf.format(new Date());// new Date()为获取当前系统时间
        List<String> goClassLate = new ArrayList<>();
        List<String> backRoomLate = new ArrayList<>();
        List<String> notRoom = new ArrayList<>();
        List<String> askLeave = new ArrayList<>();
        List<String> truant = new ArrayList<>();
//        得到当前记录表名
        String tableName = getTableName();
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());

//        查找这个班级有哪些人
        String classAllSql = "select * from student_information where course_id =?";
        List<student_informationBean> studentInformationBeans = queryRunner.query(classAllSql, new BeanListHandler<>(student_informationBean.class), classId);
//        这个班级的人 有些在passrecord表里有些没有在
        for (student_informationBean studentInformationBean : studentInformationBeans) {
//          查询当天请假的
            String askLeaveSql = "select * from leave_record where student_id = ? and start_time <= ? and end_time >= ? and isauthroity = 1";
            LeaveRecordBean leaveRecordBean = queryRunner.query(askLeaveSql, new BeanHandler<>(LeaveRecordBean.class), studentInformationBean.getId(), askcurTime, askcurTime);
            if (leaveRecordBean != null) {
                System.out.println("+++" + leaveRecordBean);
                askLeave.add(studentInformationBean.getStudent_name());
                System.out.println(leaveRecordBean);
            }
            String student_id = studentInformationBean.getStudent_id();
            String insql = "select * from " + tableName + " where person_id = ?";
            PassRecordBean passRecordBean = queryRunner.query(insql, new BeanHandler<>(PassRecordBean.class), student_id);
            if (passRecordBean != null) {
//                在考勤表里
//                下午上课迟到
                if (passRecordBean.getFlag() == 3 || passRecordBean.getFlag() == 8) {
                    goClassLate.add(studentInformationBean.getStudent_name());
                }
//                下午归寝迟到
                if (passRecordBean.getFlag() == 2) {
                    backRoomLate.add(studentInformationBean.getStudent_name());
                }
            }
        }
        reList.add(goClassLate);
        reList.add(backRoomLate);
        reList.add(notRoom);
        reList.add(askLeave);
        reList.add(truant);
        return reList;
    }

    //   晚上的详细情况Dao
    public List<List<String>> getDetailRecordNightByTea(String classId) throws SQLException {
        List<List<String>> reList = new ArrayList<>();
        //       获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String curTime = df.format(new Date());// new Date()为获取当前系统时间
        SimpleDateFormat askdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String askcurTime = askdf.format(new Date());// new Date()为获取当前系统时间
        List<String> goClassLate = new ArrayList<>();
        List<String> backRoomLate = new ArrayList<>();
        List<String> notRoom = new ArrayList<>();
        List<String> askLeave = new ArrayList<>();
        List<String> truant = new ArrayList<>();
//        得到当前记录表名
        String tableName = getTableName();
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());

//        查找这个班级有哪些人
        String classAllSql = "select * from student_information where course_id =?";
        List<student_informationBean> studentInformationBeans = queryRunner.query(classAllSql, new BeanListHandler<>(student_informationBean.class), classId);
        List<String> notRoomtemp = new ArrayList<>();
        List<String> allClassLiveName = new ArrayList<>();

//        这个班级的人 有些在passrecord表里有些没有在
        for (student_informationBean studentInformationBean : studentInformationBeans) {
//          查询当天请假的
            String askLeaveSql = "select * from leave_record where student_id = ? and start_time <= ? and end_time >= ? and isauthroity = 1";
            LeaveRecordBean leaveRecordBean = queryRunner.query(askLeaveSql, new BeanHandler<>(LeaveRecordBean.class), studentInformationBean.getId(), askcurTime, askcurTime);
//                当天请假的
            if (leaveRecordBean != null) {
                System.out.println("+++" + leaveRecordBean);
                askLeave.add(studentInformationBean.getStudent_name());
                notRoomtemp.add(studentInformationBean.getStudent_name());
            }

//            如果是住校生 就加入住校生的list中
            if (studentInformationBean.getIsliveinschool() == 1) {
                allClassLiveName.add(studentInformationBean.getStudent_name());
            }
            String student_id = studentInformationBean.getStudent_id();
            String insql = "select * from " + tableName + " where person_id = ?";
            PassRecordBean passRecordBean = queryRunner.query(insql, new BeanHandler<>(PassRecordBean.class), student_id);
            if (passRecordBean != null) {
//                在考勤表里
//                晚上上课迟到
                if (passRecordBean.getFlag() == 9) {
                    goClassLate.add(studentInformationBean.getStudent_name());
                    notRoomtemp.add(studentInformationBean.getStudent_name());
                }
//                晚上归寝迟到
                if (passRecordBean.getFlag() == 4) {
                    backRoomLate.add(studentInformationBean.getStudent_name());
                    notRoomtemp.add(studentInformationBean.getStudent_name());
                }
//                正常归寝的
                if (passRecordBean.getFlag() == 0) {
                    notRoomtemp.add(studentInformationBean.getStudent_name());
                }
            }
        }
//        班级的住校生-晚上上课迟到的-晚上归寝迟到的-请假的-正常的=未归寝的
        for (String s : allClassLiveName) {
            if (!notRoom.contains(s)) {
                notRoom.add(s);
            }
        }
        reList.add(goClassLate);
        reList.add(backRoomLate);
        reList.add(notRoom);
        reList.add(askLeave);
        reList.add(truant);
        return reList;
    }
}
