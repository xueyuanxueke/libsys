package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
public class DBUtil {
	
	 public static final String Driver = "com.mysql.jdbc.Driver";
	 public static final String url = "jdbc:mysql://localhost:3306/lib_sys?serverTimezone=Asia/Shanghai";
	 public static final String username = "root";
	 public static final String pwd = "root";
	 
	 public static Connection getCon() {
	     Connection conn = null;
	     try {
	         Class.forName(Driver);
	         conn = DriverManager.getConnection(url,username,pwd);       
	     } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     }
	     return conn;
	 }
	    
	 public static void close(Connection conn) {
	     try {
	         if(conn!=null) {
	             conn.close();
	         }
	     } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     }
	 }
	 
	 /**
	  * 执行增删改操作
	  * @param con 连接对象
	  * @param sql SQL语句
	  * @param params 替换占位符的参数
	  * @return 受影响的行数
	  * @throws SQLException
	  */
	 public static int executeUpdate(Connection con, String sql, Object... params) throws SQLException {
	     try (PreparedStatement stmt = con.prepareStatement(sql)) {
	         for (int i = 0; i < params.length; ++i) {
	             stmt.setObject(i + 1, params[i]);
	         }
	         return stmt.executeUpdate();
	     }
	 }
	 
	 /**
	  * 开启事务
	  * @param connection 连接对象
	  */
	 public static void beginTx(Connection connection) {
	     try {
	         connection.setAutoCommit(false);
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }

	 /**
	  * 提交事务
	  * @param connection 连接对象
	  */
	 public static void commitTx(Connection connection) {
	     try {
	         connection.commit();
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }

	 /**
	  * 回滚事务
	  */
	 public static void rollbackTx(Connection connection) {
	     try {
	         connection.rollback();
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }

	 
     public static void main(String[] args) {
	     System.out.println(DBUtil.getCon());
     }

}
