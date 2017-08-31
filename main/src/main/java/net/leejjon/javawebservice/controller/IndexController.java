package net.leejjon.javawebservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Feels to boilerplate code to me that you have to define a controller to go to the start page....
 */

@Controller
@RequestMapping(value = "/")
public class IndexController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView entry() {
		return new ModelAndView("/index.jsp");
	}
}
