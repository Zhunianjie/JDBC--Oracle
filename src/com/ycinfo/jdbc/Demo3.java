package com.ycinfo.jdbc;

import java.sql.*;
import java.util.Scanner;

public class Demo3 {
    public static void main(String[] args) {
        Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        Scanner sc=new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","a");
            stmt=con.createStatement();
            System.out.println("请输入账号：");
            String account=sc.nextLine();
            System.out.println("请输入密码：");
            String pwd=sc.nextLine();
            String sql="select usid,uname,pwd,tel from userInfo where uname='"+account+" ' and pwd='"+pwd+"'";
            rs=stmt.executeQuery(sql);

            //处理结果
            if(rs.next()){
                System.out.println(rs.getObject(1)+"\t"+rs.getObject(2)+"\t"+rs.getObject("pwd")+"\t"+rs.getObject("tel"));
            }else{
                System.out.println("账户或密码错误");
            }
            while(rs.next()){
                System.out.println(rs.getObject(1)+"\t"+rs.getObject("pwd")+"\t"+rs.getObject("tel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{}
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
