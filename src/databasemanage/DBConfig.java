package databasemanage;

/**
 * Created by tangjie on 2017/1/3.
 */
public class DBConfig {
    private static DBConfig instance = null;

    private String jdbcDriver;//驱动器名称
    private String dbUrl;//数据库url
    private String dbUsername;//数据库用户名
    private String dbPassword;//密码


    private int initialConnectionsNum = 5;//初始化连接池大小
    private int maxConnectionsNum = 20;//最大连接池长度
    private int incrementalConnections = 1;//每次递增的长度

    /**
     * 得到静态实例
     * @return 返回静态实例
     */
    public static DBConfig getInstance()
    {
        if (instance == null)
        {
            instance = new DBConfig();
        }
        return instance;
    }


    /**
     * 构造函数
     * */
    public DBConfig()
    {
        try {
            this.jdbcDriver="com.mysql.jdbc.Driver";
            this.dbUrl="jdbc:mysql://localhost:3306/haodaifu?useUnicode=true&characterEncoding=gbk";
            this.dbUsername ="root";
            this.dbPassword="tj203612";
        }catch(Exception ee){
            System.out.println("连接异常"+ee.toString());
        }
    }



    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public int getInitialConnectionsNum() {
        return initialConnectionsNum;
    }

    public void setInitialConnectionsNum(int initialConnectionsNum) {
        this.initialConnectionsNum = initialConnectionsNum;
    }

    public int getMaxConnectionsNum() {
        return maxConnectionsNum;
    }

    public void setMaxConnectionsNum(int maxConnectionsNum) {
        this.maxConnectionsNum = maxConnectionsNum;
    }

    public int getIncrementalConnections() {
        return incrementalConnections;
    }

    public void setIncrementalConnections(int incrementalConnections) {
        this.incrementalConnections = incrementalConnections;
    }

    public static void setInstance(DBConfig instance) {
        DBConfig.instance = instance;
    }
}

