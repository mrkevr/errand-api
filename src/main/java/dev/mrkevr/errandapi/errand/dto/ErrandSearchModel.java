package dev.mrkevr.errandapi.errand.dto;

import java.util.List;

import dev.mrkevr.errandapi.errand.api.ErrandCategory;
import lombok.Data;

@Data
public class ErrandSearchModel {

	private String keyword;
	private List<ErrandCategory> errandCategories;
	private Integer days;

}
