package com.visualization.logserver;

import com.visualization.logserver.service.LogServiceLocal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class LogServerApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(LogServerApplication.class, args);
	}
}
