package br.org.rfdouro.demorest.control;

import br.org.rfdouro.demorest.model.Tarefa;
import br.org.rfdouro.demorest.model.TarefaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author romulo
 */
@RestController
@RequestMapping("/tarefa")
public class TarefaController {

 /*
 insere automaticamente um repositório de dados para tarefas
  */
 @Autowired
 TarefaRepository repository;

 /*
 método que retorna a listagem de tarefas ordenada por descrição
 atende no endpoint /tarefa com verbo GET
  */
 @GetMapping({"", "/"})
 public List<Tarefa> getTarefas() {
  return repository.findAll(Sort.by("descricao"));
 }

/*
 método que retorna a listagem de tarefas ordenada por descrição
 fazendo uma pesquisa por parte da descrição que está salva no banco de dados
 atende no endpoint /tarefa/pesquisa com verbo GET
 e necessita de um parâmetro chamado 'descricao'
  */
 @GetMapping("/pesquisa")
 public List<Tarefa> getTarefas(String descricao) {
  return repository.procuraPorDescricao(descricao, Sort.by("descricao"));
 }

 /*
 método que recebe uma tarefa enviada na requisição e a insere no banco de dados
 retorta após inserir já com o ID
 atende no endpoint /tarefa com verbo POST
 a anotação @RequestBody é importante pois indica que os dados da requisição 
 serão enviados no corpo da requisição (em JSON)
  */
 @PostMapping({"", "/"})
 public Tarefa insere(@RequestBody Tarefa tarefa) {
  return repository.save(tarefa);
 }

 /*
 método que recebe uma tarefa enviada na requisição (com id preenchido)
 e a atualiza no banco de dados
 retorta a tarefa atualizada
 caso não tenha id na requisição retorna null
 atende no endpoint /tarefa com verbo PUT
 a anotação @RequestBody é importante pois indica que os dados da requisição 
 serão enviados no corpo da requisição (em JSON)
  */
 @PutMapping({"", "/"})
 public Tarefa atualiza(@RequestBody Tarefa tarefa) {
  if (tarefa.getId() != null) {
   return repository.save(tarefa);
  }
  return null;
 }

 /*
 método que recebe um id de tarefa enviada na requisição 
 caso tenha enviado o id, é excluída no banco de dados
 retorta uma mensagem
 o id é passado no path (caminho da url) por isso
 se usa o @PathVariable indicativo
  */
 @DeleteMapping("/{id}")
 public String atualiza(@PathVariable("id") Long id) {
  if (id != null) {
   repository.deleteById(id);
   return "Excluído";
  }
  return null;
 }

}
