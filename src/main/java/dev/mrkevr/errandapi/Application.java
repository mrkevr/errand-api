package dev.mrkevr.errandapi;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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
	CommandLineRunner runner(){
		return args -> userRepository.findAll().forEach(e -> {
			
			Testimonial t1 = Testimonial.builder()
				.userId(e.getId())
				.testifierId("USER12345681")
				.content(this.lorem1())
				.build();
			
			Testimonial t2 = Testimonial.builder()
					.userId(e.getId())
					.testifierId("USER12345756")
					.content(this.lorem2())
					.build();
			
			Testimonial t3 = Testimonial.builder()
					.userId(e.getId())
					.testifierId("USER12345756")
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
