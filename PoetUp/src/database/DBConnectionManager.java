package database;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBConnectionManager {

	public static String url = "jdbc:mysql://localhost:3306/";
	public static String dbName = "mydb";
	public static String driver = "com.mysql.cj.jdbc.Driver";
	public static String userName ;
	public static String password;
	public static String path_prop="app/properties.txt";
	

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		File file = new File(path_prop);
		try(Scanner scanner = new Scanner(file)){
			System.setIn(new FileInputStream(file));
			userName=scanner.nextLine();
			password=scanner.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		Connection conn = null;
		Class.forName(driver); 

	
		conn = DriverManager.getConnection(url+dbName,userName,password);

		
		return conn;
	}

	public static void closeConnection(Connection c) throws SQLException {

		
		c.close();
	}



	public static ResultSet selectQuery(String query) throws ClassNotFoundException, SQLException {


		//.1 creo la connessione

		Connection conn = getConnection();


		//2. creo lo statement
		Statement statment = conn.createStatement();

		//eseguo la query che ho fornito come input
		ResultSet ret = statment.executeQuery(query); //"SELECT * from STUDENTI where .... "

		//ci ritorna il result set
		return ret;
	}

	public static int insertQuery(String query) throws ClassNotFoundException, SQLException {

		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		int ret = statement.executeUpdate(query);
		conn.close();
		return ret;
	}

	public static Integer insertQueryReturnGeneratedKey(String query) throws ClassNotFoundException, SQLException {
		Integer ret = null;

		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()){
		    ret = rs.getInt(1);
		}

		conn.close();

		return ret;
	}

	public static int UpdateQuery(String query) throws ClassNotFoundException, SQLException {

		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		int ret = statement.executeUpdate(query);
		conn.close();
		return ret;
	}

	public static int deleteQuery(String query) throws ClassNotFoundException, SQLException{
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		int ret = statement.executeUpdate(query);
		conn.close();
		return ret;
	}
}