package databasemanage;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Created by tangjie on 2017/1/3.
 */
public class DBConnection {
    /**
     * ************************
     * 对数据库连接的封装
     * ************************
     *
     */
    private static DBConnection dbCon=null;
    private ConnectionPool pool;

    /**
     * 构造函数
     * @throws SQLException
     */
    public  DBConnection() throws SQLException
    {
        pool=ConnectionPool.getInstance();
        //DriverManager.setLoginTimeout(10);
    }

    /**
     * 获取静态实例
     * @return 获取静态实例
     * @throws SQLException
     */
    public static DBConnection  getInstance() throws SQLException
    {
        if(dbCon==null)
        {
            dbCon=new DBConnection();
            //DriverManager.setLoginTimeout(10);
        }
        return dbCon;
    }

    /**
     * 获取数据库连接Connection
     * @return 数据库连接Connection
     * @throws SQLException
     */
    public  Connection getConnection() throws SQLException
    {
        return pool.getConnection();
    }
    /**
     * 归还数据库连接给连接池
     */
    public void close(Connection con)
    {
        pool.returnConnection(con);;
    }
    /**
     * 获取数据库连接池实例
     * @return 数据库连接池实例
     */
    public ConnectionPool getPool()
    {
        return this.pool;
    }
}
