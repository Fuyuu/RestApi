package edu.hongik.apihw.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hongik.apihw.config.DatabaseConfig;
import edu.hongik.apihw.entity.Students;
import edu.hongik.apihw.repository.StudentRepository;

@Service
public class StudentService {
	
	public String getDegreeByName(String name) {
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
			throw new IllegalStateException("No such student");
		} else if (degrees.size() > 1) {
			throw new IllegalStateException("There are multiple students with the same name. Please provide an email address instead.");
		}
		
		return degrees.get(0);
	}
	
	
	public String getEmailByName(String email) {
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
			throw new IllegalStateException("No such student");
		} else if (emails.size() > 1) {
			throw new IllegalStateException("There are multiple students with the same name. Please contact the administrator by phone.");
		}
		
		return emails.get(0);
	}
	
	

	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	
	public int getStudentCountByDegree(String degree) {
		List<String> validDegrees = List.of("PhD", "Masters", "Undergraduate");
		if (!validDegrees.contains(degree)) {
			throw new IllegalArgumentException("Invalid degree.");
		}
		
		return studentRepository.countByDegree(degree);
	}
	
	public void addStudent(Students student) {
		String sql = "INSERT INTO students (sid, name, email, degree, graduation) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConfig.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, student.getSid()); // Students 객체에서 ID 가져오기
    	    pstmt.setString(2, student.getName());
    	    pstmt.setString(3, student.getEmail());
    	    pstmt.setString(4, student.getDegree());
    	    pstmt.setInt(5, student.getGraduation());
    	    pstmt.executeUpdate();
    	    
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
