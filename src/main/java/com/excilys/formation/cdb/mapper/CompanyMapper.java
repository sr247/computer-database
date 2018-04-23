package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.model.Company;

/**
 * Classe Mapper pour mapper les entreprises.
 * @author sr247
 */
@Component
public class CompanyMapper {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyMapper.class);

	/** 
	 * @param res Un ResultSet contenant les résultats des requêtes DAOs.
	 * @return Un object Optional<Company> qui est vide si res est vide. 
	 * @throws InstanceNotInDatabaseException 
	 */
    public Optional<Company> map(ResultSet res) throws SQLException {
		Company cpn = null;		
		try {
            if (res != null) {
				int id = res.getInt("caId");
				String name = res.getString("caName");
				cpn = new Company(id, name);
			}
		} catch (SQLException e) {
			logger.error("CompanyMapperException: {}", e.getMessage(), e);
			throw e;
		}
		return Optional.ofNullable(cpn);
	}
}
