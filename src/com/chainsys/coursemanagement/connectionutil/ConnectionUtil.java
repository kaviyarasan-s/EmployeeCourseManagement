package com.chainsys.coursemanagement.connectionutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection connection = DriverManager.getConnection(url, "hr", "hr");

		return connection;
	}

	public static void closeConnection(Connection connection,
			PreparedStatement preparedStatement, ResultSet resultSet)
			throws SQLException {
		if (connection != null) {
			connection.close();
		}

		if (preparedStatement != null) {
			preparedStatement.close();
		}

		if (resultSet != null) {
			resultSet.close();
		}

	}

}
