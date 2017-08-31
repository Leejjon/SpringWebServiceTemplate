package net.leejjon.javawebservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class IndexController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView entry() {
		return new ModelAndView("index");
	}
}
