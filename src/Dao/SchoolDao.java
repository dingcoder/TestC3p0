package Dao;

import Bean.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DruidUtils;
import utils.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SchoolDao {

    /*
    0:家长 1：老师 2：游客
     */
    public List<String> phoneIdentity(String tel) throws SQLException {
        List<String> list = new ArrayList<>();
        String sqlParent = "select * from parent_visitor_information where parent_visitor_phone = ?";
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());
        parent_visitor_informationBean parentVisitorInformationBean = queryRunner.query(sqlParent, new BeanHandler<parent_visitor_informationBean>(parent_visitor_informationBean.class), tel);
        if (parentVisitorInformationBean != null) {
            list.add("0");
            list.add(parentVisitorInformationBean.getParent_visitor_id());
            return list;
        }
        String sqlTeacher = "select * from teacher_information where teacher_phone = ?";
        teacher_informationBean teacherInformationBean = queryRunner.query(sqlTeacher, new BeanHandler<teacher_informationBean>(teacher_informationBean.class), tel);
        if (teacherInformationBean != null) {
            list.add("1");
            list.add(teacherInformationBean.getTeacher_id());
            return list;
        }
        list.add("2");
        list.add("2"+System.currentTimeMillis());
        return list;
    }

    /*根据用户id查询他权限内所能见到的10条消息，按时间排序，最新的在最前面
     * 1.如果用户是家长，则message_information表中的course_id 是0000001，且target=0
     * 2.如果是老师，则需要根据uid查询course_information，查出该老师所属班级、年级、学部，校级的course_id，
     * 	再与message_information表中的course_id对比
     */
    public List<message_informationBean> getFirstPageMessage(String uid, int flag) throws SQLException {
        List<message_informationBean> Resultlist = new ArrayList<>();

//        1-首先对message_information进行安时间排序
        String sql = "select * from message_information Order By message_time Desc";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        List<message_informationBean> message_informationBeans = queryRunner.query(sql, new BeanListHandler<message_informationBean>(message_informationBean.class));

        //uid 如果家长就是parent_visitor_id，教师是teacher_id。
        String id = uid;
//      先查老师
        String sqlTeacher = "select * from course_information where teacher_id = ?";
        List<course_information> course_informations = queryRunner.query(sql, new BeanListHandler<course_information>(course_information.class), id);
//      如果能查到
        if (course_informations != null) {
//        一个老师可能教授多个班级,存在一个List中
            List<String> course_idList = new ArrayList<>();
            for (course_information information : course_informations) {
                course_idList.add(information.getCourse_id());
            }
            int size = course_idList.size();

//           如果老师没有教授任何班级，只能看到全校信息
            if (size == 0) {
//                返回十个通知；
                int i = 0;
                for (message_informationBean bean : message_informationBeans) {
                    if (bean.getCourse_id().subSequence(0, 1).equals("1")) {
                        Resultlist.add(bean);
                        i++;
                        if (i == 10) {
                            return Resultlist;
                        }
                    }
                }
            }
//           如果老师只教授一个班级，按全校，部级，年级，班级来查10条数据
            if (size == 1) {
                String courseid = course_idList.get(0);
//                返回十个通知
                int i = 0;
                for (message_informationBean bean : message_informationBeans) {
                    if (bean.getCourse_id().subSequence(0, 1).equals("1") ||
                            bean.getCourse_id().subSequence(0, 2).equals(courseid.substring(0, 2)) ||
                            bean.getCourse_id().subSequence(0, 3).equals(courseid.substring(0, 3)) ||
                            bean.getCourse_id().subSequence(0, 5).equals(courseid.substring(0, 5))) {
                        Resultlist.add(bean);
                        i++;
                        if (i == 10) {
                            return Resultlist;
                        }
                    }
                }
//          可能老师教授多个班级，按全校，部级，年级，班级来查10条数据
            } else {
//                返回10条通知
                int i = 10;
                for (message_informationBean bean : message_informationBeans) {
                    for (int j = 0; j < size; j++) {
                        if (bean.getCourse_id().subSequence(0, 1).equals("1") ||
                                bean.getCourse_id().subSequence(0, 2).equals(course_idList.get(j).substring(0, 2)) ||
                                bean.getCourse_id().subSequence(0, 3).equals(course_idList.get(j).substring(0, 3)) ||
                                bean.getCourse_id().subSequence(0, 5).equals(course_idList.get(j).substring(0, 5))) {
                            Resultlist.add(bean);
                            i++;
                            if (i == 10) {
                                return Resultlist;
                            }
                        }
                    }
                }
            }
        }
//        如果教师表里查不到就在学生表里查询
        String sqlParent = "select * from student_information where parent1_id = ? or parent2_id = ?";
        List<student_informationBean> student_informationBeans = queryRunner.query(sqlParent, new BeanListHandler<student_informationBean>(student_informationBean.class), id, id);
//        如果能查询到
        if (student_informationBeans != null) {
//            一个家长可能有多个孩子，可能对应不同班级,建立list存
            List<String> course_idList = new ArrayList<>();
            for (student_informationBean studentInformationBean : student_informationBeans) {
                course_idList.add(studentInformationBean.getCourse_id());
            }
            int size = course_idList.size();

//            如果家长没有孩子在学校，或者说已经毕业了,只能看到全校信息
            if (size == 0) {
//                返回十个通知；
                int i = 0;
                for (message_informationBean bean : message_informationBeans) {
                    if (bean.getCourse_id().subSequence(0, 1).equals("1")) {
                        Resultlist.add(bean);
                        i++;
                        if (i == 10) {
                            return Resultlist;
                        }
                    }
                }
            }
//           如果家长只有一个孩子，按全校，部级，年级，班级来查10条数据
            if (size == 1) {
                String courseid = course_idList.get(0);
                //                返回十个通知；
                int i = 0;
                for (message_informationBean bean : message_informationBeans) {
                    if (bean.getCourse_id().subSequence(0, 1).equals("1") ||
                            bean.getCourse_id().subSequence(0, 5).equals(courseid.substring(0, 5))
                    ) {
                        Resultlist.add(bean);
                        i++;
                        if (i == 10) {
                            return Resultlist;
                        }
                    }
                }
            }
//            一个家长有多个孩子，可能对应多个班级
            else {
                int i = 10;
                for (message_informationBean bean : message_informationBeans) {
                    for (int j = 0; j < size; j++) {
                        if (bean.getCourse_id().subSequence(0, 1).equals("1") ||
                                bean.getCourse_id().subSequence(0, 5).equals(course_idList.get(j).substring(0, 5))) {
                            Resultlist.add(bean);
                            i++;
                            if (i == 10) {
                                return Resultlist;
                            }
                        }
                    }
                }
            }

        }
//   uid 既不是教师也不是家长的就返回空

        return null;
    }


    //    如果phone与数据库的一致则保存NickName和headUrl保存到数据库，返回id和用户类型（0-表示老师，1-表示家长）、姓名
