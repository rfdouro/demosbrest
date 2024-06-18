package br.org.rfdouro.demorest.control;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author romulo
 */
@RestController
public class HomeController {

 @Value("${server.servlet.context-path}")
 private String contextPath;

 /**
  * esse método maracado com requestmapping sem definição de verbo HTTP é capaz
  * de receber qualquer
  * requisição (GET, POST etc.) e retorna uma mensagem
  *
  * @return
  */
 @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
 public ResponseEntity<String> index() {
  return ResponseEntity.ok()
    .body("API de tarefas rodando - <a href='" + contextPath + "/swagger-ui.html'>Documentação</a>");
 }
}
