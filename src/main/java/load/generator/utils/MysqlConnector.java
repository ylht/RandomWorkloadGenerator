package load.generator.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;


public class MysqlConnector {
    // JDBC 驱动名及数据库 URL

    private final String DB_URL = "jdbc:mysql://111.231.50.91:3306/accDB?useSSL=false";

    // 数据库的用户名与密码，需要根据自己的设置

    private final String USER = "sqxdz";
    private final String PASS = "Biui1227..";
    private Connection conn = null;
    private Statement stmt = null;


    public MysqlConnector() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            excuteSql("set session sql_mode='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public Boolean excuteSql(String sql) {
        try {
            stmt.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
            System.exit(-1);
            return false;
        }
    }

    public void excuteSql(String sql, ArrayList<String> val) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < val.size(); i++) {
                pstmt.setObject(i + 1, val.get(i));
            }
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(sql);
            System.exit(-1);
        }
    }
}
