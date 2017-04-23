package org.pawkrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class NotificationsApplication {

	public static String PORT;

	@Autowired
	private ArduinoService arduinoService;

	public static void main(String[] args) {
		PORT = args[0];

		SpringApplication.run(NotificationsApplication.class, args);
	}

	@PreDestroy
	public void clean(){
		arduinoService.close();
	}
}
