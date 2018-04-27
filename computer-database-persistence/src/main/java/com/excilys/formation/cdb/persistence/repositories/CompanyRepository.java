package com.excilys.formation.cdb.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.core.entity.CompanyEntity;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyEntity, Long>, PagingAndSortingRepository<CompanyEntity, Long>  {

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

	@Override
	Page<CompanyEntity> findAll(Pageable arg0);

	@Override
	Iterable<CompanyEntity> findAll(Sort arg0);

	@Override
	void delete(CompanyEntity arg0);

	@Override
	void deleteAll();

	@Override
	void deleteAll(Iterable<? extends CompanyEntity> arg0);

	@Override
	void deleteById(Long arg0);

	@Override
	<S extends CompanyEntity> S save(S arg0);

	@Override
	<S extends CompanyEntity> Iterable<S> saveAll(Iterable<S> arg0);
	
	
	
}
