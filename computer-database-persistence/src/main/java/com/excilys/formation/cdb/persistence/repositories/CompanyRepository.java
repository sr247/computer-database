package com.excilys.formation.cdb.persistence.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.excilys.formation.cdb.core.entity.CompanyEntity;

public interface CompanyRepository extends CrudRepository<CompanyEntity, Long>  {

	@Override
	public Optional<CompanyEntity> findById(Long arg0);

	@Override
	public long count();

	@Override
	public boolean existsById(Long arg0);
	
	@Override
	public Iterable<CompanyEntity> findAllById(Iterable<Long> arg0);

	@Override
	public Iterable<CompanyEntity> findAll();
	
}
