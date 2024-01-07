package dev.mrkevr.errandapi.config;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import dev.mrkevr.errandapi.common.service.ServerService;
import dev.mrkevr.errandapi.testimonial.api.Testimonial;
import dev.mrkevr.errandapi.testimonial.api.TestimonialRepository;
import dev.mrkevr.errandapi.user.api.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/*
 * Mock data initialization in the dev environment
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DevProfileConfig {

	TestimonialRepository testimonialRepository;
	UserRepository userRepository;
	ServerService serverService;
	
	/*
	 * Mock Users
	 */
	@Bean
	DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
	    ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
	    resourceDatabasePopulator.addScript(new ClassPathResource("/sql/mock_users_1000.sql"));
	    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
	    dataSourceInitializer.setDataSource(dataSource);
	    dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
	    return dataSourceInitializer;
	}
	
	/*
	 * Mock Testimonials
	 */
	@Bean
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
				.content(this.getLoremIpsum())
				.build();
			
			Testimonial t2 = Testimonial.builder()
					.userId(e.getId())
					.testifierUsername(testifier2)
					.rating(rating2)
					.content(this.getLoremIpsum())
					.build();
			
			Testimonial t3 = Testimonial.builder()
					.userId(e.getId())
					.testifierUsername(testifier3)
					.rating(rating3)
					.content(this.getLoremIpsum())
					.build();
			
			testimonialRepository.saveAll(List.of(t1, t2, t3));
		});
	}
	
	private String getLoremIpsum() {
		return this.getLoremIpsumList().get(new Random().nextInt(this.getLoremIpsumList().size()));
	}
	
	private List<String> getLoremIpsumList(){
		return List.of(
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur consequat.",
			"Vivamus erat nunc, vestibulum eu bibendum eu, rhoncus tempor arcu. Proin ut dictum velit. Integer.",
			"Fusce lobortis efficitur fermentum. Sed sagittis volutpat enim, eget placerat nibh ultricies id. Proin mauris.",
			"In at sem tristique, laoreet diam id, accumsan tortor. Sed.",
			"Proin eros dui, viverra nec convallis mollis, blandit vitae purus. Aliquam non.");
	}
}
