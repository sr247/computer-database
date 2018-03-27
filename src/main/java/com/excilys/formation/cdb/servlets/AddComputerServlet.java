package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		WebServiceCompany webServCompany = WebServiceCompany.INSTANCE;
		try {
			List<CompanyDTO> companies = CompanyMapperDTO.map(webServCompany.getAllList());
			request.setAttribute("companies", companies);
		} catch (ServiceManagerException e) {
			// TODO Auto-generated catch block
			logger.debug("AddComputerError: {}", e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		}
		
		String choice = null;
		if((choice = request.getParameter("submit")) != null) {
			boolean submit = Boolean.valueOf(choice);
			if(submit) {
				try {
					WebServiceComputer webServComputer = WebServiceComputer.INSTANCE;
					String computerName = (String) request.getParameter("computerName");
					
					Optional<String> intro = Optional.ofNullable(request.getParameter("introduced"));
					Optional<String> discon = Optional.ofNullable(request.getParameter("discontinued"));
					LocalDate introduced = null;
					LocalDate discontinued = null;
					
					if(intro.isPresent()) {
						System.out.println("intro: " + intro.get());
						if(intro.get() != "") {
							introduced = LocalDate.parse(intro.get(), fmt);							
						}
					}
					
					if(discon.isPresent()) {
						System.out.println("discon: " + discon.get());
						if(discon.get() != "") {
							discontinued = LocalDate.parse(discon.get(), fmt);							
						}
					}
					
					String cpyFromSel = request.getParameter("companyId");
					System.out.println("cpyFromSel: " + cpyFromSel);
					Company company = webServCompany.getCompany(cpyFromSel);
					
					Computer cmp = new Computer(computerName, introduced, discontinued, company);
					webServComputer.createComputer(cmp);
				} catch (ServiceManagerException e) {
					// TODO Auto-generated catch block
					logger.debug("AddComputerError: {}", e.getMessage(), e);
					throw new ServletException(e.getMessage(), e);
				}
			}
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request, response);
	}
	
	protected CharSequence uselessPrint(CharSequence s, String arg) {
		System.out.println(arg+": "+s);
		return s;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