//    如果不一致则返回空
    public List<String> loginWeixin(String phone, String NickName, String headUrl) throws SQLException {
//     1、定义空数组
        List<String> IdAndUsertype = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
//       2、先查teacher_informationBean，不为空就更新，并返回
        String sqlteacher_informationBean = "select * from teacher_information where teacher_phone= ?";
        teacher_informationBean teacher_informationBean = queryRunner.query(sqlteacher_informationBean, new BeanHandler<teacher_informationBean>(teacher_informationBean.class), phone);
        if (teacher_informationBean != null) {
            String sqlupdate = "update teacher_information set teacher_nickname = ? ,teacher_head = ? where teacher_phone =?";
            Object[] params = {NickName, headUrl, phone};
            queryRunner.update(sqlupdate, params);
            IdAndUsertype.add(teacher_informationBean.getTeacher_id());
            IdAndUsertype.add("0");
            IdAndUsertype.add(teacher_informationBean.getTeacher_name());
            return IdAndUsertype;
        }
//        3、如先查teacher_informationBean为空，再查parent_visitor_information，不为空就更新，并返回
        String sqlparent_visitor_information = "select * from parent_visitor_information where parent_visitor_phone= ?";
        parent_visitor_informationBean parent_visitor_informationBean = queryRunner.query(sqlparent_visitor_information, new BeanHandler<parent_visitor_informationBean>(parent_visitor_informationBean.class), phone);
        if (parent_visitor_informationBean != null) {
            String sqlupdate = "update parent_visitor_information set parent_visitor_nickname = ? ,parent_visitor_head = ? where parent_visitor_phone =?";
            Object[] params = {NickName, headUrl, phone};
            queryRunner.update(sqlupdate, params);
            IdAndUsertype.add(parent_visitor_informationBean.getParent_visitor_id());
            IdAndUsertype.add("0");
            IdAndUsertype.add(parent_visitor_informationBean.getParent_visitor_name());
            return IdAndUsertype;
        }
//        都没查到就返回空数组
        return IdAndUsertype;
    }


    //    我传给你的4个参数，第一个teacherid是发通知的老师
