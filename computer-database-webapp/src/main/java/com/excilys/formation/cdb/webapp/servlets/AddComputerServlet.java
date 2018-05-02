package com.excilys.formation.cdb.webapp.servlets;

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

import com.excilys.formation.cdb.binding.CompanyMapperDTO;
import com.excilys.formation.cdb.core.Company;
import com.excilys.formation.cdb.core.CompanyDTO;
import com.excilys.formation.cdb.core.Computer;
import com.excilys.formation.cdb.service.ServiceCompany;
import com.excilys.formation.cdb.service.ServiceComputer;

/**
 * Servlet implementation class addComputerServlet
 */
@WebServlet("/addComputer.bak")
@Controller
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 2332590973522058116L;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddComputerServlet.class);
      
	@Autowired
	private ServiceCompany serviceCompany;
	@Autowired
	private ServiceComputer serviceComputer;
	@Autowired
	private CompanyMapperDTO companyMDTO;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.getWriter().append("Served at: ").append(request.getContextPath());
//			List<CompanyDTO> companies = companyMDTO.map(serviceCompany.getAllList());
//			request.setAttribute("companies", companies);
			this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
		} catch (Exception e) {
			logger.debug("AddComputerServletException: {}", e.getMessage(), e);
		}
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String computerName = (String) request.getParameter("computerName");
			Optional<String> intro = Optional.ofNullable(request.getParameter("introduced"));
			Optional<String> discon = Optional.ofNullable(request.getParameter("discontinued"));
			LocalDate introduced = null;
			LocalDate discontinued = null;
			
			if(intro.isPresent() && intro.get() != "") {
				introduced = LocalDate.parse(intro.get());							
			}	
			if(discon.isPresent() && discon.get() != "") {
				discontinued = LocalDate.parse(discon.get());							
			}
			
			String cpyFromSel = request.getParameter("companyId");
//			Company company = serviceCompany.getCompany(cpyFromSel);
//			Computer cmp = new Computer(computerName, introduced, discontinued, company);
//			serviceComputer.createComputer(cmp);
			doGet(request, response);
		} catch (Exception e) {
			logger.debug("AddComputerServletException: {}", e.getMessage(), e);
		}
	}

}
