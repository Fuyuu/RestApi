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
	public ResponseEntity<?> getDegreeByName(@RequestParam String name) {
		try {
			String degree = studentService.getDegreeByName(name);
			return ResponseEntity.ok(name + " : " + degree);
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/email")
	public ResponseEntity<?> getEmailByName(@RequestParam String name) {
		try {
			String email = studentService.getEmailByName(name);
			return ResponseEntity.ok(name + " : " + email);
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
		
	@GetMapping("/stat")
	public ResponseEntity<?> getStudentCountByDegree(@RequestParam String degree) {
		try {
			int count = studentService.getStudentCountByDegree(degree);
			return ResponseEntity.ok("Number of " + degree + "'s student : " + count);
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> addStudent(@RequestBody Students student) {
		studentService.addStudent(student);
		return ResponseEntity.ok("Registraion successful.");
	}
}
