package edu.hongik.apihw.crawl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.hongik.apihw.config.DatabaseConfig;
import edu.hongik.apihw.entity.Students;

@Service
public class DataInserter {
	
	public void insertCrawledData(List<Students> dataList) {
        String sql = "INSERT INTO students (sid, name, email, degree, graduation) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

        	for (Students student : dataList) {
        	    pstmt.setInt(1, student.getSid()); // Students 객체에서 ID 가져오기
        	    pstmt.setString(2, student.getName());
        	    pstmt.setString(3, student.getEmail());
        	    pstmt.setString(4, student.getDegree());
        	    pstmt.setInt(5, student.getGrad());
        	    pstmt.addBatch(); // Batch 추가
        	}

            pstmt.executeBatch(); // 일괄 처리 실행
            System.out.println("Data inserted successfully!");

        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }
}
