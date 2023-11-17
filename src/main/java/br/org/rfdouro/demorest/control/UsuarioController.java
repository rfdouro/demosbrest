package br.org.rfdouro.demorest.control;

import br.org.rfdouro.demorest.model.Usuario;
import br.org.rfdouro.demorest.model.Usuario;
import br.org.rfdouro.demorest.model.UsuarioRepository;

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
@RequestMapping("/admin/usuario")
public class UsuarioController {

 /*
 insere automaticamente um repositório de dados para usaurios
  */
 @Autowired
 UsuarioRepository repository;

 /*
 método que retorna a listagem de usuarios ordenada por login
 atende no endpoint /usuario com verbo GET
  */
 @GetMapping({"", "/"})
 public List<Usuario> getUsuarios() {
  return repository.findAll(Sort.by("login"));
 }

 /*
 método que recebe uma usuario enviada na requisição e a insere no banco de dados
 retorta após inserir já com o ID
 atende no endpoint /usuario com verbo POST
 a anotação @RequestBody é importante pois indica que os dados da requisição 
 serão enviados no corpo da requisição (em JSON)
  */
 @PostMapping({"", "/"})
 public Usuario insere(@RequestBody Usuario usuario) {
  return repository.save(usuario);
 }

 /*
 método que recebe uma usuario enviado na requisição (com id preenchido)
 e a atualiza no banco de dados
 retorta a usuario atualizado
 caso não tenha id na requisição retorna null
 atende no endpoint /usuario com verbo PUT
 a anotação @RequestBody é importante pois indica que os dados da requisição 
 serão enviados no corpo da requisição (em JSON)
  */
 @PutMapping({"", "/"})
 public Usuario atualiza(@RequestBody Usuario usuario) {
  if (usuario.getId() != null) {
   return repository.save(usuario);
  }
  return null;
 }

 /*
 método que recebe um id de usuario enviado na requisição 
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
