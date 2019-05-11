package com.ycinfo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc {

    public static void main(String[] args) {
        Connection con=null;
        Statement stmt=null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","a");
            //System.out.println(con);
            stmt=con.createStatement();
            String sql="insert into dept values(60,'研发部',8606)";
            int result =stmt.executeUpdate(sql);
            if (result>0){
                System.out.println("部门信息添加成功。。。");
            }else{
                System.out.println("部门信息添加失败。。。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{}
        if (stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
