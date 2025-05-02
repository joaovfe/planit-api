package com.planit.api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) {

		System.out.println("JDBC URL: " + System.getenv("DATABASE_URL"));
		System.out.println("JDBC URL: " + System.getenv("DATABASE_USERNAME"));
		System.out.println("JDBC URL: " + System.getenv("DATABASE_PASSWORD"));
		System.out.println("JDBC URL: " + System.getenv("ENV"));


		if (isLocalEnvironment()) {
			Dotenv dotenv = Dotenv.configure()
					.ignoreIfMissing()
					.load();

			dotenv.entries().forEach(entry ->
					System.setProperty(entry.getKey(), entry.getValue())
			);
		}

		SpringApplication.run(ApiApplication.class, args);
	}

	private static boolean isLocalEnvironment() {
		String env = System.getenv("ENV");
		return env == null || env.equalsIgnoreCase("local");
	}
}
