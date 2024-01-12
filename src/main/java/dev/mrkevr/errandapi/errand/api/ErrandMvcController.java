package dev.mrkevr.errandapi.errand.api;

import java.util.List;

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
@RequestMapping("/mvc/errands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErrandMvcController {

	ErrandService errandService;

	@GetMapping
	ModelAndView displayErrands() {

		List<ErrandResponse> errands = errandService.getAll();
		ModelAndView mav = new ModelAndView("errand-list");
		mav.addObject("errands", errands);

		return mav;
	}

	@GetMapping("/{id}")
	ModelAndView displayUser(@PathVariable String id) {
		ErrandResponse errand = errandService.getById(id);
		ModelAndView mav = new ModelAndView("errand-details");
		mav.addObject("errand", errand);
		return mav;
	}
}
