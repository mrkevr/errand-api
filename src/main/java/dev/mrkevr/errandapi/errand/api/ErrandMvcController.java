package dev.mrkevr.errandapi.errand.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dev.mrkevr.errandapi.errand.dto.ErrandResponse;
import dev.mrkevr.errandapi.errand.dto.ErrandSearchModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/mvc/errands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErrandMvcController {

	ErrandService errandService;
	
	/*
	 * Return latest 500 errands
	 */
	@GetMapping
	ModelAndView errandList(
			@ModelAttribute("searchModel") 
			ErrandSearchModel searchModel) {
		
		List<ErrandResponse> errands = errandService.findLatestErrands(500);
		
		System.out.println(errands.size());
		
		ModelAndView mav = new ModelAndView("errand-list");
		mav.addObject("errands", errands);
		return mav;
	}
	
	@GetMapping("/search")
	ModelAndView searchErrand(@ModelAttribute("searchModel") ErrandSearchModel searchModel) {

		System.out.println(searchModel);

		List<ErrandResponse> errands = errandService.searchBySpecifications(
			searchModel.getKeyword(), 
			searchModel.getErrandCategories(), 
			searchModel.getDays());

		ModelAndView mav = new ModelAndView("errand-list");
		mav.addObject("errands", errands);
		return mav;
	}

	@GetMapping("/{id}")
	ModelAndView errandDetails(@PathVariable String id) {
		ErrandResponse errand = errandService.getById(id);
		ModelAndView mav = new ModelAndView("errand-details");
		mav.addObject("errand", errand);
		return mav;
	}
	
	
}
