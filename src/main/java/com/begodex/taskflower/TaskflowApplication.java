package com.begodex.taskflower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

// Mude o application.properties para iniciar o banco de dados. Você deve criar um banco
// de dados chamado de taskflower e passar suas credênciais.

// http://localhost:8080/swagger-ui/index.html
// vá em http://localhost:8080/auth/register
// depois em http://localhost:8080/auth/login para acessar as rotas.

public class TaskflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskflowApplication.class, args);
	}

}
