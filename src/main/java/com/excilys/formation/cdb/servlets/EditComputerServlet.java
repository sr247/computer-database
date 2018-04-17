package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import com.excilys.formation.cdb.mapper.CompanyMapperDTO;
import com.excilys.formation.cdb.mapper.ComputerMapperDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.CompanyDTO;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.ComputerDTO;
import com.excilys.formation.cdb.service.ServiceCompany;
import com.excilys.formation.cdb.service.ServiceComputer;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/editComputer")
@Controller
public class EditComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EditComputerServlet.class);
	
	// Attribut de servlet visible par tous les instances ( as static )
	@Autowired
	private ServiceCompany serviceCompany;
	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private ComputerMapperDTO computerMDTO;	
	@Autowired
	private CompanyMapperDTO companyMDTO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String parameter = null;
		if((parameter = request.getParameter("id")) != null){
			ComputerDTO computer = null;
			try {
				int id = Integer.parseInt(parameter);
				computer = computerMDTO.map(serviceComputer.getComputer(id));
				request.setAttribute("idComputer", id);
				request.setAttribute("computer", computer);
				
			} catch (Exception e) {
				logger.error("EditComputerServletException: {}", e.getMessage(), e);
			}
		}
        try {
            List<CompanyDTO> companies = companyMDTO.map(serviceCompany.getAllList());
            request.setAttribute("companies", companies);
        } catch (Exception e) {
            logger.debug("EditComputerServletException: {}", e.getMessage(), e);
        }
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Optional<String> attribute = null;
		Computer computer = null;
		String parameter = null;
		if((parameter = request.getParameter("id")) != null){
			try {
				int id = Integer.parseInt(parameter);
				LocalDate introduced = null;
				LocalDate discontinued = null;
				computer = serviceComputer.getComputer(id);
			
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
					Company company = serviceCompany.getCompany(attribute.get());
					computer.setCompany(company);
				}
				
				serviceComputer.updateComputer(computer);
				logger.debug("Updated: {}", computer);
				
			} catch (NumberFormatException | ServiceManagerException e) {
				logger.error("Fail to Update: {}", computer);
				logger.error("EditComputerServletException: {}", e.getMessage(), e);
			}
		}
		doGet(request, response);
	}

}
