package apirest.reto.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Value("${app.openapi.server.local:http://localhost:8095/api}")
	private String localServerUrl;

	@Value("${app.openapi.server.public:}")
	private String publicServerUrl;

	@Bean
	public OpenAPI customOpenAPI() {
		List<Server> servers = new ArrayList<>();
		servers.add(new Server().url(localServerUrl).description("Local"));

		if (publicServerUrl != null && !publicServerUrl.isBlank()) {
			servers.add(new Server().url(publicServerUrl).description("Produccion"));
		}

		return new OpenAPI()
				.info(new Info()
						.title("API Cine - Reto Transversal")
						.version("v1"))
				.servers(servers);
	}
}
