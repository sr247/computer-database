package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.mapper.ComputerMapperDTO;
import com.excilys.formation.cdb.model.ComputerDTO;
import com.excilys.formation.cdb.pages.Pages;
import com.excilys.formation.cdb.pages.PagesComputer;
import com.excilys.formation.cdb.service.WebServiceComputer;

// StringUtils, lib interessant
// Methode isBlank = test: (isNull && isEmpty && hasNoCharacters)!

/**
 * Servlet implementation class AcceuilServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	
	private static final long serialVersionUID = 7292200966426509099L;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DashboardServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		WebServiceComputer webServComp = WebServiceComputer.INSTANCE;
		String parameter = null;
		int numComputers = 0;
		try {
			numComputers = webServComp.getNumberOf();
			request.setAttribute("numComputers", numComputers);
		} catch (ServiceManagerException e) {
			String s = String.format("DashBoardServletException: %s", e.getMessage());
			logger.debug(s, e);
		}
		
		if((parameter = request.getParameter("stride")) != null) {
			try {
				Pages.setStride(Integer.valueOf(parameter));
			} catch (NumberFormatException | ServiceManagerException e) {
				logger.debug("DashBoardServletException: {}", e.getMessage(), e);
			}			
		} else {
			String s = "DashBoardServletException: No stride provided";
			logger.debug(s);
		}
		
		PagesComputer<ComputerDTO> pageComputers = new PagesComputer<>();
		try {
			if((parameter = (String) request.getParameter("page")) != null){
				int page = Integer.valueOf(parameter);
				pageComputers.goTo(page);
			}else {
				String s = "DashBoardServletException: No page provided";
				logger.debug(s);
			}
			int offset = Pages.getPAGE_OFFSET();
			int limit = Pages.getPAGE_LIMIT();
			pageComputers = 
					new PagesComputer<ComputerDTO>
						(ComputerMapperDTO.map(webServComp.getList(offset, limit)));
			int maxNbPages = pageComputers.getNumberOfPages();
			int current = Pages.getCURRENT_PAGE().get();
			int mid = current < 3 ? 3 : (current >= 3 && current <= (maxNbPages-2) ? current : maxNbPages-2);
			
			logger.debug("Page: {} {{}, {}}", current, Pages.getPAGE_LIMIT(), Pages.getPAGE_OFFSET());
			logger.debug("mid:{} maxPages:{}", mid, maxNbPages);
			
			request.setAttribute("pageComputers", pageComputers);
			request.setAttribute("current", current);
			request.setAttribute("mid", mid);
			
		} catch (ServiceManagerException e) {
			logger.debug("DashBoardServletException: {}", e.getMessage(), e);
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Optional<String[]> names = Optional.ofNullable(request.getParameterValues("selection"));
			if(names.isPresent()) {
				List<String> list =  Arrays.asList(names.get());
				logger.debug(list.toString());				
			} else {
				logger.debug("DashBoardServletException: No checkbox checked.");
			}
		} catch (Exception e) {
			logger.debug("AddComputerServletException: {} - from:{}", e.getMessage(), e.getClass().getSimpleName(), e);
		}
		doGet(request, response);
	}

}
