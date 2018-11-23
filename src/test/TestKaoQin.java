package test;

import Service.kaoqinService;
import org.junit.Test;

import java.sql.SQLException;

public class TestKaoQin {
    @Test
    public void demo1() throws SQLException {
        kaoqinService kaoqinService = new kaoqinService();
        kaoqinService.detailRecordByTea("1110100");
    }
}
