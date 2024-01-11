package dev.mrkevr.errandapi.config;

import java.util.ArrayList;
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

import com.github.javafaker.Faker;

import dev.mrkevr.errandapi.common.service.ServerService;
import dev.mrkevr.errandapi.common.util.EnumRandomizer;
import dev.mrkevr.errandapi.errand.api.Errand;
import dev.mrkevr.errandapi.errand.api.ErrandCategory;
import dev.mrkevr.errandapi.errand.api.ErrandLocation;
import dev.mrkevr.errandapi.errand.api.ErrandRepository;
import dev.mrkevr.errandapi.errand.api.ErrandStatus;
import dev.mrkevr.errandapi.testimonial.api.TestimonialRepository;
import dev.mrkevr.errandapi.testimonial.api.TestimonialService;
import dev.mrkevr.errandapi.testimonial.dto.TestimonialCreationRequest;
import dev.mrkevr.errandapi.user.api.User;
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
	TestimonialService testimonialService;
	UserRepository userRepository;
	ServerService serverService;
	ErrandRepository errandRepository;
	
	/*
	 * Mock Users
	 */
	@Bean
	@Order(1)
	DataSourceInitializer dataSourceInitializer_User(@Qualifier("dataSource") final DataSource dataSource) {
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
	@Order(2)
	CommandLineRunner commandLineRunner_Testimonials() {
		return args -> {
			List<User> users = userRepository.findAll();
			List<String> usernames = users.stream().map(user -> user.getUsername()).collect(Collectors.toList());
			users.stream().forEach(user -> {
				List<TestimonialCreationRequest> testimonialCreationRequests = this.getTestimonialCreationRequestForUser(user.getId(), usernames, 3);
				testimonialCreationRequests.forEach(testimonial -> {
					testimonialService.add(testimonial);
				});
			});
		};
	}
	
	/*
	 * Mock Errands
	 */
	@Bean
	@Order(3)
	CommandLineRunner commandLineRunner_Errands() {
		return args -> {
			List<String> usernames = userRepository.findAll().stream().map(u -> u.getUsername()).collect(Collectors.toList());
			List<Errand> fakeErrands = this.getFakeErrands(usernames);
			errandRepository.saveAll(fakeErrands);
		};
	}
	
	/**
	 * 
	 * @param userId user id of the one getting the testimonial
	 * @param testifierUsernames List of usernames that will be randomly picked
	 * @param count Number of testimonials request to generate
	 * @return List of generated testimonial requests
	 */
	private List<TestimonialCreationRequest> getTestimonialCreationRequestForUser(String userId, List<String> testifierUsernames, int count){
		List<TestimonialCreationRequest> list = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			String testifierUsername = testifierUsernames.get(random.nextInt(testifierUsernames.size()));
			int rating = ThreadLocalRandom.current().nextInt(1, 6);
			Faker faker = Faker.instance();
			String content = faker.lorem().sentence(20);
			
			TestimonialCreationRequest testimonialCreationRequest = TestimonialCreationRequest.builder()
				.userId(userId)
				.testifierUsername(testifierUsername)
				.rating(rating)
				.content(content)
				.build();
			
			list.add(testimonialCreationRequest);
		}
		return list;
	}
	
	@Order(value = Ordered.LOWEST_PRECEDENCE)
	private List<Errand> getFakeErrands(List<String> userNames) {
		List<Errand> fakeErrands = new ArrayList<>();
		userNames.forEach(e -> {
			Faker faker = Faker.instance();
			String employerUsername = e;
			String agentUsername = null;
			String title = faker.job().title();
			String description = faker.lorem().sentence(20);
			String imageUrl = "https://source.unsplash.com/random/300x300";
			ErrandLocation errandLocation = new ErrandLocation();
			double compensation = faker.random().nextDouble();
			errandLocation.setAddress(faker.address().fullAddress());
			errandLocation.setLatitude(Double.valueOf(faker.address().latitude()));
			errandLocation.setLongitude(Double.valueOf(faker.address().longitude()));		
			ErrandCategory errandCategory = EnumRandomizer.getRandomEnumValue(ErrandCategory.class);
			ErrandStatus errandStatus = EnumRandomizer.getRandomEnumValue(ErrandStatus.class);

			Errand randomErrand = Errand.builder()
				.employerUsername(employerUsername)
				.agentUsername(null)
				.title(title)
				.description(description)
				.imageUrl(imageUrl)
				.errandLocation(errandLocation)
				.compensation(compensation)
				.errandCategory(errandCategory)
				.errandStatus(errandStatus)
				.agentUsername(agentUsername)
				.build();
			fakeErrands.add(randomErrand);
		});
		return fakeErrands;
	}
}
