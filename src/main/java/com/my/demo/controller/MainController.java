package com.my.demo.controller;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class MainController {
	
	String sSiteCode = "BS177";
	String sSitePassword = "fOUAoZn8N0Ev";
	
	
	@RequestMapping(value = { "/index", "/" })
	public String index(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model,  Locale locale) throws JsonMappingException, JsonProcessingException {
		
		return "index";
	}
	
	
}