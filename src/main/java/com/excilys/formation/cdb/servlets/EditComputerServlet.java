package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.mapper.CompanyMapperDTO;
import com.excilys.formation.cdb.mapper.ComputerMapperDTO;
import com.excilys.formation.cdb.model.CompanyDTO;
import com.excilys.formation.cdb.model.ComputerDTO;
import com.excilys.formation.cdb.service.WebServiceCompany;
import com.excilys.formation.cdb.service.WebServiceComputer;

import ch.qos.logback.classic.Logger;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EditComputerServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
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
		WebServiceCompany webServCompany = WebServiceCompany.INSTANCE;


		String parameter = null;
		if((parameter = (String) request.getParameter("id")) != null){
			ComputerDTO computer = null;
			try {
				int id = Integer.valueOf(parameter);
				computer = ComputerMapperDTO.map(webServComp.getComputer(id));
				request.setAttribute("idComputer", id);
				request.setAttribute("computer", computer);
			} catch (ServiceManagerException e) {
				// TODO Auto-generated catch block
				logger.debug("EditComputerServletException: {}", e.getMessage(), e);
			}
		}
        try {
            List<CompanyDTO> companies = CompanyMapperDTO.map(webServCompany.getAllList());
            request.setAttribute("companies", companies);
        } catch (ServiceManagerException e) {
            // TODO Auto-generated catch block
            logger.debug("AddComputerError: {}", e.getMessage(), e);
            // throw new ServletException(e.getMessage(), e);
        }
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
