package load.generator.utils;

import java.sql.*;


public class MysqlConnector {
    // JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://111.231.50.91:3306/accDB";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "sqxdz";
    private static final String PASS = "Biui1227..";
    private static Connection conn = null;
    private static Statement stmt = null;
    private static MysqlConnector instance=new MysqlConnector();

    public static MysqlConnector getInstance()
    {
        return instance;
    }

    public Boolean excuteSql(String sql)
    {



        try{
            PreparedStatement pstmt = conn.prepareCall("insert into table_1 values (?,?,?)");
            pstmt.setInt(1, 1);
            pstmt.setInt(2, 1);
            pstmt.setInt(3, 1);
            pstmt.executeUpdate();


            stmt.execute(sql);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(sql);
            System.exit(-1);
            return false;
        }
    }
    private MysqlConnector() {
        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            excuteSql("set session sql_mode='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION'");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
