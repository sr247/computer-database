package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.mapper.ComputerMapperDTO;
import com.excilys.formation.cdb.model.ComputerDTO;
import com.excilys.formation.cdb.pages.Pages;
import com.excilys.formation.cdb.pages.PagesComputer;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.service.WebServiceComputer;

/**
 * Servlet implementation class AcceuilServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
		int numComputer = webServComp.getNumberOf();
		request.setAttribute("numComputer", numComputer);		
		
		List<ComputerDTO> computers = null;
		try {
			
			String s = null;
			if((s = request.getParameter("stride")) != null) {				
				Pages.setStride(Integer.valueOf(s));			
			}else {
				logger.debug("ERROR !");
			}
			PagesComputer<ComputerDTO> computerPage = 
					new PagesComputer<ComputerDTO>(ComputerMapperDTO.map(webServComp.getList(Pages.getFrom(), Pages.getTo())));
			request.setAttribute("computers", computerPage.getContent());
		} catch (InstanceNotInDatabaseException e) {
			// TODO Auto-generated catch block
			logger.warn("[WARN] (Dashboard): {}", e.getMessage(), e);
		}
		request.setAttribute("pageFrom", Pages.getFrom());
		request.setAttribute("pageTo", Pages.getTo());
		
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
