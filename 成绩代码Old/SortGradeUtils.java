package utils;

import Bean.GradeBean;
import org.apache.commons.dbutils.QueryRunner;

import java.text.DecimalFormat;
import java.util.*;

import static utils.ObjUtils.*;

public class SortGradeUtils {
    DecimalFormat df = new DecimalFormat(".00");

    public void SortGradeByAvgClass(List<GradeBean> tempList, String beanName, String tableName, QueryRunner queryRunner) throws Exception {
        // 按照成绩排序
        tempList.sort(new Comparator<GradeBean>() {
            @Override
            public int compare(GradeBean s1, GradeBean s2) {
                int v = 0;
                try {
                    v = -Double.compare(Double.parseDouble(ObjUtils.getFieldValue(s1, beanName).toString()), Double.parseDouble(ObjUtils.getFieldValue(s2, beanName).toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return v;
            }
        });


        int index = 0;// 排名
        double lastScore = -1;// 最近一次的分
        int step = 1;
        for (int i = 0; i < tempList.size(); i++) {
            GradeBean s = tempList.get(i);
            if (Double.compare(lastScore, (Double) getFieldValue(s, beanName)) != 0) { // 如果成绩和上一名的成绩不相同,那么排名+1
                lastScore = (Double) getFieldValue(s, beanName);
                index = index + step;
                //index++;
                step = 1;
            } else {
                step++;
            }
//            String sql = "UPDATE ? SET ? = ? WHERE ? = ?";
//            int update = queryRunner.update(sql, "exam111000020180910", beanName + "_classgrade", index, "grade_id", s.getGrade_id());
            //String sql = "UPDATE exam111000020180910 SET subject1_classgrade='1'";
            String sql = null;
            if (beanName.equals("totalscore")) {
                sql = "update " + tableName + " set  grade  = " + index + " where course_id = " + s.getCourse_id() + " and stu_id = " + s.getStu_id();

            } else {
                sql = "update " + tableName + " set " + beanName + "_grade" + " = " + index + " where course_id = " + s.getCourse_id() + " and stu_id = " + s.getStu_id();
            }
            System.out.println("平均分排名开始：");
            System.out.println("=-=-=-=-=-" + sql);
            int update = queryRunner.update(sql);
            System.out.println("\t学号 :" + s.getGrade_id() + "\t科目 :" + beanName + "\t名次 :" + index + "\t分数 :" + getFieldValue(s, beanName) + "\t名字 :" + s.getStu_name());
            System.out.println("平均分排名结束：");
        }
    }

    public double SortGradeByGrade(List<GradeBean> tempList, String beanName, String tableName, QueryRunner queryRunner) throws Exception {
        // 按照成绩排序
        tempList.sort(new Comparator<GradeBean>() {
            @Override
            public int compare(GradeBean s1, GradeBean s2) {
                int v = 0;
                try {
                    v = -Double.compare(Double.parseDouble(ObjUtils.getFieldValue(s1, beanName).toString()), Double.parseDouble(ObjUtils.getFieldValue(s2, beanName).toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return v;
            }
        });


        int index = 0;// 排名
        double lastScore = -1;// 最近一次的分
        int step = 1;
        double allGradeAvg = 0;
        for (int i = 0; i < tempList.size(); i++) {
            GradeBean s = tempList.get(i);
            allGradeAvg = (double) ObjUtils.getFieldValue(s, beanName) + allGradeAvg;
            if (Double.compare(lastScore, (Double) getFieldValue(s, beanName)) != 0) { // 如果成绩和上一名的成绩不相同,那么排名+1
                lastScore = (Double) getFieldValue(s, beanName);
                index = index + step;
                //index++;
                step = 1;
            } else {
                step++;
            }
//            String sql = "UPDATE ? SET ? = ? WHERE ? = ?";
//            int update = queryRunner.update(sql, "exam111000020180910", beanName + "_classgrade", index, "grade_id", s.getGrade_id());
            //String sql = "UPDATE exam111000020180910 SET subject1_classgrade='1'";
            String sql = null;
            if (beanName.equals("totalscore")) {
                sql = "update " + tableName + " set  grade  = " + index + " where grade_id = " + s.getGrade_id();

            } else {
                sql = "update " + tableName + " set " + beanName + "_grade" + " = " + index + " where grade_id = " + s.getGrade_id();
            }
            System.out.println("年级排名开始：");
            System.out.println("=-=-=-=-=-" + sql);
            int update = queryRunner.update(sql);
            //System.out.println(s.getStu_name() + " 更新成功 : " + i);
            System.out.println("\t学号 :" + s.getGrade_id() + "\t科目 :" + beanName + "\t名次 :" + index + "\t分数 :" + getFieldValue(s, beanName) + "\t名字 :" + s.getStu_name());
            System.out.println("年级排名结束：");
        }
        System.out.println("年级总分---------" + allGradeAvg + "          年级人数  " + tempList.size());
        allGradeAvg = allGradeAvg / tempList.size();
        double format = Double.parseDouble(df.format(allGradeAvg));
        System.out.println("单科分数平均===" + format);
        return format;
    }

    public double SortGradeByClass(List<GradeBean> tempList, String beanName, String tableName, QueryRunner queryRunner) throws Exception {
        //System.out.println("---------" + beanName);
        // 按照成绩排序

        tempList.sort(new Comparator<GradeBean>() {
            @Override
            public int compare(GradeBean s1, GradeBean s2) {
                int v = 0;
                try {
                    v = -Double.compare(Double.parseDouble(ObjUtils.getFieldValue(s1, beanName).toString()), Double.parseDouble(ObjUtils.getFieldValue(s2, beanName).toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return v;
            }
        });


        int index = 0;// 排名
        double lastScore = -1;// 最近一次的分
        int step = 1;
        double classTotal = 0;
        for (int i = 0; i < tempList.size(); i++) {
            GradeBean s = tempList.get(i);
            double value = (double) ObjUtils.getFieldValue(s, beanName);
            classTotal += value;
            if (Double.compare(lastScore, (Double) getFieldValue(s, beanName)) != 0) { // 如果成绩和上一名的成绩不相同,那么排名+1
                lastScore = (Double) getFieldValue(s, beanName);
                index = index + step;
                //index++;
                step = 1;
            } else {
                step++;
            }
//            String sql = "UPDATE ? SET ? = ? WHERE ? = ?";
//            int update = queryRunner.update(sql, "exam111000020180910", beanName + "_classgrade", index, "grade_id", s.getGrade_id());
            //String sql = "UPDATE exam111000020180910 SET subject1_classgrade='1'";
            String sql = null;
            if (beanName.equals("totalscore")) {
                sql = "update " + tableName + " set  class_grade  = " + index + " where grade_id = " + s.getGrade_id();

            } else {
                sql = "update " + tableName + " set " + beanName + "_classgrade" + " = " + index + " where grade_id = " + s.getGrade_id();
            }
            System.out.println("班级开始：");
            System.out.println("=-=-=-=-=-" + sql);
            int update = queryRunner.update(sql);
            //System.out.println(s.getStu_name() + " 更新成功 : " + i);
            System.out.println("\t学号 :" + s.getGrade_id() + "\t科目 :" + beanName + "\t名次 :" + index + "\t分数 :" + getFieldValue(s, beanName) + "\t名字 :" + s.getStu_name());
            System.out.println("班级结束：");
        }
        classTotal = classTotal / tempList.size();

        double format = Double.parseDouble(df.format(classTotal));
        return format;
    }
}
