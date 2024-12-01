package edu.hongik.apihw.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
// Lombok의 Getter와 Setter 자동 생성 기능을 이용
@Getter
@Setter
public class Students {
	// sid를 수동으로 증가
	private static int idCounter = 1;
	private static final Object lock = new Object();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sid;
	private String name;
	private String email;
	private String degree;
	private int graduation;
	
	public Students(String name, String email, String degree, int graduation) {
		synchronized(lock) {
			this.sid = idCounter++;
		}
		this.name = name;
        this.email = email;
        this.degree = degree;
        this.graduation = graduation;
	}

}
