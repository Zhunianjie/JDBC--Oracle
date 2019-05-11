package com.ycinfo.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPro extends Properties {
    private static ReadPro instance =new ReadPro();

    private ReadPro(){
        InputStream is=null;

        try {
            is=ReadPro.class.getClassLoader().getResourceAsStream("db.properties");
            this.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
   public static ReadPro getInstance(){
        return instance;
   }
}
