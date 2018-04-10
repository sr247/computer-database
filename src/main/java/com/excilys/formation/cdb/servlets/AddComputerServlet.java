package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.mapper.CompanyMapperDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.CompanyDTO;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.WebServiceCompany;
import com.excilys.formation.cdb.service.WebServiceComputer;

/**
 * Servlet implementation class addComputerServlet
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 2332590973522058116L;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddComputerServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		WebServiceCompany webServCompany = WebServiceCompany.INSTANCE;
		try {
			List<CompanyDTO> companies = CompanyMapperDTO.map(webServCompany.getAllList());
			request.setAttribute("companies", companies);
		} catch (ServiceManagerException e) {
			logger.debug("AddComputerServletException: {}", e.getMessage(), e);
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebServiceCompany webServCompany = WebServiceCompany.INSTANCE;
		WebServiceComputer webServComputer = WebServiceComputer.INSTANCE;
		try {
			String computerName = (String) request.getParameter("computerName");
			
			Optional<String> intro = Optional.ofNullable(request.getParameter("introduced"));
			Optional<String> discon = Optional.ofNullable(request.getParameter("discontinued"));
			
			LocalDate introduced = null;
			LocalDate discontinued = null;
			
			if(intro.isPresent()) {
				if(intro.get() != "") {
					introduced = LocalDate.parse(intro.get());							
				}
			}	
			if(discon.isPresent()) {
				if(discon.get() != "") {
					discontinued = LocalDate.parse(discon.get());							
				}
			}
			
			String cpyFromSel = request.getParameter("companyId");
			Company company = webServCompany.getCompany(cpyFromSel);
			Computer cmp = new Computer(computerName, introduced, discontinued, company);
			webServComputer.createComputer(cmp);
			logger.debug(String.format("Created: %s", cmp));
		} catch (ServiceManagerException e) {
			logger.debug("AddComputerServletException: {}", e.getMessage(), e);
		}
		doGet(request, response);
	}

}
