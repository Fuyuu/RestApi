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
	// Jsoup을 이용해 크롤링한 데이터를 SQL Query를 이용해 DB에 삽입
	public void insertCrawledData(List<Students> studentList) {
		
        String sql = "INSERT INTO students (sid, name, email, degree, graduation) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

        	for (Students student : studentList) {
        		// 크롤링한 값들을 각 Students 객체의 attribute에 저장하여 리스트 생성
        	    pstmt.setInt(1, student.getSid()); 
        	    pstmt.setString(2, student.getName());
        	    pstmt.setString(3, student.getEmail());
        	    pstmt.setString(4, student.getDegree());
        	    pstmt.setInt(5, student.getGraduation());
        	    pstmt.addBatch(); // Batch 추가
        	}

            pstmt.executeBatch(); // 일괄 처리 실행
            System.out.println("DB에 데이터를 성공적으로 삽입했습니다.");

        } catch (SQLException e) {
            System.err.println("데이터 삽입 중 오류 발생: " + e.getMessage());
        }
    }
}
