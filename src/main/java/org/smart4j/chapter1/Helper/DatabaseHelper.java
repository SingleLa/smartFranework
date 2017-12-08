package org.smart4j.chapter1.Helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.Util.ProsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2017/5/31.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final QueryRunner QUERT_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
    private static final BasicDataSource DATA_SOURCE ;

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    static {
        Properties props = ProsUtil.loadProps("config.properties");
        DRIVER = props.getProperty("jdbc.driver");
        URL = props.getProperty("jdbc.url");
        USERNAME = props.getProperty("jdbc.username");
        PASSWORD = props.getProperty("jdbc.password");
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(  PASSWORD);

    }

    /**
     * 获取数据库连接
     */

    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        try {
            conn = DATA_SOURCE.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
            LOGGER.error("get connecton fail ",e);
        }finally {
            CONNECTION_HOLDER.set(conn);
        }
        return  conn;
    }
    /**
     * 关闭连接
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error("close connection fail",e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }



    /**
     * 查询实体列表
     */
    public static <T>List<T> quertEntityList(Class<T> entityClass,String sql ,Object... params){
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERT_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        }catch (SQLException e){

        }finally {
            closeConnection();
        }
        return entityList;
    }
    /**
     * 查询单体对象
     */
    public static <T>T queryEntuty(Class<T> entityClass,String sql,Object... params){
        T entity = null;
        try {
            Connection conn = getConnection();
            entity = QUERT_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        }catch (SQLException e){

        }finally {
            closeConnection();
        }
        return entity;
    }
    /**
     * 执行查询语句
     */
    public static List<Map<String, Object>> executeQuery(String sql,Object... params){
        List<Map<String, Object>> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERT_RUNNER.query(conn,sql,new MapListHandler(),params);
        }catch (SQLException e){

        }finally {
            closeConnection();
        }
        return entityList;
    }
    /**
     * 执行更新语句（update,insert,delete）
     */
    public static int executeUpdate(String sql,Object... params){
        int row = 0;
        try{
            Connection conn =getConnection();
            row = QUERT_RUNNER.update(conn,sql,params);
        }catch (SQLException E){

        }finally {
            closeConnection();
        }
        return  row;
    }
    public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fileMap){

        String sql = "INSERT INTO "+ getTableName(entityClass) ;
        StringBuilder colums = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for(String filedName : fileMap.keySet()){
            colums.append(filedName).append(",");
            values.append("?, ");
        }
        colums.replace(colums.lastIndexOf(","),colums.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql += colums + " VALUES "+values;
        Object[] params = fileMap.values().toArray();
        return executeUpdate(sql,params) == 1;
    }
    public static String getTableName(Class<?> entityClass){
       return entityClass.getSimpleName();
    }
    /**
     * 更新实体
     */
    public  <T>boolean updataEntity(Class<T> entityClass,Map<String,Object> fileMap,long id){
        String sql = "Update "+ getTableName(entityClass) + "set";
        StringBuilder colums = new StringBuilder();
        for(String filedName : fileMap.keySet()){
            colums.append(filedName).append("=?, ");

        }
        sql += colums.substring(0,colums.lastIndexOf(", ")) + " WHERE id =? ";
        List<Object> paramsList = new ArrayList<>();
        paramsList.addAll(fileMap.values());
        paramsList.add(id);
        Object[] params = paramsList.toArray();
        return  executeUpdate(sql,params) == 1;
    }



}
