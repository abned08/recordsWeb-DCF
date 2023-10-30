package com.dcfB;

import com.dcfB.controller.receivingEnterController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class RecordsApplication {

	public static void main(String[] args) {
		new File(receivingEnterController.uploadDirectory).mkdir();
		SpringApplication.run(RecordsApplication.class, args);
	}

}
