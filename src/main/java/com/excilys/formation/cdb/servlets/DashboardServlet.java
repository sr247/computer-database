package com.excilys.formation.cdb.servlets;

import java.io.IOException;

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
// Methode isBlank = test: (isNull && isEmpty && hasNoCharacters)

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		WebServiceComputer webServComp = WebServiceComputer.INSTANCE;
		String parameter = null;
		
		int numComputers = 0;
		try {
			numComputers = webServComp.getNumberOf();
			request.setAttribute("numComputers", numComputers);
		} catch (ServiceManagerException e) {
			// TODO Auto-generated catch block
			logger.debug("DashBoardServletException: {}", e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		}
		
		if((parameter = request.getParameter("stride")) != null) {				
			try {
				Pages.setStride(Integer.valueOf(parameter));
			} catch (NumberFormatException | ServiceManagerException e) {
				// TODO Auto-generated catch block
				logger.debug("DashBoardServletException: {}", e.getMessage(), e);
			}			
		} else {
			logger.debug("DashBoardServletException: No stride provided");
		}
		
		PagesComputer<ComputerDTO> pageComputers = new PagesComputer<>();
		try {
			if((parameter = (String) request.getAttribute("way")) != null){
				if(parameter.equals("next")) {
					pageComputers.next();
				}else if(parameter.equals("prev")) {
					pageComputers.preview();
				}
			}else {
				logger.debug("DashBoardServletException: No page provided");
			}
			int offset = Pages.getPAGE_OFFSET();
			int limit = Pages.getPAGE_LIMIT();
			pageComputers = 
					new PagesComputer<ComputerDTO>(ComputerMapperDTO.map(webServComp.getList(offset, limit)));
			
			pageComputers.update();
			int maxNbPages = pageComputers.getNumberOfPages();
			int current = Pages.getCURRENT_PAGE().get();
			int mid = current < 3  ? 3 : (current >= 3 && current <= (maxNbPages-2) ? current : maxNbPages-2);
			
//			System.out.println(String.format("Page: %s {%s, %s}", current, Pages.getPAGE_LIMIT(), Pages.getPAGE_OFFSET()));
//			System.out.println(String.format("mid: {%s} maxPages:{%s}", mid, maxNbPages));
			logger.debug(String.format("Page: %s {%s, %s}", current, Pages.getPAGE_LIMIT(), Pages.getPAGE_OFFSET()));
			logger.debug(String.format("mid: {%s} maxPages:{%s}", mid, maxNbPages));
			request.setAttribute("pageComputers", pageComputers);
			request.setAttribute("current", current);
			request.setAttribute("mid", mid);
			
		} catch (ServiceManagerException e) {
			// TODO Auto-generated catch block
			logger.debug("DashBoardServletException: {}", e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
