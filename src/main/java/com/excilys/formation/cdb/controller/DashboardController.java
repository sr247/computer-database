package com.excilys.formation.cdb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.mapper.ComputerMapperDTO;
import com.excilys.formation.cdb.model.ComputerDTO;
import com.excilys.formation.cdb.pages.Pages;
import com.excilys.formation.cdb.pages.PagesComputer;
import com.excilys.formation.cdb.service.ServiceComputer;

 
@Controller
@RequestMapping("/")
public class DashboardController {
	
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DashboardController.class);
	private static final String DASHBOARD_EXCEPTION = "DashBoardControllerException: {}";
	
	private PagesComputer<ComputerDTO> pagesComputer;
	private ServiceComputer service;
	private ComputerMapperDTO computerMDTO;
	
	public DashboardController(PagesComputer<ComputerDTO> pagesComputer, ServiceComputer service, ComputerMapperDTO computerMDTO) {
		this.pagesComputer = pagesComputer;
		this.service = service;
		this.computerMDTO = computerMDTO;
	}
	
    @GetMapping("/dashboard")
    public ModelAndView dashboard(@RequestParam Map<String, String> params) {
    	ModelAndView modelAndView = new ModelAndView();
    	int offset = Pages.getPAGE_OFFSET();
		int limit = Pages.getPAGE_LIMIT();
		try {
			pagesComputer.setContent(computerMDTO.map(service.getList(offset, limit)));
		} catch (ServiceManagerException e) {
			logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
		}
    	logger.info(params.toString());
    	logger.info("{} {}\n{}", offset, limit, pagesComputer.getContent());
    	modelAndView.addObject("computers", pagesComputer.getContent());
        return modelAndView;
    }
}