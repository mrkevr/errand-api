package dev.mrkevr.errandapi.errand.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.errandapi.errand.dto.ErrandResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/demo/errands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErrandDemoController {
	
	ErrandService errandService;
	
	@GetMapping("/{id}")
	ModelAndView displayUser(@PathVariable String id) {

		ErrandResponse errand = errandService.getById(id);
		ModelAndView mav = new ModelAndView("errand-details");
		mav.addObject("errand", errand);

		return mav;
	}
}
