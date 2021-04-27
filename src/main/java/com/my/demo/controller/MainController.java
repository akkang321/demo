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
	public String index(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model) throws JsonMappingException, JsonProcessingException {
		
		return "index";
	}
	
	@RequestMapping(value = { "/sucess"})
	public String sucess(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model) throws JsonMappingException, JsonProcessingException {
		
		return "sucess";
	}
	
	@RequestMapping(value = { "/fail"})
	public String fail(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model) throws JsonMappingException, JsonProcessingException {
		
		return "fail";
	}
	
	@RequestMapping(value = { "/checkplus_main"})
	public String checkplus_main(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model) throws JsonMappingException, JsonProcessingException {
		
		return "checkplus_main";
	}
	
	@RequestMapping(value = { "/checkplus_fail"})
	public String checkplus_fail(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model) throws JsonMappingException, JsonProcessingException {
		
		return "checkplus_fail";
	}
	
	@RequestMapping(value = { "/checkplus_success"})
	public String checkplus_success(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model) throws JsonMappingException, JsonProcessingException {
		
		return "checkplus_success";
	}
	
	
	
	
	
}