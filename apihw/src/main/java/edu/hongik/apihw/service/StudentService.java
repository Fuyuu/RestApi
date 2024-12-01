package edu.hongik.apihw.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.hongik.apihw.config.DatabaseConfig;
import edu.hongik.apihw.entity.Students;
import edu.hongik.apihw.repository.StudentRepository;

@Service
public class StudentService {
	
	public String getDegreeByName(String name) {
		// 특정 이름을 가진 학생의 학위를 질의
		String sql = "SELECT degree FROM students WHERE name = ?";
		List<String> degrees = new ArrayList<>();
		
		try (Connection conn = DatabaseConfig.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, name);
			try (ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					degrees.add(rs.getString("degree"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Database Error: " + e.getMessage(), e);
		}
		
		if (degrees.isEmpty()) {
			// 주어진 이름을 가진 학생이 DB에 없음
			throw new IllegalStateException("No such student.");
		} else if (degrees.size() > 1) {
			// 동일한 이름을 가진 학생이 여러 명 존재함
			throw new IllegalStateException("There are multiple students with the same name. Please provide an email address instead.");
		}
		
		return degrees.get(0);
	}
	
	
	public String getEmailByName(String email) {
		// 특정 이름을 가진 학생의 이메일 주소를 질의	
		String sql = "SELECT email FROM students WHERE name = ?";
		List<String> emails = new ArrayList<>();
		
		try (Connection conn = DatabaseConfig.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, email);
			try (ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					emails.add(rs.getString("email"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Database Error: " + e.getMessage(), e);
		}
		
		if (emails.isEmpty()) {
			// 주어진 이름을 가진 학생이 DB에 없음
			throw new IllegalStateException("No such student.");
		} else if (emails.size() > 1) {
			// 동일한 이름을 가진 학생이 여러 명 존재함
			throw new IllegalStateException("There are multiple students with the same name. Please contact the administrator by phone.");
		}
		
		return emails.get(0);
	}
	
	

	private final StudentRepository studentRepository;
	
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	public int getStudentCountByDegree(String degree) {
		// 각 학위 별 학생의 수를 반환
		List<String> validDegrees = List.of("PhD", "Masters", "Undergraduate");
		if (!validDegrees.contains(degree)) {
			// PhD, Masters, Undergraduate 이외의 학위를 질의했을 경우
			throw new IllegalArgumentException("유효하지 않은 학위명입니다.");
		}
		
		return studentRepository.countByDegree(degree);
	}
	
	public void registerStudent(Students student) {
		// 중복을 체크하기 위한 SQL문과 DB에 새로운 학생을 추가하기 위한 SQL문
		String checkQuery = "SELECT COUNT(*) FROM students WHERE name = ? AND email = ? AND degree = ? AND graduation = ?";
		String insertQuery = "INSERT INTO students (sid, name, email, degree, graduation) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DatabaseConfig.getConnection();
			PreparedStatement checkStmt = conn.prepareStatement(checkQuery);	
			PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
			
			// 중복 확인
	        checkStmt.setString(1, student.getName());
	        checkStmt.setString(2, student.getEmail());
	        checkStmt.setString(3, student.getDegree());
	        checkStmt.setInt(4, student.getGraduation());
	        ResultSet rs = checkStmt.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            throw new IllegalArgumentException("Already registered.");
	        }
	        
			// 주어진 값들을 각 Students 객체의 attribute에 저장하여 DB에 추가
			insertStmt.setInt(1, student.getSid());
			insertStmt.setString(2, student.getName());
			insertStmt.setString(3, student.getEmail());
			insertStmt.setString(4, student.getDegree());
			insertStmt.setInt(5, student.getGraduation());
			insertStmt.executeUpdate();
    	    
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
