package com.chainsys.coursemanagement.connectionutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() throws Exception {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Class not found!");
		}
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, "hr", "hr");
		} catch (SQLException e) {
			throw new Exception("Driver not found!");
		}
		return connection;
	}
	public static void closeConnection(Connection connection,
			PreparedStatement preparedStatement, ResultSet resultSet) throws Exception {
		try {
			if (connection != null) {
				connection.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			throw new Exception("Unable to close the connection!");
		}
	}
}
