package ro.utcn.pt.assignment3.DataLayer;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *  Creates the connection to the Data Base
 * */
public class DBConnection {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/ordermanagement";

    static final String USER = "root";
    static final String PASS = "spune-miIshmael";

    public Connection connection;

    public static DBConnection db;

    /**
     *  Constructor creates the database connection
     * */
    private DBConnection(){
        try{
            System.out.println("Connecting to DATABASE...\n");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized DBConnection getConnection(){
        if(db == null){
            db = new DBConnection();
        }
        return db;
    }
}