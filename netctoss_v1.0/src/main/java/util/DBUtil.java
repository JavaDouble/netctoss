package util;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;





/**
 * DBCP2数据库连接池工具类 bdcp2.2版本
 * Oracle11g对应JDBC4.0 不支持dbcp2
 *
 * 使用DBCP连接池和事物管理ThreadLocal工具类
 * 读取src/main/resources包下的db.properties配置文件
 *
 * @author Double
 *
 */
public class DBUtil {

	private static ThreadLocal<Connection> tl;
	private static Properties properties=new Properties();
	private static BasicDataSource bds=null;

	static{

		try {
			tl=new ThreadLocal<Connection>();
			String path="db.properties";

			properties.load(DBUtil.class.getClassLoader().getResourceAsStream(path));

			String driver=properties.getProperty("jdbc.driver");
			String url=properties.getProperty("jdbc.url");
			String user=properties.getProperty("jdbc.username");
			String psw=properties.getProperty("jdbc.password");

			System.out.println("driver:"+driver);
			System.out.println("url:"+url);
			System.out.println("user:"+user);
			System.out.println("password:"+psw);


			int initialSize=Integer.valueOf(properties.getProperty("jdbc.initialSize"));
			int maxTotal=Integer.parseInt(properties.getProperty("jdbc.maxTotal"));
			int maxIdle=Integer.valueOf(properties.getProperty("jdbc.maxIdle"));
			long maxWait=Long.valueOf(properties.getProperty("jdbc.maxWait"));

			System.out.println("initialSize:"+initialSize);
			System.out.println("maxTotal:"+maxTotal);
			System.out.println("maxIdle:"+maxIdle);
			System.out.println("maxWait:"+maxWait);


			bds=new BasicDataSource();
			bds.setDriverClassName(driver);
			bds.setUrl(url);
			bds.setUsername(user);
			bds.setPassword(psw);

			//设置初始化参数 我们叫这种参数叫管理策略参数
			bds.setInitialSize(initialSize);
			//设置最大连接数
			bds.setMaxActive(maxTotal);
			//设置最大等待时间
			bds.setMaxWait(maxWait);
			//设置最大空闲
			bds.setMaxIdle(maxIdle);

		} catch (Exception e) {
			System.out.println("加载静态资源失败!");
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		tl.set(bds.getConnection());
		return bds.getConnection();
	}

	/**
	 * 关闭数据库连接
	 * @return
	 */
	public static void closeConnection() {
		if(tl.get()!=null){
			try {
				//关闭连接前，提交事务
				TransCommit();
				tl.get().close();
				tl.remove();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 开启事务
	 * @throws Exception
	 */
	public static void TransBegin() throws Exception {
		try {
			tl.get().setAutoCommit(false);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 回滚事务
	 * @throws SQLException
	 */
	public static void TransRollBack() {
		try {
			tl.get().rollback();
			tl.get().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交事务
	 * @throws SQLException
	 */
	public static void TransCommit() throws SQLException {
		tl.get().commit();
		tl.get().setAutoCommit(true);
	}

	/**
	 * 测试连接
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Connection conn=DBUtil.getConnection();
			System.out.println(conn.getClass());
			System.out.println("数据库连接成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeConnection();
		}
	}
}
