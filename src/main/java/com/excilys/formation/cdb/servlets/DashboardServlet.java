package com.excilys.formation.cdb.servlets;

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

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.mapper.ComputerMapperDTO;
import com.excilys.formation.cdb.model.ComputerDTO;
import com.excilys.formation.cdb.pages.Pages;
import com.excilys.formation.cdb.pages.PagesComputer;
import com.excilys.formation.cdb.service.ServiceComputer;

/**
 * Servlet implementation class AcceuilServlet
 */
@WebServlet("/dashboard")
@Controller
public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = 7292200966426509099L;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DashboardServlet.class);
	private static final String DASHBOARD_EXCEPTION = "DashBoardServletException: {}";

	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private ComputerMapperDTO computerMDTO;
	
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String parameter = null;
		int numComputers = 0;
		try {
			numComputers = serviceComputer.getNumberOf();
			request.setAttribute("numComputers", numComputers);
		} catch (ServiceManagerException e) {
			logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
		}
		
		if((parameter = request.getParameter("stride")) != null) {
			try {
				Pages.setStride(Integer.valueOf(parameter));
			} catch (NumberFormatException | ServiceManagerException e) {
				logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
			}			
		} else {
			String s = "No stride provided";
			logger.debug(DASHBOARD_EXCEPTION, s);
		}
		
		PagesComputer<ComputerDTO> pageComputers = new PagesComputer<>();
		try {
			if((parameter = request.getParameter("page")) != null){
				int page = Integer.parseInt(parameter);
				pageComputers.goTo(page);
			}else {
				String s = "No page provided";
				logger.debug(DASHBOARD_EXCEPTION, s);
			}
			int offset = Pages.getPAGE_OFFSET();
			int limit = Pages.getPAGE_LIMIT();
			pageComputers = 
					new PagesComputer<ComputerDTO> (computerMDTO.map(serviceComputer.getList(offset, limit)));
			int maxNbPages = pageComputers.getNumberOfPages();
			int current = Pages.getCURRENT_PAGE().get();
			int mid = current < 3 ? 3 : (current >= 3 && current <= (maxNbPages-2) ? current : maxNbPages-2);
			
			logger.info("Page: {} {{}, {}}", current, Pages.getPAGE_LIMIT(), Pages.getPAGE_OFFSET());
			logger.info("mid:{} maxPages:{}", mid, maxNbPages);
			
			request.setAttribute("pageComputers", pageComputers);
			request.setAttribute("current", current);
			request.setAttribute("mid", mid);
			
		} catch (Exception e) {
			logger.error(DASHBOARD_EXCEPTION, e.getMessage(), e);
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
				serviceComputer.deleteComputerFromIDList(idListInteger);
			} else {
				String s = "No checkbox checked.";
				logger.info(DASHBOARD_EXCEPTION, s);
			}
		} catch (Exception e) {
			logger.debug(DASHBOARD_EXCEPTION, e.getMessage(), e);
		}
		doGet(request, response);
	}

}
