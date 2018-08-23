package org.smart4j.framework.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.PropsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


/**
 * @author tjj .
 * 数据库操作助手类
 */
public final class DatabaseHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER;
    //本地线程  为了隔离线程 每个线程之中只有一个Connection   ：每次connection的存取值 都在这里面操作
    private static final ThreadLocal<Connection> CONNECTION_THREAD;
    //数据库链接是个耗费资源和时间的过程 查询完数据库后，如果不关闭连接，而是暂时存放起来，当别人使用时，
    //把这个连接给他们使用。就避免了一次建立数据库连接和断开的操作时间消耗。
    private static final BasicDataSource DATA_SOURCE;
   /* private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;*/
    static{
    	CONNECTION_THREAD=new ThreadLocal<Connection>();
    	QUERY_RUNNER=new QueryRunner();   	
       // Properties conf= PropsUtil.loadProps("config.properties");
        String driver=ConfigHelper.getJDBCDriver();
        String  url=ConfigHelper.getJdbcUrl();
        String username=ConfigHelper.getJdbcUsername();
        String password=ConfigHelper.getJdbcPassword();
        DATA_SOURCE=new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
        
    }

    /**
     *获取数据库连接
     */
    public static Connection getConnection(){
        Connection conn=CONNECTION_THREAD.get();
        if(conn==null){
        try {
            conn=DATA_SOURCE.getConnection();
        } catch (SQLException e) {
           LOGGER.error("get connection failure",e);
           throw  new RuntimeException(e);
        }finally {
            CONNECTION_THREAD.set(conn);
        }
        }
        return conn;
    }
    
    /**
     * 获取数据源
     */
    public static BasicDataSource getDataSource(){
    	return DATA_SOURCE;
    }
   /* //关闭数据库连接     使用了连接池 可以省略这一步
     
    public static  void closeConnection(){
        Connection conn=CONNECTION_THREAD.get();
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_THREAD.remove();
            }
        }
    }*/
    /**
     * 查询实体  因为查询的 东西可能涉及到链接查询所以
     * 不能想插入这样 直接写 可以自动根据class 来写入表名
     */
    public static<T>List<T> queryEntityList(Class<T> entityClass,String sql,Object...params){
        List<T> entityList;
        try {
            Connection conn=getConnection();
            entityList=QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure ",e);
            throw new RuntimeException(e);
        }
        return  entityList;
    }
    /**
     * 查询实体
     * 因为查询的 东西可能涉及到链接查询所以
     * 不能想插入这样 直接写 可以自动根据class 来写入表名
     */
    public static <T>T queryEntity(Class<T>entityClass,String sql,Object...params){
        T entity;
        try {
            Connection conn=getConnection();
            entity=QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure",e);
            throw new RuntimeException(e);
        }
        return  entity;
    }
    /**
     * 执行查询语句
     * Map 表示列名与列值的映射关系 一条信息放在一个map中
     */
    public static List<Map<String,Object>>excuteQuery(String sql,Object...params){
        List<Map<String,Object>> result;
        Connection conn=getConnection();
        try {
            result=QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("excute query failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 只返回一个结果
     */
    public static String  query(String sql,Object...params){
        Connection conn=getConnection();
        String result=null;
        try {
         result=  QUERY_RUNNER.query(conn,sql,new ScalarHandler<String>(),params);
        } catch (SQLException e) {
            LOGGER.error("query failure ",e);
            throw  new RuntimeException(e);
        }
        return result;
    }
    /**
     * 结果集返回一个List中
     */
    public static List<Object[]> queryList(String sql,Object...params){
        Connection conn=getConnection();
         List<Object[]> list;
        try {
            //每一行是一个数组
            list=QUERY_RUNNER.query(conn,sql,new ArrayListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("query list failure",e);
            throw new RuntimeException(e);
        }
        return  list;
    }

    /**
     *返回单列结果集Set
     */
    public static <T> Set<T> querySet(String sql,Object...params){
        Set<T> result=new HashSet<T>();
        try {
            List<Object[]> resultList= queryList(sql,params);
            for (Object[] objArr:resultList) {
                result.add((T) objArr[0]);
            }
        } catch (Exception e) {
           LOGGER.error("query set failure",e);
           throw new RuntimeException(e);
        }
        return  result;
    }

    /**
     * 执行更新语句 包括update insert delete  根据sql操作关键字来判断
     * return The number of rows updated
     */
    public static int executeUpdate(String sql,Object...params){
        int rows=0;
        try {
            Connection conn=getConnection();
           LOGGER.info("sql :{}",sql);
            rows=QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
            LOGGER.error("execute update error",e);
        }
        return rows;
    }
    /**
     * 插入实体
     */
    public static <T>boolean insertEntity(Class<T> entityClass, Map<String,Object>filedmap){  	
    	if(CollectionUtil.isEmpty(filedmap)){
    		LOGGER.error("can not insert entity : filedMp is empty");
    		return false;
    	}
    	String sql="insert into "+getTableName(entityClass);
    	StringBuilder columns=new StringBuilder("(");
    	StringBuilder values=new StringBuilder("(");
    	for (String fieldName : filedmap.keySet()) {
			columns.append(fieldName).append(", ");
			values.append("? , ");
		}
    	columns.replace(columns.lastIndexOf(", "), columns.length(), ")");  	
    	values.replace(values.lastIndexOf(", "), values.length(), ")");  
    	sql+=columns+" values "+values;
    	Object[] params=filedmap.values().toArray();   	
    	return executeUpdate(sql, params)==1;
    	}
    
    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
    	if(CollectionUtil.isEmpty(fieldMap)){
    		LOGGER.error("can not update entity:fieldMap is empty");
    		return false;
    	}
    	String sql="update "+getTableName(entityClass)+" set ";
    	StringBuilder columns=new StringBuilder();
    	for (String   fieldName : fieldMap.keySet()) {
    		columns.append(fieldName).append("= ?, ");			
		}
    	sql+=columns.substring(0,columns.lastIndexOf(", "))+" where id =?";
    	List<Object> paramList=new ArrayList<Object>();
    	paramList.addAll(fieldMap.values());
    	paramList.add(id);
    	Object[] params=paramList.toArray();
    	return executeUpdate(sql, params)==1;
    }
    
    /**
     * 删除实体
     */
    public static <T>boolean deleteEntity(Class<T>entityClass,long id){
    	String sql="delete from "+getTableName(entityClass)+ " where id=?";
    	return executeUpdate(sql, id)==1;
    }
    /**
     * 执行sql文件
     */
    public static void excuteSqlFile(String filePath){
         InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
         BufferedReader reader=new BufferedReader(new InputStreamReader(is));
         String sql;
         try {
			while((sql=reader.readLine())!=null){
			 	DatabaseHelper.executeUpdate(sql);
			 }
		} catch (IOException e) {
		LOGGER.error("excute sql file failure",e);
			throw new RuntimeException(e);
		}
    }
    
    /**
     * 获取表名
     */
	private static<T> String getTableName(Class<T> entityClass) {		
		return entityClass.getSimpleName().toLowerCase();
	}
	/**
	 * 开启事务
	 */
	 public static void beginTransaction(){
		 Connection conn=getConnection();
		 if(conn!=null){
			 try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				LOGGER.error("bengin transaction faliure",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_THREAD.set(conn);
			}
		 }
	 }
	 /**
	  * 提交事务
	  */
	 public static void commitTransaction(){
		 Connection conn=getConnection();
		 if(conn!=null){
			 try {
				conn.commit();
			} catch (SQLException e) {
				LOGGER.error("commit transaction failure",e);
				throw new RuntimeException(e);
			}finally{
				CONNECTION_THREAD.remove();//事务提交或者回滚后需要移除本地线程变量中的connection
			}
		 }
	 }
	 /**
	  * 回滚事务
	  */
	    public static void rollbackTransaction(){
	    	Connection conn=getConnection();
	    	if(conn!=null){
	    		try {
					conn.rollback();
				} catch (SQLException e) {
					LOGGER.error("rollback transaction failure",e);
					throw new RuntimeException(e);
				}finally{
					CONNECTION_THREAD.remove();
				}
	    	}
	    }
    }


