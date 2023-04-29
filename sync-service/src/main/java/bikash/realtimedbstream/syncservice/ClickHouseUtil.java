package bikash.realtimedbstream.syncservice;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clickhouse.client.internal.google.type.DateTime;
import com.clickhouse.jdbc.ClickHouseConnection;
import com.clickhouse.jdbc.ClickHouseDataSource;

import bikash.realtimedbstream.syncservice.model.Employees;

@Service
public class ClickHouseUtil {
	@Value("${clickhouse.uri}")
	private static String url;
	
	private static ClickHouseConnection getConnection() throws Exception {
		ClickHouseDataSource dataSource = new ClickHouseDataSource("jdbc:ch://admin:admin@localhost:8123");		
		return dataSource.getConnection();
	}
	
	public static void initDB() throws Exception {
		ClickHouseConnection conn =getConnection();
		Statement stmt = conn.createStatement() ;	    
		boolean createDbOk=stmt.execute("CREATE DATABASE IF NOT EXISTS employeedb");
	    boolean createTableOk= stmt.execute("create table if not exists employeedb.employees(\n"
	    		+ "    empid Int64,\n"
	    		+ "    fname String not null,\n"
	    		+ "    lname String,\n"
	    		+ "    dept String,\n"
	    		+ "    email String,\n"
	    		+ "    dob Date\n"
	    		+ ")ENGINE = MergeTree()\n"
	    		+ "PRIMARY KEY(empid)");
	    stmt.close();
	    conn.close();
	}
	
	public static boolean insert(Employees emp) throws Exception {
		ClickHouseConnection conn =getConnection();
		PreparedStatement pstmt=conn.prepareStatement("INSERT INTO employeedb.employees\n"
	    		+ "(empid, fname, lname, dept, email, dob)\n"
	    		+ "VALUES(?, ?, ?, ?, ?, ?)");
	    pstmt.setInt(1, emp.getEmpid());
	    pstmt.setString(2,emp.getFname());
	    pstmt.setString(3,emp.getLname());
	    pstmt.setString(4,emp.getDept());
	    pstmt.setString(5,emp.getEmail());
	    
	    pstmt.setObject(6, emp.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	    boolean ok= pstmt.execute();
	    pstmt.close();
	    conn.close();
	    return ok;
	}
	public static boolean update(Employees emp) throws Exception {
		ClickHouseConnection conn =getConnection();
		PreparedStatement pstmt=conn.prepareStatement("ALTER TABLE employeedb.employees\n"
	    		+ "UPDATE fname=?, lname=?, dept=?, email=?, dob=?\n"
	    		+ "WHERE empid=?");
	    pstmt.setString(1,emp.getFname());
	    pstmt.setString(2,emp.getLname());
	    pstmt.setString(3,emp.getDept());
	    pstmt.setString(4,emp.getEmail());
	    pstmt.setObject(5,emp.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	    pstmt.setInt(6, emp.getEmpid());
	    boolean ok= pstmt.execute();
	    pstmt.close();
	    conn.close();
	    return ok;
	}
	public static boolean delete(Employees emp) throws Exception {
		ClickHouseConnection conn =getConnection();
		PreparedStatement pstmt=conn.prepareStatement("ALTER TABLE employeedb.employees\n"
	    		+ "DELETE WHERE empid=?");
	    pstmt.setInt(1, emp.getEmpid());
	    boolean ok=pstmt.execute();
	    pstmt.close();
	    conn.close();
	    return ok;
	}
}
