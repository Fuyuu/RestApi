package edu.hongik.apihw.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConfig {
	// postgreSQL DB와의 연결 설정
	private static final String URL = "jdbc:postgresql://localhost:5432/hongik";
	private static final String USER = "postgres";
	private static final String PASSWORD = "1234";

	public static Connection getConnection() throws SQLException {
	    return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
