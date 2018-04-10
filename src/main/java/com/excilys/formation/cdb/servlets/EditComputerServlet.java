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
import com.excilys.formation.cdb.mapper.ComputerMapperDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.CompanyDTO;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.ComputerDTO;
import com.excilys.formation.cdb.service.WebServiceCompany;
import com.excilys.formation.cdb.service.WebServiceComputer;

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		WebServiceComputer webServComputer = WebServiceComputer.INSTANCE;
		WebServiceCompany webServCompany = WebServiceCompany.INSTANCE;

		String parameter = null;
		if((parameter = request.getParameter("id")) != null){
			ComputerDTO computer = null;
			try {
				int id = Integer.valueOf(parameter);
				computer = ComputerMapperDTO.map(webServComputer.getComputer(id));
				request.setAttribute("idComputer", id);
				request.setAttribute("computer", computer);
				logger.debug("Intro: {} \nDiscon: {}",computer.getIntroduced().toString(), computer.getDiscontinued().toString());
				
			} catch (ServiceManagerException e) {
				logger.debug("EditComputerServletException: {}", e.getMessage(), e);
			}
		}
        try {
            List<CompanyDTO> companies = CompanyMapperDTO.map(webServCompany.getAllList());
            request.setAttribute("companies", companies);
        } catch (ServiceManagerException e) {
            logger.debug("EditComputerServletException: {}", e.getMessage(), e);
        }
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebServiceComputer webServComputer = WebServiceComputer.INSTANCE;
		WebServiceCompany webServCompany = WebServiceCompany.INSTANCE;
		
		Optional<String> attribute = null;
		Computer computer = null;
		String parameter = null;
		if((parameter = request.getParameter("id")) != null){
			try {
				int id = Integer.valueOf(parameter);
				LocalDate introduced = null;
				LocalDate discontinued = null;
				computer = webServComputer.getComputer(id);
			
				if((attribute = Optional.ofNullable((String) request.getParameter("computerName"))).isPresent() ) {
					computer.setName(attribute.get());
				}
				
				if((attribute = Optional.ofNullable((String) request.getParameter("introducted"))).isPresent() ) {
					if(attribute.get() != "") {
						introduced = LocalDate.parse(attribute.get());							
					}
					computer.setIntroduced(introduced);
				}	
					
				if((attribute = Optional.ofNullable((String) request.getParameter("discontinued"))).isPresent() ) {
					if(attribute.get() != "") {
						discontinued = LocalDate.parse(attribute.get());							
					}
					computer.setDiscontinued(discontinued);
				}	
				
				if((attribute = Optional.ofNullable((String) request.getParameter("companyId"))).isPresent() ) {
					Company company = webServCompany.getCompany(attribute.get());
					computer.setCompany(company);
				}
				
				webServComputer.updateComputer(computer);
				System.out.println(String.format("Updated: %s", computer));
				
			} catch (NumberFormatException | ServiceManagerException e) {
				logger.error("EditComputerServletException: {}", e.getMessage(), e);
				System.out.println(String.format("Fail to Update: %s\nException: %s", computer, e.getMessage()));
			}
		}
		doGet(request, response);
	}

}
