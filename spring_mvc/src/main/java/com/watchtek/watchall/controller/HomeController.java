package com.watchtek.watchall.controller;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController
{

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/home.do", method = RequestMethod.GET)
    public String home(Locale locale, Model model)
    {
        logger.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "views/home";
    }
    
    @RequestMapping(value = "/test.do", method = RequestMethod.GET)
    @ResponseBody
    public List<HashMap<String, Object>> test(Model model)
    {
    	List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    	
    	HashMap<String, Object> data1 = new HashMap<String, Object>();
    	data1.put("name", "도란");
    	data1.put("age", "25");

    	HashMap<String, Object> data2 = new HashMap<String, Object>();
    	data2.put("name", "홍길동");
    	data2.put("age", "23");

    	list.add(data1);
    	list.add(data2);

    	return list;



    	
      
    }

}
