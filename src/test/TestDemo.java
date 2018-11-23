package test;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

class Stu {// 学生类
    private String name;
    private double score;// 成绩

    public Stu(String name, double score) {
        this.name = name;
        this.score = score;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

//测试类
public class TestDemo {
    public static void main(String[] args) {
        List<Stu> stus = Arrays.asList(new Stu("Tom", 79.5), new Stu("Jack", 52), new Stu("Amdy", 79.5),
                new Stu("Lucy", 68), new Stu("Cherry", 79.5), new Stu("Jerry", 52), new Stu("Sweet", 91),
                new Stu("Solem", 65));
        fun1(stus);
        System.out.println("---------------分割线---------------------");
        fun2(stus);
    }

    // 方法一:传统的方法
    public static void fun1(List<Stu> stus) {
        // 按照成绩排序
        stus.sort(new Comparator<Stu>() {
            @Override
            public int compare(Stu s1, Stu s2) {
                return -Double.compare(s1.getScore(), s2.getScore());
            }
        });
        int index = 0;// 排名
        double lastScore = -1;// 最近一次的分

        for (int i = 0; i < stus.size(); i++) {
            Stu s = stus.get(i);
            if (Double.compare(lastScore, s.getScore())!=0) { // 如果成绩和上一名的成绩不相同,那么排名+1
                lastScore = s.getScore();
                index++;
            }
            System.out.println("名次:" + index + "\t分数" + s.getScore() + "\t名字" + s.getName());
        }
    }


    // 方法2: Java8开始支持的Lambada表达式配合 Stream API 来进行分组排序
    public static void fun2(List<Stu> stus) {
        List<Entry<Double, List<Stu>>> list = stus.stream().collect(Collectors.groupingBy(Stu::getScore)).entrySet()
                .stream().sorted((s1, s2) -> -Double.compare(s1.getKey(), s2.getKey())).collect(Collectors.toList());
        int index = 1;
        for (Entry<Double, List<Stu>> entry : list) {
            System.out.print("名次:" + index + "\t分数:" + entry.getKey() + "\t名字");
            entry.getValue().forEach((s) -> System.out.print("  " + s.getName()));
            System.out.println();
            index++;
        }
    }
}