package test;

import Bean.GradeBean;
import Bean.course_information;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;
import utils.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class TestDruidDao {
    @Test
    public void demo1() throws SQLException {
        String tableName = "exam1542435753050";
        String sql = "select * from " + tableName;
        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());
        List<GradeBean> beans = queryRunner.query(sql, new BeanListHandler<>(GradeBean.class));
        for (GradeBean bean : beans) {
            System.out.println(bean);
        }
    }
}
