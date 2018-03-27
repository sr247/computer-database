package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.List;

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
		
		int numComputer = 0;
		try {
			numComputer = webServComp.getNumberOf();
			request.setAttribute("numComputer", numComputer);		
		} catch (ServiceManagerException e) {
			// TODO Auto-generated catch block
			logger.debug("DashBoardServletInfo: {}", e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		}
		
		String stride = null;
		if((stride = request.getParameter("stride")) != null) {				
			Pages.setStride(Integer.valueOf(stride));			
		}else {
			logger.debug("DashBoardServletInfo: No stride provided");
		}
		
		PagesComputer<ComputerDTO> computerPage = null;
		try {
			computerPage = new PagesComputer<ComputerDTO>(ComputerMapperDTO.map(webServComp.getList(Pages.getFrom(), Pages.getTo())));
			request.setAttribute("computers", computerPage.getContent());
			request.setAttribute("pageFrom", Pages.getFrom());
			request.setAttribute("pageTo", Pages.getTo());
		} catch (ServiceManagerException e) {
			// TODO Auto-generated catch block
			logger.debug("DashBoardServletInfo: {}", e.getMessage(), e);
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
