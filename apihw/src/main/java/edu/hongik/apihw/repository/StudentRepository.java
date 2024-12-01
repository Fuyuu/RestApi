package edu.hongik.apihw.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.hongik.apihw.config.DatabaseConfig;

@Repository
public class StudentRepository {

	public int countByDegree(String degree) {
		// 각 학위별 학생 수를 출력하는 SQL문
		String sql = "SELECT COUNT(*) AS count FROM students WHERE degree = ?";
		int count = 0;
		
		try (Connection conn = DatabaseConfig.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, degree);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt("count");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("학생 수 집계 중 오류 발생: " + e.getMessage());
		}
		
		return count;
	}
}
