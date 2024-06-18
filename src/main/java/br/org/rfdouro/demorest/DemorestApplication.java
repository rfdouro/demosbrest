package br.org.rfdouro.demorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

//@ServletComponentScan
@SpringBootApplication
public class DemorestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemorestApplication.class, args);
	}

	/*
	 * objeto gerenciado pelo Spring que adiciona informações à documentação
	 */
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("Auth JWT"))
				.components(new Components()
						.addSecuritySchemes("Auth JWT",
								new SecurityScheme()
										.name("Auth JWT")
										.type(SecurityScheme.Type.HTTP)
										.scheme("Bearer")
										.bearerFormat("JWT")))
				.info(new Info()
						.title("Servidor de tarefas")
						.version("1.0.0")
						.contact(new Contact().email("rfdouro@gmail.com").name("Rômulo Douro"))
						.description("<h1>Exemplo de REST API para tarefas</h1><p>Código disponível no "
						+ "<a href='https://github.com/rfdouro/demosbrest' target='_blank'>Github</a></p>"
						+ "<p>Teste o login usando os seguintes <b>login - senha</b>:</p>"
						+ "<p><ul><li>admin - 1234</li><li>usuario1 - 1234</li><li>usuario2 - 1234</li></ul></p>"));
	}

	/*
		* Filtro para liberação das rotas - trata CROSSORIGIN
	 */
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		// não exige credenciais
		config.setAllowCredentials(false);
		// permite acesso de qualquer origem
		config.addAllowedOrigin("*");
		// permite acesso com qualquer header http
		config.addAllowedHeader("*");
		// essas configurações são para permitir métodos específicos
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		// aplica esse filtro em todos os endpoints da API
		source.registerCorsConfiguration("/**", config);
		// registra o filtro na aplicação
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		// indica ordem de execução desse filtro (no caso será o primeiro a ser
		// executado sempre em todas as requisições)
		bean.setOrder(0);
		return bean;
	}

}
