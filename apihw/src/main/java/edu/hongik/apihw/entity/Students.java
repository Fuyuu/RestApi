package edu.hongik.apihw.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Students {
	private static int idCounter = 1;
	private static final Object lock = new Object();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sid;
	private String name;
	private String email;
	private String degree;
	private int grad;
	
	public Students(String name, String email, String degree, int grad) {
		synchronized(lock) {
			this.sid = idCounter++;
		}
		this.name = name;
        this.email = email;
        this.degree = degree;
        this.grad = grad;
	}
	
	@Override
	public String toString() {
		return "Student{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", grad=" + grad +
                ", degree='" + degree + '\'' +
                '}';
	}
}
