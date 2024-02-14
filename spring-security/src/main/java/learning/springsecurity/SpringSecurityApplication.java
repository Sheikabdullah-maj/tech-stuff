package learning.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
		// Use the below section to encode your password , ultimately the username and this encoded password should be persisted/derived from DB or any other source.
		/*PasswordEncoder encode = new BCryptPasswordEncoder();
		String encodedPassword = encode.encode("dummy");
		System.out.println(encodedPassword);*/
	}

}
