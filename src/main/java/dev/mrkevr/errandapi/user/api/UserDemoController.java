package dev.mrkevr.errandapi.user.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.errandapi.testimonial.api.TestimonialService;
import dev.mrkevr.errandapi.testimonial.dto.TestimonialResponse;
import dev.mrkevr.errandapi.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/demo/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDemoController {

	UserService userService;
	TestimonialService testimonialService;

	@GetMapping
	ModelAndView displayUsers(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "50") int size,
			@RequestParam(required = false) String query) {

		List<UserResponse> userResponses = null;
		if (ObjectUtils.isEmpty(query) || query == null) {
			userResponses = userService.getAll(page, size);
		} else {
			userResponses = userService.getAllWithQuery(page, size, query);
		}

		ModelAndView mav = new ModelAndView("users-demo");
		mav.addObject("users", userResponses);

		return mav;
	}
	
	@GetMapping("/{username}")
	ModelAndView displayUser(@PathVariable String username) {

		UserResponse user = userService.getByUsername(username);
		List<TestimonialResponse> testimonials = testimonialService.getAllByUserId(user.getId());
		
		ModelAndView mav = new ModelAndView("user-profile");
		mav.addObject("user", user);
		mav.addObject("testimonials", testimonials);
		return mav;
	}

}
