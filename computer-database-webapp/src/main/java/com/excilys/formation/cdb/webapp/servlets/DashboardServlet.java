package com.excilys.formation.cdb.webapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cdb.binding.ComputerMapperDTO;
import com.excilys.formation.cdb.core.ComputerDTO;
import com.excilys.formation.cdb.service.pages.Pages;
import com.excilys.formation.cdb.service.pages.PagesComputer;
import com.excilys.formation.cdb.service.ServiceComputer;

/**
 * Servlet implementation class AcceuilServlet
 */
@WebServlet("/dashboard.bak")
@Controller
public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = 7292200966426509099L;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DashboardServlet.class);
	private static final String DASHBOARD_EXCEPTION = "DashBoardServletException: {}";

	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired 
	private ComputerMapperDTO computerMDTO;
	@Autowired
	private PagesComputer<ComputerDTO> pagesComputer;
	
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parameter = null;
		int numComputers = 0;
		try {
			response.getWriter().append("Served at: ").append(request.getContextPath());
//			numComputers = serviceComputer.getNumberOf();
			request.setAttribute("numComputers", numComputers);
		} catch (Exception e) {
			logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
		}
		
		if((parameter = request.getParameter("stride")) != null) {
			try {
				pagesComputer.setStride(Integer.valueOf(parameter));
			} catch (NumberFormatException e) {
				logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
			}			
		} else {
			String s = "No stride provided";
			logger.debug(DASHBOARD_EXCEPTION, s);
		}
		
		try {
			if((parameter = request.getParameter("page")) != null){
				int page = Integer.parseInt(parameter);
				pagesComputer.goTo(page);
			}else {
				String s = "No page provided";
				logger.debug(DASHBOARD_EXCEPTION, s);
			}
			int offset = pagesComputer.getOffset();
			int limit = pagesComputer.getStride();
//			pagesComputer.setContent(computerMDTO.map(serviceComputer.getList(offset, limit)));
			
			int numberOfPages = pagesComputer.getNumberOfPages();
			int currentPage = pagesComputer.getCurrentPage();
			int focusPage = currentPage < 3 ? 3 : (currentPage >= 3 && currentPage <= (numberOfPages-2) ? currentPage : numberOfPages-2);
			
			logger.info("Page: {} {{}, {}}", currentPage, pagesComputer.getStride(), pagesComputer.getOffset());
			logger.info("mid:{} maxPages:{}", focusPage, numberOfPages);
			
			request.setAttribute("pagesComputer", pagesComputer);
			request.setAttribute("current", currentPage);
			request.setAttribute("focusPage", focusPage);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
		}
	}	
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Optional<String[]> ids = Optional.ofNullable(request.getParameterValues("selection"));
			if(ids.isPresent()) {
				List<String> idListString = new ArrayList<>();
				for(String s : ids.get()) {
					for(String ss : s.split(",")){
						idListString.add(ss);
					}
				}
				List<Integer> idListInteger =  idListString.stream()
						.map(Integer::parseInt)
						.collect(Collectors.toList());
				logger.info("Liste des checkboxes: {}", idListInteger.toString());
//				serviceComputer.deleteComputerFromIDList(idListInteger);
			} else {
				String s = "No checkbox checked.";
				logger.info(DASHBOARD_EXCEPTION, s);
			}
			doGet(request, response);
		} catch (Exception e) {
			logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
		}
	}

}
