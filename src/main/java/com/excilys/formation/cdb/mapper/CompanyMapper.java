package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;

/**
 * Classe Mapper pour mapper les entreprises.
 * @author sr247
 */
public enum CompanyMapper {

    INSTANCE;
    
    /** Construit un Mapper pour les entreprises.
	 * @
	 */
	CompanyMapper() {
	}

	/** 
	 * @param res Un ResultSet contenant les résultats des requêtes DAOs.
	 * @return Un object Optional<Company> qui est vide si res est vide. 
	 */
    public static Optional<Company> map(ResultSet res) {
		Company cpn = null;		
		try {
            if (!res.equals(null)) {
				int id = res.getInt("ID");
				String name = res.getString("NAME");
				cpn = new Company(id, name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(cpn);
	}
}
