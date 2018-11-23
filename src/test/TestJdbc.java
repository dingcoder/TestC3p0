package test;

import java.sql.SQLException;
import java.util.List;

import Bean.GradeBean;
import Bean.course_information;
import Bean.student_informationBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;
import utils.JDBCUtils;


public class TestJdbc {

    @Test
    public void testdemo() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from ?";
        List<GradeBean> query = queryRunner.query(sql, new BeanListHandler<>(GradeBean.class), "exam111000020180910");
        for (GradeBean gradeBean : query) {
            System.out.println(gradeBean);
        }

    }

    @Test
    public void test01() throws SQLException {
        String class_id = "11111";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from student_information where class_id =?";
        List<student_informationBean> query = queryRunner.query(sql, new BeanListHandler<student_informationBean>(student_informationBean.class), class_id);
        System.out.println(query);
    }

    @Test
    public void test02() throws SQLException {
        String student_base64 = "1";
        String student_id = "2";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "insert into student_information(student_base64) value(?) where student_id=?";
        int i = queryRunner.update(sql, student_base64, student_id);
        System.out.println(i);
    }

    @Test
    public void test03() throws SQLException {
        String course_id = "12234452";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String getgrade = course_id.substring(0, 1);
        String sql = "select * from course_information where course_id like ?";
        String s = getgrade + "___" + "00";
        List<course_information> query = queryRunner.query(sql, new BeanListHandler<course_information>(course_information.class), s);
        System.out.println(query);
    }


}