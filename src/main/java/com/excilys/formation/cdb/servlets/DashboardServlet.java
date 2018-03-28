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
import com.excilys.formation.cdb.utils.Pair;

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
		
		int numComputers = 0;
		try {
			numComputers = webServComp.getNumberOf();
			request.setAttribute("numComputers", numComputers);
		} catch (ServiceManagerException e) {
			// TODO Auto-generated catch block
			logger.debug("DashBoardServletException: {}", e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		}
		
		String parameter = null;
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
			if((parameter = request.getParameter("way")) != null){
				System.out.println(String.format("Page Suivante: {}", (PagesComputer.getNumberOfPages().toString())));
				if(parameter.equals("next")) {
					pageComputers.next();
				}else if(parameter.equals("prev")) {
					pageComputers.preview();
				}
			}else {
				logger.debug("DashBoardServletException: No page provided");
			}			
			PagesComputer.update();
			Pair<Integer, Integer> range = Pages.getPageRange();
			pageComputers = 
					new PagesComputer<ComputerDTO>(ComputerMapperDTO.map(webServComp.getList(range.getFst(), range.getSnd())));
			request.setAttribute("pages", PagesComputer.getNumberOfPages());
			request.setAttribute("pageComputers", pageComputers);
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
