package com.begodex.taskflower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// ESTE MODULO CONTEM APENAS A API E A FUNCIONALIDADE BÁSICA DO FRONTEND COM O KANBAN,
// POR QUESTOES DE METODOLOGIA DE DESENVOLVIMENTO PESSOAL ( ESTUDO, PROTOTIPO, VALIDACAO,
// TESTE, CODIGO) AS OUTRAS FUNCOES E TELAS FORAM ADIADAS. SERÃO IMPLEMENTADAS AO LONGO
// DO TEMPO.

// ACOMPANHE NO INSTA @begodex
// att, gabriel begodex 🐋


// Mude o application.properties para iniciar o banco de dados. Você deve criar um banco
// de dados chamado de taskflower e passar suas credênciais.

// DOCUMENTACAO -> http://localhost:8080/swagger-ui/index.html
// VA EM -> http://localhost:8080/auth/register
// DEPOIS EM -> http://localhost:8080/auth/login para receber o token.

// USE -> Bearer + seu_token no header Authentication
// Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6InN0cmluZyIsImV4cCI6MTc2MjU1Mjk5Nn0.iOJ42ko8MaiunryWMlNRnXajYLNM5nTcGlIRoyQh_t8

// 😉 APRESENTACAO, ENTENDA O PROJETO:
// https://www.figma.com/deck/Fdt9MktMgdNBBZdL9Qox8u/Taskflower?node-id=1-93&t=uGRUt6usaIiDX2Pu-1&scaling=min-zoom&content-scaling=fixed&page-id=0%3A1
public class TaskflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskflowApplication.class, args);
	}

}
