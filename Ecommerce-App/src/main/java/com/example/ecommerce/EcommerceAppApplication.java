package com.example.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
//public class EcommerceAppApplication {
//
//	public static void main(String[] args) {
//
//		SpringApplication.run(EcommerceAppApplication.class, args);
//	}
//
//}




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 1800) // 30 minutes instead of 10 seconds
public class EcommerceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppApplication.class, args);

		System.out.println("\n========================================");
		System.out.println("🛒 Sasta Bazar Application Started!");
		System.out.println("========================================");
		System.out.println("📱 Application URL: http://localhost:8080");
		System.out.println("🏠 Home Page: http://localhost:8080/index.html");
		System.out.println("🔐 Login Page: http://localhost:8080/login.html");
		System.out.println("📝 Register Page: http://localhost:8080/register.html");
		System.out.println("⏰ Session Timeout: 30 minutes");
		System.out.println("========================================\n");
	}
}