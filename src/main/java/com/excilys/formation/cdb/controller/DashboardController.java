package com.excilys.formation.cdb.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ModelAndView dashboard(@RequestParam Map<String, String> params,
    		@RequestParam(value = "page", defaultValue = "1") int pageUrlParam,
    		@RequestParam(value = "stride", defaultValue = "10") int strideUrlParam) throws ServiceManagerException {
    	ModelAndView modelAndView = new ModelAndView();

    	logger.info(String.valueOf(pageUrlParam));
    	logger.info(String.valueOf(strideUrlParam));	

    	int offset = Pages.getOffset();
    	int stride = Pages.getStride();
    	
		try {
			pagesComputer.setContent(computerMDTO.map(service.getList(offset, strideUrlParam)));
			Pages.setStride(pageUrlParam);
			pagesComputer.setCurrentPage(pageUrlParam);
		} catch (Exception e) {
			logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
		}

		int numberOfPages = pagesComputer.getNumberOfPages();
		int currentPage = pagesComputer.getCurrentPage();
		int focus = currentPage < 3 ? 3 : (currentPage >= 3 && currentPage <= (numberOfPages - 2) ? currentPage : numberOfPages - 2);
		
    	logger.info("{}; {}; {}; {}; {}", offset, stride, numberOfPages, currentPage, focus);
    	modelAndView.addObject("pagesComputer", pagesComputer);
    	modelAndView.addObject("numberOfPages", numberOfPages);
    	modelAndView.addObject("current", currentPage);
    	modelAndView.addObject("focus", focus);
        return modelAndView;
    }
    
    
   @PostMapping("/dashboard")
   public @ResponseBody ResponseEntity<String> postDelete(){
	   
	   return new ResponseEntity<>(HttpStatus.OK);
   }
}