//    第二个type是发送方式
//    爹三个target是发送对象，对应数据库course_id
//    第四个参数是通知内容
//    时间就是插入数据库的时间
    public boolean addMsg(String teacherid, String type, String course_id, String content, String identity,
                          int target) throws SQLException {
        String sql = "insert into message_information (teacher_id,method,course_id,message_content,sender_identity,target,message_time) value(?,?,?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Date message_time = new Date();
        Object[] params = {teacherid, type, course_id, content, identity, target, message_time};
        int update = queryRunner.update(sql, params);
        System.out.println("更新成功" + update);
        if (update == 0) {
            return false;
        } else {
            return true;
        }
    }

    //    第一个，我给你教师的id号，查询教师的权力等级，就是教师表中powergrade的5个字段
//    public List<String> getpowergrade(String teacherid) throws SQLException {
//        String sql = "select * from teacher_information where teacher_id = ?";
//        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
//        teacher_informationBean teacher_informationBean = queryRunner.query(sql, new BeanHandler<teacher_informationBean>(teacher_informationBean.class), teacherid);
//        List<String> powergrade = new ArrayList<>();
//        powergrade.add(teacher_informationBean.getTeacher_name());
//        powergrade.add(teacher_informationBean.getPower_grade1());
//        powergrade.add(teacher_informationBean.getPower_grade2());
//        powergrade.add(teacher_informationBean.getPower_grade3());
//        powergrade.add(teacher_informationBean.getPower_grade4());
//        powergrade.add(teacher_informationBean.getPower_grade5());
//        return powergrade;
//    }

    //所以你还要查另一张表，返回这个老师教的班级id和班级名称
//因为一个老师可能教多个班级嘛，所以按照班级1id、班级1名称、班级2id、班级2名称这种形式，你看看可以搞不
    public List<String> teachClass(String teacherid) throws SQLException {
        String sql = "select * from course_information where teacher_id = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        List<course_information> query = queryRunner.query(sql, new BeanListHandler<course_information>(course_information.class), teacherid);
        int size = query.size();
        List<String> TeacherAndClass = new ArrayList<>();
        if (size == 0) {
            return TeacherAndClass;
        } else {
            int i = 0;
            for (course_information c : query) {
                TeacherAndClass.add(c.getCourse_id());
                TeacherAndClass.add(c.getCourse_name());
            }
        }
        return TeacherAndClass;
    }

    //    int identity=schoolDao.getIdentity(uId);
//    小丁丁需要你写在dao里写一个函数，给你一个String uid，如果是教师就返回2，如果是家长就返回1，如果没查到就是0
    public int getIdentity(String uid) throws SQLException {
//        1、先查教师
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sqlTeacher = "select * from teacher_information where teacher_id = ?";
        teacher_informationBean queryTeacher = queryRunner.query(sqlTeacher, new BeanHandler<teacher_informationBean>(teacher_informationBean.class), uid);
        if (queryTeacher != null) {
            return 2;
        }
//        2、再查家长
        String sqlParent = "select * from parent_visitor_information where parent_visitor_id = ?";
        parent_visitor_informationBean queryParent = queryRunner.query(sqlParent, new BeanHandler<parent_visitor_informationBean>(parent_visitor_informationBean.class), uid);
        if (queryParent != null) {
            return 1;
        }
//        3、都没查到就返回
        return 0;
    }


    public List<course_information> getClassList(String course_id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String getgrade = course_id.substring(0, 2);
        String sql = "select * from course_information where course_id like ?";
        String s = getgrade + "___" + "00";
        System.out.println("ssss" + s);
        List<course_information> query = queryRunner.query(sql, new BeanListHandler<course_information>(course_information.class), s);
        return query;
    }

    public List<student_informationBean> getStuList(String class_id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from student_information where class_id =?";
        List<student_informationBean> query = queryRunner.query(sql, new BeanListHandler<student_informationBean>(student_informationBean.class), class_id);
        return query;
    }

    public boolean upLoadStuPic(String student_id, String student_base64) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "insert into student_information(student_base64) value(?) where student_id=?";
        int i = queryRunner.update(sql, student_base64, student_id);
        if (i == 0) {
            return false;
        } else {
            return true;
        }
    }
}
