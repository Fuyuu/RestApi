package edu.hongik.apihw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.hongik.apihw.entity.Students;
import edu.hongik.apihw.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/degree")
	public ResponseEntity<String> getDegreeByName(@RequestParam String name) {
		// 특정 이름을 가진 학생의 학위 유형 질의
		try {
			String degree = studentService.getDegreeByName(name);
			return ResponseEntity.ok(name + " : " + degree);
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/email")
	public ResponseEntity<String> getEmailByName(@RequestParam String name) {
		// 특정 이름을 가진 학생의 이메일 주소 질의
		try {
			String email = studentService.getEmailByName(name);
			return ResponseEntity.ok(name + " : " + email);
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
		
	@GetMapping("/stat")
	public ResponseEntity<String> getStudentCountByDegree(@RequestParam String degree) {
		// 각 학위 별 학생의 수 반환
		try {
			int count = studentService.getStudentCountByDegree(degree);
			return ResponseEntity.ok("Number of " + degree + "'s student : " + count);
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/register")
	public ResponseEntity<String> registerStudent(@RequestBody Students student) {
		try {
			// 신규 학생 등록
			studentService.registerStudent(student);
			return ResponseEntity.ok("Registraion successful.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
}
