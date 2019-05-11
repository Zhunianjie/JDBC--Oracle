package com.ycinfo.jdbc;

import java.sql.*;
import java.util.Scanner;

public class Demo5 {
    public static void main(String[] args) {
        Connection con =null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Scanner sc =new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:oracle","scott","a");
            String sql ="select usid,unanme,pwd,tel from userInfo where uname=? and pwd=?";
            pstmt = con.prepareStatement(sql); //预编译执行语句
            System.out.println("请输入账号：");
            String account=sc.nextLine();

            System.out.println("请输入密码：");
            String pwd=sc.nextLine();

            //给预编译执行语句中的占位符赋值
            pstmt.setString(1,account); //给预编译语句中的第一个？赋值
            pstmt.setObject(2,pwd);

            rs=pstmt.executeQuery(); //执行编译语句
            if (rs.next()){
                System.out.println(rs.getObject(1)+"\t"+rs.getObject(2)+"\t"+rs.getObject("pwd")+"\t"+rs.getObject("tel"));
            }else{
                System.out.println("账号或密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if (rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        }
    }



