package test;

import Bean.course_information;
import Dao.SchoolDao;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;
import utils.JDBCUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDao {
    @Test
    public void testList() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<List<String>> ll = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        JSONObject JSONObject = new JSONObject();
        System.out.println(list2.size());
        JSONObject.put("值", list2);
        System.out.println(JSONObject);

    }

    @Test
    public void demo10() throws SQLException {

        SchoolDao schoolDao = new SchoolDao();
        List<String> list = schoolDao.phoneIdentity("444");
        for (String sql : list) {
            System.out.println(sql);

        }
    }

    @Test
    public void test01() throws SQLException {
        SchoolDao schoolDao = new SchoolDao();
        List<course_information> list = schoolDao.getClassList("1110000");
        System.out.println("退出了");
        System.out.println(list.get(0));
    }

    @Test
    public void test02() throws SQLException {
        SchoolDao schoolDao = new SchoolDao();
        int identity = schoolDao.getIdentity("3");
        System.out.println("这是结果" + identity);
    }

    @Test
    public void test03() throws SQLException {
        SchoolDao schoolDao = new SchoolDao();
        boolean b = schoolDao.addMsg("2", "1", "sda", "sdasdasd", "2321", 1122);
        System.out.println("插入成功" + b);
    }

    @Test
    public void test04() throws SQLException {
        String sql = "SELECT course_information.`course_id` AS djw FROM course_information WHERE course_name=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), "2");
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "     " + entry.getValue());

            }
        }
    }
}
