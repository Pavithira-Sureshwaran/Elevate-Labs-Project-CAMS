package com.college.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties p = new Properties();
            p.load(is);
            url = p.getProperty("db.url");
            user = p.getProperty("db.user");
            password = p.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {
                System.out.println(e);

            // e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url,user,password);
    }
}
