package test;

import Dao.gradeDao;
import org.junit.Test;


public class TestGradeDao {
    @Test
    public void demo1() throws Exception {
        gradeDao dao = new gradeDao();
        dao.sortGradeDao();
    }
}
