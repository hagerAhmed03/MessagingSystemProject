package broker;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionTwo {
	
	private static BasicDataSource dataSource;
	 
    private static void getDataSource()
    {
 
        if (dataSource == null)
        {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setUrl("jdbc:mysql://localhost:3306/MessagingDataBase");
            ds.setUsername("root");
            ds.setPassword("root");
 
 
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
 
            dataSource = ds;
        }
    }
    
    
    public void setDataSource() 
    {
    	DatabaseConnectionTwo.getDataSource();
    }
     
    
    public Connection getConnection() 
    {
    	try {
    		return 	dataSource.getConnection();
    	}
    	catch(Exception e) {
    		return null;
    	}
       
    	
    }
  
}