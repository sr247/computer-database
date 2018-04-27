package com.excilys.formation.cdb.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.core.entity.ComputerEntity;

@Repository
public interface ComputerRepository extends PagingAndSortingRepository<ComputerEntity, Long>, CrudRepository<ComputerEntity, Long> {

	@Override
	long count();

	@Override
	Optional<ComputerEntity> findById(Long arg0);
	
	@Override
	Iterable<ComputerEntity> findAllById(Iterable<Long> arg0);
	
	@Override
	Page<ComputerEntity> findAll(Pageable pageable);

	@Override
	Iterable<ComputerEntity> findAll();
	
	@Override
	void deleteById(Long arg0);

	@Override
	void delete(ComputerEntity arg0);

	@Override
	void deleteAll(Iterable<? extends ComputerEntity> arg0);

	@Override
	boolean existsById(Long arg0);

	@Override
	<S extends ComputerEntity> S save(S arg0);

	@Override
	<S extends ComputerEntity> Iterable<S> saveAll(Iterable<S> arg0);

	@Override
	Iterable<ComputerEntity> findAll(Sort arg0);

}
