package net.java_school.db.dbpool;

import org.apache.commons.dbcp2.BasicDataSource;

public class MySQLConnectionManager extends ConnectionManager {

	public MySQLConnectionManager() {
		super("mysql");

		String driverClassName = "com.mysql.jdbc.Driver";
		String driverType = "jdbc:mysql";
		String options = "?useSSL=false&amp;useUnicode=yes&amp;characterEncoding=UTF-8";

		String url = driverType + "://" + dbServer + ":" + port + "/" + dbName + options;
		
		BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName(driverClassName);
		ds.setUrl(url);
		
		ds.setUsername(userID);
		ds.setPassword(passwd);
		ds.setInitialSize(initConn);
		ds.setMaxTotal(maxConn);
		ds.setMaxWaitMillis(maxWait);

		this.ds = ds;
	}

}