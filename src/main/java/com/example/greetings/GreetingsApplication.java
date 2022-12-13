package com.example.greetings;

import com.example.greetings.model.Greeting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
@ComponentScan({"com.example.greetings"})
public class GreetingsApplication {

//	private ArrayList<Greeting> greetings = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(GreetingsApplication.class, args);
	}



}

