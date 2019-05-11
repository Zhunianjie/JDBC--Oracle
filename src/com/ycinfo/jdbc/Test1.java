package com.ycinfo.jdbc;

import org.junit.Test;

public class Test1{
    public static void main(String[] args) {
        DBHelper db =new DBHelper();
        String sql="insert into userInfo values(?,?,?,?)";
        System.out.println(db.update(sql,1004,"小艾","123456","15096097777"));
    }
    @Test
    public void test1(){
        DBHelper db=new DBHelper();
        String sql="insert into dept values(?,?,?)";
        System.out.println(db.update(sql,60,"研发部","8606"));
    }

    @Test
    public void test2(){
        DBHelper db=new DBHelper();
        String sql="delete from dept where deptno =?";
        System.out.println(db.update(sql,60));
    }
}
