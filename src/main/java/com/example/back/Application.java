package com.example.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

@SpringBootApplication
public class Application {
	// private static final Logger logger =
	// LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logApplicationStartup() {
		System.out.println(ansi().fg(GREEN).a("=> API Listening : port 3001").reset());
		System.out.println(ansi().fg(GREEN).a("=> DB PostgreSQL : port 5432").reset());
	}

}
