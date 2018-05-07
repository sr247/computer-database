package com.excilys.formation.cdb.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.binding.CompanyMapperDTO;
import com.excilys.formation.cdb.binding.ComputerMapperDTO;
import com.excilys.formation.cdb.core.CompanyDTO;
import com.excilys.formation.cdb.core.ComputerDTO;
import com.excilys.formation.cdb.core.entity.CompanyEntity;
import com.excilys.formation.cdb.core.entity.ComputerEntity;
import com.excilys.formation.cdb.service.ServiceCompany;
import com.excilys.formation.cdb.service.ServiceComputer;
import com.excilys.formation.cdb.service.pages.PagesComputer;

@Controller
public class ComputerController {

	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerController.class);
	private static final int CENTER_VALUE = 2;

	private PagesComputer<ComputerDTO> pages;
	private ServiceComputer serviceComputer;
	private ServiceCompany serviceCompany;
	private ComputerMapperDTO computerMDTO;
	private CompanyMapperDTO companyMDTO;

	@Autowired
	public ComputerController(PagesComputer<ComputerDTO> pages, 
			ComputerMapperDTO computerMDTO,
			CompanyMapperDTO companyMDTO,
			ServiceComputer serviceComputer,
			ServiceCompany serviceCompany) {
		this.pages = pages;
		this.serviceComputer = serviceComputer;
		this.serviceCompany = serviceCompany;
		this.computerMDTO = computerMDTO;
		this.companyMDTO= companyMDTO;
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard(@RequestParam(value = "page", defaultValue = "0") int pageUrlParam,
			@RequestParam(value = "stride", defaultValue = "10") int strideUrlParam) {
		ModelAndView modelAndView = new ModelAndView();

		logger.info("PageUrl:{}", String.valueOf(pageUrlParam));
		logger.info("StrideUrl:{}", String.valueOf(strideUrlParam));		

		try {
			pages.setStride(strideUrlParam);
			pages.goTo(pageUrlParam);
			Page<ComputerEntity> page = serviceComputer.getList(pages.getCurrentPage(), pages.getStride());
			logger.info("Afficher: {}", page.getContent());
			Page<ComputerDTO> pageComputer = page.map((ComputerEntity c) -> computerMDTO.map(c));
			int numberOfPages = pages.getNumberOfPages();
			int currentPage = pages.getCurrentPage();
			int focus = currentPage < CENTER_VALUE ? CENTER_VALUE
					: ( currentPage >= CENTER_VALUE && currentPage <= (numberOfPages - 3) ? currentPage
							: numberOfPages - 3 );

			logger.info("{}; {}; {}; {}; {}", pages.getOffset(), pages.getStride(), numberOfPages, currentPage, focus);			
			modelAndView.addObject("ComputerDTO", new ComputerDTO());
			modelAndView.addObject("numberOfElements", pages.getNumberOfElements());
			modelAndView.addObject("numberOfPages", pages.getNumberOfPages());
			modelAndView.addObject("pageComputer", pageComputer.getContent());
			modelAndView.addObject("currentPage", pages.getCurrentPage());
			modelAndView.addObject("stride", pages.getStride());
			modelAndView.addObject("focus", focus);
		} catch (Exception e) {
			logger.error("DashboardComputerGet: {}", e.getMessage(), e);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:500");
		}
		return modelAndView;
	}

	@PostMapping("/dashboard")
	public ModelAndView postDelete(@RequestParam("selection") List<String> stringList) {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("DeleteForm: {}", stringList);
		try {
			List<Long> idList = stringList.stream().map(Long::valueOf).collect(Collectors.toList());
			serviceComputer.deleteComputerFromIDList(idList);	
			modelAndView.addObject("ComputerDTO", new ComputerDTO());
		} catch (Exception e) {
			logger.error("EditComputerPost: {}", e.getMessage(), e);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:500");
		}
		modelAndView.setViewName("redirect:dashboard");		
		return modelAndView;
	}

	@GetMapping("/addComputer")
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Page<CompanyEntity> page= serviceCompany.getAllList();
			Page<CompanyDTO> pageCompany = page.map((CompanyEntity c) -> companyMDTO.map(c));
			modelAndView.addObject("ComputerDTO", new ComputerDTO());
			modelAndView.addObject("companies", pageCompany.getContent());
		} catch (Exception e) {
			logger.error("AddComputerGet: {}", e.getMessage(), e);
			modelAndView.addObject("statusOperation", new ArrayList<Exception>().add(e));
			modelAndView.setViewName("redirect:500");
		}
		return modelAndView;
	}

	@PostMapping("/addComputer")
	public ModelAndView postAdd(@ModelAttribute("ComputerDTO") ComputerDTO computer) {
		ModelAndView modelAndView = new ModelAndView("addComputer");
		logger.info("Add: {}", computer);
		try {
			serviceComputer.createComputer(computer);			
		} catch (Exception e) {
			logger.error("AddComputerPost: {}", e.getMessage(), e);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:500");
		}
		return modelAndView;
	}

	@GetMapping("/editComputer")
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "1") long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			ComputerEntity computer = serviceComputer.getComputer(id);
			ComputerDTO computerDTO = computerMDTO.map(computer);
			
			Page<CompanyEntity> page= serviceCompany.getAllList();
			Page<CompanyDTO> pageCompany = page.map((CompanyEntity c) -> companyMDTO.map(c));
			modelAndView.addObject("ComputerDTO", new ComputerDTO());
			modelAndView.addObject("selectedComputer", computerDTO);
			modelAndView.addObject("companies", pageCompany.getContent());
		} catch (Exception e) {
			logger.error("EditComputerGet: {}", e.getMessage(), e);
			modelAndView.setViewName("redirect:500");
		}
		return modelAndView;
	}

	@PostMapping("/editComputer")
	public ModelAndView postEdit(@ModelAttribute("ComputerDTO") ComputerDTO computer) {
		ModelAndView modelAndView = new ModelAndView("editComputer");
		logger.info("Edit: {}", computer);
		try {
			serviceComputer.updateComputer(computer);
			modelAndView.addObject("ComputerDTO", new ComputerDTO());
		} catch (Exception e) {
			logger.error("EditComputerPost: {}", e.getMessage(), e);
			modelAndView.setViewName("redirect:500");
		}
		return modelAndView;
	}

	@GetMapping("/403")
	public ModelAndView error403() {
		return new ModelAndView();
	}

	@GetMapping("/404")
	public ModelAndView error404() {
		return new ModelAndView();
	}

	@GetMapping("/500")
	public ModelAndView error500() {
		return new ModelAndView();
	}

}