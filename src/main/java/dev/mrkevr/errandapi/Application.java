package dev.mrkevr.errandapi;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.mrkevr.errandapi.testimonial.api.Testimonial;
import dev.mrkevr.errandapi.testimonial.api.TestimonialRepository;
import dev.mrkevr.errandapi.user.api.UserRepository;

@SpringBootApplication
public class Application {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	TestimonialRepository testimonialRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
//	@Bean
	DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
	    ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
	    resourceDatabasePopulator.addScript(new ClassPathResource("/sql/mock_users_1000.sql"));
	    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
	    dataSourceInitializer.setDataSource(dataSource);
	    dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
	    return dataSourceInitializer;
	}
	
//	@Bean
	@Order(value = Ordered.LOWEST_PRECEDENCE)
	CommandLineRunner runner(){
		
	
		List<String> usernames = userRepository.findAll().stream().map(u -> u.getUsername()).collect(Collectors.toList());
		Random random = new Random();
		
		return args -> userRepository.findAll().forEach(e -> {
			
		
			String testifier1 = usernames.get(random.nextInt(usernames.size()));
			String testifier2 = usernames.get(random.nextInt(usernames.size()));
			String testifier3 = usernames.get(random.nextInt(usernames.size()));
			int rating1 = ThreadLocalRandom.current().nextInt(1, 6);
			int rating2 = ThreadLocalRandom.current().nextInt(1, 6);
			int rating3 = ThreadLocalRandom.current().nextInt(1, 6);
			
			Testimonial t1 = Testimonial.builder()
				.userId(e.getId())
				.testifierUsername(testifier1)
				.rating(rating1)
				.content(this.lorem1())
				.build();
			
			Testimonial t2 = Testimonial.builder()
					.userId(e.getId())
					.testifierUsername(testifier2)
					.rating(rating2)
					.content(this.lorem2())
					.build();
			
			Testimonial t3 = Testimonial.builder()
					.userId(e.getId())
					.testifierUsername(testifier3)
					.rating(rating3)
					.content(this.lorem3())
					.build();
			
			testimonialRepository.saveAll(List.of(t1, t2, t3));
		});
	}
	
	private String lorem1() {
		return "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
				+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ";
	}
	
	private String lorem2() {
		return "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
				+ "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	}
	
	private String lorem3() {
		return "Lorem ipsum dolor sit, cididunt ut labore et dolore magna aliqua. "
				+ "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	}
}
