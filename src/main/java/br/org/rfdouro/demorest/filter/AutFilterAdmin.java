package br.org.rfdouro.demorest.filter;

import br.org.rfdouro.demorest.util.JWTUtil;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author romulo
 */
@Component
public class AutFilterAdmin implements Filter {

 //injeta automaticamente a classe de utilidades do JWT
 @Autowired
 private JWTUtil jwtUtil;

 /**
  * esse método recebe todas as requisições e direciona corretamente a resposta
  * 
  * @param servletRequest
  * @param servletResponse
  * @param filterChain
  * @throws IOException
  * @throws ServletException 
  */
 @Override
 public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
  HttpServletRequest request = (HttpServletRequest) servletRequest;
  HttpServletResponse response = (HttpServletResponse) servletResponse;

  //String caminho = ((HttpServletRequest) request).getRequestURI();

  String token = jwtUtil.recuperaTokenRequisicao(request);
  String nomeUsuario = jwtUtil.getUsuarioNoToken(token);
  
  boolean permissaoAdmin = jwtUtil.verificaAccesso(token, "ADMIN");

  if (nomeUsuario != null && permissaoAdmin) {
   filterChain.doFilter(request, response);
  } else {
   //caso não tenha usuário autenticado, não retorna nada
   throw new ServletException("Não Autorizado");
  }

 }

}
