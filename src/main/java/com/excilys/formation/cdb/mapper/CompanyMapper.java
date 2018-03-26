package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.model.Company;

/**
 * Classe Mapper pour mapper les entreprises.
 * @author sr247
 */
public enum CompanyMapper {

    INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyMapper.class);
    
    /** Construit un Mapper pour les entreprises.
	 * @
	 */
	CompanyMapper() {
	}

	/** 
	 * @param res Un ResultSet contenant les résultats des requêtes DAOs.
	 * @return Un object Optional<Company> qui est vide si res est vide. 
	 * @throws InstanceNotInDatabaseException 
	 */
    public static Optional<Company> map(ResultSet res) throws InstanceNotInDatabaseException {
		Company cpn = null;		
		try {
            if (res != null) {
				int id = res.getInt("caId");
				String name = res.getString("caName");
				cpn = new Company(id, name);
			}
		} catch (SQLException e) {
			logger.error("Error in database: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("Error in database:", e);
		}
		return Optional.ofNullable(cpn);
	}
}
