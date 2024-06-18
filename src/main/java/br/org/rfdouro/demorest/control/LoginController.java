package br.org.rfdouro.demorest.control;

import br.org.rfdouro.demorest.model.Usuario;
import br.org.rfdouro.demorest.model.UsuarioRepository;
import br.org.rfdouro.demorest.util.JWTUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author romulo
 */
@RestController
public class LoginController {

 //injeta automaticamente a classe de utilidades do JWT
 @Autowired
 JWTUtil jwtUtil;
 @Autowired
 UsuarioRepository usuarioRepository;

 /**
  * recebe um usuario e senha via POST valida se for: 1 - usuario = usuario 2 - senha = 1234
  *
  * @param usuario
  * @param senha
  * @return
  */
 @PostMapping("/login")
 public String logar(String usuario, String senha) {
  /*if (usuario != null && !usuario.isEmpty() && senha != null && !senha.isEmpty()) {
   if (usuario.equals("usuario") && senha.equals("1234")) {
   String token = jwtUtil.geraTokenUsuario(usuario);
   return token;
   }else{
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USUÁRIO OU SENHA INVÁLIDOS");
   }
  } else {
   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CREDENCIAIS INVÁLIDAS");
  }*/

  if (usuario != null && !usuario.isEmpty() && senha != null && !senha.isEmpty()) {
   Usuario u = usuarioRepository.findByLogin(usuario);
   if (u != null && u.getPassword().equals(senha)) {
    Map<String, Object> claims = new HashMap<>();
    //adiciona o LOGIN no token
    claims.put("LOGIN", u.getLogin());
    //adiciona o campo de permissões no token
    claims.put("PERMISSOES", u.getPermissoes());
    String token = jwtUtil.geraTokenUsuario(usuario, claims);
    return token;
   } else {
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USUÁRIO OU SENHA INVÁLIDOS");
   }
  } else {
   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CREDENCIAIS INVÁLIDAS");
  }
 }
}
