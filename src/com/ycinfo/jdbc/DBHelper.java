package com.ycinfo.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
    static {
        try {
            Class.forName(ReadPro.getInstance().getProperty("ClassName"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接的方法
     */
    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(ReadPro.getInstance().getProperty("url"),
                    ReadPro.getInstance().getProperty("user"),ReadPro.getInstance().getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * 关闭资源的方法
     * @param rs
     * @param pstmt
     * @param con
     */
    private void closeAll(ResultSet rs, PreparedStatement pstmt,Connection con){
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
            if (con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给预编译执行语句中的占位符赋值
     * @param pstmt 要赋值的预编译块
     * @param params 值列表
     */
    private void setValues(PreparedStatement pstmt,Object ... params){
        if (pstmt==null||params==null||params.length <=0){
            return;
        }
        for(int i=0,len=params.length;i<len; ++i){
            try {
                pstmt.setObject(i+1,params[i]);
            } catch (SQLException e) {
                System.out.println("第"+(i+1)+"个占位符注值失败。。。");
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行更新操作，包括update，delete和insert
     * @param sql 要执行的更新语句
     * @param params 要执行的更新语句中占位符？对应的值
     * @return 返回语句执行后，影响的数据行数
     */
    public int update(String sql,Object ...params){
        Connection con=null;
        PreparedStatement pstmt =null;
        int result =-1;
        try{ con=this.getConnection();
            pstmt=con.prepareStatement(sql);
            this.setValues(pstmt,params);
            result=pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            this.closeAll(null,pstmt,con);
        }
        return result;
    }

    /**
     * 查询数据
     * @param sql 要执行查询的语句
     * @param params 要执行查询语句中占位符的值
     * @return 每一行数据存到一个map中，以列名为建，对应列的值为值。将所有行记录到list集合中
     */
    public List<Map<String,String>> find(String sql,Object ...params){
        List<Map<String,String>> list=new ArrayList<Map<String, String>>();
        Connection con=null;
        PreparedStatement pstmt =null;
        ResultSet rs=null;

        try{
            con=this.getConnection();
            pstmt =con.prepareStatement(sql);
            this.setValues(pstmt,params);
            rs=pstmt.executeQuery();
            Map<String,String>map=null;

            ResultSetMetaData rsmd =rs.getMetaData(); //获取结果集在原数据信息
            int columnCount =rsmd.getColumnCount(); //获取结果中的列数量
            String[] columnNames =new String[columnCount]; //声明一个数组，用来存放列的名称
            for (int i=0;i<columnCount;++i){
                columnNames[i]=rsmd.getColumnName(i+1);
            }

            while (rs.next()){  //每循环一次就是一行数据，那么要将这一行数据存到map中
                map=new HashMap<String, String>();
                //列的名称已经写死，无法查其他的表？
                //如果能取到，我们可以将这些列名存到一个数组中，然后遍历数组取值即可
                for (String col :columnNames){
                    map.put(col,rs.getString(col));
                }
                list.add(map);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            this.closeAll(rs,pstmt,con);
        }
        return list;
    }

    /**
     *
     * @param sql
     * @param params
     * @return
     */
    public int getTotal(String sql, Object ... params){
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int result =0;

        try{
            con=this.getConnection();
            pstmt=con.prepareStatement(sql);
            this.setValues(pstmt,params);
            rs=pstmt.executeQuery();
            if (rs.next()){
                result =rs.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
