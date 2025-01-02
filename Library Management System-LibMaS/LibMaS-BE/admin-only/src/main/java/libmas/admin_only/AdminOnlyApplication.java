package libmas.admin_only;

import libmas.admin_only.config.FrontEndProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FrontEndProperties.class)
public class AdminOnlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminOnlyApplication.class, args);
	}

}
