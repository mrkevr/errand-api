package dev.mrkevr.errandapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.mrkevr.errandapi.user.api.User;
import dev.mrkevr.errandapi.user.api.UserRepository;

@SpringBootApplication
public class Application {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository userRepository) {
//		
//	}
//	
//	
//	private List<User> getDummyUsers(){
//		
//		User user1 = User.builder()
//			.username("amy_santiago")
//			.password(passwordEncoder.encode("password"))
//			.aboutMe(this.lorem())
//			.avatar("C:/Users/User/Desktop/TEST_FOLDER/image_dir/amy_santiago.jpg")
//			.errandsWorked(5)
//			.phone("094594589749")
//			.email("amysantiago@b99.com")
//			.rating(3)
//			.build();
//		
//		return List.of(user1);
//	}
//	
//	private String lorem() {
//		return "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
//				+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
//				+ "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
//				+ "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
//	}
}
