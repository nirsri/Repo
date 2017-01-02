package com.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FOR TRIAL PURPOSES-CAN BE IGNORED
 * This class is not related to the exercise
 * @author NS
 *
 */
@Controller
public class MvcController {

	@RequestMapping("/greeting")
	public @ResponseBody String test(){
		return "Hello";
	}


}
