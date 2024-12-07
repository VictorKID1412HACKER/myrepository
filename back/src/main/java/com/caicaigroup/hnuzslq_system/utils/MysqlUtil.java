package com.caicaigroup.hnuzslq_system.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 备份MySQL数据库(Windows)与删库
 */
@Component
public class MysqlUtil {

    private static String mysqlIp;// mysql主机ip
    private static String mysqlPort;//端口
    private static String userName;//用户名
    private static String password;//密码
    private static String database;//数据库名
    private static String filePath;//存储文件夹
    private static String DBURL;

    @Value("${file.download.url.oldDatabases}")
    public void setFilePath(String filePath) {
        MysqlUtil.filePath = filePath;
    }

    @Value("${spring.datasource.username}")
    public void setUserName(String userName) {
        MysqlUtil.userName = userName;
    }

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        MysqlUtil.password = password;
    }

    @Value("${spring.datasource.database}")
    public void setDatabase(String database) {
        MysqlUtil.database = database;
    }

    @Value("${spring.datasource.mysqlIp}")
    public void setMysqlIp(String mysqlIp) {
        MysqlUtil.mysqlIp = mysqlIp;
    }

    @Value("${spring.datasource.mysqlPort}")
    public void setMysqlPort(String mysqlPort) {
        MysqlUtil.mysqlPort = mysqlPort;
    }

    @Value("${spring.datasource.url}")
    public void setDBURL(String DBURL) {
        MysqlUtil.DBURL = DBURL;
    }

    private static final SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 备份
     */
    public static void backUpData() {
        String format = yearMonthDayFormat.format(new Date());
        //备份文件全路径
        System.out.println("++++++++++" + filePath);
        if (!new File(filePath).exists()) {
            new File(filePath).mkdir();
        }
        String resultFile = filePath + File.separator + mysqlIp + "_" + database + "_" + format + ".sql";
        new File(resultFile).delete();
        try {
            String cmd = "mysqldump --column-statistics=0 -h" + mysqlIp + " -P" + mysqlPort + " -u" + userName + " -p" + password + " --databases " + database
                    +" --tables" + getTableNames() + " > " + resultFile;
            //打印出命令，在CMD中运行一下看看是否报错
//            System.out.println("cmd:" + new String[]{"bash", "-c", cmd}.toString());
//            Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});
            System.out.println(cmd);
            Process process = Runtime.getRuntime().exec("cmd /c " + cmd);
            if (process == null) {
                System.err.println(process.getErrorStream());
                process.getErrorStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删库
     * @throws Exception
     */
    public static void deleteData () throws Exception {
        Connection conn=null;      //数据库连接
        Statement stmt=null;       //数据库操作
        String sql="DELETE FROM t_fbpz";  //SQL语句
        sql+="DELETE FROM t_fbqk";  //SQL语句
        sql+="DELETE FROM t_gkcjx";  //SQL语句
        sql+="DELETE FROM t_jhtz";  //SQL语句
        sql+="DELETE FROM t_ksxx";  //SQL语句
        sql+="DELETE FROM t_lqfb";  //SQL语句
        sql+="DELETE FROM t_zsjh";  //SQL语句
        sql+="DELETE FROM t_zydm";  //SQL语句
        sql+="DELETE FROM t_zyzy";  //SQL语句
        Class.forName("com.mysql.cj.jdbc.Driver");    //加载驱动程序
        conn= DriverManager.getConnection(DBURL,userName,password);
        stmt=conn.createStatement();   //实例化Statement对象
        stmt.executeUpdate(sql);    //执行数据库更新操作
        stmt.close();     //操作关闭
        conn.close();     //数据库关闭
    }

    /**
     * 获取连接
     * @return
     */
    public static Connection getConn () {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动
            conn = DriverManager.getConnection(DBURL, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭连接
     * @param state
     * @param conn
     */
    public static void close (PreparedStatement state, Connection conn) {
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getTableNames() throws Exception{
        Connection connection = getConn();
        String tableNames = "";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select table_name from information_schema.tables where table_schema='"+database+"'");
        System.out.println("方法被执行");
        while (rs.next())
        {
            // System.out.println(rs.getString("Cno"));
            System.out.println(rs.getString("TABLE_NAME"));
            //System.out.println(rs.getString());
//                System.out.println(rs.getString("tables"));/
            String name = rs.getString("TABLE_NAME");
            if ("t_".equals(name.substring(0,2)) || "td".equals(name.substring(0,2))){
                if ("hn".equals(name.substring(name.length() - 2, name.length())) ||
                        "gs".equals(name.substring(name.length() - 2, name.length()))||
                            "sh".equals(name.substring(name.length() - 2, name.length()))) {
                    continue;
                } else {
                    tableNames +=" " + name;
                }
            }
        }
        return tableNames;
    }

    /**
     * 防止sql注入
     * @param s 输入字符串
     * @return
     */
    public static Boolean sqlZhuru(String s) {
        if (s.contains(".") || s.contains("=") || s.contains("&")) {
            return false;
        } else {
            return true;
        }
    }

}