package Dao;

import Bean.GradeBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.ObjUtils;
import utils.DruidUtils;
import utils.SortGradeUtils;

import java.util.ArrayList;
import java.util.List;

public class gradeDao implements Cloneable {
    public void sortGradeDao() throws Exception {
        String tableName = "exam111000020180910";
        //字段表字典
        List<String> strs = new ArrayList<>();
        strs.add("subject1");
        strs.add("subject2");
        strs.add("subject3");
        strs.add("subject4");
        strs.add("subject5");
        strs.add("subject6");
        strs.add("subject7");
        strs.add("subject8");
        strs.add("subject9");
        strs.add("zonghe");
        strs.add("totalscore");
        int strsSize = strs.size();

        QueryRunner queryRunner = new QueryRunner(DruidUtils.getDatasource());
//     假设已经有总数据了，先要清洗数据
        String clearALLSql = "select * from " + tableName;
        List<GradeBean> clearAllList = queryRunner.query(clearALLSql, new BeanListHandler<>(GradeBean.class));
        List<String> stuID = new ArrayList<>();

        //        取出零分的人，最后再放回去
//        List<GradeBean> storeZero = new ArrayList<>();

        String askSql = " ";
        for (int n = 0; n < clearAllList.size(); n++) {
//            如果总分为0取出来
//            if (clearAllList.get(n).getTotalscore() == 0) {
//                storeZero.add(clearAllList.get(n));
//            }

            stuID.add(clearAllList.get(n).getCourse_id());
            if (n == (clearAllList.size() - 1)) {
                askSql = askSql + " ? ";
            } else {
                askSql = askSql + " ? ,";
            }
        }

//        排班级之前先清洗之前排的数据重排 DELETE FROM 表名称 WHERE 列名称 = 值
        System.out.println("ask" + askSql);
        Object[] paramters = new Object[stuID.size()];
        stuID.toArray(paramters);
        String cleanSql = "DELETE FROM " + tableName + " WHERE  stu_id in (" + askSql + ")";
        int i1 = queryRunner.update(cleanSql, paramters);
        String AutoIncreamentSql = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
        int i2 = queryRunner.update(AutoIncreamentSql);
        System.out.println("i1  ----" + i1 + "i2  ----" + i2);

//      删除总分为0的人
//        for (GradeBean deleteBean : storeZero) {
//            String deleteSql = "DELETE FROM " + tableName + " WHERE stu_id = ?";
//            queryRunner.update(deleteSql, deleteBean.getStu_id());
//        }

//     清洗完把所有人查出来
        String GetALLSql = "select * from " + tableName;
        List<GradeBean> allList = queryRunner.query(GetALLSql, new BeanListHandler<>(GradeBean.class));
//        所有人数
        int allSize = allList.size();
        List<Integer> splitInt = new ArrayList<>();
        splitInt.add(0);
        String tempCourseID = allList.get(0).getCourse_id();

        for (int j = 0; j < allList.size(); j++) {
            if (!allList.get(j).getCourse_id().equals(tempCourseID)) {
                splitInt.add(j);
                tempCourseID = allList.get(j).getCourse_id();
            }
        }


        splitInt.add(allSize);
        for (Integer integer : splitInt) {
            System.out.println("+++++" + integer);
        }

//        先排年级
        GradeBean gradeBeanAllGrade = new GradeBean();
        for (int i = 0; i < strsSize; i++) {
            System.out.println("bean " + strs.get(i));
            List<GradeBean> Tlist = new ArrayList<>();
            for (int k = 0; k < allList.size(); k++) {
                Tlist.add(allList.get(k));
            }
            SortGradeUtils sortGradeUtils = new SortGradeUtils();
            double avgBysubect = sortGradeUtils.SortGradeByGrade(Tlist, strs.get(i), tableName, queryRunner);
            ObjUtils.setFieldValue(gradeBeanAllGrade, strs.get(i), avgBysubect);
            gradeBeanAllGrade.setExam_name(Tlist.get(0).getExam_name());
            gradeBeanAllGrade.setCourse_id(Tlist.get(0).getCourse_id().substring(0, 3) + "0000");
            gradeBeanAllGrade.setStu_id((long) Integer.parseInt(Tlist.get(0).getCourse_id().substring(0, 3) + "0000"));
            gradeBeanAllGrade.setStu_name(Tlist.get(0).getExam_name().substring(0, 6));
            Tlist.clear();
        }

        System.out.println("------------年级分数" + gradeBeanAllGrade);
        List<GradeBean> avgList = new ArrayList<>();
//        再排班级
        for (int j = 0; j < splitInt.size() - 1; j++) {
            int startId = splitInt.get(j);
            int endId = endId = splitInt.get(j + 1) - 1;
            System.out.println("startId" + startId + "endId" + endId);
            //按段去插入
            List<GradeBean> tempList = new ArrayList<>();
            for (int m = 0; m < allList.size(); m++) {
                if (m >= startId && m <= endId) {
                    tempList.add(allList.get(m));
                }
            }

            GradeBean gradeBean = new GradeBean();
            for (int i = 0; i < strsSize; i++) {
                System.out.println("bean " + strs.get(i));
                List<GradeBean> Tlist = new ArrayList<>();
                for (int k = 0; k < tempList.size(); k++) {
                    Tlist.add(tempList.get(k));
                }
                SortGradeUtils sortGradeUtils = new SortGradeUtils();
                double avgBysubect = sortGradeUtils.SortGradeByClass(Tlist, strs.get(i), tableName, queryRunner);
                ObjUtils.setFieldValue(gradeBean, strs.get(i), avgBysubect);
                gradeBean.setExam_id(Tlist.get(0).getExam_id());
                gradeBean.setExam_name(Tlist.get(0).getExam_name());
                gradeBean.setCourse_id(Tlist.get(0).getCourse_id());
                gradeBean.setStu_id((long) Integer.parseInt(Tlist.get(0).getCourse_id()));
                gradeBean.setStu_name(Tlist.get(0).getExam_name().substring(0, 6) + Tlist.get(0).getCourse_id().substring(3, 5) + "班");
                Tlist.clear();
            }
            avgList.add(gradeBean);
        }
//       平均分先插入数据库中
        for (GradeBean bean : avgList) {
            System.out.println(bean);
            String insertsql = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Object[] p = {bean.getGrade_id(), bean.getExam_id(), bean.getCourse_id(), bean.getExam_name(),
                    bean.getStu_id(), bean.getStu_xueji(), bean.getStu_name(),
                    bean.getSubject1(), bean.getSubject1_classgrade(), bean.getSubject2_classgrade(),
                    bean.getSubject2(), bean.getSubject2_classgrade(), bean.getSubject2_classgrade(),
                    bean.getSubject3(), bean.getSubject3_classgrade(), bean.getSubject3_classgrade(),
                    bean.getSubject4(), bean.getSubject4_classgrade(), bean.getSubject4_classgrade(),
                    bean.getSubject5(), bean.getSubject5_classgrade(), bean.getSubject5_classgrade(),
                    bean.getSubject6(), bean.getSubject6_classgrade(), bean.getSubject6_classgrade(),
                    bean.getSubject7(), bean.getSubject7_classgrade(), bean.getSubject7_classgrade(),
                    bean.getSubject8(), bean.getSubject8_classgrade(), bean.getSubject8_classgrade(),
                    bean.getSubject9(), bean.getSubject9_classgrade(), bean.getSubject9_classgrade(),
                    bean.getZonghe(), bean.getZonghe_classgrade(), bean.getZonghe_grade(),
                    bean.getTotalscore(), bean.getClass_grade(), bean.getGrade(),
            };
            queryRunner.update(insertsql, p);
        }

        List<GradeBean> allListSecond = queryRunner.query(GetALLSql, new BeanListHandler<>(GradeBean.class));
        int sizeSecond = allListSecond.size();

        //排班级平均分
        for (int i = 0; i < strsSize; i++) {
            System.out.println("bean " + strs.get(i));
            List<GradeBean> Tlist = new ArrayList<>();
            for (GradeBean bean : avgList) {
                Tlist.add(bean);
            }
            SortGradeUtils sortGradeUtils = new SortGradeUtils();
            sortGradeUtils.SortGradeByAvgClass(Tlist, strs.get(i), tableName, queryRunner);
            Tlist.clear();
        }

//        全年级平均分插入
        String insertsql = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] p = {gradeBeanAllGrade.getGrade_id(), gradeBeanAllGrade.getExam_id(),
                gradeBeanAllGrade.getCourse_id(), gradeBeanAllGrade.getExam_name(),
                gradeBeanAllGrade.getStu_id(), gradeBeanAllGrade.getStu_xueji(), gradeBeanAllGrade.getStu_name(),
                gradeBeanAllGrade.getSubject1(), gradeBeanAllGrade.getSubject1_classgrade(),
                gradeBeanAllGrade.getSubject2_classgrade(),
                gradeBeanAllGrade.getSubject2(), gradeBeanAllGrade.getSubject2_classgrade(),
                gradeBeanAllGrade.getSubject2_classgrade(),
                gradeBeanAllGrade.getSubject3(), gradeBeanAllGrade.getSubject3_classgrade(),
                gradeBeanAllGrade.getSubject3_classgrade(),
                gradeBeanAllGrade.getSubject4(), gradeBeanAllGrade.getSubject4_classgrade(),
                gradeBeanAllGrade.getSubject4_classgrade(),
                gradeBeanAllGrade.getSubject5(), gradeBeanAllGrade.getSubject5_classgrade(),
                gradeBeanAllGrade.getSubject5_classgrade(),
                gradeBeanAllGrade.getSubject6(), gradeBeanAllGrade.getSubject6_classgrade(),
                gradeBeanAllGrade.getSubject6_classgrade(),
                gradeBeanAllGrade.getSubject7(), gradeBeanAllGrade.getSubject7_classgrade(),
                gradeBeanAllGrade.getSubject7_classgrade(),
                gradeBeanAllGrade.getSubject8(), gradeBeanAllGrade.getSubject8_classgrade(),
                gradeBeanAllGrade.getSubject8_classgrade(),
                gradeBeanAllGrade.getSubject9(), gradeBeanAllGrade.getSubject9_classgrade(),
                gradeBeanAllGrade.getSubject9_classgrade(),
                gradeBeanAllGrade.getZonghe(), gradeBeanAllGrade.getZonghe_classgrade(),
                gradeBeanAllGrade.getZonghe_grade(),
                gradeBeanAllGrade.getTotalscore(), gradeBeanAllGrade.getClass_grade(), gradeBeanAllGrade.getGrade(),
        };
        queryRunner.update(insertsql, p);
    }
}
